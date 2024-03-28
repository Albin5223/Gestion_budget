package fr.albin.budgetgestion.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.albin.budgetgestion.model.DepenseModel;
import fr.albin.budgetgestion.fragments.DepensePopUp;
import fr.albin.budgetgestion.R;
import fr.albin.budgetgestion.model.Transaction;

public class DepenseAdapter extends RecyclerView.Adapter<DepenseAdapter.ViewHolder> {


    private int idRecycler;
    public Context context;
    ArrayList<Transaction> depenseList;
    private int idLayout;

    public DepenseAdapter(ArrayList<Transaction>l, int recycler, int layout,Context cont){
        idRecycler = recycler;
        this.idLayout = layout;
        depenseList = l;
        context = cont;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(idRecycler,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Recuperer les information de la transaction
        Transaction currentDepense = depenseList.get(position);

        //Metre a jour les informations de la plante
        holder.depenseName.setText(currentDepense.libele);
        holder.depenseMontant.setText(""+currentDepense.montant+" €");
        holder.depenseDate.setText(currentDepense.date);


        //Interaction lors du click sur une plant
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DepensePopUp popUp = new DepensePopUp(DepenseAdapter.this,currentDepense);
               popUp.show();
           }
       });
    }

    @Override
    public int getItemCount() {
        if(idLayout == R.layout.solde_layout){
            return Math.min(5,depenseList.size());
        }
        return depenseList.size();
    }

    //boite pour ranger tous les composants à controler
    class ViewHolder extends RecyclerView.ViewHolder{
        View view;

        public TextView depenseName;
        public TextView depenseMontant;
        public TextView depenseDate;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            depenseName = (TextView) view.findViewById(R.id.libelle_TextView);
            depenseMontant = (TextView)view.findViewById(R.id.montant_TextView);
            depenseDate = (TextView)view.findViewById(R.id.date_TextView);



        }
    }
}
