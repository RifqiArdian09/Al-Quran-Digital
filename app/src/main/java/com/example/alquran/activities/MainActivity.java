package com.example.alquran.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alquran.R;
import com.example.alquran.adapters.SurahAdapter;
import com.example.alquran.api.ApiClient;
import com.example.alquran.api.ApiService;
import com.example.alquran.models.Surah;
import com.example.alquran.models.SurahResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SurahAdapter surahAdapter;
    private List<Surah> surahList = new ArrayList<>();

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvSurah);
        progressBar = findViewById(R.id.progressBar);

        setupRecyclerView();
        getAllSurah();
    }

    private void setupRecyclerView() {
        surahAdapter = new SurahAdapter(this, surahList, surah -> {
            Intent intent = new Intent(MainActivity.this, SurahDetailActivity.class);
            intent.putExtra("surah_number", surah.getNumber());
            intent.putExtra("surah_name", surah.getEnglishName());
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(surahAdapter);
    }

    private void getAllSurah() {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SurahResponse> call = apiService.getAllSurah();

        call.enqueue(new Callback<SurahResponse>() {
            @Override
            public void onResponse(Call<SurahResponse> call, Response<SurahResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Surah[] data = response.body().getData();
                        Log.d(TAG, "Jumlah surah diterima: " + data.length);
                        surahList.clear();
                        surahList.addAll(Arrays.asList(data));
                        surahAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e(TAG, "Kesalahan saat parsing data: ", e);
                        showError();
                    }
                } else {
                    Log.e(TAG, "Gagal: " + response.code() + " - " + response.message());
                    showError();
                }
            }

            @Override
            public void onFailure(Call<SurahResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Gagal koneksi ke API", t);
                showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }
}
