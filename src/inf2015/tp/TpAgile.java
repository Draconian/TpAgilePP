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

public class TpAgile {

    public static void main(String[] args) {
        boolean fichierValide=false;
        Employe employer = new Employe();
        JSONArray vide = new JSONArray();

        try {
           fichierValide = employer.chargerFeuillerTemps(args[0]);
        
            employer.calculerFeuilleTemps();
            employer.analyserFeuilleTemps();
            if(fichierValide==true){
            ErreurLog.Instance().ecrireErreurDansFichier(args[1]);
            }else{
                JsonUtil.ecrireJsonArrayDansFichier(vide, args[1]);
                ErreurLog.Instance();
            }
        } catch (IOException ex) {
            Logger.getLogger(TpAgile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
