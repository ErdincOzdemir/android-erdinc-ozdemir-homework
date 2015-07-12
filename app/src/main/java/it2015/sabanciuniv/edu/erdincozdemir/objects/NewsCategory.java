package it2015.sabanciuniv.edu.erdincozdemir.objects;

import java.io.Serializable;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class NewsCategory implements Serializable {

    private int id;
    private String name;

    public NewsCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
