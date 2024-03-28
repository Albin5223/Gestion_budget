package fr.albin.budgetgestion.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.albin.budgetgestion.MainActivity;
import fr.albin.budgetgestion.R;
import fr.albin.budgetgestion.ResearchFile;

public class ChoiceSoldeFragement extends Fragment {

    MainActivity mainActivity;
    
    public ChoiceSoldeFragement(MainActivity mA){
        mainActivity = mA;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = R.layout.fragment_choix_solde;

        View view = inflater.inflate(layout,container,false);
        EditText editText = view.findViewById(R.id.input_choix_nouveau_solde);

        Button valider = view.findViewById(R.id.button_valider_nouveau_solde);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                float f = new Float(s);
                MainActivity.porteFeuille.setAllSolde(f,f);

                mainActivity.loadFragment(new SoldeFragment(MainActivity.depenseListe),R.string.solde_title);
                float[] tab = {f,f};

                ResearchFile.writeSoldeOnExternalStorage(mainActivity,tab);
            }
        });

        return view;
    }
}
