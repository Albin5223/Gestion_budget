package fr.albin.budgetgestion.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import fr.albin.budgetgestion.R;

public class PopUpValidation extends Dialog {

    Button valider;
    public PopUpValidation(@NonNull Context context) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.popup_valider_action);
        valider = findViewById(R.id.pop_up_valider_button_valider);


        Button annuler = findViewById(R.id.pop_up_valider_button_annuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpValidation.this.dismiss();
            }
        });
    }

    public Button getButtonValider(){
        return valider;
    }
}
