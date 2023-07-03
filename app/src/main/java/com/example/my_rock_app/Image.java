package com.example.my_rock_app;

public class Image {

    private int imageId;
    private String imgName;

    public Image(int imageId,String imgName){
        imageId= imageId;
        imgName=imgName ;
    }

    public int getImageId() {
        return imageId;
    }

    public String getImgName() {
        return imgName;
    }
}
