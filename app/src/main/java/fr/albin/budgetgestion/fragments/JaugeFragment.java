package fr.albin.budgetgestion.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.albin.budgetgestion.MainActivity;
import fr.albin.budgetgestion.R;
import fr.albin.budgetgestion.model.PorteFeuille;
import fr.albin.budgetgestion.view.CircularProgressBar;

public class JaugeFragment extends Fragment {


    private CircularProgressBar progressBar;

    private PorteFeuille porteFeuille;
    public JaugeFragment(PorteFeuille porteFeuille){
        this.porteFeuille = porteFeuille;
    }



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = R.layout.jauge_layout;

        View view = inflater.inflate(layout,container,false);

        progressBar = view.findViewById(R.id.circularProgressBar);


        updateProgress();

        return view;
    }

    //L' argument progress represente le ratio entre le solde initial et le solde courant;
    private void updateProgress() {
        int progress = (int) ( porteFeuille.getSolde_courant()/porteFeuille.getSolde_initiale()*100);
        String solde = porteFeuille.getSolde_courant() +" €";
        progressBar.setProgress(progress,solde);

    }
}
