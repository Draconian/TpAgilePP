/**
 * ErreurEmployeTeletravailMaximum - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.Employe;

public class ErreurEmployeTeletravailMaximum extends Erreur {

    protected static final String ERREUR_MESSAGE = "L'employé %s a dépassé le nombre "
            + "d'heures de télétravail. Il ne peut pas dépasser %.1f heures.";
    private int maxMinutes;

    public ErreurEmployeTeletravailMaximum(Employe employeErreur, int maxMinutes) {
        super(ERREUR_MESSAGE, employeErreur);
        this.maxMinutes = maxMinutes;
    }

    @Override
    protected String afficherErreur() {
        float heures = (float) this.maxMinutes / (float) 60;
        return String.format(erreurMessage, this.employeErreur.getTypeEmploye(), heures);
    }
}
