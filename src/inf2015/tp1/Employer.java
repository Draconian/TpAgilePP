/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp1;

import java.io.IOException;
import java.util.Iterator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author fdufault
 */
public class Employer {

    private int _numeroEmployer = 0;
    private int _minutesTeleTravail = 0;
    private int _minutesJoursOuvrableBureau = 0;
    private int _minutesWeekendBureau = 0;
    private static final int MAX_HEURES_BUREAU = 43;
    private static final int MIN_HEURES_BUREAU_ADMIN = 36;
    private static final int MIN_HEURES_BUREAU_NORMAL = 38;
    private static final int MAX_HEURES_TELETRAV_ADMIN = 10;
    private static final int MIN_HEURES_BUREAU_NORMAL_OUVRABLE = 6;
    private static final int MIN_HEURES_BUREAU_ADMIN_OUVRABLE = 4;
    private static final int TELETRAVAIL_ID_MIN = 900;

    public void AnalyserFeuilleTemps(String aCheminFichierInput, String aCheminFichierOutput) throws IOException {
        JSONObject jsonEmployer = JSONUtil.loadJSONObjectFichier(aCheminFichierInput);


        this._numeroEmployer = jsonEmployer.getInt("numero_employe");

        //Obtenir jours ouvrable
        this.obternirJourOuvrable(jsonEmployer.getJSONArray("jour1"));
        this.obternirJourOuvrable(jsonEmployer.getJSONArray("jour2"));
        this.obternirJourOuvrable(jsonEmployer.getJSONArray("jour3"));
        this.obternirJourOuvrable(jsonEmployer.getJSONArray("jour4"));
        this.obternirJourOuvrable(jsonEmployer.getJSONArray("jour5"));

        //Obtenir weekend
        this.obternirWeekend(jsonEmployer.getJSONArray("weekend1"));
        this.obternirWeekend(jsonEmployer.getJSONArray("weekend2"));

        //Analyser la feuille de temps
        JSONArray jsonErreur = this.AnalyserFeuilleTemps();

        JSONUtil.writeJSONArray(jsonErreur, aCheminFichierOutput);
        
    }

    private void obternirJourOuvrable(JSONArray jsonProjets) {

        JSONObject jsonProjet;
        int projetID, minutes;
        Iterator<JSONObject> it = jsonProjets.iterator();

        while (it.hasNext()) {
            jsonProjet = it.next();

            projetID = jsonProjet.getInt("projet");
            minutes = jsonProjet.getInt("minutes");

            if (projetID >= TELETRAVAIL_ID_MIN) {
                this._minutesTeleTravail += minutes;
            } else {
                this._minutesJoursOuvrableBureau += minutes;
            }

        }

    }

    private void obternirWeekend(JSONArray jsonProjets) {

        JSONObject jsonProjet;
        int projetID, minutes;
        Iterator<JSONObject> it = jsonProjets.iterator();

        while (it.hasNext()) {
            jsonProjet = it.next();

            projetID = jsonProjet.getInt("projet");
            minutes = jsonProjet.getInt("minutes");

            if (projetID >= TELETRAVAIL_ID_MIN) {
                this._minutesTeleTravail += minutes;
            } else {
                this._minutesWeekendBureau += minutes;
            }

        }
    }
    
    private JSONArray AnalyserFeuilleTemps()
    {
        JSONArray jsonErreurs = new JSONArray();
        
        if(((this._minutesJoursOuvrableBureau + this._minutesTeleTravail + this._minutesWeekendBureau) / 60) > MAX_HEURES_BUREAU)
        {
            jsonErreurs.add("L'employé n'a pas travaillé le nombre d'heures minimal.");
        }
        
        if (this._numeroEmployer < 1000) {
            //Analyser employé administration.
            
            this.AnalyserFeuilleTempsAdministration(jsonErreurs);
            
        } else {
            //Analyser employé normal.
            this.AnalyserFeuilleTempsNormal(jsonErreurs);
            
        }
        
        return jsonErreurs;
    }
    
    private JSONArray AnalyserFeuilleTempsNormal(JSONArray aJSONErreur)
    {
     
         if (((this._minutesWeekendBureau + this._minutesJoursOuvrableBureau) / 60) < MIN_HEURES_BUREAU_NORMAL) {
            aJSONErreur.add("L'employé normal n'a pas travaillé le nombre d'heures minimal au bureau.");
        }

        if ((this._minutesJoursOuvrableBureau / 60) < MIN_HEURES_BUREAU_NORMAL_OUVRABLE) {
            aJSONErreur.add("L'employé normal n'a pas travaillé le nombre d'heures minimal au bureau (jour ouvrable).");
        }
        
        
        return aJSONErreur;
    }
    
    private JSONArray AnalyserFeuilleTempsAdministration(JSONArray aJSONErreur)
    {
         if (((this._minutesWeekendBureau + this._minutesJoursOuvrableBureau)  / 60) < MIN_HEURES_BUREAU_ADMIN) {
            aJSONErreur.add("L'employé administration n'a pas travaillé le nombre d'heures minimal au bureau.");
        }

        if ((this._minutesJoursOuvrableBureau / 60) < MIN_HEURES_BUREAU_ADMIN_OUVRABLE) {
            aJSONErreur.add("L'employé administration n'a pas travaillé le nombre d'heures minimal au bureau (jour ouvrable).");
        }

        if (this._minutesTeleTravail > MAX_HEURES_TELETRAV_ADMIN) {
            aJSONErreur.add("L'employé administration a dépassé le nombre d'heures de télétravail.");
        }
        
        
        return aJSONErreur;
    }
}
