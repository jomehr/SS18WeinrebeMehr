package com.example.jan.kassenzettel_scan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.data.ReceiptData;
import com.example.jan.kassenzettel_scan.activities.ReceiptDetails;

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<ReceiptData> receiptData;

    // create constructor to initialize context and data sent from MainActivity
    public ReceiptAdapter(Context context, List<ReceiptData> data) {
        layoutInflater = LayoutInflater.from(context);
        this.receiptData = data;
    }

    @NonNull
    @Override
    // Inflate the layout when ViewHolder created
    public ReceiptAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View view = layoutInflater.inflate(R.layout.container_receipt, parent, false);

        return new ViewHolder(view);
    }

    @Override
    // Bind data
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get current position of item in recyclerview to bind data and assign values from list
        ReceiptData curReceipt = receiptData.get(position);
        holder.owner.setText(curReceipt.getUserFirstName());
        holder.name.setText(curReceipt.getStoreName());
        holder.date.setText(curReceipt.getDateString());
        holder.articleNumber.setText(String.valueOf(curReceipt.getNumberArticles()));
        holder.total.setText(String.valueOf(curReceipt.getTotalAmount()));
        holder.currency.setText(curReceipt.getCurrency());

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return receiptData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView owner, name, date, articleNumber, total, currency;

        // create constructor to get widget reference
        ViewHolder(View itemView) {
            super(itemView);
            owner = itemView.findViewById(R.id.receipt_owner_name);
            name = itemView.findViewById(R.id.receipt_storename);
            date = itemView.findViewById(R.id.receipt_date);
            articleNumber = itemView.findViewById(R.id.receipt_numberarticles);
            total = itemView.findViewById(R.id.receipt_total);
            currency = itemView.findViewById(R.id.receipt_currency);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReceiptDetails.class);
                    context.startActivity(intent);
                }
            });
        }
    }

}
