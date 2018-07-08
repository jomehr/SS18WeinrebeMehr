package com.example.jan.kassenzettel_scan.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.adapter.CreateArticleAdapter;
import com.example.jan.kassenzettel_scan.data.ArticleData;
import com.example.jan.kassenzettel_scan.data.ReceiptData;
import com.example.jan.kassenzettel_scan.data.UserData;
import com.example.jan.kassenzettel_scan.network.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class TabCreate extends Fragment {

    private static final String TAG = "TabCreate";

    private String storeName, currencyName, ownerName;
    private TextView totalText, changeText;
    private EditText paidText;
    private RecyclerView recyclerView;
    private CreateArticleAdapter createArticleAdapter;

    private Button saveButton;

    private List<ArticleData> dataArticle = new ArrayList<>();
    private List<UserData> dataUser = new ArrayList<>();

    private RequestParams params;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab_create, container, false);

        Spinner storeSpinner = rootView.findViewById(R.id.create_storeSpinner);
        Spinner currenySpinner = rootView.findViewById(R.id.create_currencySpinner);
        Spinner userSpinner  = rootView.findViewById(R.id.create_ownerSpinner);
        totalText = rootView.findViewById(R.id.create_totalValue);
        changeText = rootView.findViewById(R.id.create_changeValue);
        paidText = rootView.findViewById(R.id.create_paidValue);
        recyclerView = rootView.findViewById(R.id.create_recylerView);
        saveButton = rootView.findViewById(R.id.create_saveButton);
        Button addButton = rootView.findViewById(R.id.create_addButton);

        saveButton.setActivated(false);
        saveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));


        dataUser.add(new UserData("5b3c96b1d96557e668a9e759", "Jan Mehr", "5b3e4e980a85503024f7b5c8"));
        dataUser.add(new UserData("5b3c96e8d96557e668a9e75b", "Armin Weinrebe", "5b3e4e980a85503024f7b5c8"));
        dataUser.add(new UserData("5b3c96f9d96557e668a9e75c", "Sebastian Wiesendahl", "5b3e4e980a85503024f7b5c8"));

        for (UserData user : dataUser) {
            user.setDefaultParticipation(100.00 / ((double) dataUser.size()));
            Log.d(TAG, String.valueOf(dataUser.size()) + " " + String.valueOf(user.getDefaultParticipation()));
        }

        createArticleAdapter = new CreateArticleAdapter(getContext(), dataArticle, dataUser);
        recyclerView.setAdapter(createArticleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));


        paidText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) s = "0";
                double tmp = Double.parseDouble(s.toString()) - Double.parseDouble(totalText.getText().toString());
                if (tmp < 0) {
                    paidText.setError("Dieser Wert ist zu klein!");
                } else {
                    String result = String.format(Locale.US, "%.2f", tmp);
                    changeText.setText(result);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                double total = Double.parseDouble(totalText.getText().toString());
                double paid = Double.parseDouble(paidText.getText().toString());
                double change = Double.parseDouble(changeText.getText().toString());
                if (total > 0 && (paid > 0 && paid >= total)) {
                    saveButton.setActivated(true);
                    saveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    saveButton.setActivated(false);
                    saveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                }
            }
        });

        storeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storeName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        currenySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currencyName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ownerName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createArticleAdapter.addArticle(new ArticleData(currencyName, dataUser.size()));
                recyclerView.smoothScrollToPosition(createArticleAdapter.getItemCount());
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveButton.isActivated()) {
                    saveData(createArticleAdapter);
                } else {
                    Toast.makeText(getContext(),"Erstelle mind. einen Artikel und fülle alle Felder aus!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void saveData(CreateArticleAdapter adapter) {
        ReceiptData receiptData = null;
        String ownerId = null;
        if (ownerName.equals("Jan Mehr")) ownerId = "5b3c96b1d96557e668a9e759";
        if (ownerName.equals("Armin Weinrebe")) ownerId = "5b3c96e8d96557e668a9e75b";
        if (ownerName.equals("Sebastian Wiesendahl")) ownerId = "5b3c96f9d96557e668a9e75c";

        try {
            receiptData = new ReceiptData(
                    null,
                    ownerId,
                    storeName,
                    null,
                    createArticleAdapter.getItemCount(),
                    Double.parseDouble(totalText.getText().toString()),
                    Double.parseDouble(paidText.getText().toString()),
                    Double.parseDouble(changeText.getText().toString()),
                    currencyName);

        }catch (Exception e) {
            e.printStackTrace();
        }

        dataArticle = adapter.getListData();

        try {
            JSONObject receiptObject = new JSONObject();
            receiptObject.put("type", "GROUP");
            receiptObject.put("group", "5b3e4e980a85503024f7b5c8");
            receiptObject.put("owner", receiptData != null ? receiptData.getOwnerId() : null);
            receiptObject.put("store", receiptData != null ? receiptData.getStoreName() : null);
            receiptObject.put("total", receiptData != null ? receiptData.getTotalAmount() : 0);
            receiptObject.put("paid", receiptData != null ? receiptData.getPaidAmount() : 0);
            receiptObject.put("change", receiptData != null ? receiptData.getChangeAmount() : 0);
            receiptObject.put("currency", receiptData != null ? receiptData.getCurrency() : null);

            JSONArray articleArray = new JSONArray();
            for (ArticleData article : dataArticle) {
                JSONObject articleObject = new JSONObject();
                articleObject.put("name", article.getArticleName());
                articleObject.put("amount", article.getNumberArticles());
                articleObject.put("priceSingle", article.getPriceSingle());
                articleObject.put("priceTotal", article.getPriceTotal());

                JSONArray participantArray = new JSONArray();
                for (UserData user : dataUser) {
                    JSONObject participationObject = new JSONObject();
                    participationObject.put("participant", user.getUserId());
                    participationObject.put("percentage", user.getDefaultParticipation());

                    participantArray.put(participationObject);
                }

                articleObject.put("participation", participantArray);

                articleArray.put(articleObject);
            }
            receiptObject.put("articles", articleArray);

            ByteArrayEntity entity = null;
            try {
                entity = new ByteArrayEntity(receiptObject.toString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (entity != null) {
                entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            }
            RestClient.post(this.getContext(),"receipt/", entity, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d(TAG, "Success with " + statusCode);
                    Toast.makeText(getContext(), "Der Kassenzettel wurde erfolgreich erstellt", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    if (statusCode == 404) {
                        Log.d(TAG, "Failure with " + statusCode);
                    } else if (statusCode == 201){
                        Log.d(TAG, "Success with " + statusCode);
                        Toast.makeText(getContext(), "Der Kassenzettel wurde erfolgreich erstellt", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d(TAG, "Failure with " + statusCode);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    if (statusCode == 404) {
                        Log.d(TAG, "Failure with " + statusCode);
                    } else if (statusCode == 201){
                        Log.d(TAG, "Success with " + statusCode);
                        Toast.makeText(getContext(), "Der Kassenzettel wurde erfolgreich erstellt", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d(TAG, "Failure with " + statusCode);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if (statusCode == 404) {
                        Log.d(TAG, "Failure with " + statusCode);
                    } else {
                        Log.d(TAG, "Failure with " + statusCode);
                    }
                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
