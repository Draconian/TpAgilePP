/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp;

import inf2015.tp.Jour.TypeJour;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fdufault
 */
public class EmployeTest {

    @Test
    public void testSemaineContientUnSeulCongeParental() {
        Employe employe = new Employe(null, null);
        Jour jour1 = new Jour("jour1", TypeJour.OUVRABLE);
        Jour jour2 = new Jour("jour2", TypeJour.OUVRABLE);

        jour1.ajoutProjet(new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 400));
        jour2.ajoutProjet(new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 300));

        employe.semaines.add(jour1);
        employe.semaines.add(jour2);

        employe.verifierCongeParental();

        assertEquals(ErreurJournal.Instance().erreurs.size(), 1);
    }
}