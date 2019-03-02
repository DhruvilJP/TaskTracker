package com.example.tasktracker;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowStatsFragment extends Fragment {
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String arguments = this.getArguments().getString("arguments");
        view = inflater.inflate(R.layout.showstatsfragment, container, false);
        TextView txtTotal = (TextView) view.findViewById(R.id.txtTotal);
        txtTotal.setText("Total Tasks: " + arguments.split(",",2)[0]);
        TextView txtPending = (TextView) view.findViewById(R.id.txtPending);
        txtPending.setText("Pending Tasks: " + arguments.split(",",2)[1]);
        return view;
    }
}
