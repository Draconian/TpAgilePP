/**
 * TPAgile - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;

public class TpAgile {

    public static void main(String[] args) {
        Employe employer = new Employe();

        try {
            employer.chargerFeuillerTemps(args[0]);
            employer.calculerFeuilleTemps();
            employer.analyserFeuilleTemps();

            ErreurLog.Instance().ecrireErreurDansFichier(args[1]);
        } catch (IOException | JSONException ex) {
            ecrireErreurDansFichier(args[1], ex);
        }
    }

    private static void ecrireErreurDansFichier(String cheminFichier, Exception exception) {
        
        System.out.println("Exception message:" + exception.getMessage());
        
        try {
            ErreurLog.Instance().effacerTout();
            ErreurLog.Instance().ecrireErreurDansFichier(cheminFichier);
        } catch (IOException ex) {
            Logger.getLogger(TpAgile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
