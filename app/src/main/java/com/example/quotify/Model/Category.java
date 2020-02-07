package com.example.quotify.Model;

import java.util.ArrayList;

public class Category
{
    private String Cname;
    private String Img;

    public Category() {
    }

    public Category(String categoryname, String Img) {
        Cname = categoryname;
        this.Img = Img;
    }

    public String getCname() {
        return Cname;
    }

    public void setCategoryname(String cname) {
        Cname = cname;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }
}
