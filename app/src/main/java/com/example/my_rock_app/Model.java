package com.example.my_rock_app;

import android.graphics.Bitmap;

public class Model {
    public class ImageWithTitle {
        private Bitmap image;
        private String title;

        public ImageWithTitle(Bitmap image, String title) {
            this.image = image;
            this.title = title;
        }

        public Bitmap getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }
    }
}
