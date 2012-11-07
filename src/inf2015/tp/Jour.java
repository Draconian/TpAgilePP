/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp;

import java.util.ArrayList;

/**
 *
 * @author fdufault
 */
public class Jour {

    private static final int MINUTES_JOURNEE_FERIEE = 420;
    private ArrayList<Projet> _projetsJournee = new ArrayList<>();
    private TypeJour _typeJournee;
    private String _nomJour;

    private Jour(String aNomJour, TypeJour aTypeJournee) {
        this._typeJournee = aTypeJournee;
        this._nomJour = aNomJour;
    }

    public boolean estJourneeFerie() {
        boolean estFerie = false;

        for (Projet projet : this._projetsJournee) {
            if (projet.estCongeFerie()) {
                estFerie = true;
            }
        }

        return estFerie;
    }

    public boolean contientTeleTravail() {
        boolean estTeleTravail = false;

        for (Projet projet : this._projetsJournee) {
            if (projet.estTeleTravail()) {
                estTeleTravail = true;
            }
        }

        return estTeleTravail;
    }

    public boolean contientTravailBureau() {
        boolean estTravailBureau = false;

        for (Projet projet : this._projetsJournee) {
            if (projet.estTravailBureau()) {
                estTravailBureau = true;
            }
        }

        return estTravailBureau;

    }

    public int getMinutesJourneeFeriee() {
        int minutes = 0;

        for (Projet projet : this._projetsJournee) {
            if (projet.estCongeFerie()) {
                minutes += projet.getMinutes();
            }
        }

        return minutes;
    }

    public int getMinutesTeletravail()
    {
        int minutes = 0;
        
        for(Projet projet : this._projetsJournee)
        {
            if(projet.estTeleTravail())
            {
                minutes = minutes + projet.getMinutes();
            }
        }
        
        return minutes;
    }
    
    public int getMinutesBureau() {
        int minutes = 0;

        for (Projet projet : this._projetsJournee) {
            if (projet.estTravailBureau() || projet.estCongeFerie()) {
                minutes = minutes + projet.getMinutes();
            }
        }
        
        return minutes;
    }
    
    public void ajoutProjet(Projet aNouveauProjet) {
        this._projetsJournee.add(aNouveauProjet);
    }

    public void analyserJour() {
        if (this.estJourneeFerie()) {
            this.analyserJourFerie();
        } else {
            // AJOUTER AUTRE JOUR SPECIAUX
        }


    }

    private void analyserJourFerie() {
        if (this._typeJournee == TypeJour.WEEKEND) {
            ErreurLog.Instance().ajoutErreur("Le jour " + this._nomJour + " qui est fériée, ne doit pas être le weekend.");
        }

        if (this.contientTravailBureau()) {
            ErreurLog.Instance().ajoutErreur("Le jour " + this._nomJour + " qui est fériée, ne doit pas contenir de temps au bureau.");
        }

        if (this.getMinutesJourneeFeriee() != MINUTES_JOURNEE_FERIEE) {
            ErreurLog.Instance().ajoutErreur("Le jour \"" + this._nomJour + "\" qui est fériée, doit contenir " + MINUTES_JOURNEE_FERIEE + " minutes. (Il contient " + this.getMinutesJourneeFeriee() + " minutes.)");
        }

    }

    public boolean estJourOuvrable()
    {
        return (this._typeJournee == TypeJour.OUVRABLE);
    }
    
    @Override
    public String toString()
    {
        return String.format("%s | Type: %s",this._nomJour, this._typeJournee);
    }
    
    public enum TypeJour {

        OUVRABLE,
        WEEKEND
    }

    public static Jour CreerJour(String aNomJour) {
        Jour jour;

        if (aNomJour.startsWith("jour")) {
            jour = new Jour(aNomJour, TypeJour.OUVRABLE);
        } else {
            jour = new Jour(aNomJour, TypeJour.WEEKEND);
        }

        return jour;
    }
    
    
}
