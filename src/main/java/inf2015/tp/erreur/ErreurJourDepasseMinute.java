/**
 * ErreurJourDepasseMinute - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.jour.Jour;

public class ErreurJourDepasseMinute extends Erreur {

    protected static final String ERREUR_MESSAGE = "Le jour %s contient %d "
            + "minutes,il dépasse le maximum de minutes authorisés qui est %d minutes";
    private int maxMinutes;

    public ErreurJourDepasseMinute(Jour jourErreur, int maxMinutes) {
        super(ERREUR_MESSAGE, jourErreur);

        this.maxMinutes = maxMinutes;
    }

    @Override
    protected String afficherErreur() {
        return String.format(ERREUR_MESSAGE, jourErreur.getNomJour(),
                jourErreur.getTotalMinutesJournee(), maxMinutes);
    }
}
