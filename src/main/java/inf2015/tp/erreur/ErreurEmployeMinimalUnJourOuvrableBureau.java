/**
 * ErreurEmployeMinimalUnJourOuvrableBureau - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.employe.Employe;
import inf2015.tp.jour.Jour;

public class ErreurEmployeMinimalUnJourOuvrableBureau extends Erreur {

    protected static final String ERREUR_MESSAGE = "L'employé %s n'a pas "
            + "travaillé le nombre d'heures minimal au bureau (jour ouvrable) pour "
            + "le jour %s. L'employé %s doit travailler au moins %d minutes par jour.";
    int minMinutes;

    public ErreurEmployeMinimalUnJourOuvrableBureau(Employe employeErreur,
            Jour jourErreur, int minMinutes) {
        super(ERREUR_MESSAGE, employeErreur, jourErreur);
        this.minMinutes = minMinutes;
    }

    @Override
    protected String afficherErreur() {
        return String.format(ERREUR_MESSAGE, this.employeErreur.getTypeEmploye(),
                this.jourErreur.getNomJour(), this.employeErreur.getTypeEmploye(),
                this.minMinutes);
    }
}
