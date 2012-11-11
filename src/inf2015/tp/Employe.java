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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private String cheminFichierFeuilleTemps;
    private String cheminFichierErreur;

    public Employe(String cheminFichierFeuilleTemps, String cheminFichierErreur) {
        this.cheminFichierFeuilleTemps = cheminFichierFeuilleTemps;
        this.cheminFichierErreur = cheminFichierErreur;
    }

    public boolean approuverFeuilleDeTemps() {
        try {
            this.chargerFeuillerTemps();
            this.calculerFeuilleTemps();
            this.analyserFeuilleTemps();
        } catch (JSONException | IOException ex) {
            ErreurJournal.Instance().effacerTout();
            System.out.println("Erreur dans le fichier JSON d'entrée: " + ex.getMessage());
        }

        return this.estFeuilleTempsValide();
    }

    private void chargerFeuillerTemps() throws IOException, JSONException {
        JSONObject jsonEmployer = JsonUtil.chargerJsonObjetDuFichier(this.cheminFichierFeuilleTemps);
        this.numeroEmployer = jsonEmployer.getInt("numero_employe");

        for (int i = 0; i < JOUR_SEMAINES.length; i++) {
            this.chargerJourFeuilleTemps(JOUR_SEMAINES[i], jsonEmployer);
        }
    }

    private void chargerJourFeuilleTemps(String nomJour, JSONObject jsonEmployer) throws JSONException {
        Jour jour;

        if (jsonEmployer.containsKey(nomJour)) {
            jour = Employe.chargerJourAPartirJSONArray(nomJour, jsonEmployer.getJSONArray(nomJour));
            jour.analyserJour();
            this.semaines.add(jour);
        } else {
            throw new JSONException("Erreur, il manque un jour dans votre fichier d'entrée.");
        }
    }

    private void calculerFeuilleTemps() {
        for (Jour jour : this.semaines) {
            if (jour.estJourOuvrable()) {
                this.minutesJoursOuvrableBureau += jour.getMinutesBureau();
            } else {
                this.minutesWeekendBureau += jour.getMinutesBureau();

            }
            this.minutesTeleTravail += jour.getMinutesTeletravail();
        }
    }

    private void analyserFeuilleTemps() {

        if ((this.minutesJoursOuvrableBureau + this.minutesTeleTravail + this.minutesWeekendBureau) > MAX_MINUTES_BUREAU) {
            ErreurJournal.Instance().ajoutErreur("L'employé n'a pas travaillé le nombre d'heures minimal.");
        }

        if (this.numeroEmployer < EMPLOYER_ADMINISTRATION_ID) {
            this.analyserFeuilleTempsAdministration();
        } else if (this.numeroEmployer < EMPLOYER_PRODUCTION_ID) {
            this.analyserFeuilleTempsProduction();
        } else if (this.numeroEmployer >= EMPLOYER_EXPLOITATION_ID) {
            this.analyserFeuilleTempsExploitation();
        }

    }

    private void analyserFeuilleTempsAdministration() {
        if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_ADMIN) {
            ErreurJournal.Instance().ajoutErreur("L'employé administration n'a pas travaillé le nombre d'heures minimal au bureau.");
        }

        if ((this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_ADMIN_OUVRABLE) {
            ErreurJournal.Instance().ajoutErreur("L'employé administration n'a pas travaillé le nombre d'heures minimal au bureau (jour ouvrable).");
        }

        if (this.minutesTeleTravail > MAX_MINUTES_TELETRAV_ADMIN) {
            ErreurJournal.Instance().ajoutErreur("L'employé administration a dépassé le nombre d'heures de télétravail.");
        }
    }

    private void analyserFeuilleTempsProduction() {
        analyserFeuilleTempsProductionEtExploitation("production");
    }

    private void analyserFeuilleTempsExploitation() {
        analyserFeuilleTempsProductionEtExploitation("exploitation");
    }

    private void analyserFeuilleTempsProductionEtExploitation(String typeEmployer) {

        if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_NORMAL) {
            ErreurJournal.Instance().ajoutErreur("L'employé " + typeEmployer + " n'a pas travaillé le nombre d'heures minimal au bureau.");
        }

        if (this.minutesJoursOuvrableBureau < MIN_MINUTES_BUREAU_NORMAL_OUVRABLE) {
            ErreurJournal.Instance().ajoutErreur("L'employé " + typeEmployer + " n'a pas travaillé le nombre d'heures minimal au bureau (jour ouvrable).");
        }

    }

    private boolean estFeuilleTempsValide() {
        boolean estValide = ErreurJournal.Instance().contientErreur();

        if (!estValide) {
            this.ecritureDesErreurs();
        }

        return estValide;
    }

    private void ecritureDesErreurs() {
        try {
            ErreurJournal.Instance().ecrireErreurDansFichier(this.cheminFichierErreur);
        } catch (IOException ex) {
            System.out.println("Impossible d'écrire le fichier d'erreur: " + ex.getMessage());
            Logger.getLogger(Employe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Jour chargerJourAPartirJSONArray(String nomJour, JSONArray jsonProjets) throws JSONException {
        JSONObject jsonProjet;

        Jour jour = Jour.CreerJour(nomJour);

        Iterator<JSONObject> it = jsonProjets.iterator();
        while (it.hasNext()) {
            jsonProjet = it.next();
            jour.ajoutProjet(new Projet(jsonProjet.getInt("projet"), jsonProjet.getInt("minutes")));
        }

        return jour;
    }
}
