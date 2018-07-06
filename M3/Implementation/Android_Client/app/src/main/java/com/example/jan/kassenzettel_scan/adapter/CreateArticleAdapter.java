package com.example.jan.kassenzettel_scan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.data.ArticleData;
import com.example.jan.kassenzettel_scan.data.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
Custom adapter to display a list of receipts in a custom xml container.
To implement this the following sources were used:
Tutorial displaying json data in recycler view: http://androidcss.com/android/fetch-json-data-android/
The official android documentation:
https://developer.android.com/guide/topics/ui/layout/recyclerview,
https://developer.android.com/reference/android/support/v7/widget/RecyclerView
https://developer.android.com/reference/android/text/TextWatcher
*/

public class CreateArticleAdapter extends RecyclerView.Adapter<CreateArticleAdapter.ViewHolder> {

    private static final String TAG = "CreateArticleAdapter";

    private List<ArticleData> articleData;
    private List<UserData> userData;

    private Context context;

    private ArticleData curArticle;
    private TextView total;

    private int mExpandedPosition = -1;
    private int previousExpandedPosition;

    // create constructor to initialize context and data sent from Activity
    public CreateArticleAdapter(Context context, List<ArticleData> articleData, List<UserData> userData) {
        Log.d(TAG, "calling constructor...");
        this.context = context;
        this.articleData = articleData;
        this.userData = userData;
    }

    @NonNull
    @Override
    // Inflate the layout when ViewHolder created
    public CreateArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        Log.d(TAG, "calling onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_create_article, parent, false);
        ViewHolder holder = new ViewHolder(view);

        total = parent.getRootView().findViewById(R.id.create_totalValue);

        return holder;
    }

    @Override
    // Bind data
    public void onBindViewHolder(@NonNull final CreateArticleAdapter.ViewHolder holder, int position) {
        // Get current position of item in recyclerview to bind data and assign values
        Log.d(TAG, "calling onBindViewHolder at position: " + position);
        //TODO: BUG: the delete-button doesn't completely delete data. If add-button is used after deleting, the new item contains data of deleted one
        curArticle = articleData.get(position);

        holder.currencyTotal.setText(curArticle.getCurrency());
        holder.currencySingle.setText(curArticle.getCurrency());
        holder.participants.setText(String.valueOf(curArticle.getParticipants()));

        if (curArticle.getArticleName() != null) {
            holder.name.setText(curArticle.getArticleName());
        } else {
            holder.name.setHint("Artikelname");
        }
        if (curArticle.getNumberArticles() != 0) {
            holder.amount.setText(String.valueOf(curArticle.getNumberArticles()));
        } else {
            holder.amount.setHint("0");
        }

        if (curArticle.getPriceSingle() != 0) {
            holder.priceSingle.setText(String.valueOf(curArticle.getPriceSingle()));
        } else {
            holder.priceSingle.setHint("0.0");
        }

        //TODO: BUG: changes in participation overrides every other article participation
        final boolean isExpanded = position == mExpandedPosition;
        holder.participantLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);

        Log.d(TAG, String.valueOf(userData.size()));
        ParticipantAdapter adapter = new ParticipantAdapter(context, userData);
        holder.participantLayout.setAdapter(adapter);

        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            previousExpandedPosition = holder.getAdapterPosition();

        //TODO: BUG: if content_height is wrap_content, the expanded layout only shows first participation
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:holder.getAdapterPosition();
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return articleData.size();
    }

    public List<ArticleData> getListData() {
        return articleData;
    }

    //calculate total cost for each articles total price in data set
    private double setTotalCost() {
        double totalcost = 0;
        if (articleData.isEmpty()) {
            Log.d(TAG, "setTotalCost: ArticleData is empty");
            return totalcost;
        } else {
            for (ArticleData article : articleData) {
                Log.d(TAG, "setTotalCost: "+ String.valueOf(article.getPriceTotal()));
                totalcost += article.getPriceTotal();
            }
            return totalcost;
        }
    }

    //add new empty article to data set and view holder
    public void addArticle(ArticleData article) {
        articleData.add(article);
        notifyItemInserted(articleData.size() - 1);
        if (setTotalCost() != 0) total.setText(String.valueOf(setTotalCost()));
    }

    //remove article from data set and view holder, calculates new total cost from remaining articles
    private void removeArticle(int position) {
        Log.d(TAG, String.valueOf(position));
        articleData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, 1);
        total.setText(String.valueOf(setTotalCost()));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        EditText name, amount, priceSingle;
        TextView currencyTotal, currencySingle, priceTotal, participants;
        Button deleteItemButton;
        ListView participantLayout;

        // create constructor to get widget reference
        ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "calling Viewholder");

            name = itemView.findViewById(R.id.create_article_name);
            name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 0) {
                        s = "";
                    }
                    curArticle.setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            currencyTotal = itemView.findViewById(R.id.create_article_currencyTotal);
            currencySingle = itemView.findViewById(R.id.create_article_currencySingle);

            amount = itemView.findViewById(R.id.create_article_amount);
            amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 0) {
                        s = "0";
                    }
                    curArticle.setNumberArticles(Integer.valueOf(s.toString()));
                }

                @Override
                public void afterTextChanged(Editable s) {
                    curArticle.setPriceTotal(curArticle.getNumberArticles() * curArticle.getPriceSingle());
                    priceTotal.setText(String.valueOf(curArticle.getNumberArticles() * curArticle.getPriceSingle()));
                }
            });

            priceSingle = itemView.findViewById(R.id.create_article_priceSingle);
            priceSingle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 0) {
                        s = "0";
                    }
                    curArticle.setPriceSingle(Double.valueOf(s.toString()));
                }

                @Override
                public void afterTextChanged(Editable s) {
                    double tmp = curArticle.getNumberArticles() * curArticle.getPriceSingle();
                    curArticle.setPriceTotal(tmp);
                    String result = String.format(Locale.US, "%.2f", tmp);
                    priceTotal.setText(result);
                }
            });

            priceTotal = itemView.findViewById(R.id.create_article_priceTotal);
            priceTotal.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    double tmp = setTotalCost();
                    String result = String.format(Locale.US, "%.2f", tmp);
                    total.setText(result);
                }
            });

            participants= itemView.findViewById(R.id.create_article_participantNumber);

            deleteItemButton = itemView.findViewById(R.id.create_delete_button);
            deleteItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "deleting at " + getAdapterPosition());
                    removeArticle(getAdapterPosition());
                }
            });

            participantLayout = itemView.findViewById(R.id.create_article_participant_layout);
        }
    }
}
