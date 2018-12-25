package com.example.yaswanth.newsapp;

public class NewsItems {
    private String authorName;
    private String secName;
    private String webTitle;
    private String webUrl;
    private String publishDate;

    public NewsItems(String webTitle, String authorName, String secName, String publishDate, String webUrl) {
        this.secName = secName;
        this.authorName = authorName;
        this.webTitle = webTitle;
        this.publishDate = publishDate;
        this.webUrl = webUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getSecName() {
        return secName;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getPublishDate() {
        return publishDate;
    }
}
