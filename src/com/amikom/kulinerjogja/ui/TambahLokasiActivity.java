
package com.amikom.kulinerjogja.ui;

import com.amikom.kulinerjogja.R;
import com.amikom.kulinerjogja.utils.LogManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TambahLokasiActivity extends Activity implements OnClickListener {
    private Button mBtnSimpan, mBtnBatal;
    private TextView mNama, mAlamat, mDeskripsi, mTelp, mHarga, mJam, mAmbilFoto;
    private ImageView image;
    private String mLat, mLong;
    private ProgressBar mProgress;
    private ScrollView mScroll;
    private Bitmap bitmap;

    // number of images to select
    private static final int PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_lokasi_kuliner_layout);
        initView();
    }

    private void initView() {
        mBtnSimpan = (Button) findViewById(R.id.btn_simpan_tambah);
        mBtnBatal = (Button) findViewById(R.id.btn_batal_tambah);
        mNama = (TextView) findViewById(R.id.nama_tambah);
        mAlamat = (TextView) findViewById(R.id.alamat_tambah);
        mDeskripsi = (TextView) findViewById(R.id.desc_tambah);
        mTelp = (TextView) findViewById(R.id.telf_tambah);
        mHarga = (TextView) findViewById(R.id.harga_tambah);
        mJam = (TextView) findViewById(R.id.jam_tambah);
        mAmbilFoto = (TextView) findViewById(R.id.ambil_foto);
        image = (ImageView) findViewById(R.id.image_tambah);
        mAmbilFoto.setPaintFlags(mAmbilFoto.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mAmbilFoto.setOnClickListener(this);
        mBtnSimpan.setOnClickListener(this);
        mBtnBatal.setOnClickListener(this);

        mProgress = (ProgressBar) findViewById(R.id.progress_tambah);
        mScroll = (ScrollView) findViewById(R.id.scrol_tambah);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_simpan_tambah:
            	new ImageUploadTask().execute();
//                simpan();
                break;
            case R.id.btn_batal_tambah:
                batal();
                break;
            case R.id.ambil_foto:
            	selectImageFromGallery();
            	break;
            default:
                break;
        }
    }

    private void batal() {

        onBackPressed();
    }

    private void simpan() {
        if (mNama.getText().toString().equals("")) {
            Toast.makeText(this, "Silahkan inputkan nama", Toast.LENGTH_SHORT).show();
            mNama.requestFocus();
        } else if (mAlamat.getText().toString().equals("")) {
            Toast.makeText(this, "Silahkan inputkan alamat", Toast.LENGTH_SHORT).show();
            mAlamat.requestFocus();
        } else if (mDeskripsi.getText().toString().equals("")) {
            Toast.makeText(this, "Silahkan inputkan deskripsi", Toast.LENGTH_SHORT).show();
            mDeskripsi.requestFocus();
        } else if (mTelp.getText().toString().equals("")) {
            Toast.makeText(this, "Silahkan inputkan telf", Toast.LENGTH_SHORT).show();
            mTelp.requestFocus();
        } else if (mJam.getText().toString().equals("")) {
            Toast.makeText(this, "Silahkan inputkan jam buka", Toast.LENGTH_SHORT).show();
            mJam.requestFocus();
        } else if (mHarga.getText().toString().equals("")) {
            Toast.makeText(this, "Silahkan inputkan harga", Toast.LENGTH_SHORT).show();
            mHarga.requestFocus();
        } else {
            generateLatLong(mAlamat.getText().toString());
            String url = "http://jogjakuliner.topmodis.com/restoran/data";
            AsyncHttpClient client = new AsyncHttpClient();

            RequestParams params = new RequestParams();
            params.put("nama_restoran", mNama.getText().toString());
            params.put("alamat_restoran", mAlamat.getText().toString());
            params.put("deskripsi", mDeskripsi.getText().toString());
            params.put("telp", mTelp.getText().toString());
            params.put("jambuka", mJam.getText().toString());
            params.put("harga", mHarga.getText().toString());
            params.put("lat", mLat);
            params.put("longitude", mLong);
            params.put("foto", "");

            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    LogManager.print("onstart");
                    mProgress.setVisibility(View.VISIBLE);
                    mScroll.setVisibility(View.GONE);
                }

                @Override
                public void onFinish() {
                    LogManager.print("onfinish");
                    mProgress.setVisibility(View.GONE);
                    mScroll.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    LogManager.print("onsucces " + response);
                    Toast.makeText(getApplicationContext(), "Data berhasil ditambahkan",
                            Toast.LENGTH_LONG).show();
                    clearForm();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse,
                        Throwable e) {
                    Toast.makeText(TambahLokasiActivity.this,
                            "Gagal Menyimpan, masalah koneksi", Toast.LENGTH_LONG)
                            .show();
                }

            });
        }

    }

    private void clearForm() {
        mNama.setText("");
        mAlamat.setText("");
        mDeskripsi.setText("");
        mTelp.setText("");
        mJam.setText("");
        mHarga.setText("");
        image.setImageBitmap(null);
    }

    private void generateLatLong(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);

            if (address.size() == 0) {
                Toast.makeText(TambahLokasiActivity.this,
                        "Gagal Menyimpan, alamat tidak dikenali google map", Toast.LENGTH_LONG)
                        .show();
                mLat = "0";
                mLong = "0";
            } else {
                Address location = address.get(0);
                mLat = String.valueOf(location.getLatitude());
                mLong = String.valueOf(location.getLongitude());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Opens dialog picker, so the user can select image from the gallery. The
     * result is returned in the method <code>onActivityResult()</code>
     */
    public void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE);
    }

    /**
     * The class connects with server and uploads the photo
     */
    class ImageUploadTask extends AsyncTask<Void, Void, String> {
        private final String webAddressToPost = "http://jogjakuliner.topmodis.com/assets/img";

        // private ProgressDialog dialog;
        private final ProgressDialog dialog = new ProgressDialog(TambahLokasiActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Uploading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpPost httpPost = new HttpPost(webAddressToPost);

                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.JPEG, 100, bos);
                byte[] data = bos.toByteArray();
                String file = Base64.encodeBytes(data);
                entity.addPart("uploaded", new StringBody(file));

                entity.addPart("someOtherStringToSend", new StringBody("your string here"));

                httpPost.setEntity(entity);
                HttpResponse response = httpClient.execute(httpPost,
                        localContext);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                response.getEntity().getContent(), "UTF-8"));

                String sResponse = reader.readLine();
                return sResponse;
            } catch (Exception e) {
                // something went wrong. connection with the server error
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        	simpan();
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "file uploaded + "+result,
                    Toast.LENGTH_LONG).show();
        }

    }

    /**
     * The method decodes the image file to avoid out of memory issues. Sets the
     * selected image in to the ImageView.
     * 
     * @param filePath
     */
    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        image.setImageBitmap(bitmap);
    }

    /**
     * Retrives the result returned from selecting image, by invoking the method
     * <code>selectImageFromGallery()</code>
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {
                MediaStore.Images.Media.DATA
            };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            decodeFile(picturePath);

        }
    }
}
