package com.example.alquran.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alquran.R;
import com.example.alquran.models.Surah;

import java.util.List;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahViewHolder> {

    private Context context;
    private List<Surah> surahList;
    private OnItemClickListener listener;

    public SurahAdapter(Context context, List<Surah> surahList, OnItemClickListener listener) {
        this.context = context;
        this.surahList = surahList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Surah surah);
    }

    @NonNull
    @Override
    public SurahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_surah, parent, false);
        return new SurahViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahViewHolder holder, int position) {
        Surah surah = surahList.get(position);
        holder.bind(surah);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(surah));
    }

    @Override
    public int getItemCount() {
        return surahList.size();
    }

    class SurahViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber, tvSurahName, tvTranslation, tvNumberOfAyahs;

        SurahViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvSurahName = itemView.findViewById(R.id.tvSurahName);
            tvTranslation = itemView.findViewById(R.id.tvTranslation);
            tvNumberOfAyahs = itemView.findViewById(R.id.tvNumberOfAyahs);
        }

        void bind(Surah surah) {
            tvNumber.setText(String.valueOf(surah.getNumber()));
            tvSurahName.setText(context.getString(R.string.surah_name_format,
                    surah.getEnglishName(), surah.getName()));
            tvTranslation.setText(surah.getEnglishNameTranslation());
            tvNumberOfAyahs.setText(context.getString(R.string.number_of_ayat,
                    surah.getNumberOfAyahs()));
        }
    }
}