/**
 * ErreurJourCongeAvecAutreProjet - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.Employe;

public class ErreurEmployeMaximumBureau extends Erreur {

    protected static final String ERREUR_MESSAGE = "L'employé %s a dépassé le "
            + "nombre d'heures maximum au bureau. Celui-ci ne peut pas dépasser"
            + ": %.1f heures.";
    private int maxMinutes;

    public ErreurEmployeMaximumBureau(Employe employeErreur, int maxMinutes) {
        super(ERREUR_MESSAGE, employeErreur);
        this.maxMinutes = maxMinutes;
    }

    @Override
    protected String afficherErreur() {
        float heures = (float) this.maxMinutes / (float) 60;
        return String.format(ERREUR_MESSAGE, this.employeErreur.getTypeEmploye(), heures);
    }
}
