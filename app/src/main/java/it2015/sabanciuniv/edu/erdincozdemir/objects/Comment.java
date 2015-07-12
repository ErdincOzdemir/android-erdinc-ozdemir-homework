package it2015.sabanciuniv.edu.erdincozdemir.objects;

import java.io.Serializable;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class Comment implements Serializable {

    private int id;
    private int newsId;
    private String text;
    private String name;
    private String lastName;

    public Comment() {
    }

    public Comment(int id, int newsId, String text, String name, String lastName) {
        this.id = id;
        this.newsId = newsId;
        this.text = text;
        this.name = name;
        this.lastName = lastName;
    }

    public Comment(int newsId, String text, String name, String lastName) {
        this.newsId = newsId;
        this.text = text;
        this.name = name;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
