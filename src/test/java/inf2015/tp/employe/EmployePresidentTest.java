/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp.employe;

/**
 *
 * @author Lyes
 */

import inf2015.tp.Projet;
import inf2015.tp.erreur.Erreur;
import inf2015.tp.erreur.ErreurEmployeMaximumBureau;
import inf2015.tp.erreur.ErreurEmployeMinimalUnJourOuvrableBureau;
import inf2015.tp.erreur.ErreurEmployeMinimumBureau;
import inf2015.tp.erreur.ErreurEmployeTeletravailMaximum;
import inf2015.tp.erreur.ErreurJournal;
import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import static org.junit.Assert.*;
import org.junit.Test;

public class EmployePresidentTest {
  @Test
    public void testGetTypeEmploye() {
        String typeEmploye = "President";
        EmployePresident employe = new EmployePresident(0, null);
        assertEquals(typeEmploye, employe.getTypeEmploye());
    } 
      @Test
    public void testEstEmploye() {
        int employeID = 6000;

        assertTrue(EmployePresident.estEmploye(employeID));
    }
          @Test
    public void testEstPasEmploye() {
        int employeID = 1001;

        assertFalse(EmployePresident.estEmploye(employeID));
    }
          
}
