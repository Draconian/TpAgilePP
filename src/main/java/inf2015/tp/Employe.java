/**
 * Employe - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import inf2015.tp.erreur.ErreurJournal;
import inf2015.tp.jour.Jour;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Employe {

    protected static final String[] JOUR_SEMAINES = {"jour1", "jour2", "jour3",
        "jour4", "jour5", "weekend1", "weekend2"};
    protected static final int MAX_HEURE_BUREAU = 43 * 60;
    protected static final int MAX_MINUTES_PAR_JOUR = 24 * 60;
    protected static final int MIN_MINUTES_BUREAU_ADMIN = 2250; //37.5h
    protected static final int MIN_MINUTES_BUREAU_ADMIN_OUVRABLE = 4 * 60;
    protected static final int MAX_MINUTES_TELETRAV_ADMIN = 10 * 60;
    protected static final int MAX_MINUTES_BUREAU_ADMIN = 2490; //41.5h
    protected static final int EMPLOYER_ADMINISTRATION_ID = 1000;
    protected static final int MIN_MINUTES_EXPLOITATION = 2370; //39.5h 
    protected static final int MIN_MINUTES_BUREAU_PAR_JOUR_EXPLOITATION_OUVRABLE = 6 * 60;
    protected static final int EMPLOYER_EXPLOITATION_ID = 5000;
    protected static final int MIN_MINUTES_BUREAU_DEVELOPPEMENT_OUVRABLE = 38 * 60;
    protected static final int MIN_MINUTES_BUREAU_PAR_JOUR_DEVELOPEMENT_OUVRABLE = 6 * 60;
    protected static final int EMPLOYER_DEVELOPPEMENT_ID = 2000;
    protected static final int MIN_MINUTES_BUREAU_DIRECTEUR = 43 * 60;
    protected static final int MIN_MINUTES_BUREAU_DIRECTEUR_OUVRABLE = 4 * 60;
    protected static final int EMPLOYER_DIRECTION_ID = 5000;
    protected int numeroEmployer = 0;
    protected int minutesTeleTravail = 0;
    protected int minutesJoursOuvrableBureau = 0;
    protected int minutesWeekendBureau = 0;
    protected ArrayList<Jour> semaines = new ArrayList<Jour>();
    protected int[] minuteParJour = new int[5];
    protected String cheminFichierFeuilleTemps;
    protected String cheminFichierErreur;
    protected ErreurJournal erreurJournal;

    public Employe(int numeroEmployer, ErreurJournal erreurJournal) {
        this.numeroEmployer = numeroEmployer;
        this.erreurJournal = erreurJournal;

    }

    public String getTypeEmploye() {
        return "employé";
    }

    public boolean validerFeuilleDeTemps() {
        try {

            this.calculerFeuilleTemps();
            this.analyserFeuilleTemps();
        } catch (Exception ex) {
            System.out.println("Lecture du fichier JSON a échoué: " + ex.getMessage());
            this.priseEnChargeJsonMalForme();
            return false;
        }

        return this.estFeuilleTempsValide();
    }

    protected void minutesDeChaqueJourOuvrable() {
        int i = 0;
        for (Jour jour : this.semaines) {
            if (i < 5) {
                minuteParJour[i] = jour.getTotalMinutesJournee();
                i++;
            }
        }
    }

    protected void calculerFeuilleTemps() {
        for (Jour jour : this.semaines) {
            jour.verifierMaxMinutesJour();
            if (jour.estJourOuvable()) {
                this.minutesJoursOuvrableBureau += jour.getMinutesBureau();
            } else {
                this.minutesWeekendBureau += jour.getMinutesBureau();
            }
            this.minutesTeleTravail += jour.getMinutesTeletravail();
        }
        minutesDeChaqueJourOuvrable(); //Tableau  5 indices 0 lundi 5 vendredi


    }

    protected void analyserFeuilleTemps() {

        if (this.numeroEmployer <= EMPLOYER_ADMINISTRATION_ID) {
            this.analyserFeuilleTempsAdministration();
        } else if (this.numeroEmployer <= EMPLOYER_DEVELOPPEMENT_ID) {
            this.analyserFeuilleTempsDeveloppement();
        } else if (this.numeroEmployer <= EMPLOYER_EXPLOITATION_ID) {
            this.analyserFeuilleTempsExploitation();
        } else if (this.numeroEmployer >= EMPLOYER_DIRECTION_ID) {
            this.analyserFeuilleTempsDirection();
        }
        this.verifierCongeParental();
    }

    protected void verifierQuotidiennementDirecteur() {
        for (int i = 0; i < 5; i++) {
            if (minuteParJour[i] < MIN_MINUTES_BUREAU_DIRECTEUR_OUVRABLE) {
                //TODO ErreurJournal.Instance().ajoutErreur("L'employé de la direction n'a pas travaillé le nombre d'heures minimal au bureau (jour ouvrable) pendant le  " + JOUR_SEMAINES[i] + ".L'employé doit travailler au moins " + MIN_MINUTES_BUREAU_DIRECTEUR_OUVRABLE + " minutes par jour.");
            }

        }

    }

    protected void verifierQuotidiennementAdministration() {
        for (int i = 0; i < 5; i++) {
            if (minuteParJour[i] < MIN_MINUTES_BUREAU_ADMIN_OUVRABLE) {
                //TODO ErreurJournal.Instance().ajoutErreur("L'employé administration n'a pas travaillé le nombre d'heures minimal au bureau (jour ouvrable) pendant le " + JOUR_SEMAINES[i] + ".L'employé doit travailler au moins " + MIN_MINUTES_BUREAU_ADMIN_OUVRABLE + " minutes par jour.");
            }

        }

    }

    protected void verifierQuotidiennementDeveloppement() {
        int nbJourOuvrable = 5;
        for (int i = 0; i < nbJourOuvrable; i++) {
            if (minuteParJour[i] < MIN_MINUTES_BUREAU_PAR_JOUR_DEVELOPEMENT_OUVRABLE) {
                //TODO ErreurJournal.Instance().ajoutErreur("L'employé de développement n'a pas travaillé le nombre d'heures minimal le  pendant le " + JOUR_SEMAINES[i] + ".L'employé doit travailler au moins " + MIN_MINUTES_BUREAU_PAR_JOUR_DEVELOPEMENT_OUVRABLE + " minutes par jour.");
            }


        }
    }

    protected void verifierQuotidiennementExploitation() {
        for (int i = 0; i < 5; i++) {
            if ((minuteParJour[i]) < MIN_MINUTES_BUREAU_PAR_JOUR_EXPLOITATION_OUVRABLE) {
                //TODO ErreurJournal.Instance().ajoutErreur("L'employé d'exploitation n'a pas travaillé le nombre d'heures minimal au bureau  pendant le " + JOUR_SEMAINES[i] + ".L'employé doit travailler au moins " + MIN_MINUTES_BUREAU_PAR_JOUR_EXPLOITATION_OUVRABLE + " minutes par jour.");
            }
        }
    }

    protected void analyserFeuilleTempsAdministration() {

        verifierQuotidiennementAdministration();
        if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_ADMIN) {
            //TODO ErreurJournal.Instance().ajoutErreur("L'employé administration n'a pas travaillé le nombre d'heures minimal au bureau. Il doit travailler " + (double) MIN_MINUTES_BUREAU_ADMIN / 60 + " heures.");
        } else if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) > MAX_MINUTES_BUREAU_ADMIN) {
            //TODO ErreurJournal.Instance().ajoutErreur("L'employé administration a dépassé le nombre d'heures maximum au bureau. Celui-ci ne peut pas dépasser : " + (double) MAX_MINUTES_BUREAU_ADMIN / 60 + " heures.");
        }

        if (this.minutesTeleTravail > MAX_MINUTES_TELETRAV_ADMIN) {
            //TODO ErreurJournal.Instance().ajoutErreur("L'employé administration a dépassé le nombre d'heures de télétravail. Il ne peut pas dépasser " + (double) MAX_MINUTES_TELETRAV_ADMIN / 60 + " heures.");
        }
    }

    protected void analyserFeuilleTempsDeveloppement() {
        verifierQuotidiennementDeveloppement();
        if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_DEVELOPPEMENT_OUVRABLE) {
            //TODO ErreurJournal.Instance().ajoutErreur("L'employé de développement n'a pas travaillé le nombre d'heures minimal au bureau. Il doit faire " + (double) MIN_MINUTES_BUREAU_DEVELOPPEMENT_OUVRABLE / 60 + " heures.");
        } else if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) > MAX_HEURE_BUREAU) {
            //TODO ErreurJournal.Instance().ajoutErreur("L'employé de développement a dépassé le nombre d'heures maximum au bureau.Il ne peut pas dépasser " + (double) MAX_HEURE_BUREAU / 60 + " .");
        }

    }

    protected void analyserFeuilleTempsExploitation() {
        verifierQuotidiennementExploitation();
        if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) < MIN_MINUTES_EXPLOITATION) {
            //TODO ErreurJournal.Instance().ajoutErreur("L'employé d'exploitation n'a pas travaillé le nombre d'heures minimal au bureau. Il doit faire au moins " + (double) MIN_MINUTES_EXPLOITATION / 60 + " heures");
        } else if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) > MAX_HEURE_BUREAU) {
            //TODO ErreurJournal.Instance().ajoutErreur("L'employé d'exploitation a dépassé le nombre d'heures maximum au bureau. Il ne peut pas dépasser " + MAX_HEURE_BUREAU + " .");
        }

    }

    protected void analyserFeuilleTempsDirection() {
        verifierQuotidiennementDirecteur();
        if ((this.minutesWeekendBureau + this.minutesJoursOuvrableBureau) < MIN_MINUTES_BUREAU_DIRECTEUR) {
            //TODO ErreurJournal.Instance().ajoutErreur("Le directeur n'a pas travaillé le nombre d'heure minimum. (" + (double) MIN_MINUTES_BUREAU_DIRECTEUR / 60 + ")");
        }


    }

    protected boolean estFeuilleTempsValide() {
        

        if (!this.erreurJournal.estVide()) {
            this.ecritureDesErreurs();
        }

        return this.erreurJournal.estVide();
    }

    protected void verifierCongeParental() {
        boolean estCongeParental = false;

        for (Jour jour : this.semaines) {
            if (estCongeParental && jour.estJourneeCongeParental()) {
                
                //TODO ErreurJournal.Instance().ajoutErreur("Une semaine ne peut contenir plus qu'un congé parental.");
                break;
            } else if (jour.estJourneeCongeParental()) {
                estCongeParental = true;
            }
        }
    }

    protected void ecritureDesErreurs() {
        try {
            this.erreurJournal.ecrireErreurDansFichier(this.cheminFichierErreur);
        } catch (IOException ex) {
            System.out.println("Impossible d'écrire le fichier d'erreur: " + ex.getMessage());
            Logger.getLogger(Employe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void priseEnChargeJsonMalForme() {
        this.erreurJournal.effacerTout();
        this.ecritureDesErreurs();
    }
}
