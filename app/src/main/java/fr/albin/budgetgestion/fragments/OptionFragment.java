package fr.albin.budgetgestion.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.albin.budgetgestion.MainActivity;
import fr.albin.budgetgestion.R;
import fr.albin.budgetgestion.ResearchFile;
import fr.albin.budgetgestion.model.ObjetAajouter;

public class OptionFragment extends Fragment  {

    MainActivity mainActivity;

    public OptionFragment(MainActivity m){
        mainActivity = m;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = R.layout.fragment_option;

        View view = inflater.inflate(layout,container,false);
        Button restaurer = view.findViewById(R.id.button_reinitialiser);
        restaurer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float[] vide = new float[0];
                delete_historiqueTransaction();
                ResearchFile.writeSoldeOnExternalStorage(getContext(),vide);

                MainActivity.porteFeuille.setAllSolde(-1,-1);
                mainActivity.loadFragment(new SoldeFragment(MainActivity.depenseListe),R.string.solde_title);
                Toast.makeText(mainActivity,R.string.data_deleted , Toast.LENGTH_LONG).show();
            }
        });

        Button augmenter = view.findViewById(R.id.button_augmenter_solde);
        augmenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AjouterPopUp asp = new AjouterPopUp(mainActivity, ObjetAajouter.REVENU);
                asp.show();
            }
        });

        Button effacerHistorique = view.findViewById(R.id.button_effacer_historique_Transaction);
        effacerHistorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_historiqueTransaction();
            }
        });


        Button button_mode_course = view.findViewById(R.id.button_mode_course);
        button_mode_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpInitialisationModeCourse pimc = new PopUpInitialisationModeCourse(mainActivity);
                pimc.show();
            }
        });
        return view;
    }


    private void delete_historiqueTransaction(){
        MainActivity.depenseListe.clear();
        MainActivity.revenuListe.clear();
        ResearchFile.writeTransactionOnExternalStorage(getContext(),MainActivity.revenuListe,false);
        ResearchFile.writeTransactionOnExternalStorage(getContext(),MainActivity.depenseListe,true);
    }
}
