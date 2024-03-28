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

import fr.albin.budgetgestion.MainActivity;
import fr.albin.budgetgestion.R;
import fr.albin.budgetgestion.adapter.ArticleAdapter;


public class PopUpVoirMesArticle extends Fragment {

    int layout;
    View view;
    MainActivity mainActivity;

    public PopUpVoirMesArticle(MainActivity ma){
        mainActivity = ma;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = R.layout.fragment_voir_mes_articles;

        view = inflater.inflate(layout,container,false);

        if(MainActivity.articleListe.size()==0){
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.noArticle,new NoHistorique(R.string.noArticle));
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else{
            RecyclerView verticalRecyclerView = view.findViewById(R.id.mes_articles_RecyclerView);
            verticalRecyclerView.setAdapter(new ArticleAdapter(MainActivity.articleListe,R.layout.item_vertical_articles,layout,mainActivity));
        }

        return view;

    }


}
