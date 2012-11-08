/**
 * Jour - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import java.util.ArrayList;

public class Jour {

    private static final int MINUTES_JOURNEE_FERIEE = 420;
    private ArrayList<Projet> projetsJournee = new ArrayList<>();
    private TypeJour typeJournee;
    private String nomJour;

    private Jour(String nomJour, TypeJour typeJournee) {
        this.typeJournee = typeJournee;
        this.nomJour = nomJour;
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

    public int getMinutesJourneeFeriee() {
        int minutes = 0;

        for (Projet projet : this.projetsJournee) {
            if (projet.estCongeFerie()) {
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
            if (projet.estTravailBureau() || projet.estCongeFerie()) {
                minutes = minutes + projet.getMinutes();
            }
        }

        return minutes;
    }

    public void ajoutProjet(Projet nouveauProjet) {
        this.projetsJournee.add(nouveauProjet);
    }

    public void analyserJour() {
        if (this.estJourneeFerie()) {
            this.analyserJourFerie();
        } else {
            // AJOUTER AUTRE JOUR SPECIAUX
        }
    }

    public boolean estJourOuvrable() {
        return (this.typeJournee == TypeJour.OUVRABLE);
    }

    @Override
    public String toString() {
        return String.format("%s | Type: %s", this.nomJour, this.typeJournee);
    }

    public enum TypeJour {

        OUVRABLE,
        WEEKEND
    }

    private void analyserJourFerie() {
        if (this.typeJournee == TypeJour.WEEKEND) {
            ErreurLog.Instance().ajoutErreur("Le jour " + this.nomJour + " qui est fériée, ne doit pas être le weekend.");
        }

        if (this.contientTravailBureau()) {
            ErreurLog.Instance().ajoutErreur("Le jour " + this.nomJour + " qui est fériée, ne doit pas contenir de temps au bureau.");
        }

        if (this.getMinutesJourneeFeriee() != MINUTES_JOURNEE_FERIEE) {
            ErreurLog.Instance().ajoutErreur("Le jour \"" + this.nomJour + "\" qui est fériée, doit contenir " + MINUTES_JOURNEE_FERIEE + " minutes. (Il contient " + this.getMinutesJourneeFeriee() + " minutes.)");
        }
    }
    
    public static Jour CreerJour(String nomJour) {
        Jour jour;

        if (nomJour.startsWith("jour")) {
            jour = new Jour(nomJour, TypeJour.OUVRABLE);
        } else {
            jour = new Jour(nomJour, TypeJour.WEEKEND);
        }

        return jour;
    }
}
