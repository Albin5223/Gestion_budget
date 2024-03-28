package fr.albin.budgetgestion.model;

public class RevenuModel extends Transaction{

    public RevenuModel(String n, float m, String d, String des, boolean b) {
        super(n, m, d, des, b);
    }

    public RevenuModel(String s, boolean b) {
        super(s, b);
    }
}
