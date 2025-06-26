package com.example.alquran.models;

public class Surah {
    private int number;
    private String name;
    private String englishName;
    private String englishNameTranslation;
    private int numberOfAyahs;
    private String revelationType;

    public Surah(int number, String name, String englishName,
                 String englishNameTranslation, int numberOfAyahs, String revelationType) {
        this.number = number;
        this.name = name;
        this.englishName = englishName;
        this.englishNameTranslation = englishNameTranslation;
        this.numberOfAyahs = numberOfAyahs;
        this.revelationType = revelationType;
    }

    // Getters
    public int getNumber() { return number; }
    public String getName() { return name; }
    public String getEnglishName() { return englishName; }
    public String getEnglishNameTranslation() { return englishNameTranslation; }
    public int getNumberOfAyahs() { return numberOfAyahs; }
    public String getRevelationType() { return revelationType; }
}