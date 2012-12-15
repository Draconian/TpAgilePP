/**
 * Jour - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.jour;

import inf2015.tp.Projet;
import inf2015.tp.erreur.ErreurJournal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Jour {

    public static final int MINUTES_JOURNEE_FERIEE = 480;
    public static final int MINUTES_JOURNEE_MALADIE = 480;
    public static final int MINUTES_JOURNEE_CONGE_PARENTAL = 480;
    public static final int MINUTES_JOURNEE_CONGE_VACANCES = 480;
    public static final int MAX_MINUTES_TRANSPORT = 300;
    public static final int MAX_MINUTES_PAR_JOURS = 1440; //24h
    public static final int MAX_MINUTES_PAR_JOURS_AVEC_CONGE = 1920; //32h
    protected ArrayList<Projet> projetsJournee = new ArrayList<Projet>();
    protected String nomJour;
    protected ErreurJournal erreurJournal;

    protected Jour(String nomJour, ErreurJournal erreurJournal) {
        this.nomJour = nomJour;
        this.erreurJournal = erreurJournal;
    }

    public String getNomJour() {
        return this.nomJour;
    }

    public List<Projet> getProjetsJournee() {
        return Collections.unmodifiableList(projetsJournee);

    }

    public int getMinutesJourneeFeriee() {
        int minutes = 0;

        for (Projet projet : this.projetsJournee) {
            if (projet.estCongeFerie()) {
                minutes += projet.getMinutes();
            }
        }

        return minutes;
    }

    public int getTotalMinutesJournee() {
        int minutes = 0;
        for (Projet projet : this.projetsJournee) {
            minutes += projet.getMinutes();
        }
        return minutes;
    }

    public int getMinutesJourneeVacance() {
        int minutes = 0;

        for (Projet projet : this.projetsJournee) {
            if (projet.estCongeVacance()) {
                minutes += projet.getMinutes();
            }
        }

        return minutes;
    }

    public int getMinutesJourneeMaladie() {
        int minutes = 0;

        for (Projet projet : this.projetsJournee) {
            if (projet.estCongeMaladie()) {
                minutes += projet.getMinutes();
            }
        }

        return minutes;
    }

    public int getMinutesTeletravail() {
        int minutes = 0;

        for (Projet projet : this.projetsJournee) {
            if (projet.estTeleTravail()) {
                minutes = minutes + projet.getMinutes();
            }

        }

        return minutes;
    }

    public int getMinutesBureau() {
        int minutes = 0;

        for (Projet projet : this.projetsJournee) {
            if (projet.estTravailBureau() || projet.estCongeFerie() || projet.estCongeVacance() || projet.estCongeParental() || projet.estCongeMaladie()) {
                minutes = minutes + projet.getMinutes();
            }

        }
        return minutes;
    }

    public int getMinutesJourneeCongeParental() {
        int minutes = 0;
        for (Projet projet : this.projetsJournee) {
            if (projet.estCongeParental()) {
                minutes = minutes + projet.getMinutes();
            }
        }

        return minutes;
    }

    public int getMinutesTransport() {
        int minutes = 0;
        for (Projet projet : this.projetsJournee) {
            if (projet.estTransport()) {
                minutes = minutes + projet.getMinutes();
            }
        }

        return minutes;
    }

    public boolean estJourneeVacances() {
        boolean estVacances = false;
        for (Projet projet : this.projetsJournee) {
            if (projet.estCongeVacance()) {
                estVacances = true;
            }
        }

        return estVacances;
    }

    public boolean estJourneeFerie() {
        boolean estFerie = false;

        for (Projet projet : this.projetsJournee) {
            if (projet.estCongeFerie()) {
                estFerie = true;
            }
        }

        return estFerie;
    }

    public boolean estJourMaladie() {
        boolean estMaladie = false;

        for (Projet projet : this.projetsJournee) {
            if (projet.estCongeMaladie()) {
                estMaladie = true;
            }
        }

        return estMaladie;
    }

    public boolean contientTransport() {
        boolean contientTransport = false;
        for (Projet projet : this.projetsJournee) {
            if (projet.estTransport()) {
                contientTransport = true;
            }
        }
        return contientTransport;
    }

    public boolean estJourneeCongeParental() {
        boolean estCongeParental = false;


        for (Projet projet : this.projetsJournee) {
            if (projet.estCongeParental()) {
                estCongeParental = true;
            }
        }

        return estCongeParental;
    }

    public boolean contientTeleTravail() {
        boolean estTeleTravail = false;

        for (Projet projet : this.projetsJournee) {
            if (projet.estTeleTravail()) {
                estTeleTravail = true;
            }
        }

        return estTeleTravail;
    }

    public boolean contientTravailBureau() {
        boolean estTravailBureau = false;

        for (Projet projet : this.projetsJournee) {
            if (projet.estTravailBureau()) {
                estTravailBureau = true;
            }
        }

        return estTravailBureau;
    }

    public boolean contientAutresProjetsQue(int projetID) {
        boolean contientAutreProjet = false;

        for (Projet projet : this.projetsJournee) {
            if (projet.getProjetID() != projetID) {
                contientAutreProjet = true;
            }
        }

        return contientAutreProjet;
    }

    public void ajoutProjet(Projet nouveauProjet) {
        this.projetsJournee.add(nouveauProjet);
    }

    public void analyserJour() {
        if (this.estJourneeVacances()) {
            this.analyserJourVacances();
        }
        if (this.estJourneeCongeParental()) {
            this.analyserJourParental();
        }

        if (this.estJourneeFerie()) {
            this.analyserJourFerie();
        }

        if (this.estJourMaladie()) {
            this.analyserJourMaladie();
        }


    }

    @Override
    public String toString() {
        return String.format("%s", this.nomJour);
    }

    public abstract boolean estJourOuvrable();

    protected abstract void analyserJourFerie();

    protected abstract void analyserJourMaladie();

    protected abstract void analyserJourVacances();

    protected abstract void analyserJourParental();

    public abstract void verifierMaxMinutesJour();
}
