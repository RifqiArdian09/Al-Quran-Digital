package com.example.alquran.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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

        // Set toolbar sebagai action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rvSurah);
        progressBar = findViewById(R.id.progressBar);

        setupRecyclerView();
        getAllSurah();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Cari surah...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                surahAdapter.filter(newText);
                return true;
            }
        });

        return true;
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
                    Surah[] data = response.body().getData();
                    Log.d(TAG, "Jumlah surah diterima: " + data.length);
                    surahList.clear();
                    surahList.addAll(Arrays.asList(data));
                    surahAdapter.updateList(surahList);
                } else {
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
        Toast.makeText(this, "Gagal memuat data", Toast.LENGTH_SHORT).show();
    }
}
