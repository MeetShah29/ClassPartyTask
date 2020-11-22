package com.example.news;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.Model.Author;
import com.example.news.Model.Items;
import com.example.news.Model.MetaData;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView recyclerView;
    Adapter adapter;
    List<Items> items = new ArrayList<>();
    List<Author> author = new ArrayList<>();
    String urlParam = "https://www.wired.com/feed/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retriveJson(urlParam);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar=findViewById(R.id.progress_bar);
        shimmerFrameLayout=findViewById(R.id.shrimmer_layout);
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onStart() {
        isConnected();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void retriveJson(String url) {
        Call<MetaData> call = ApiClient.getInstance().getApi().getMetaData(url);
        call.enqueue(new Callback<MetaData>() {
            @Override
            public void onResponse(Call<MetaData> call, Response<MetaData> response) {
                if (response.isSuccessful() && response.body().getItems()!= null ) {
                    items.clear();
                    items = response.body().getItems();
                    author.clear();
                    adapter = new Adapter(MainActivity.this, items);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MetaData> call, Throwable t) {
                Log.i("Connection issue",t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(),
                        t.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("No internet connection available")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        recreate();
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
                System.exit(0);
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        builder.show();

    }

    private void isConnected(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo connection= connectivityManager.getActiveNetworkInfo();

        if(connection == null ||!connection.isConnected() || !connection.isAvailable()){
            showCustomDialog();
        }
    }
}