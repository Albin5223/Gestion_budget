package fr.albin.budgetgestion.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import fr.albin.budgetgestion.MainActivity;
import fr.albin.budgetgestion.R;
import fr.albin.budgetgestion.ResearchFile;
import fr.albin.budgetgestion.model.Article;
import fr.albin.budgetgestion.model.ObjetAajouter;
import fr.albin.budgetgestion.model.RevenuModel;
import fr.albin.budgetgestion.model.SoldeInsuffisantError;

public class AjouterPopUp extends Dialog {



    private Button valider;
    ObjetAajouter objet;
    MainActivity mainActivity;

    public AjouterPopUp(@NonNull MainActivity context, ObjetAajouter objet) {
        super(context);
        mainActivity=context;
        this.objet = objet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_ajouter_objet);

        TextView titlePage = findViewById(R.id.title_textView);
        titlePage.setText(objet.getPageTitle());

        TextView libelle = findViewById(R.id.input_add_objet_libelle);
        libelle.setHint(objet.getLibelleTitle());


        TextView mont = findViewById(R.id.input_add_objet_montant);
        mont.setHint(objet.getMontantTitle());


        valider = findViewById(R.id.button_valider_objet);
        valider.setText(objet.getTitleButton());
        valider.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String lib = libelle.getText().toString();
                String progressValue=mont.getText().toString();
                if(lib.length()==0){
                    Toast.makeText(getContext(), getContext().getString(R.string.noLibellePresent), Toast.LENGTH_LONG).show();
                }
                else{
                    if(progressValue.length() == 0){
                        Toast.makeText(getContext(), getContext().getString(R.string.noMontantPresent), Toast.LENGTH_LONG).show();
                    }
                    else {
                        switch (objet){
                            case REVENU:addRevenu(progressValue,lib);break;
                            case ARTICLE:addArticle(progressValue,lib);break;
                        }
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addRevenu(String progressValue, String libelle){
        float montant = new Float(progressValue);
        String date = ResearchFile.recupererData();
        RevenuModel revenu = new RevenuModel(libelle, montant, date, "Aucune description", false);
        MainActivity.revenuListe.add(0, revenu);
        MainActivity.porteFeuille.setSoldeCourant(montant);
        ResearchFile.writeSoldeOnExternalStorage(getContext(), MainActivity.porteFeuille.getAllSolde());
        ResearchFile.writeTransactionOnExternalStorage(getContext(), MainActivity.revenuListe, false);
        this.dismiss();

    }

    private void addArticle(String progressValue, String libelle){
        float montant = new Float(progressValue);

        Article art = new Article(libelle,montant);
        try {
            MainActivity.porteFeuilleCourse.addArticle(art);
            MainActivity.articleListe.add(art);
            mainActivity.loadFragment(new ModeCourseFragment(mainActivity),R.string.subtitle_mode_course_article);
            ResearchFile.writeCourseOnExternalStorage(getContext(),MainActivity.articleListe,MainActivity.porteFeuilleCourse);
            this.dismiss();

        } catch (SoldeInsuffisantError e) {
            Toast.makeText(getContext(),R.string.solde_insuffisant,Toast.LENGTH_LONG).show();
        }

    }




}
