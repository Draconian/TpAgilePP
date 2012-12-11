/**
 * EmployeDeveloppementTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.employe;

import inf2015.tp.Projet;
import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import inf2015.tp.erreur.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmployeDeveloppementTest {

    @Test
    public void testGetTypeEmploye() {
        String typeEmploye = "Développement";
        EmployeDeveloppement employe = new EmployeDeveloppement(0, null);

        assertEquals(typeEmploye, employe.getTypeEmploye());
    }

    @Test
    public void testEstEmploye() {
        int numeroEmploye = 1500;

        assertTrue(EmployeDeveloppement.estEmploye(numeroEmploye));
    }
   @Test
    public void testEstPasEmploye() {
        int numeroEmploye = 3500;
        assertFalse(EmployeDeveloppement.estEmploye(numeroEmploye));
    }
 
    @Test
    public void TestAnalyserFeuilleTempsValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Employe employe = new EmployeDeveloppement(0, erreurJournal);
        employe.minutesJoursOuvrableBureau = 2200;
        employe.minutesWeekendBureau = 200;      
        employe.analyserFeuilleTemps();
        assertTrue(erreurJournal.estVide());
    }
    @Test
    public void TestAnalyserFeuilleTempsInvalideInferieurTempsMinimum(){
        ErreurJournal erreurJournal = new ErreurJournal();
        Employe employe = new EmployeDeveloppement(0, erreurJournal);
        employe.minutesJoursOuvrableBureau = 1900;
        employe.minutesWeekendBureau = 200;      
        employe.analyserFeuilleTemps();
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurEmployeMinimumBureau.class,erreur.getClass());
    }
    @Test
    public void TestAnalyserFeuilleTempsSuperieurTempsMaximum(){
         ErreurJournal erreurJournal = new ErreurJournal();
        Employe employe = new EmployeDeveloppement(0, erreurJournal);
        employe.minutesJoursOuvrableBureau = 2500;
        employe.minutesWeekendBureau = 200;      
        employe.analyserFeuilleTemps();
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurEmployeMaximumBureau.class,erreur.getClass());
    }
       @Test
    public void testVerifierMinimumMinutesQuotidienneValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour", erreurJournal);
        Projet projet = new Projet(100, 500);
        jour.ajoutProjet(projet);
        Employe employe = new EmployeDeveloppement(0, erreurJournal);
        employe.ajoutJour(jour);

        employe.verifierMinimumMinutesQuotidiennes();

        assertTrue(erreurJournal.estVide());
    }

    @Test
    public void testVerifierMinimumMinutesQuotidienneInvalide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourOuvrable("jour", erreurJournal);
        Projet projet = new Projet(100, 100);
        jour.ajoutProjet(projet);
        Employe employe = new EmployeDeveloppement(0, erreurJournal);
        employe.ajoutJour(jour);

        employe.verifierMinimumMinutesQuotidiennes();
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurEmployeMinimalUnJourOuvrableBureau.class, erreur.getClass());
    }
   


    
    

 
}
