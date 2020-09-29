package com.chops.app.model;

public class Mycart {

    public String PID;
    public String Title;
    public String Image;
    public String Desc;
    public String Net;
    public String Gross;
    public String Pieces;
    public int Contity;
    public double Price;
    public double TotalPrice;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getNet() {
        return Net;
    }

    public void setNet(String net) {
        Net = net;
    }

    public String getGross() {
        return Gross;
    }

    public void setGross(String gross) {
        Gross = gross;
    }

    public String getPieces() {
        return Pieces;
    }

    public void setPieces(String pieces) {
        Pieces = pieces;
    }

    public int getContity() {
        return Contity;
    }

    public void setContity(int contity) {
        Contity = contity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }
}
