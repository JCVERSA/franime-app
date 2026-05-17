package com.apkgen.template;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.apkgen.template.adapter.AnimeAdapter;
import com.apkgen.template.api.ApiService;
import com.apkgen.template.api.RetrofitClient;
import com.apkgen.template.databinding.ActivityMainBinding;
import com.apkgen.template.model.Anime;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AnimeAdapter.OnAnimeClickListener {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private AnimeAdapter adapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.d(TAG, "MainActivity créée");

        apiService = RetrofitClient.getApiService();

        setupRecyclerView();
        loadAnimes();
    }

    private void setupRecyclerView() {
        adapter = new AnimeAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.setAdapter(adapter);

        Log.d(TAG, "RecyclerView configuré");
    }

    private void loadAnimes() {
        Log.d(TAG, "Chargement des animes en cours...");

        showLoading(true);

        apiService.getAllAnimes().enqueue(new Callback<List<Anime>>() {
            @Override
            public void onResponse(Call<List<Anime>> call, Response<List<Anime>> response) {
                Log.d(TAG, "Réponse reçue: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    List<Anime> animes = response.body();
                    Log.d(TAG, "Nombre d'animes reçus: " + animes.size());

                    adapter.setAnimeList(animes);

                    showLoading(false);
                    showError(false);
                } else {
                    Log.e(TAG, "Erreur: réponse non réussie");
                    showError(true, "Erreur lors du chargement");
                    showLoading(false);
                }
            }

            @Override
            public void onFailure(Call<List<Anime>> call, Throwable t) {
                Log.e(TAG, "Erreur réseau: " + t.getMessage(), t);

                showError(true, "Erreur réseau: " + t.getMessage());
                showLoading(false);
            }
        });
    }

    private void showLoading(boolean show) {
        binding.progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void showError(boolean show) {
        showError(show, getString(R.string.error_loading));
    }

    private void showError(boolean show, String message) {
        binding.errorText.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            binding.errorText.setText(message);
        }
        binding.recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onAnimeClick(Anime anime) {
        Log.d(TAG, "Anime cliqué: " + anime.getTitle());
        // TODO: Naviguer vers détails
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity reprise");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity détruite");
    }
}