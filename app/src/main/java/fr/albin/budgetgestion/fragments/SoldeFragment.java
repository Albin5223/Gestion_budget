package fr.albin.budgetgestion.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.albin.budgetgestion.MainActivity;
import fr.albin.budgetgestion.model.DepenseModel;
import fr.albin.budgetgestion.R;
import fr.albin.budgetgestion.adapter.DepenseAdapter;
import fr.albin.budgetgestion.adapter.DepenseItemDecoration;
import fr.albin.budgetgestion.model.Transaction;

public class SoldeFragment extends Fragment {

    ArrayList<Transaction> liste;

    public SoldeFragment(ArrayList<Transaction> l){
        liste = l;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = R.layout.solde_layout;

        View view = inflater.inflate(layout,container,false);

        //Recupérer le RecyclerView de solde_layout correspondant aux dernieres dépenses
        if(liste.size()==0){
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.noDepense,new NoHistorique(R.string.noDepense));
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else{
            RecyclerView verticalRecyclerView = view.findViewById(R.id.lastDepense_RecyclerView);
            verticalRecyclerView.setAdapter(new DepenseAdapter(liste,R.layout.item_vertical_depense,layout,this.getContext()));
            verticalRecyclerView.addItemDecoration(new DepenseItemDecoration());
        }





        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();;

        transaction.replace(R.id.FragmentJaugeSolde,new JaugeFragment(MainActivity.porteFeuille));
        transaction.addToBackStack(null);
        transaction.commit();



        return view;
    }
}
