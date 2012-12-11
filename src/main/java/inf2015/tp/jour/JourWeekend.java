/**
 * JourWeekend - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.jour;

import inf2015.tp.erreur.ErreurJournal;
import inf2015.tp.Projet;
import inf2015.tp.erreur.ErreurJourCongeWeekend;
import inf2015.tp.erreur.ErreurJourDepasseMinute;

public class JourWeekend extends Jour {

    public JourWeekend(String nomJour, ErreurJournal erreurJournal) {
        super(nomJour, erreurJournal);
    }

    @Override
    public boolean estJourOuvrable() {
        return false;
    }

    @Override
    public void verifierMaxMinutesJour() {
        int minutesJournee = this.getTotalMinutesJournee();
        
        if (minutesJournee > MAX_MINUTES_PAR_JOURS) {
            erreurJournal.ajoutErreur(new ErreurJourDepasseMinute(this, MAX_MINUTES_PAR_JOURS));
        }
    }

    @Override
    protected void analyserJourFerie() {
        erreurJournal.ajoutErreur(new ErreurJourCongeWeekend(this, "férié"));
    }

    @Override
    protected void analyserJourMaladie() {
        erreurJournal.ajoutErreur(new ErreurJourCongeWeekend(this, "maladie"));
    }

    @Override
    protected void analyserJourVacances() {
        erreurJournal.ajoutErreur(new ErreurJourCongeWeekend(this, "vacances"));

    }

    @Override
    protected void analyserJourParental() {
        erreurJournal.ajoutErreur(new ErreurJourCongeWeekend(this, "parental"));

    }
}
