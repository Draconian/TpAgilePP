/**
 * JourOuvrable - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.jour;

import inf2015.tp.ErreurJournal;
import inf2015.tp.Projet;

public class JourOuvrable extends Jour {

    public JourOuvrable(String nomJour) {
        super(nomJour);
    }

    public boolean estJourOuvable() {
        return true;
    }

    protected void analyserJourFerie() {
        comparerJourSpecialEtMinutesRequis(this.nomJour, "férié", this.getMinutesJourneeFeriee(), Jour.MINUTES_JOURNEE_FERIEE);
    }

    protected void analyserJourMaladie() {
        if (this.contientAutresProjetsQue(Projet.PROJET_ID_CONGE_MALADIE)) {
           ErreurJournal.Instance().ajoutErreur("\nLe jour " + this.nomJour + " a d'autre code de projet dans la même journée.");
        }

        comparerJourSpecialEtMinutesRequis(this.nomJour, "maladie", this.getMinutesJourneeMaladie(), Jour.MINUTES_JOURNEE_MALADIE);
    }

    protected void analyserJourVacances() {
        comparerJourSpecialEtMinutesRequis(this.nomJour, "vacance", this.getMinutesJourneeVacance(), Jour.MINUTES_JOURNEE_CONGE_VACANCES);
    }

    protected void analyserJourParental() {
        if (this.contientAutresProjetsQue(Projet.PROJET_ID_CONGE_PARENTAL)) {
            ErreurJournal.Instance().ajoutErreur(String.format("\nLe jour \"%s\" qui est congé parental ne doit pas avoir d'autre projet dans la même journée.", this.nomJour));
        }

        comparerJourSpecialEtMinutesRequis(this.nomJour, "congé parental", this.getMinutesJourneeCongeParental(), Jour.MINUTES_JOURNEE_CONGE_PARENTAL);
    }
}
