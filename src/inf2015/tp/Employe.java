/**
 * Employe - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class Employe {

    private static final String[] JOUR_SEMAINES = {"jour1", "jour2", "jour3",
        "jour4", "jour5", "weekend1", "weekend2"};
    private static final int MAX_MINUTES_BUREAU = 43 * 60;
    private static final int MIN_MINUTES_BUREAU_ADMIN = 36 * 60;
    private static final int MIN_MINUTES_BUREAU_NORMAL = 38 * 60;
    private static final int MAX_MINUTES_TELETRAV_ADMIN = 10 * 60;
    private static final int MIN_MINUTES_BUREAU_NORMAL_OUVRABLE = 6 * 60;
    private static final int MIN_MINUTES_BUREAU_ADMIN_OUVRABLE = 4 * 60;
    private static final int EMPLOYER_ADMINISTRATION_ID = 1000;
    private static final int EMPLOYER_PRODUCTION_ID = 2000;
    private static final int EMPLOYER_EXPLOITATION_ID = 2000;
    private int numeroEmployer = 0;
    private int minutesTeleTravail = 0;
    private int minutesJoursOuvrableBureau = 0;
    private int minutesWeekendBureau = 0;
    private ArrayList<Jour> semaines = new ArrayList<>();

    public boolean chargerFeuillerTemps(String cheminFichierInput) throws IOException {
        JSONObject jsonEmployer = JsonUtil.chargerJsonObjetDuFichier(cheminFichierInput);
        boolean fichierValide=true;
        
        try{
        this.numeroEmployer = jsonEmployer.getInt("numero_employe");
        }catch(JSONException e){
                fichierValide=false;
            
        }
       
        for (int i = 0; i < JOUR_SEMAINES.length && fichierValide; i++) {
            if (jsonEmployer.containsKey(JOUR_SEMAINES[i])) {
                Jour jour = Employe.obternirJourAPartirJSONArray(JOUR_SEMAINES[i], jsonEmployer.getJSONArray(JOUR_SEMAINES[i]));
                jour.analyserJour();
                this.semaines.add(jour);
                fichierValide=true;
            } else {
                System.out.println("Erreur, il manque un jour dans votre fichier d'entrée.");
              
                i = JOUR_SEMAINES.length; // break;<
                fichierValide=false;
            }
        }
        return fichierValide;
    }

    public void calculerFeuilleTemps() {
        for (Jour jour : this.semaines) {
            if (jour.estJourOuvrable()) {
                this.minutesJoursOuvrableBureau += jour.getMinutesBureau();
            } else {
                this.minutesWeekendBureau += jour.getMinutesBureau();

            }

            this.minutesTeleTravail += jour.getMinutesTeletravail();
        }
    }

    public void analyserFeuilleTemps() {

        if ((this.minutesJoursOuvrableBureau + this.minutesTeleTravail + this.minutesWeekendBureau) > MAX_MINUTES_BUREAU) {
            ErreurLog.Instance().ajoutErreur("L'employé n'a pas travaillé le nombre d'heures minimal.");
        }



        if (this.numeroEmployer < EMPLOYER_ADMINISTRATION_ID) {
            this.analyserFeuilleTempsAdministration();
        } else if (this.numeroEmployer < EMPLOYER_PRODUCTION_ID) {
            this.analyserFeuilleTempsProduction();
        } else if (this.numeroEmployer >= EMPLOYER_EXPLOITATION_ID) {
            this.analyserFeuilleTempsExploitation();
        }

    }

    private void analyserFeuilleTempsProductionEtExploitation(String typeEmployer) {

        if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_NORMAL) {
            ErreurLog.Instance().ajoutErreur("L'employé " + typeEmployer + " n'a pas travaillé le nombre d'heures minimal au bureau.");
        }


        if (this.minutesJoursOuvrableBureau < MIN_MINUTES_BUREAU_NORMAL_OUVRABLE) {
            ErreurLog.Instance().ajoutErreur("L'employé " + typeEmployer + " n'a pas travaillé le nombre d'heures minimal au bureau (jour ouvrable).");
        }

    }

    private void analyserFeuilleTempsAdministration() {
        if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_ADMIN) {
            ErreurLog.Instance().ajoutErreur("L'employé administration n'a pas travaillé le nombre d'heures minimal au bureau.");
        }

        if ((this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_ADMIN_OUVRABLE) {
            ErreurLog.Instance().ajoutErreur("L'employé administration n'a pas travaillé le nombre d'heures minimal au bureau (jour ouvrable).");
        }

        if (this.minutesTeleTravail > MAX_MINUTES_TELETRAV_ADMIN) {
            ErreurLog.Instance().ajoutErreur("L'employé administration a dépassé le nombre d'heures de télétravail.");
        }
    }

    private void analyserFeuilleTempsProduction() {
        analyserFeuilleTempsProductionEtExploitation("production");
    }

    private void analyserFeuilleTempsExploitation() {
        analyserFeuilleTempsProductionEtExploitation("exploitation");
    }

    private static Jour obternirJourAPartirJSONArray(String nomJour, JSONArray jsonProjets) {
        JSONObject jsonProjet;
        Iterator<JSONObject> it = jsonProjets.iterator();

        Jour jour = Jour.CreerJour(nomJour);


        while (it.hasNext()) {
            jsonProjet = it.next();

            jour.ajoutProjet(new Projet(jsonProjet.getInt("projet"), jsonProjet.getInt("minutes")));
        }

        return jour;
    }
}
