/**
 * ErreurEmployeMaximumBureau - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.MinuteHeureConvertion;
import inf2015.tp.employe.Employe;

public class ErreurEmployeMaximumBureau extends Erreur {

    protected static final String ERREUR_MESSAGE = "L'employé %s a dépassé le "
            + "nombre d'heures maximum au bureau. Celui-ci ne peut pas dépasser"
            + ": %.2f heures (%d minutes).";
    private int maxMinutes;

    public ErreurEmployeMaximumBureau(Employe employeErreur, int maxMinutes) {
        super(ERREUR_MESSAGE, employeErreur);
        this.maxMinutes = maxMinutes;
    }

    @Override
    protected String afficherErreur() {
        int minutes = this.maxMinutes;
        float heures = MinuteHeureConvertion.minutesVersHeures(minutes);
        return String.format(ERREUR_MESSAGE, this.employeErreur.getTypeEmploye(), heures, minutes);
    }
}
