package com.example.jan.kassenzettel_scan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.data.ArticleData;

import java.util.List;

/*

*/
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<ArticleData> articleData;

    // create constructor to initialize context and data sent from Activity
    public ArticleAdapter(Context context, List<ArticleData> data) {
        layoutInflater = LayoutInflater.from(context);
        this.articleData = data;
    }

    @NonNull
    @Override
    // Inflate the layout when ViewHolder created
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = layoutInflater.inflate(R.layout.container_receipt_article_collapsed, parent, false);

        return new ArticleAdapter.ViewHolder(view);
    }

    @Override
    // Bind data
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position) {
        // Get current position of item in recyclerview to bind data and assign values from list
        ArticleData curArticle = articleData.get(position);
        holder.name.setText(curArticle.getArticleName());
        holder.priceTotal.setText(String.valueOf(curArticle.getPriceTotal()));
        holder.participants.setText(String.valueOf(curArticle.getParticipants()));
        holder.amount.setText(String.valueOf(curArticle.getNumberArticles()));
        holder.priceSingle.setText(String.valueOf(curArticle.getPriceSingle()));
        holder.currencyTotal.setText(curArticle.getCurrency());
        holder.currencySingle.setText(curArticle.getCurrency());
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return articleData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, priceTotal, currencyTotal, participants, amount, priceSingle, currencySingle;

        // create constructor to get widget reference
        ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.receipt_article_name);
            priceTotal = itemView.findViewById(R.id.receipt_article_priceTotal);
            currencyTotal = itemView.findViewById(R.id.receipt_article_currencyTotal);
            participants = itemView.findViewById(R.id.receipt_article_participantNumber);
            amount = itemView.findViewById(R.id.receipt_article_amount);
            priceSingle = itemView.findViewById(R.id.receipt_article_priceSingle);
            currencySingle = itemView.findViewById(R.id.receipt_article_currencySingle);

        }
    }
}
