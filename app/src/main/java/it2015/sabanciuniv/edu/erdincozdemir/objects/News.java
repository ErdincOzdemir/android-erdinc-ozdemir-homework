package it2015.sabanciuniv.edu.erdincozdemir.objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class News implements Serializable {

    private int id;
    private String title;
    private String text;
    private Date date;
    private String image;
    private String categoryName;

    public News() {
    }

    public News(int id, String title, String text, Date date, String image, String categoryName) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.image = image;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
