package com.example.alquran.api;

import com.example.alquran.models.AyatResponse;
import com.example.alquran.models.SurahResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("surah")
    Call<SurahResponse> getAllSurah();

    @GET("surah/{number}/editions/quran-uthmani,id.indonesian")
    Call<AyatResponse> getSurahWithTranslation(@Path("number") int surahNumber);
}