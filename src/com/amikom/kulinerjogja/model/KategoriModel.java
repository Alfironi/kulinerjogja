
package com.amikom.kulinerjogja.model;

public class KategoriModel {
    private String mNama;
    private String mId;

    public KategoriModel(String mNama, String mId) {
        super();
        this.mNama = mNama;
        this.mId = mId;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmNama() {
        return mNama;
    }

    public void setmNama(String mNama) {
        this.mNama = mNama;
    }

}
