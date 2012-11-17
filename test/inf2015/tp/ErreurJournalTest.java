/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fdufault
 */
public class ErreurJournalTest {

    @Test
    public void testAjoutErreur() {
        String erreurMessage = "Une erreur";
        ErreurJournal erreurJournal = new ErreurJournal();
        
        erreurJournal.ajoutErreur(erreurMessage);
        String erreurAjouter = erreurJournal.erreurs.getString(0);
        
        assertEquals(erreurMessage,erreurAjouter);
    }

    @Test
    public void testEffacerTout() {
        ErreurJournal erreurJournal = new ErreurJournal();
        erreurJournal.ajoutErreur("Une erreur");
        erreurJournal.ajoutErreur("Une erreur 2");
        
        erreurJournal.effacerTout();
        
        assertTrue(erreurJournal.erreurs.isEmpty());
    }

    @Test
    public void testContientErreur() {
        ErreurJournal erreurJournal = new ErreurJournal();
        erreurJournal.ajoutErreur("123");
        erreurJournal.ajoutErreur("12345");
        
        assertTrue(erreurJournal.contientErreur());
    }
    
    @Test
    public void testContientPasErreur() {
        ErreurJournal erreurJournal = new ErreurJournal();
        
        assertFalse(erreurJournal.contientErreur());
    }
}
