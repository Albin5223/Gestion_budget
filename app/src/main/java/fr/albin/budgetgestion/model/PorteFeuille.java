package fr.albin.budgetgestion.model;

public class PorteFeuille {

    float solde_initiale;
    float solde_courant;

    public PorteFeuille(float initial,float courant){
        solde_courant = courant;
        solde_initiale = initial;
    }

    public PorteFeuille(float nb){
        this(nb,nb);
    }

    public float[] getAllSolde(){
        return new float[]{solde_initiale, solde_courant};
    }


    public void addDepense (DepenseModel depense) throws SoldeInsuffisantError{
        if(solde_courant<depense.getMontant()){
            throw new SoldeInsuffisantError();
        }
        solde_courant = solde_courant - depense.getMontant();
    }

    public void setAllSolde(float init,float courant){
        solde_initiale = init;
        solde_courant = courant;
    }

    public void addArticle(Article article) throws SoldeInsuffisantError{
        if(solde_courant<article.getPrice()){
            throw new SoldeInsuffisantError();
        }
        solde_courant = solde_courant - article.getPrice();
    }

    public float getSolde_courant(){
        return solde_courant;
    }

    public float getSolde_initiale(){
        return solde_initiale;
    }



    public void setSoldeCourant(Float nb) {
        float l = solde_courant+nb;
        if(l<solde_initiale){
            solde_courant=l;
        }
        setAllSolde(l,l);
    }

    public String toLine(){
        return solde_initiale+DepenseModel.LINE_SEPARATOR+solde_courant+DepenseModel.LINE_SEPARATOR;
    }

    public void deleteArticle(Article article){
        solde_courant+= article.getPrice();
    }
}
