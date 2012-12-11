/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp.erreur;

import inf2015.tp.employe.Employe;

/**
 *
 * @author Lyes
 */
public class ErreurTempsMaximaleTransport extends Erreur{
  protected static final String ERREUR_MESSAGE = "L'employé %s a dépassé le nombre "
            + "d'heures de transport. Il ne peut pas dépasser %.1f heures dans la semaine.";
    private int maxMinutes;
    
   public ErreurTempsMaximaleTransport(Employe employeErreur, int maxMinutes) {
        super(ERREUR_MESSAGE, employeErreur);
        this.maxMinutes = maxMinutes;
    }
    @Override
    protected String afficherErreur() {
          return String.format(ERREUR_MESSAGE, employeErreur.getNumeroEmploye(),
                 maxMinutes);
    }
    
}
