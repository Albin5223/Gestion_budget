package fr.albin.budgetgestion.fragments;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fr.albin.budgetgestion.MainActivity;
import fr.albin.budgetgestion.model.DepenseModel;
import fr.albin.budgetgestion.R;
import fr.albin.budgetgestion.adapter.DepenseAdapter;
import fr.albin.budgetgestion.adapter.DepenseItemDecoration;
import fr.albin.budgetgestion.model.RevenuModel;
import fr.albin.budgetgestion.model.Transaction;

public class HistoriqueFragment extends Fragment {

    ArrayList<Transaction> liste;
    View view;
    int layout;

    public HistoriqueFragment(ArrayList<Transaction> l){

        liste = l;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = R.layout.historique_layout;

        view = inflater.inflate(layout,container,false);

        loadHistorique(MainActivity.depenseListe,R.string.noDepense,R.layout.item_vertical_depense);
        BottomNavigationView touchBar = view.findViewById(R.id.touchBarViewHistorique);
        touchBar.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.historique_depense_page:
                        loadHistorique(MainActivity.depenseListe,R.string.noDepense,R.layout.item_vertical_depense);
                        return true;
                    case R.id.historique_revenu_page:
                        loadHistorique(MainActivity.revenuListe,R.string.noRevenu,R.layout.item_vertical_revenu);
                        return true;
                }
                return false;
                }


        });
        return view;
    }

    private void loadMessage(int id){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.noHistorique,new NoHistorique(id));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadHistorique(ArrayList<Transaction> liste,int id_layout){
        RecyclerView verticalRecyclerView = view.findViewById(R.id.vertical_RecyclerView);
        verticalRecyclerView.setAdapter(new DepenseAdapter(liste,id_layout,layout,this.getContext()));

    }

    private void loadHistorique(ArrayList<Transaction> liste, int id,int id_layout){
        if(liste.size()==0){
            loadMessage(id);

        }
        else{
            loadMessage(R.string.vide);
        }
        loadHistorique(liste,id_layout);
    }
}
