package com.example.newsapk;

public class NewsModel {
    private String image;
    private String headline;
    private  String publisher;
    private  String date;
    private  String link;

    public NewsModel(String image, String headline, String publisher,String link,String date) {
        this.image = image;
        this.headline = headline;
        this.publisher = publisher;
        this.date=date;
        this.link=link;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public NewsModel() {

    }

    public String getImage() {

        return image;
    }

    public String getHeadline() {
        return headline;
    }

    public String getPublisher() {
        return publisher;
    }
    public String getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }
}