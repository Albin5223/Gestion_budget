package fr.albin.budgetgestion;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import fr.albin.budgetgestion.fragments.AddDepenseFragment;
import fr.albin.budgetgestion.fragments.ChoiceSoldeFragement;
import fr.albin.budgetgestion.fragments.HistoriqueFragment;
import fr.albin.budgetgestion.fragments.ModeCourseFragment;
import fr.albin.budgetgestion.fragments.NoHistorique;
import fr.albin.budgetgestion.fragments.OptionFragment;
import fr.albin.budgetgestion.fragments.PopUpVoirMesArticle;
import fr.albin.budgetgestion.fragments.SoldeFragment;
import fr.albin.budgetgestion.model.Article;
import fr.albin.budgetgestion.model.DepenseModel;
import fr.albin.budgetgestion.model.PorteFeuille;
import fr.albin.budgetgestion.model.RevenuModel;
import fr.albin.budgetgestion.model.Transaction;

public class MainActivity extends AppCompatActivity {

    private static final int RC_STORAGE_WRITE_PERMS = 100;
    public static ArrayList<Transaction> depenseListe;
    public static ArrayList<Transaction> revenuListe;
    public static ArrayList<Article> articleListe;
    public static PorteFeuille porteFeuille;
    public static PorteFeuille porteFeuilleCourse;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_STORAGE_WRITE_PERMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFromStorage();

            }
        }
    }

    private boolean checkWriteExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{WRITE_EXTERNAL_STORAGE},
                    RC_STORAGE_WRITE_PERMS);

            return true;
        }
        return false;
    }

    private void readFromStorage(){

        // 3 - CHECK PERMISSION

        if (checkWriteExternalStoragePermission()) return;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkWriteExternalStoragePermission();

        float[] infoSolde = ResearchFile.readSoldeFromStorage(this);
        porteFeuille = new PorteFeuille(infoSolde[0],infoSolde[1]);
        revenuListe = ResearchFile.readTransactionFromStorage(this,false);
        depenseListe = ResearchFile.readTransactionFromStorage(this,true);

        porteFeuilleCourse = new PorteFeuille(-1,-1);
        articleListe = ResearchFile.readCourseFromStorage(this,porteFeuilleCourse);

        //Injecter le fragment dans notre boite
        if(porteFeuille.getSolde_initiale()==-1){
            loadFragment(new ChoiceSoldeFragement(this),R.string.solde_page_title);

        }
        else{
            loadFragment(new SoldeFragment(depenseListe),R.string.solde_title);
        }


        //Importer la touchBar

        BottomNavigationView touchBar = findViewById(R.id.touchBarView);
        touchBar.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(porteFeuille.getSolde_initiale()==-1){
                    loadFragment(new ChoiceSoldeFragement(MainActivity.this),R.string.solde_page_title);
                    return true;
                }
                else{
                    switch (item.getItemId()){
                        case R.id.solde_page :
                            loadFragment(new SoldeFragment(depenseListe),R.string.solde_title);
                            return true;
                        case R.id.ajouter_page:
                            loadFragment(new AddDepenseFragment(MainActivity.this),R.string.ajouter_title);
                            return true;
                        case R.id.option_page:
                            loadFragment(new OptionFragment(MainActivity.this),R.string.option_title);
                            return true;
                        case R.id.historique_page:
                            loadFragment(new HistoriqueFragment(depenseListe),R.string.historique_title);
                            return true;
                        case R.id.AVENIR_page:
                            if(porteFeuilleCourse.getSolde_initiale()==-1){
                                loadFragment(new NoHistorique(R.string.noActivite),R.string.noActivite);
                            }
                            else{
                                loadFragment(new ModeCourseFragment(MainActivity.this),R.string.option_mode_course_title_icon);
                            }

                            return true;
                    }
                    return false;
                }

            }

        });




    }

    public void loadFragment(Fragment fragment, int id) {
        TextView title = findViewById(R.id.page_title);
        title.setText(getResources().getString(id));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ContenerFragment,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void removeCourse(){
        porteFeuilleCourse.setAllSolde(-1,-1);
        articleListe.clear();
    }
}