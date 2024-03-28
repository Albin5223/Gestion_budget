package fr.albin.budgetgestion;


import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import fr.albin.budgetgestion.model.Article;
import fr.albin.budgetgestion.model.DepenseModel;
import fr.albin.budgetgestion.model.PorteFeuille;
import fr.albin.budgetgestion.model.Transaction;

public class ResearchFile {


    private static final String FILENAME_REVENU = "mesRevenus.txt";
    private static final String FILENAME_DEPENSE = "mesDepenses.txt";
    private static final String FILENAME_SOLDE = "mesSoldes.txt";
    private static final String FILENAME_COURSE = "mesCourses.txt";

    private static final String FOLDERNAME = "GestionBudget";

    private static File createOrGetFile(File destination, String fileName, String folderName){

        File folder = new File(destination, folderName);

        return new File(folder, fileName);
    }

    private static String readOnFile(Context context, File file){

        String result ="";
        if (file.exists()) {

            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(file));
                try {

                    String line = br.readLine();
                    while (line != null) {
                        result+=line;
                        line = br.readLine();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    br.close();
                }
            }
            catch (IOException e) {
                Toast.makeText(context, context.getString(R.string.error_read_happened), Toast.LENGTH_LONG).show();
            }
        }

        return result;
    }

    // ---

    private static void writeOnFile(Context context, String text[], File file){

        try {
            file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);

            Writer w = new BufferedWriter(new OutputStreamWriter(fos));


            try {
                for (int i = 0;i<text.length;i++){
                    w.write(text[i]);

                }
                w.flush();
                fos.getFD().sync();

            } finally {
                w.close();
                Toast.makeText(context, context.getString(R.string.saved), Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            Toast.makeText(context, context.getString(R.string.error_happened), Toast.LENGTH_LONG).show();
        }
    }

    public static String getTextFromStorage(File rootDestination, Context context, String fileName, String folderName){

        File file = createOrGetFile(rootDestination, fileName, folderName);

        return readOnFile(context, file);
    }

    private static void setTextInStorage(File rootDestination, Context context, String fileName, String folderName, String[] text){

        File file = createOrGetFile(rootDestination, fileName, folderName);
        System.out.println("Chemin F --->" + file.getAbsolutePath());
        writeOnFile(context, text, file);
    }

    // ----------------------------------
    // EXTERNAL STORAGE
    // ----------------------------------


    public static boolean isExternalStorageWritable() {

        String state = Environment.getExternalStorageState();

        return (Environment.MEDIA_MOUNTED.equals(state));
    }

    public static boolean isExternalStorageReadable() {

        String state = Environment.getExternalStorageState();

        return (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }



    private static void writeOnExternalStorage(Context context,String[] texte,String FILE) {

        if (isExternalStorageWritable()) {
            Toast.makeText(context,R.string.authorization_write_accepted, Toast.LENGTH_LONG).show();

            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

            System.out.println("Chemin  -- >"+directory.getPath());
            setTextInStorage(directory, context, FILE, FOLDERNAME, texte);

        } else {
            Toast.makeText(context,R.string.authorization_write_denied, Toast.LENGTH_LONG).show();
        }
    }

    public static void writeTransactionOnExternalStorage(Context context,ArrayList<Transaction> liste,boolean depense) {

        int taille = liste.size();
        String[] tab =new String[taille];

        for (int i = 0;i<taille;i++){
            tab[i] = liste.get(i).toLine();
        }
        if(depense){
            writeOnExternalStorage(context,tab,FILENAME_DEPENSE);
        }
        else{
            writeOnExternalStorage(context,tab,FILENAME_REVENU);
        }
    }




    public static ArrayList<Transaction> readTransactionFromStorage(Context context,boolean depense) {
        if (isExternalStorageReadable()) {
            File directory;

            directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

            String texte = "";
            if(depense){
                texte = getTextFromStorage(directory, context, FILENAME_DEPENSE, FOLDERNAME);
            }
            else{
                texte = getTextFromStorage(directory,context,FILENAME_REVENU,FOLDERNAME);
            }

            ArrayList<Transaction> liste = new ArrayList<>();
            Scanner scan = new Scanner(texte);
            scan.useDelimiter(DepenseModel.LINE_SEPARATOR);

            while(scan.hasNext()){
                String line = scan.next();
                liste.add(new Transaction(line,depense));
            }
            scan.close();
            return liste;
        }

        return new ArrayList<Transaction>();
    }


    public static void removeCourseOnExternalStorage(Context context){
        String[] tab = {""};
        writeOnExternalStorage(context,tab,FILENAME_COURSE);
    }

    public static void writeCourseOnExternalStorage(Context context,ArrayList<Article> listeCourse,PorteFeuille porteFeuille ) {

        int taille = listeCourse.size()+1;
        String[] tab =new String[taille];
        tab[0] = porteFeuille.toLine();

        int compt = 0;
        for (int i = 1;i<taille;i++){
            tab[i] = listeCourse.get(compt).toLine();
            compt++;
        }

        writeOnExternalStorage(context,tab,FILENAME_COURSE);
    }

    public static ArrayList<Article> readCourseFromStorage(Context context, PorteFeuille porteFeuille) {
        if (isExternalStorageReadable()) {
            File directory;

            directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

            String texte = "";

            texte = getTextFromStorage(directory,context,FILENAME_COURSE,FOLDERNAME);


            ArrayList<Article> liste = new ArrayList<>();
            Scanner scan = new Scanner(texte);
            scan.useDelimiter(DepenseModel.LINE_SEPARATOR);

            if(scan.hasNext()){
                porteFeuille.setAllSolde(new Float(scan.next()),new Float(scan.next()));
                while(scan.hasNext()){
                    String line = scan.next();
                    liste.add(new Article(line));
                }
                scan.close();
                return liste;
            }
        }
        return new ArrayList<Article>();
    }




    public static float[] readSoldeFromStorage(Context context) {
        float[] tab = new float[2];
        tab[0]=-1;
        tab[0]=-1;
        if (isExternalStorageReadable()) {
            File directory;

            directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            String texte = getTextFromStorage(directory, context, FILENAME_SOLDE, FOLDERNAME);

            ArrayList<DepenseModel> liste = new ArrayList<>();
            Scanner scan = new Scanner(texte);
            scan.useDelimiter(DepenseModel.DEFAULT_SEPARATOR);
            int i=0;
            while(scan.hasNext()){
                float line = new Float(scan.next());
                tab[i] = line;
                i++;
            }
            scan.close();
            return tab;
        }

        return tab;
    }

    public static void writeSoldeOnExternalStorage(Context context,float[] liste) {

        int taille = liste.length;
        String[] tab =new String[1];
        tab[0]="";

        for (int i = 0;i<taille;i++){
            tab[0]+=liste[i]+DepenseModel.DEFAULT_SEPARATOR;
        }
        if(tab[0].length()!=0){
            tab[0] = tab[0].substring(0,tab[0].length()-1);
        }
        writeOnExternalStorage(context,tab,FILENAME_SOLDE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String recupererData() {
        LocalDateTime datetime = LocalDateTime.now();
        String date_Full = datetime.toString();
        Scanner scanner = new Scanner(date_Full);
        scanner.useDelimiter("T");
        String date = scanner.next();
        scanner.close();

        Scanner scan = new Scanner(date);
        scan.useDelimiter("-");

        String result ="";
        while(scan.hasNext()){
            result = "/"+scan.next()+result;
        }

        result = result.substring(1);

        return result;
    }




}
