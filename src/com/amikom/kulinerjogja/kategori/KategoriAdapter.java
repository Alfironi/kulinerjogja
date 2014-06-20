
package com.amikom.kulinerjogja.kategori;

import com.amikom.kulinerjogja.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class KategoriAdapter extends BaseAdapter {
    Context mContext;
    List<KategoriModel> mItems;

    public KategoriAdapter(Context mContext, List<KategoriModel> mItems) {
        super();
        this.mContext = mContext;
        this.mItems = mItems;
    }

    private class ViewHolder {
        TextView txtNomor;
        TextView txtNama;
        RatingBar rating;
    }

    public void updateProduct(List<KategoriModel> ratingModels) {
        // TODO Auto-generated method stub
        this.mItems = ratingModels;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return mItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) mContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_kategori, null);
            holder = new ViewHolder();
            holder.txtNama = (TextView) convertView.findViewById(R.id.txt_list_kategori);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        KategoriModel model = (KategoriModel) getItem(position);
        holder.txtNama.setText(model.getmNama());
        return convertView;
    }

}
