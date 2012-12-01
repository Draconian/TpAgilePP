/**
 * ErreurJourSpecialEgalMinutes - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.jour.Jour;

public class ErreurJourSpecialEgalMinutes extends Erreur {

    protected static final String ERREUR_MESSAGE = "Le jour %s qui est %s, "
            + "doit contenir %d minutes. (Il contient %d minutes.)";
    private String typeDeJour;
    private int maxMinutes;

    public ErreurJourSpecialEgalMinutes(Jour jourErreur, String typeDeJour, int maxMinutes) {
        super(ERREUR_MESSAGE, jourErreur);
        this.typeDeJour = typeDeJour;
        this.maxMinutes = maxMinutes;
        
    }

    @Override
    protected String afficherErreur() {
        return String.format(ERREUR_MESSAGE, super.jourErreur.getNomJour(),
                this.typeDeJour, this.maxMinutes, this.jourErreur.getTotalMinutesJournee());
    }
}
