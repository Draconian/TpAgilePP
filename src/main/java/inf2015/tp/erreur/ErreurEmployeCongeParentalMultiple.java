/**
 * ErreurEmployeCongeParentalMultiple - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

public class ErreurEmployeCongeParentalMultiple extends Erreur {

    protected static final String ERREUR_MESSAGE = "Une semaine ne peut contenir"
            + " plus qu'un congé parental.";

    public ErreurEmployeCongeParentalMultiple() {
        super(ERREUR_MESSAGE, null, null);
    }

    @Override
    protected String afficherErreur() {
        return this.erreurMessage;
    }
}
