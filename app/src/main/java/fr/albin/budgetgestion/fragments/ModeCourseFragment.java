package fr.albin.budgetgestion.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fr.albin.budgetgestion.MainActivity;
import fr.albin.budgetgestion.R;
import fr.albin.budgetgestion.ResearchFile;
import fr.albin.budgetgestion.adapter.DepenseAdapter;
import fr.albin.budgetgestion.adapter.DepenseItemDecoration;
import fr.albin.budgetgestion.model.DepenseModel;
import fr.albin.budgetgestion.model.ObjetAajouter;
import fr.albin.budgetgestion.model.PorteFeuille;
import fr.albin.budgetgestion.model.SoldeInsuffisantError;
import fr.albin.budgetgestion.model.Transaction;

public class ModeCourseFragment extends Fragment {


    PorteFeuille porteFeuille;
    MainActivity mainActivity;
    public ModeCourseFragment(MainActivity mainActivity){
        porteFeuille = MainActivity.porteFeuilleCourse;
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = R.layout.fragment_mode_course;

        View view = inflater.inflate(layout,container,false);


        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentJaugeSolde,new JaugeFragment(porteFeuille));
        transaction.addToBackStack(null);
        transaction.commit();

        Button voirArticle = view.findViewById(R.id.button_mode_course_VoirMesArticles);
        voirArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpVoirMesArticle mesArticles = new PopUpVoirMesArticle(mainActivity);
                mainActivity.loadFragment(mesArticles,R.string.subtitle_voir_mes_articles);
            }
        });


        BottomNavigationView touchBar = view.findViewById(R.id.touchBarViewModeCourse);
        touchBar.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ajouter_article:
                        AjouterPopUp add = new AjouterPopUp(mainActivity, ObjetAajouter.ARTICLE);
                        add.show();
                        return true;
                    case R.id.terminer_mode_course:
                        PopUpValidation validation = new PopUpValidation(getContext());
                        validation.show();

                        validation.getButtonValider().setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(View v) {
                                float montant_total = MainActivity.porteFeuilleCourse.getSolde_initiale()-MainActivity.porteFeuilleCourse.getSolde_courant();
                                try {
                                    if(MainActivity.articleListe.size() == 0){
                                        validation.dismiss();
                                        MainActivity.removeCourse();
                                        mainActivity.loadFragment(new SoldeFragment(MainActivity.depenseListe),R.string.solde_title);
                                        return;
                                    }
                                    DepenseModel course = new DepenseModel("Course",montant_total, ResearchFile.recupererData(),"Aucune description");
                                    MainActivity.depenseListe.add(0,course);
                                    MainActivity.removeCourse();
                                    MainActivity.porteFeuille.addDepense(course);
                                    ResearchFile.removeCourseOnExternalStorage(getContext());
                                    ResearchFile.writeTransactionOnExternalStorage(getContext(),MainActivity.depenseListe,true);
                                    validation.dismiss();
                                    mainActivity.loadFragment(new SoldeFragment(MainActivity.depenseListe),R.string.solde_title);
                                } catch (SoldeInsuffisantError e) {
                                    Toast.makeText(getContext(),R.string.solde_insuffisant,Toast.LENGTH_LONG);
                                }

                            }
                        });
                        return true;
                }
                return false;
            }
        });
        return view;
    }
}
