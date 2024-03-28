package fr.albin.budgetgestion.model;

import fr.albin.budgetgestion.R;

public enum ObjetAajouter {
    REVENU,ARTICLE;

    private static int[] libeleEditText={R.string.ajouter_libelle_revenu,R.string.ajouter_libelle_article};
    private static int[] montantEditText={R.string.ajouter_Montant_revenu,R.string.ajouter_Montant_article};
    private static int[] titleTextView = {R.string.option_ajouter_salaire,R.string.option_ajouter_article};
    private static int[] buttonValiderTitle= {R.string.solde_page_valider,R.string.valider_article};

    public int getLibelleTitle(){
        return libeleEditText[this.ordinal()];
    }

    public int getMontantTitle(){
        return montantEditText[this.ordinal()];
    }

    public int getPageTitle(){
        return titleTextView[this.ordinal()];
    }

    public int getTitleButton(){
        return buttonValiderTitle[this.ordinal()];
    }
}
