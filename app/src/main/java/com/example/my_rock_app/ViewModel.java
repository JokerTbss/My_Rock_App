package com.example.my_rock_app;

public class ViewModel {
    int img ;
    String name ;

    public ViewModel(int img , String name ){
        this.img=img;
        this.name= name;
    }

    public ViewModel(String name){
        this.name=name;
    }
}
