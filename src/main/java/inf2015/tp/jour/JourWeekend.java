/**
 * JourWeekend - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.jour;

import inf2015.tp.ErreurJournal;
import inf2015.tp.Projet;

public class JourWeekend extends Jour {

    public JourWeekend(String nomJour) {
        super(nomJour);
    }

    @Override
    public boolean estJourOuvable() {
        return false;
    }

    @Override
    protected void analyserJourFerie() {
        ErreurJournal.Instance().ajoutErreur(String.format("\nLe jour \"%s\" qui est férié ne doit pas être le weekend.", this.nomJour));
    }

    @Override
    protected void analyserJourMaladie() {
        ErreurJournal.Instance().ajoutErreur(String.format("\nLe jour \"%s\" qui est %s ne doit pas être le weekend.", this.nomJour, "maladie"));
    }

    @Override
    protected void analyserJourVacances() {
        ErreurJournal.Instance().ajoutErreur(String.format("\nLe jour \"%s\" qui est %s ne doit pas être le weekend.", this.nomJour, "vacances"));
    }

    @Override
    protected void analyserJourParental() {
        ErreurJournal.Instance().ajoutErreur(String.format("\nLe jour \"%s\" qui est %s ne doit pas être le weekend.", this.nomJour, "Congé parental"));
    }
}
