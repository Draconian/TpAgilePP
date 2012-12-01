/**
 * Erreur - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.Employe;
import inf2015.tp.jour.Jour;

public abstract class Erreur {

    protected String erreurMessage;
    protected Employe employeErreur;
    protected Jour jourErreur;

    protected Erreur(String erreurMessage, Jour erreurJour) {
        this.erreurMessage = erreurMessage;
        this.jourErreur = erreurJour;
    }

    protected Erreur(String erreurMessage, Employe erreurEmploye) {
        this.erreurMessage = erreurMessage;
        this.employeErreur = erreurEmploye;
    }

    protected Erreur(String erreurMessage, Employe erreurEmploye, Jour erreurJour) {
        this.erreurMessage = erreurMessage;
        this.employeErreur = erreurEmploye;
        this.jourErreur = erreurJour;
    }

    public Jour getJourErreur() {
        return this.jourErreur;
    }

    protected abstract String afficherErreur();

    @Override
    public String toString() {
        return this.afficherErreur();
    }
}
