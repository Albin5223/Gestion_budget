package fr.albin.budgetgestion.model;

import java.util.Scanner;

public class Transaction {

    //True = depense
    //False = revenu
    boolean depense;
    public String libele;
    public float montant;
    public String date;
    public String description;

    public static String DEFAULT_SEPARATOR =";";
    public static String LINE_SEPARATOR ="!";

    public Transaction (String n, float m,String d,String des,boolean b) {
        libele = n;
        montant = m;
        date = d;
        description = des;
        depense = b;
    }

    public Transaction(String s,boolean b){
        depense = b;
        Scanner scan = new Scanner(s);
        scan.useDelimiter(DEFAULT_SEPARATOR);
        libele = scan.next();
        String m = scan.next();
        date = scan.next();
        description = scan.next();

        montant = StringtoFloat(m);

        scan.close();
    }

    private float StringtoFloat(String m) {
        float f = new Float(m);
        return f;
    }

    public String toLine(){
        String s = libele+DEFAULT_SEPARATOR+montant+DEFAULT_SEPARATOR+date+DEFAULT_SEPARATOR+description+LINE_SEPARATOR;
        return s;
    }

    public boolean isDepense(){
        return depense;
    }
}
