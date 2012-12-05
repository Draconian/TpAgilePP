/**
 * EmployeDirectionTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.employe;

import inf2015.tp.Projet;
import inf2015.tp.erreur.Erreur;
import inf2015.tp.erreur.ErreurEmployeMinimalUnJourOuvrableBureau;
import inf2015.tp.erreur.ErreurJournal;
import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmployeDirectionTest {

    @Test
    public void testGetTypeEmploye() {
        String typeEmploye = "Direction";

        Employe employe = new EmployeDirection(0, null);

        assertEquals(typeEmploye, employe.getTypeEmploye());
    }

    @Test
    public void testEstEmploye() {
        int numeroEmploye = 5500;

        assertTrue(EmployeDirection.estEmploye(numeroEmploye));
    }

    @Test
    public void testVerifierMinimumMinutesQuotidienneAdministrationValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour", erreurJournal);
        Projet projet = new Projet(100, 500);
        jour.ajoutProjet(projet);
        Employe employe = new EmployeAdministration(0, erreurJournal);
        employe.ajoutJour(jour);

        employe.verifierMinimumMinutesQuotidiennes();

        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void testVerifierMinimumMinutesQuotidienneAdministrationInvalide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour", erreurJournal);
        Projet projet = new Projet(100, 100);
        jour.ajoutProjet(projet);
        Employe employe = new EmployeAdministration(0, erreurJournal);
        employe.ajoutJour(jour);

        employe.verifierMinimumMinutesQuotidiennes();

        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurEmployeMinimalUnJourOuvrableBureau.class, erreur.getClass());
    }

    @Test
    public void testEstPasEmploye() {
        int numeroEmploye = 6000;

        assertFalse(EmployeDirection.estEmploye(numeroEmploye));
    }
}
