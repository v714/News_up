package com.example.vineet.news;


public class Application {
    private String title ;
//    private String description;
private String link;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
public String getLink() {


    return link;
}

    public void setLink(String link) {
        this.link = link;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "" + getTitle() + "\n" +
             //   " Description: " + getDescription() +
                "\n" + " link " + getLink() + "\n" +
                "" + getDate();
    }
}
