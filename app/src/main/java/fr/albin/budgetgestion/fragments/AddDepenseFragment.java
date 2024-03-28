package fr.albin.budgetgestion.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;
import java.util.Scanner;

import fr.albin.budgetgestion.model.DepenseModel;
import fr.albin.budgetgestion.MainActivity;
import fr.albin.budgetgestion.R;
import fr.albin.budgetgestion.ResearchFile;
import fr.albin.budgetgestion.model.SoldeInsuffisantError;

public class AddDepenseFragment extends Fragment {

    private MainActivity context;
    private EditText input_libelle;
    private EditText input_montant;
    private EditText input_description;

    public AddDepenseFragment(MainActivity c){
        context = c;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = R.layout.fragment_add_depense;

        View view = inflater.inflate(layout,container,false);

        input_libelle = view.findViewById(R.id.input_add_libelle);
        input_montant=view.findViewById(R.id.input_add_montant);
        input_description=view.findViewById(R.id.input_add_description);


        Button confirmButton = view.findViewById(R.id.button_valider);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                 recupererInformationDepense();
            }
        });
        return view;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void recupererInformationDepense(){
        String lib =input_libelle.getText().toString();
        if(lib.length()==0){
            Toast.makeText(context,R.string.noLibellePresent, Toast.LENGTH_LONG).show();
            return;
        }
        String libelle = lib;
        String des = input_description.getText().toString();
        if(des.length()==0){
            des="Aucune descripton";
        }

        String description = verifierDescription(des);
        String nombre = input_montant.getText().toString();
        if(nombre.length()==0){
            Toast.makeText(context,R.string.noMontantPresent, Toast.LENGTH_LONG).show();
            return;
        }
        float montant = new Float(nombre);
        String date = ResearchFile.recupererData();

        DepenseModel depense = new DepenseModel(libelle,montant,date,description);
        try {
            MainActivity.porteFeuille.addDepense(depense);
            MainActivity.depenseListe.add(0,depense);
            ResearchFile.writeTransactionOnExternalStorage(context,MainActivity.depenseListe,true);
            ResearchFile.writeSoldeOnExternalStorage(context,MainActivity.porteFeuille.getAllSolde());
        } catch (SoldeInsuffisantError e) {
            Toast.makeText(context,R.string.solde_insuffisant, Toast.LENGTH_LONG).show();
        }

        context.loadFragment(new SoldeFragment(context.depenseListe),R.string.solde_title);
    }



    private String verifierDescription(String des) {
        return des;
    }
}
