package fr.albin.budgetgestion.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.albin.budgetgestion.R;

public class NoHistorique extends Fragment{

    int idString;

    public NoHistorique(int id){
        this.idString = id;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = R.layout.fragment_no_depense;

        View view = inflater.inflate(layout, container, false);
        TextView message = view.findViewById(R.id.textViewNoHistorique);
        message.setText(idString);

        return view;
    }
}
