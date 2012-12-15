/**
 * ErreurEmployeTeletravailMaximum - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.MinuteHeureConvertion;
import inf2015.tp.employe.Employe;

public class ErreurEmployeTeletravailMaximum extends Erreur {

    protected static final String ERREUR_MESSAGE = "L'employé %s a dépassé le nombre "
            + "d'heures de télétravail. Il ne peut pas dépasser %.2f heures (%d minutes).";
    private int maxMinutes;

    public ErreurEmployeTeletravailMaximum(Employe employeErreur, int maxMinutes) {
        super(ERREUR_MESSAGE, employeErreur);
        this.maxMinutes = maxMinutes;
    }

    @Override
    protected String afficherErreur() {
        float heures = MinuteHeureConvertion.minutesVersHeures(this.maxMinutes);
        return String.format(erreurMessage, this.employeErreur.getTypeEmploye(), heures, this.maxMinutes);
    }
}
