package fr.albin.budgetgestion.model;

import java.util.Scanner;

public class DepenseModel extends Transaction{



    public DepenseModel(String n, float m,String d,String des){
        super(n,m,d,des,true);
    }

    public DepenseModel(String s){
        super(s,true);
    }



    public float getMontant(){
        return montant;
    }


}
