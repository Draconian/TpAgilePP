/**
 * ErreurLog - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import java.io.IOException;
import net.sf.json.JSONArray;

public class ErreurLog {

    private static ErreurLog ERREUR_LOG_INSTANCE = new ErreurLog();
    private JSONArray erreurs = new JSONArray();

    public void ajoutErreur(String aErreurMessage) {
        this.erreurs.add(aErreurMessage);
        System.out.println(aErreurMessage);
    }

    public void ecrireErreur(String CheminFichier) throws IOException {
        JSONUtil.writeJSONArray(this.erreurs, CheminFichier);
    }

    @Override
    public String toString() {
        //toString(5) => 5 espaces pour chaque indentation.
        return this.erreurs.toString(5);
    }

    public void effacerTout() {
        this.erreurs.clear();
    }

    public static ErreurLog Instance() {
        return ERREUR_LOG_INSTANCE;
    }
}
