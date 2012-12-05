/**
 * Employe - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.employe;

import inf2015.tp.erreur.ErreurEmployeCongeParentalMultiple;
import inf2015.tp.erreur.ErreurEmployeMaximumBureau;
import inf2015.tp.erreur.ErreurEmployeMinimalUnJourOuvrableBureau;
import inf2015.tp.erreur.ErreurEmployeMinimumBureau;
import inf2015.tp.erreur.ErreurJournal;
import inf2015.tp.jour.Jour;
import java.util.ArrayList;

public abstract class Employe {

    protected static final String[] JOUR_SEMAINES = {"jour1", "jour2", "jour3",
        "jour4", "jour5", "weekend1", "weekend2"};
    protected int numeroEmployer = 0;
    protected int minutesTeleTravail = 0;
    protected int minutesJoursOuvrableBureau = 0;
    protected int minutesWeekendBureau = 0;
    protected ArrayList<Jour> semaines = new ArrayList<Jour>();
    protected ErreurJournal erreurJournal;
    protected int minimumMinutesParJourOuvrable = 0;
    protected int minimumMinutesBureau = 0;
    protected int maximumMinutesBureau = 0;
    protected int maximumMinutesTeletravail = 0;

    public Employe(int numeroEmployer, ErreurJournal erreurJournal) {

        this.numeroEmployer = numeroEmployer;
        this.erreurJournal = erreurJournal;
    }

    public abstract String getTypeEmploye();

    protected abstract void analyserFeuilleTemps();

    public int getNumeroEmploye() {
        return this.numeroEmployer;
    }

    public void ajoutJour(Jour jour) {
        this.semaines.add(jour);
    }

    public ArrayList<Jour> getSemaine() {
        return this.semaines;
    }

    public boolean validerFeuilleDeTemps() {
        this.calculerFeuilleTemps();
        this.analyserFeuilleTemps();
        this.verifierMinimumMinutesQuotidiennes();
        this.verifierCongeParental();
        
        return this.erreurJournal.estVide();
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
    }

    protected void analyserFeuilleTempsGeneral() {
        int minutesBureauTotal = this.minutesWeekendBureau + this.minutesJoursOuvrableBureau;

        if (minutesBureauTotal < this.minimumMinutesBureau) {
            this.erreurJournal.ajoutErreur(new ErreurEmployeMinimumBureau(this, this.minimumMinutesBureau));
        } else if (minutesBureauTotal > this.maximumMinutesBureau) {
            this.erreurJournal.ajoutErreur(new ErreurEmployeMaximumBureau(this, maximumMinutesBureau));
        }

    }

    protected void verifierMinimumMinutesQuotidiennes() {
        for (Jour jour : this.semaines) {
            if (jour.estJourOuvable() && jour.getMinutesBureau() < this.minimumMinutesParJourOuvrable) {
                this.erreurJournal.ajoutErreur(new ErreurEmployeMinimalUnJourOuvrableBureau(this, jour, this.minimumMinutesParJourOuvrable));
            }
        }
    }

    protected void verifierCongeParental() {
        boolean estCongeParental = false;

        for (Jour jour : this.semaines) {
            if (estCongeParental && jour.estJourneeCongeParental()) {
                this.erreurJournal.ajoutErreur(new ErreurEmployeCongeParentalMultiple());
                break;
            } else if (jour.estJourneeCongeParental()) {
                estCongeParental = true;
            }
        }
    }
}
