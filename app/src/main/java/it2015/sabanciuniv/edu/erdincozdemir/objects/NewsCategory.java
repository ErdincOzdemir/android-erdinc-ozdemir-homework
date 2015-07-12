package it2015.sabanciuniv.edu.erdincozdemir.objects;

/**
 * Created by Erdinc on 12.07.2015.
 */
public class NewsCategory {

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
}
