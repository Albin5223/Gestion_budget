package fr.albin.budgetgestion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.albin.budgetgestion.MainActivity;
import fr.albin.budgetgestion.R;
import fr.albin.budgetgestion.ResearchFile;
import fr.albin.budgetgestion.fragments.PopUpVoirMesArticle;
import fr.albin.budgetgestion.model.Article;
import fr.albin.budgetgestion.model.Transaction;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.FragViewHolder>{

    ArrayList<Article> listeArticle;
    private int idRecycler;

    private int idLayout;
    MainActivity mainActivity;

    public ArticleAdapter(ArrayList<Article> liste, int recycler, int layout, MainActivity mainActivity){
        listeArticle = liste;
        idRecycler = recycler;
        idLayout = layout;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public FragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(idRecycler,parent,false);
        return new ArticleAdapter.FragViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragViewHolder holder, int position) {
        Article article = listeArticle.get(position);


        holder.article_libelle.setText(article.getName());
        holder.article_montant.setText(article.getPrice()+" €");

        holder.article_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.porteFeuilleCourse.deleteArticle(article);
                listeArticle.remove(article);
                ResearchFile.writeCourseOnExternalStorage(mainActivity,listeArticle,MainActivity.porteFeuilleCourse);
                mainActivity.loadFragment(new PopUpVoirMesArticle(mainActivity),R.string.subtitle_voir_mes_articles);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listeArticle.size();
    }

    class FragViewHolder extends RecyclerView.ViewHolder{
        View view;

        public TextView article_libelle;
        public TextView article_montant;
        public ImageView article_remove;

        public FragViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            article_libelle = view.findViewById(R.id.item_article_libelle_TextView);
            article_montant = view.findViewById(R.id.item_article_montant_TextView);
            article_remove = view.findViewById(R.id.item_article_remove_article_ImageView);

        }


    }
}
