/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author fdufault
 */
public class Employer {
    private static final String[] JOUR_SEMAINES = {"jour1", "jour2", "jour3", "jour4", "jour5", "weekend1", "weekend2"};
    private static final int MAX_MINUTES_BUREAU = 43 * 60;
    private static final int MIN_MINUTES_BUREAU_ADMIN = 36 * 60;
    private static final int MIN_MINUTES_BUREAU_NORMAL = 38 * 60;
    private static final int MAX_MINUTES_TELETRAV_ADMIN = 10 * 60;
    private static final int MIN_MINUTES_BUREAU_NORMAL_OUVRABLE = 6 * 60;
    private static final int MIN_MINUTES_BUREAU_ADMIN_OUVRABLE = 4 * 60;
    private static final int EMPLOYER_ADMINISTRATION_ID = 1000;
    
    private int _numeroEmployer = 0;
    private int _minutesTeleTravail = 0;
    private int _minutesJoursOuvrableBureau = 0;
    private int _minutesWeekendBureau = 0;
    private ArrayList<Jour> _semaines = new ArrayList<>();


    /**
     * Analyser feuille de temps JSON.
     *
     * @param aCheminFichierInput
     * @param aCheminFichierOutput
     * @throws IOException
     */
    public void chargerFeuillerTemps(String aCheminFichierInput) throws IOException {
        JSONObject jsonEmployer = JSONUtil.loadJSONObjectFichier(aCheminFichierInput);

        this._numeroEmployer = jsonEmployer.getInt("numero_employe");
           //Very nice commit
        for (int i = 0; i < JOUR_SEMAINES.length; i++) {
            if (jsonEmployer.containsKey(JOUR_SEMAINES[i])) {
                Jour jour = this.obternirJour(JOUR_SEMAINES[i], jsonEmployer.getJSONArray(JOUR_SEMAINES[i]));
                jour.analyserJour();
                this._semaines.add(jour);
            } else {
                System.out.println("Jour manquant: " + JOUR_SEMAINES[i]);
                ErreurLog.Instance().effacerTout();
                i = JOUR_SEMAINES.length; // break;
            }
        }
    }

    public void calculerFeuilleTemps() {
        for (Jour jour : this._semaines) {
            if (jour.estJourOuvrable()) {
                this._minutesJoursOuvrableBureau += jour.getMinutesBureau();
            } else {
                this._minutesWeekendBureau += jour.getMinutesBureau();

            }

            this._minutesTeleTravail += jour.getMinutesTeletravail();
        }
    }

    public void analyserFeuilleTemps() {

        if ((this._minutesJoursOuvrableBureau + this._minutesTeleTravail + this._minutesWeekendBureau) > MAX_MINUTES_BUREAU) {
            ErreurLog.Instance().ajoutErreur("L'employé n'a pas travaillé le nombre d'heures minimal.");
        }

        if (this._numeroEmployer < EMPLOYER_ADMINISTRATION_ID) {
            this.analyserFeuilleTempsAdministration();

        } else {
            this.analyserFeuilleTempsNormal();
        }
    }

    private void analyserFeuilleTempsNormal() {

        if ((this._minutesWeekendBureau + this._minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_NORMAL) {
            ErreurLog.Instance().ajoutErreur("L'employé normal n'a pas travaillé le nombre d'heures minimal au bureau.");
        }

        if (this._minutesJoursOuvrableBureau < MIN_MINUTES_BUREAU_NORMAL_OUVRABLE) {
            ErreurLog.Instance().ajoutErreur("L'employé normal n'a pas travaillé le nombre d'heures minimal au bureau (jour ouvrable).");
        }

    }

    private void analyserFeuilleTempsAdministration() {
        if ((this._minutesWeekendBureau + this._minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_ADMIN) {
            ErreurLog.Instance().ajoutErreur("L'employé administration n'a pas travaillé le nombre d'heures minimal au bureau.");
        }

        if ((this._minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_ADMIN_OUVRABLE) {
            ErreurLog.Instance().ajoutErreur("L'employé administration n'a pas travaillé le nombre d'heures minimal au bureau (jour ouvrable).");
        }

        if (this._minutesTeleTravail > MAX_MINUTES_TELETRAV_ADMIN) {
            ErreurLog.Instance().ajoutErreur("L'employé administration a dépassé le nombre d'heures de télétravail.");
        }
    }

    private static Jour obternirJour(String aNomJour, JSONArray jsonProjets) {
        JSONObject jsonProjet;
        Iterator<JSONObject> it = jsonProjets.iterator();

        Jour jour = Jour.CreerJour(aNomJour);


        while (it.hasNext()) {
            jsonProjet = it.next();

            jour.ajoutProjet(new Projet(jsonProjet.getInt("projet"), jsonProjet.getInt("minutes")));
        }

        return jour;
    }
}
