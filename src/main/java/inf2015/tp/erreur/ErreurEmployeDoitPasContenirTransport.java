/**
 * ErreurEmployeDoitPasContenirTransport - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.employe.Employe;

public class ErreurEmployeDoitPasContenirTransport extends Erreur {

    protected static final String ERREUR_MESSAGE = "L'employé %s n'a pas le "
            + "droit de utiliser du temps de transport.Le temps de transport "
            + "est reservé uniquement au employés d'administration et de la "
            + "direction.";

    public ErreurEmployeDoitPasContenirTransport(Employe employeErreur) {
        super(ERREUR_MESSAGE, employeErreur);

    }

    @Override
    protected String afficherErreur() {
        return String.format(ERREUR_MESSAGE, employeErreur.getNumeroEmploye());
    }
}