package com.example.technado.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.technado.ArticleApplication;
import com.example.technado.R;
import com.example.technado.models.Article;
import com.example.technado.models.ArticleData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ArticlesAdapter.OnArticleClickListener {

    RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ArticleData> articleDataList = new ArrayList<>();
    ArticlesAdapter articlesAdapter;
    Article article;
    ProgressDialog progressDialog;
    FloatingActionButton addArticle;

    AlertDialog alertDialog;
    Button delete, add;
    EditText title, body;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int totalItems;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setupDialog();

        getAllArticles();

        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                Log.d("Articles", "Load more items");
                isLoading = true;
                currentPage++;
                getPaginatedArticles(currentPage);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void init() {
        relativeLayout = findViewById(R.id.relativeLayout);
        addArticle = findViewById(R.id.addArticle);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        articlesAdapter = new ArticlesAdapter(MainActivity.this, articleDataList, this);
        recyclerView.setAdapter(articlesAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        addArticle.setOnClickListener(this);
    }

    private void getAllArticles() {
        if (isNetworkConnected()) {
            progressDialog.show();
            ArticleApplication.getApiCallInstance().getAllArticles().enqueue(new Callback<Article>() {
                @Override
                public void onResponse(Call<Article> call, Response<Article> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        article = response.body();
                        totalItems = article.getMeta().getTotal();

                        articleDataList.addAll(response.body().getArticleData());
                        articlesAdapter.notifyDataSetChanged();

                        if (articlesAdapter.getItemCount() <= totalItems) {
                            articlesAdapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Article> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("ApiResponse", t.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPaginatedArticles(int page) {
        if (isNetworkConnected()) {
            ArticleApplication.getApiCallInstance().getPaginatedArticles(page).enqueue(new Callback<Article>() {
                @Override
                public void onResponse(Call<Article> call, Response<Article> response) {
                    if (response.isSuccessful()) {
                        isLoading = false;
                        articlesAdapter.removeLoadingFooter();
                        articlesAdapter.addAll(response.body().getArticleData());

                        if (articlesAdapter.getItemCount() != totalItems) {
                            articlesAdapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Article> call, Throwable t) {
                    Log.e("ApiResponse", t.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
            articlesAdapter.removeLoadingFooter();
            currentPage--;
        }
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void setupDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.article_form, null);
        delete = dialogView.findViewById(R.id.deleteBtn);
        add = dialogView.findViewById(R.id.addBtn);
        title = dialogView.findViewById(R.id.titleText);
        body = dialogView.findViewById(R.id.bodyText);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(dialogView);

        alertDialog = builder.create();

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                title.setText("");
                body.setText("");
            }
        });
    }

    @Override
    public void onClick(int position) {
        //for editing

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete article api integration
                Toast.makeText(MainActivity.this, "Delete item", Toast.LENGTH_SHORT).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put article api integration
                Toast.makeText(MainActivity.this, "Update Item", Toast.LENGTH_SHORT).show();
            }
        });

        ArticleData data = articleDataList.get(position);
        delete.setText("Delete");
        add.setText("Update");
        title.setText(data.getTitle());
        body.setText(data.getBody());
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        //for adding

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //just dismiss dialog
                Toast.makeText(MainActivity.this, "Cancel Dialog", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //post new article integration
                Toast.makeText(MainActivity.this, "Add Item", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setText("Cancel");
        add.setText("Add");
        alertDialog.show();
    }
}