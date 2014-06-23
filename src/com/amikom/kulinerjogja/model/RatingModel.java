
package com.amikom.kulinerjogja.model;



public class RatingModel {
    private String mNomor;
    private String mNama;
    private int mRating;

    public RatingModel(String mNomor, String mNama, int mRating) {
        super();
        this.mNomor = mNomor;
        this.mNama = mNama;
        this.mRating = mRating;
    }

   
    
    public String getmNomor() {
        return mNomor;
    }

    public void setmNomor(String mNomor) {
        this.mNomor = mNomor;
    }

    public String getmNama() {
        return mNama;
    }

    public void setmNama(String mNama) {
        this.mNama = mNama;
    }

    public int getmRating() {
        return mRating;
    }

    public void setmRating(int mRating) {
        this.mRating = mRating;
    }

}
