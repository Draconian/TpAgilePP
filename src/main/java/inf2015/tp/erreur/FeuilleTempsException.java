/**
 * FeuilleTempsException - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

public class FeuilleTempsException extends Exception {

    public FeuilleTempsException(String message, Exception exceptionInterne) {
        super(message, exceptionInterne);
    }

    public FeuilleTempsException(String message) {
        super(message);
    }
}
