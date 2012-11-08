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

public class TpAgile {

    public static void main(String[] args) {
        Employe employer = new Employe();

        try {
            employer.chargerFeuillerTemps(args[0]);
            employer.calculerFeuilleTemps();
            employer.analyserFeuilleTemps();
            ErreurLog.Instance().ecrireErreurDansFichier(args[1]);
        } catch (IOException ex) {
            Logger.getLogger(TpAgile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
