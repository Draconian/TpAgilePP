/**
 * JourOuvrable - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.jour;

import inf2015.tp.Projet;
import inf2015.tp.erreur.ErreurJourCongeAvecAutreProjet;
import inf2015.tp.erreur.ErreurJourDepasseMinute;
import inf2015.tp.erreur.ErreurJourSpecialEgalMinutes;
import inf2015.tp.erreur.ErreurJournal;

public class JourOuvrable extends Jour {

    public JourOuvrable(String nomJour, ErreurJournal erreurJournal) {
        super(nomJour, erreurJournal);
    }

    @Override
    public boolean estJourOuvrable() {
        return true;
    }

    @Override
    public void verifierMaxMinutesJour() {
        int minutesJournee = this.getTotalMinutesJournee();

        if (this.estJourneeVacances() || this.estJourneeFerie()) {
            if (minutesJournee > MAX_MINUTES_PAR_JOURS_AVEC_CONGE) {
                erreurJournal.ajoutErreur(new ErreurJourDepasseMinute(this, MAX_MINUTES_PAR_JOURS_AVEC_CONGE));
            }
        } else {
            if (minutesJournee > MAX_MINUTES_PAR_JOURS) {
                erreurJournal.ajoutErreur(new ErreurJourDepasseMinute(this, MAX_MINUTES_PAR_JOURS));
            }
        }
    }

    @Override
    protected void analyserJourFerie() {
        this.comparerJourSpecialEtMinutesRequis("férié", this.getMinutesJourneeFeriee(), Jour.MINUTES_JOURNEE_FERIEE);
    }

    @Override
    protected void analyserJourMaladie() {
        if (this.contientAutresProjetsQue(Projet.PROJET_ID_CONGE_MALADIE)) {
            erreurJournal.ajoutErreur(new ErreurJourCongeAvecAutreProjet(this, "maladie"));
        }

        comparerJourSpecialEtMinutesRequis("maladie", this.getMinutesJourneeMaladie(), Jour.MINUTES_JOURNEE_MALADIE);
    }

    @Override
    protected void analyserJourVacances() {
        comparerJourSpecialEtMinutesRequis("vacance", this.getMinutesJourneeVacance(), Jour.MINUTES_JOURNEE_CONGE_VACANCES);
    }

    @Override
    protected void analyserJourParental() {
        if (this.contientAutresProjetsQue(Projet.PROJET_ID_CONGE_PARENTAL)) {
            erreurJournal.ajoutErreur(new ErreurJourCongeAvecAutreProjet(this, "parental"));
        }

        comparerJourSpecialEtMinutesRequis("congé parental", this.getMinutesJourneeCongeParental(), Jour.MINUTES_JOURNEE_CONGE_PARENTAL);
    }

    protected void comparerJourSpecialEtMinutesRequis(String typeJourSpecial, int jourMinutes, int jourMinutesRequis) {

        if (jourMinutes != jourMinutesRequis) {
            erreurJournal.ajoutErreur(new ErreurJourSpecialEgalMinutes(this, typeJourSpecial, Jour.MINUTES_JOURNEE_FERIEE));
        }

    }
}
