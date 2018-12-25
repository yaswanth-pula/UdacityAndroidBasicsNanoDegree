package com.example.yaswanth.tourguide;

public class Places {
    //variable for Place Image
    private int mPlaceImageId;
    //variable for Place Text(Name)
    private String mPlaceText;

    public Places(int mPlaceImageId, String mPlaceText) {
        this.mPlaceImageId = mPlaceImageId;
        this.mPlaceText = mPlaceText;
    }

    public int getmPlaceImageId() {

        return mPlaceImageId;
    }

    public void setmPlaceImageId(int mPlaceImageId) {
        this.mPlaceImageId = mPlaceImageId;
    }

    public String getmPlaceText() {
        return mPlaceText;
    }

    public void setmPlaceText(String mPlaceText) {
        this.mPlaceText = mPlaceText;
    }
}
