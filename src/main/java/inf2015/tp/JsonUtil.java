/**
 * JsonUtil - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import inf2015.tp.erreur.FeuilleTempsException;
import java.io.*;
import java.util.Scanner;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

    private static String SAUT_LIGNE = System.getProperty("line.separator");

    public static JSONObject chargerJsonObjetDuFichier(String fichierChemin) throws FeuilleTempsException {
        JSONObject jsonObject = null;
        try {
            String jsonString = JsonUtil.chargerContenuDuFichier(fichierChemin);

            jsonObject = JSONObject.fromObject(jsonString);
        } catch (Exception e) {
            throw new FeuilleTempsException("Impossible de charger le fichier JSON.", e);
        }

        return jsonObject;
    }

    public static JSONArray chargerJsonArrayDuFichier(String fichierChemin) throws FeuilleTempsException {
        JSONArray jsonArray = null;
        try {
            String jsonString = JsonUtil.chargerContenuDuFichier(fichierChemin);
            jsonArray = JSONArray.fromObject(jsonString);
        } catch (Exception e) {
            throw new FeuilleTempsException("Impossible de charger le fichier JSON.", e);
        }
        return jsonArray;
    }

    public static void ecrireJsonObjetDansFichier(JSONObject jsObject, String fichierChemin) throws IOException {
        //toString(5) => 5 espaces pour chaque indentation.
        String jsonTexte = jsObject.toString(5);

        JsonUtil.ecrireTexteDansFichier(jsonTexte, fichierChemin);
    }

    public static void ecrireJsonArrayDansFichier(JSONArray jsArray, String fichierChemin) throws IOException {
        //toString(5) => 5 espaces pour chaque indentation.
        String jsonTexte = jsArray.toString(5);

        JsonUtil.ecrireTexteDansFichier(jsonTexte, fichierChemin);
    }

    private static String chargerContenuDuFichier(String fichierChemin) throws IOException {
        StringBuilder jsonFileBuffer = new StringBuilder();
        File fichier = new File(fichierChemin);
        Scanner scannerJSON = new Scanner(fichier, "UTF-8");

        while (scannerJSON.hasNext()) {
            String ligne = scannerJSON.nextLine();
            jsonFileBuffer.append(ligne).append(SAUT_LIGNE);
        }

        return jsonFileBuffer.toString();
    }

    private static void ecrireTexteDansFichier(String texte, String fichierChemin) throws IOException {
        BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichierChemin), "UTF8"));
        bWriter.write(texte);

        bWriter.close();
    }
}
