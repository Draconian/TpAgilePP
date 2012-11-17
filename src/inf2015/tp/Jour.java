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

    private static final int MINUTES_JOURNEE_FERIEE = 480;
    private static final int MINUTES_JOURNEE_MALADIE = 480;
    private ArrayList<Projet> projetsJournee = new ArrayList<>();
    private TypeJour typeJournee;
    private String nomJour;

    private Jour(String nomJour, TypeJour typeJournee) {
        this.typeJournee = typeJournee;
        this.nomJour = nomJour;
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
            if (projet.estTravailBureau() || projet.estCongeFerie()) {
                minutes = minutes + projet.getMinutes();
            }
        }

        return minutes;
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

    public void ajoutProjet(Projet nouveauProjet) {
        this.projetsJournee.add(nouveauProjet);
    }

    public void analyserJour() {
        if (this.estJourneeFerie()) {
            this.analyserJourFerie();
        } else if (this.estJourMaladie()) {
            this.analyserJourMaladie();
        }
    }

    public boolean estJourOuvrable() {
        return (this.typeJournee == TypeJour.OUVRABLE);
    }

    @Override
    public String toString() {
        return String.format("%s | Type: %s", this.nomJour, this.typeJournee);
    }

    private void analyserJourFerie() {
        this.analyserJourSpecial("férié");

        if (this.estJourMaladie()) {
            ErreurJournal.Instance().ajoutErreur(String.format("Le jour \"%s\" qui est férié, ne doit pas contenir de temps maladies.", this.nomJour));
        }

        comparerJourSpecialEtMinutesRequis(this.nomJour, "férié", this.getMinutesJourneeFeriee(), MINUTES_JOURNEE_FERIEE);
    }

    private void analyserJourMaladie() {
        this.analyserJourSpecial("maladie");

        if (this.contientTeleTravail()) {
            ErreurJournal.Instance().ajoutErreur(String.format("Le jour \"%s\" qui est maladie, ne doit pas contenir de temps télétravail.", this.nomJour));
        }

        if (this.estJourneeFerie()) {
            ErreurJournal.Instance().ajoutErreur(String.format("Le jour \"%s\" qui est maladie, ne doit pas contenir de temps fériés", this.nomJour));
        }

        comparerJourSpecialEtMinutesRequis(this.nomJour, "maladie", this.getMinutesJourneeMaladie(), MINUTES_JOURNEE_MALADIE);
    }
 
            

    private void analyserJourSpecial(String typeJourSpecial) {

        if (this.typeJournee == TypeJour.WEEKEND) {
            ErreurJournal.Instance().ajoutErreur(String.format("Le jour \"%s\" qui est %s ne doit pas être le weekend.", this.nomJour, typeJourSpecial));
        }

        if (this.contientTravailBureau()) {
            ErreurJournal.Instance().ajoutErreur(String.format("Le jour \"%s\" qui est %s, ne doit pas contenir de temps au bureau.", this.nomJour, typeJourSpecial));
        }
    }

    public enum TypeJour {

        OUVRABLE,
        WEEKEND
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

    private static void comparerJourSpecialEtMinutesRequis(String nomJour, String typeJourSpecial, int jourMinutes, int jourMinutesRequis) {

        if (jourMinutes != jourMinutesRequis) {
            ErreurJournal.Instance().ajoutErreur(String.format("Le jour \"%s\" qui est %s, doit contenir %d minutes. (Il contient %d minutes.)",
                    nomJour, typeJourSpecial, jourMinutesRequis, jourMinutes));
        }
    }
}
