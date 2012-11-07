/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author fdufault
 */
public class JSONUtil {

    private static Charset ENCODAGE = StandardCharsets.UTF_8;

    /**
     * Charger fichier text et convertie en JSONObject.
     * @param aFichierChemin Chemin complet vers le fichier texte
     * @return JSONObject à partir du fichier texte.
     * @throws IOException
     */
    public static JSONObject loadJSONObjectFichier(String aFichierChemin) throws IOException {
        JSONObject jsonObject;

        String jsonString = JSONUtil.loadStringFichier(aFichierChemin);

        jsonObject = JSONObject.fromObject(jsonString);

        return jsonObject;
    }

    /**
     * Charger fichier text et convertie en JSONArray.
     * @param aFichierChemin Chemin complet vers le fichier texte
     * @return  JSONArray à partir du fichier texte.
     * @throws IOException
     */
    public static JSONArray loadJSONArrayFichier(String aFichierChemin) throws IOException {
        JSONArray jsonArray;

        String jsonString = JSONUtil.loadStringFichier(aFichierChemin);

        jsonArray = JSONArray.fromObject(jsonString);

        return jsonArray;
    }

    private static String loadStringFichier(String aFichierChemin) throws IOException {
        String sautLigne = System.getProperty("line.separator");

        Path pathFichier = Paths.get(aFichierChemin);

        StringBuilder jsonFileBuffer = new StringBuilder();


        Scanner scannerJSON = new Scanner(pathFichier, ENCODAGE.name());


        while (scannerJSON.hasNext()) {
            String ligne = scannerJSON.nextLine();
            jsonFileBuffer.append(ligne).append(sautLigne);
        }

        return jsonFileBuffer.toString();
    }

    /**
     * Écrire l'objet JSON dans un fichier texte, avec indentation.
     * @param jsObject Le JSON object à écrire
     * @param aFichierChemin Chemin complet vers fichier.
     * @throws IOException 
     */
    public static void writeJSONOject(JSONObject jsObject, String aFichierChemin) throws IOException {
        //toString(5) => 5 espaces pour chaque indentation.
        String jsonTexte = jsObject.toString(5);
        
        JSONUtil.writeString(jsonTexte, aFichierChemin);
    }

    /**
     * Écrire l'array JSON dans un fichier texte, avec indentation.
     * @param jsArray Le JSON array à écrire.
     * @param aFichierChemin Chemin complet vers fichier.
     * @throws IOException 
     */
    public static void writeJSONArray(JSONArray jsArray, String aFichierChemin) throws IOException {
        //toString(5) => 5 espaces pour chaque indentation.
        String jsonTexte = jsArray.toString(5);

        JSONUtil.writeString(jsonTexte, aFichierChemin);
    }

    private static void writeString(String aTexte, String aFichierChemin) throws IOException {
        
        BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter (new FileOutputStream(aFichierChemin),"UTF8"));
        bWriter.write(aTexte);
        
        bWriter.close();
        
        //Files.write(fichierPath, aTexte.getBytes(), StandardOpenOption.CREATE);
    }
}
