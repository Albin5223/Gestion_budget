package fr.albin.budgetgestion.model;

import java.util.Scanner;

public class Article {

    private float prix;
    private String name;

    public Article(String nom, float prix){
        name = nom;
        this.prix = prix;
    }

    public Article(String line){
        Scanner scan = new Scanner(line);
        scan.useDelimiter(DepenseModel.DEFAULT_SEPARATOR);

        name = scan.next();
        prix = new Float(scan.next());
    }

    public String toLine(){
        return name+DepenseModel.DEFAULT_SEPARATOR+prix+DepenseModel.LINE_SEPARATOR;
    }

    public float getPrice(){
        return prix;
    }

    public String getName(){
        return name;
    }
}
