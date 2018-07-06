package com.example.jan.kassenzettel_scan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jan.kassenzettel_scan.R;
import com.example.jan.kassenzettel_scan.data.UserData;

import java.util.ArrayList;
import java.util.List;

public class ParticipantAdapter extends ArrayAdapter<UserData>{

    private List<UserData> users;

    ParticipantAdapter(Context context, List<UserData> objects) {
        super(context, 0, objects);
        this.users = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = LayoutInflater.from(getContext()).inflate(R.layout.container_participants, parent, false);

        final UserData user = users.get(position);

        TextView userName = listItem.findViewById(R.id.participant_name);
        final TextView participation = listItem.findViewById(R.id.participant_participation);

        EditText editParticipation = listItem.findViewById(R.id.participant_editParticipation);
        editParticipation.setHint(String.valueOf(user.getDefaultParticipation())+"%");
        editParticipation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) s="0";
                user.setDefaultParticipation(Double.valueOf(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                participation.setText(s);
            }
        });

        userName.setText(user.getUserName());
        participation.setText(String.valueOf(user.getDefaultParticipation()));

        return listItem;
    }

}
