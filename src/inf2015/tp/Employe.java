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

    protected static final String[] JOUR_SEMAINES = {"jour1", "jour2", "jour3",
        "jour4", "jour5", "weekend1", "weekend2"};
    protected static final int MAX_MINUTES_EXPLOITATION=2370; //39.5h 
    protected static final int MAX_MINUTES_PAR_JOUR = 24 * 60;
    protected static final int MIN_MINUTES_BUREAU_ADMIN = 2250; //37.5h
    protected static final int MAX_MINUTES_BUREAU_ADMIN = 2490; //41.5h
    protected static final int MIN_MINUTES_BUREAU_DIRECTEUR = 43 * 60;
    protected static final int MAX_MINUTES_TELETRAV_ADMIN = 10 * 60;
    protected static final int MIN_MINUTES_BUREAU_DEVELOPPEMENT_OUVRABLE = 38 * 60;
    protected static final int MIN_MINUTES_BUREAU_PAR_JOUR_DEVELOPEMENT_OUVRABLE = 6* 60;
    protected static final int MIN_MINUTES_BUREAU_ADMIN_OUVRABLE = 4 * 60;
    protected static final int EMPLOYER_ADMINISTRATION_ID = 1000;
    protected static final int EMPLOYER_DEVELOPPEMENT_ID = 2000;
    protected static final int EMPLOYER_EXPLOITATION_ID = 5000;
    protected static final int EMPLOYER_DIRECTION_ID = 5000;
    protected int numeroEmployer = 0;
    protected int minutesTeleTravail = 0;
    protected int minutesJoursOuvrableBureau = 0;
    protected int minutesWeekendBureau = 0;
    protected ArrayList<Jour> semaines = new ArrayList<>();
    protected String cheminFichierFeuilleTemps;
    protected String cheminFichierErreur;

    public Employe(String cheminFichierFeuilleTemps, String cheminFichierErreur) {
        this.cheminFichierFeuilleTemps = cheminFichierFeuilleTemps;
        this.cheminFichierErreur = cheminFichierErreur;
    }

    public boolean validerFeuilleDeTemps() {
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

    protected void chargerFeuillerTemps() throws IOException, JSONException {
        JSONObject jsonEmployer = JsonUtil.chargerJsonObjetDuFichier(this.cheminFichierFeuilleTemps);
        this.numeroEmployer = jsonEmployer.getInt("numero_employe");

        for (int i = 0; i < JOUR_SEMAINES.length; i++) {
            this.chargerJourFeuilleTemps(JOUR_SEMAINES[i], jsonEmployer);
        }
    }

    protected void chargerJourFeuilleTemps(String nomJour, JSONObject jsonEmployer) throws JSONException, IOException {
        Jour jour;

        if (jsonEmployer.containsKey(nomJour)) {
            jour = Employe.chargerJourAPartirJSONArray(nomJour, jsonEmployer.getJSONArray(nomJour));
            jour.analyserJour();
            this.semaines.add(jour);
        } else  {
            throw new JSONException("Erreur, il manque un jour dans votre fichier d'entrée.");
        }
    }

    protected void calculerFeuilleTemps() {
     int minutesJournee;
        for (Jour jour : this.semaines) {
          
            minutesJournee= jour.getMinutesParJour();
            jour.verifierMinutes(minutesJournee);
            if (jour.estJourOuvrable()) {
                this.minutesJoursOuvrableBureau += jour.getMinutesBureau();
            } else {
                this.minutesWeekendBureau += jour.getMinutesBureau();
            }
            this.minutesTeleTravail += jour.getMinutesTeletravail();
         
        }
     
    }

    protected void analyserFeuilleTemps() {
        if (this.numeroEmployer < EMPLOYER_ADMINISTRATION_ID) {
            this.analyserFeuilleTempsAdministration();
        } else if (this.numeroEmployer < EMPLOYER_DEVELOPPEMENT_ID) {
            this.analyserFeuilleTempsDeveloppement();
        } else if (this.numeroEmployer < EMPLOYER_EXPLOITATION_ID) {
            this.analyserFeuilleTempsExploitation();
        } else if (this.numeroEmployer >= EMPLOYER_DIRECTION_ID) {
            this.analyserFeuilleTempsDirection();
        }

        this.verifierCongeParental();
        

    }

    protected void analyserFeuilleTempsAdministration() {
        if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_ADMIN) {
            ErreurJournal.Instance().ajoutErreur("L'employé administration n'a pas travaillé le nombre d'heures minimal au bureau.");
        }else if((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) > MAX_MINUTES_BUREAU_ADMIN) {
             ErreurJournal.Instance().ajoutErreur("L'employé administration a dépassé le nombre d'heures maximum au bureau.");
        }
        if ((this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_ADMIN_OUVRABLE) {
            ErreurJournal.Instance().ajoutErreur("L'employé administration n'a pas travaillé le nombre d'heures minimal au bureau (jour ouvrable).");
        }
        if (this.minutesTeleTravail > MAX_MINUTES_TELETRAV_ADMIN) {
            ErreurJournal.Instance().ajoutErreur("L'employé administration a dépassé le nombre d'heures de télétravail.");
        }
    }

    protected void analyserFeuilleTempsDeveloppement() {
        if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_DEVELOPPEMENT_OUVRABLE) {
            ErreurJournal.Instance().ajoutErreur("L'employé de dévelopement n'a pas travaillé le nombre d'heures minimal au bureau.");
        }
        
        if (this.minutesJoursOuvrableBureau < MIN_MINUTES_BUREAU_PAR_JOUR_DEVELOPEMENT_OUVRABLE) {
            ErreurJournal.Instance().ajoutErreur(String.format("L'employé de développement n'a pas travaillé le nombre d'heures minimal au bureau (jour ouvrable)."));
        }

    }


    protected void analyserFeuilleTempsExploitation() {
             if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) < MAX_MINUTES_EXPLOITATION) {
                ErreurJournal.Instance().ajoutErreur("L'employé d'exploitation n'a pas fais fait le nombre d'heure minimum!");
             }
    }


    protected void analyserFeuilleTempsDirection() {
       
        if ( (this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_DIRECTEUR ) {
            ErreurJournal.Instance().ajoutErreur("Le directeur n'a pas travaillé le nombre d'heure minimum. (" +  MIN_MINUTES_BUREAU_DIRECTEUR + ")");
        }
        
        
    }  

    protected boolean estFeuilleTempsValide() {
        boolean estValide = !ErreurJournal.Instance().contientErreur();

        if (!estValide) {
            this.ecritureDesErreurs();
        }

        return estValide;
    }

    protected void verifierCongeParental() {
        boolean estCongeParental = false;

        for (Jour jour : this.semaines) {
            if (estCongeParental && jour.estJourneeCongeParental()) {
                ErreurJournal.Instance().ajoutErreur("Une semaine ne peut contenir plus qu'un congé parental.");
            } else if (jour.estJourneeCongeParental()) {
                estCongeParental = true;
            }
        }
    }
    
    protected void ecritureDesErreurs() {
        try {
            ErreurJournal.Instance().ecrireErreurDansFichier(this.cheminFichierErreur);
        } catch (IOException ex) {
            System.out.println("Impossible d'écrire le fichier d'erreur: " + ex.getMessage());
            Logger.getLogger(Employe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static Jour chargerJourAPartirJSONArray(String nomJour, JSONArray jsonProjets) throws JSONException {
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
