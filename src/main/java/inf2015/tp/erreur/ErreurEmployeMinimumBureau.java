/**
 * ErreurEmployeMinimumBureau - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.MinuteHeureConvertion;
import inf2015.tp.employe.Employe;

public class ErreurEmployeMinimumBureau extends Erreur {

    protected static final String ERREUR_MESSAGE = "L'employé %s n'a pas fait le  "
            + "nombre d'heures minimum au bureau. Celui-ci doit faire au moins"
            + ": %.2f heures (%d minutes).";
    private int maxMinutes;

    public ErreurEmployeMinimumBureau(Employe employeErreur, int maxMinutes) {
        super(ERREUR_MESSAGE, employeErreur);
        this.maxMinutes = maxMinutes;
    }

    @Override
    protected String afficherErreur() {
        float heures = MinuteHeureConvertion.minutesVersHeures(this.maxMinutes);
        return String.format(ERREUR_MESSAGE, this.employeErreur.getTypeEmploye(), heures, this.maxMinutes);
    }
}
