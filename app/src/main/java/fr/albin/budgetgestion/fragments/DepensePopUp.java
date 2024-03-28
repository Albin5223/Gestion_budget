package fr.albin.budgetgestion.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import fr.albin.budgetgestion.R;
import fr.albin.budgetgestion.adapter.DepenseAdapter;
import fr.albin.budgetgestion.model.DepenseModel;
import fr.albin.budgetgestion.model.Transaction;

public class DepensePopUp extends Dialog {

    private DepenseAdapter adapter;
    private Transaction depense;

    public DepensePopUp(DepenseAdapter ad, Transaction d) {
        super(ad.context);
        depense = d;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_depense_detail);
        initialiserView();
        initialiseButtonClose();
    }

    private void initialiseButtonClose() {
        ImageView cross = findViewById(R.id.close_button);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    //Cette methode permet de modifier les TextView de la popUp
    private void initialiserView() {
        TextView libele = findViewById(R.id.libelle_input);
        libele.setText(depense.libele);

        TextView montant = findViewById(R.id.montant_input);
        montant.setText(depense.montant+" €");

        TextView date = findViewById(R.id.date_input);
        date.setText(depense.date);

        TextView descrition = findViewById(R.id.description_input);
        descrition.setText(depense.description);
    }




}
