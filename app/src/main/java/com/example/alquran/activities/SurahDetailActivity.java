package com.example.alquran.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alquran.R;
import com.example.alquran.adapters.AyatAdapter;
import com.example.alquran.api.ApiClient;
import com.example.alquran.api.ApiService;
import com.example.alquran.models.Ayat;
import com.example.alquran.models.AyatData;
import com.example.alquran.models.AyatResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurahDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvTitle;
    private AyatAdapter ayatAdapter;
    private List<Ayat> ayatList = new ArrayList<>();
    private int surahNumber;
    private String surahName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_detail);

        setupToolbar();
        getIntentData();
        initViews();
        setupRecyclerView();
        getSurahWithTranslation();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void getIntentData() {
        surahNumber = getIntent().getIntExtra("surah_number", 1);
        surahName = getIntent().getStringExtra("surah_name");
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tvTitle);
        recyclerView = findViewById(R.id.rvAyat);
        progressBar = findViewById(R.id.progressBar);
        tvTitle.setText(surahName);
    }

    private void setupRecyclerView() {
        ayatAdapter = new AyatAdapter(this, ayatList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ayatAdapter);
    }

    private void getSurahWithTranslation() {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AyatResponse> call = apiService.getSurahWithTranslation(surahNumber);

        call.enqueue(new Callback<AyatResponse>() {
            @Override
            public void onResponse(Call<AyatResponse> call, Response<AyatResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    processResponse(response.body());
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<AyatResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showError();
            }
        });
    }

    private void processResponse(AyatResponse response) {
        AyatData[] data = response.getData();
        if (data != null && data.length >= 2) {
            ayatList.clear();
            for (int i = 0; i < data[0].getAyahs().length; i++) {
                Ayat ayat = new Ayat(
                        i + 1,
                        data[0].getAyahs()[i].getText(),
                        data[1].getAyahs()[i].getText()
                );
                ayatList.add(ayat);
            }
            ayatAdapter.notifyDataSetChanged();
        }
    }

    private void showError() {
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}