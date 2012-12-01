/**
 * ErreurJourCongeWeekend - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.jour.Jour;

public class ErreurJourCongeWeekend extends Erreur {

    protected static String ERREUR_MESSAGE = "Le jour %s qui est %s ne "
            + "doit pas être le weekend.";
    private String typeDeJour;

    public ErreurJourCongeWeekend(Jour jourErreur, String typeDeJour) {
        super(ERREUR_MESSAGE, jourErreur);
        this.typeDeJour = typeDeJour;

    }

    @Override
    protected String afficherErreur() {
        return String.format(ERREUR_MESSAGE, this.jourErreur.getNomJour(), this.typeDeJour);
    }
}
