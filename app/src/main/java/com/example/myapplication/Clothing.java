package com.example.myapplication;

public class Clothing {
    private String id;
    private String name; // Название одежды
    private String type; // Тип одежды (например, рубашка, куртка)

    public Clothing(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }
}
