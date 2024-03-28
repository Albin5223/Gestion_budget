package fr.albin.budgetgestion.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import fr.albin.budgetgestion.MainActivity;
import fr.albin.budgetgestion.R;

public class PopUpInitialisationModeCourse extends Dialog {

    Button valider;
    MainActivity mainActivity;
    EditText mont;

    public PopUpInitialisationModeCourse(@NonNull MainActivity context) {
        super(context);

        mainActivity = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_initialiser_mode_course);

        mont = findViewById(R.id.input_init_mode_course);

        valider = findViewById(R.id.button_valider_revenu);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String montantString = mont.getText().toString();
                if (montantString.length() == 0) {
                    Toast.makeText(getContext(), getContext().getString(R.string.noMontantPresent), Toast.LENGTH_SHORT).show();
                } else {
                    float montant = new Float(montantString);
                    if(montant >MainActivity.porteFeuille.getSolde_courant()){
                        Toast.makeText(getContext(), getContext().getString(R.string.solde_insuffisant), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    MainActivity.porteFeuilleCourse.setAllSolde(montant,montant);
                    mainActivity.loadFragment(new ModeCourseFragment(mainActivity), R.string.mode_course_title);
                    PopUpInitialisationModeCourse.this.dismiss();
                }
            }
        });
    }
}
