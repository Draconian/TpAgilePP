/**
 * ErreurJournalTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.Employe;
import static org.junit.Assert.*;
import org.junit.Test;

public class ErreurJournalTest {

    @Test
    public void testAjoutErreur() {
        Erreur erreurExpected = new ErreurEmployeCongeParentalMultiple();
        ErreurJournal erreurJournal = new ErreurJournal();

        erreurJournal.ajoutErreur(erreurExpected);
        Erreur erreurRecu = erreurJournal.erreurs.get(0);

        assertSame(erreurExpected, erreurRecu);
    }

    @Test
    public void testGetErreurAIndex() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Erreur erreur1 = new ErreurEmployeCongeParentalMultiple();
        erreurJournal.ajoutErreur(erreur1);

        Employe employe = new Employe(500, null);
        int maxMinutes = 60;
        Erreur erreur2 = new ErreurEmployeMaximumBureau(employe, maxMinutes);
        erreurJournal.ajoutErreur(erreur2);

        assertSame(erreur1, erreurJournal.getErreurAIndex(0));
        assertSame(erreur2, erreurJournal.getErreurAIndex(1));
    }

    @Test
    public void testEffacerTout() {
        Erreur erreur = new ErreurEmployeCongeParentalMultiple();
        ErreurJournal erreurJournal = new ErreurJournal();
        erreurJournal.ajoutErreur(erreur);
        erreurJournal.effacerTout();

        assertTrue(erreurJournal.erreurs.isEmpty());
    }

    @Test
    public void testNombresErreurs() {
        int nombresErreursExpecter = 2;
        
        ErreurJournal erreurJournal = new ErreurJournal();
        Erreur erreur1 = new ErreurEmployeCongeParentalMultiple();
        erreurJournal.ajoutErreur(erreur1);

        Employe employe = new Employe(500, null);
        int maxMinutes = 60;
        Erreur erreur2 = new ErreurEmployeMaximumBureau(employe, maxMinutes);
        erreurJournal.ajoutErreur(erreur2);

        int nombresErreursRecu = erreurJournal.getNombresErreurs();

        assertEquals(nombresErreursExpecter, nombresErreursRecu);
    }

    @Test
    public void testEstVide() {
        ErreurJournal erreurJournal = new ErreurJournal();

        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void testEstPasVide() {
        Erreur erreur = new ErreurEmployeCongeParentalMultiple();
        ErreurJournal erreurJournal = new ErreurJournal();
        erreurJournal.ajoutErreur(erreur);
        
        assertFalse(erreurJournal.estVide());
    }

    @Test
    public void testConvertirEnJsonArray() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Erreur erreur = new ErreurEmployeCongeParentalMultiple();
        erreurJournal.ajoutErreur(erreur);

        Employe employe = new Employe(500, null);
        int maxMinutes = 60;
        erreur = new ErreurEmployeMaximumBureau(employe, maxMinutes);

        erreurJournal.ajoutErreur(erreur);

        String jsonExpected = "[\"Une semaine ne peut contenir"
                + " plus qu'un congé parental.\",\"L'employé employé a dépassé le nombre d'heures maximum au "
                + "bureau. Celui-ci ne peut pas dépasser: 1,0 heures.\"]";
        String jsonrecu = erreurJournal.convertirEnJsonArray().toString();

        assertEquals(jsonExpected, jsonrecu);
    }
}
