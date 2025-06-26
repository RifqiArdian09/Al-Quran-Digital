package com.example.alquran.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alquran.R;
import com.example.alquran.models.Ayat;

import java.util.List;

public class AyatAdapter extends RecyclerView.Adapter<AyatAdapter.AyatViewHolder> {

    private Context context;
    private List<Ayat> ayatList;

    public AyatAdapter(Context context, List<Ayat> ayatList) {
        this.context = context;
        this.ayatList = ayatList;
    }

    @NonNull
    @Override
    public AyatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ayat, parent, false);
        return new AyatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AyatViewHolder holder, int position) {
        Ayat ayat = ayatList.get(position);
        holder.bind(ayat);
    }

    @Override
    public int getItemCount() {
        return ayatList.size();
    }

    class AyatViewHolder extends RecyclerView.ViewHolder {
        TextView tvAyatNumber, tvAyatArabic, tvAyatTranslation;

        AyatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAyatNumber = itemView.findViewById(R.id.tvAyatNumber);
            tvAyatArabic = itemView.findViewById(R.id.tvAyatArabic);
            tvAyatTranslation = itemView.findViewById(R.id.tvAyatTranslation);
        }

        void bind(Ayat ayat) {
            tvAyatNumber.setText(String.valueOf(ayat.getNumber()));
            tvAyatArabic.setText(ayat.getText());
            tvAyatTranslation.setText(ayat.getTranslation());
        }
    }
}