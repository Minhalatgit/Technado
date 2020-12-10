package com.example.technado.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technado.R;
import com.example.technado.models.ArticleData;

import java.util.List;


public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ArticleData> articleDataList;
    Context context;
    OnArticleClickListener onArticleClickListener;

    private boolean isLoadingAdded = false;
    private static final int ITEM = 1;
    private static final int LOADING = 2;

    public ArticlesAdapter(Context context, List<ArticleData> articleDataList, OnArticleClickListener onArticleClickListener) {
        this.context = context;
        this.articleDataList = articleDataList;
        this.onArticleClickListener = onArticleClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.article_item, parent, false);
            return new ArticleHolder(view, onArticleClickListener);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.loading_item, parent, false);
            return new LoadingHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArticleData articleData = articleDataList.get(position);

        switch (getItemViewType(position)) {

            case ITEM:
                ArticleHolder articleHolder = (ArticleHolder) holder;

                articleHolder.id.setText(articleData.getId());

                if (articleData.getTitle() != null)
                    articleHolder.title.setText(articleData.getTitle());

                if (articleData.getBody() != null)
                    articleHolder.body.setText(articleData.getBody());
                break;

            case LOADING:
                //do nothing
                break;
        }
    }


    @Override
    public int getItemCount() {
        return articleDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == articleDataList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView id, title, body;
        OnArticleClickListener onArticleClickListener;

        public ArticleHolder(@NonNull View itemView, OnArticleClickListener onArticleClickListener) {
            super(itemView);
            id = itemView.findViewById(R.id.idNumber);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);

            this.onArticleClickListener = onArticleClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onArticleClickListener != null)
                onArticleClickListener.onClick(getAdapterPosition());
        }
    }

    public interface OnArticleClickListener {
        void onClick(int position);
    }

    //helper methods
    public void add(ArticleData articleData) {
        articleDataList.add(articleData);
        notifyItemInserted(articleDataList.size() - 1);
    }

    public void addAll(List<ArticleData> articleDataList) {
        for (ArticleData data : articleDataList) {
            add(data);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ArticleData());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = articleDataList.size() - 1;
        ArticleData item = getItem(position);
        if (item != null) {
            articleDataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ArticleData getItem(int position) {
        return articleDataList.get(position);
    }
}
