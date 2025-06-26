package com.example.alquran.models;

public class Ayat {
    private int number;
    private String text;
    private String translation;

    public Ayat(int number, String text, String translation) {
        this.number = number;
        this.text = text;
        this.translation = translation;
    }

    // Getters
    public int getNumber() { return number; }
    public String getText() { return text; }
    public String getTranslation() { return translation; }
}