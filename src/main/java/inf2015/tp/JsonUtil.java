/**
 * JsonUtil - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

    private static String SAUT_LIGNE = System.getProperty("line.separator");
    private static Charset ENCODAGE = StandardCharsets.UTF_8;

    public static JSONObject chargerJsonObjetDuFichier(String fichierChemin) throws IOException {
        JSONObject jsonObject;

        String jsonString = JsonUtil.chargerContenuDuFichier(fichierChemin);

        jsonObject = JSONObject.fromObject(jsonString);

        return jsonObject;
    }

    public static JSONArray chargerJsonArrayDuFichier(String fichierChemin) throws IOException {
        JSONArray jsonArray;

        String jsonString = JsonUtil.chargerContenuDuFichier(fichierChemin);
        jsonArray = JSONArray.fromObject(jsonString);

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

        Path pathFichier = Paths.get(fichierChemin);
        StringBuilder jsonFileBuffer = new StringBuilder();

        Scanner scannerJSON = new Scanner(pathFichier, ENCODAGE.name());

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
