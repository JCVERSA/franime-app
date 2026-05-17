package com.apkgen.template.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.apkgen.template.R;
import com.apkgen.template.databinding.ItemAnimeBinding;
import com.apkgen.template.model.Anime;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    private List<Anime> animeList = new ArrayList<>();
    private OnAnimeClickListener clickListener;

    public interface OnAnimeClickListener {
        void onAnimeClick(Anime anime);
    }

    public AnimeAdapter(OnAnimeClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAnimeBinding binding = ItemAnimeBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new AnimeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        Anime anime = animeList.get(position);
        holder.bind(anime);
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public void setAnimeList(List<Anime> newList) {
        this.animeList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addAnimes(List<Anime> newAnimes) {
        if (newAnimes != null) {
            int previousSize = animeList.size();
            animeList.addAll(newAnimes);
            notifyItemRangeInserted(previousSize, newAnimes.size());
        }
    }

    public void clear() {
        animeList.clear();
        notifyDataSetChanged();
    }

    public class AnimeViewHolder extends RecyclerView.ViewHolder {

        private ItemAnimeBinding binding;

        public AnimeViewHolder(ItemAnimeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && clickListener != null) {
                    clickListener.onAnimeClick(animeList.get(position));
                }
            });
        }

        public void bind(Anime anime) {
            Glide.with(itemView.getContext())
                    .load(anime.getPosterUrl())
                    .placeholder(R.color.surface_variant)
                    .error(R.color.error_light)
                    .into(binding.animeImage);

            binding.animeTitle.setText(anime.getTitle());

            String yearStatus = anime.getStartDate() + " • " + anime.getStatus();
            binding.animeYear.setText(yearStatus);

            binding.animeSynopsis.setText(anime.getShortDescription());
        }
    }
}