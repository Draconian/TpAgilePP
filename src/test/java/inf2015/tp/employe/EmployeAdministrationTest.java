/**
 * EmployeExploitationTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.employe;

import inf2015.tp.Projet;
import inf2015.tp.erreur.Erreur;
import inf2015.tp.erreur.ErreurEmployeMaximumBureau;
import inf2015.tp.erreur.ErreurEmployeMinimalUnJourOuvrableBureau;
import inf2015.tp.erreur.ErreurEmployeMinimumBureau;
import inf2015.tp.erreur.ErreurEmployeTeletravailMaximum;
import inf2015.tp.erreur.ErreurJournal;
import inf2015.tp.erreur.ErreurTempsMaximaleTransport;
import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import static org.junit.Assert.*;
import org.junit.Test;

public class EmployeAdministrationTest {
    
    @Test
    public void testGetTypeEmploye() {
        String typeEmploye = "Administration";
        EmployeAdministration employe = new EmployeAdministration(0, null);
        
        assertEquals(typeEmploye, employe.getTypeEmploye());
    }
    
    @Test
    public void testEstEmploye() {
        int employeID = 500;
        
        assertTrue(EmployeAdministration.estEmploye(employeID));
    }
    
    @Test
    public void testEstPasEmploye() {
        int employeID = 1001;
        
        assertFalse(EmployeAdministration.estEmploye(employeID));
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
    public void testVerifierTeleTravailValide() {
        int minutesTeletravail = 599;
        ErreurJournal erreurJournal = new ErreurJournal();
        
        EmployeAdministration employe = new EmployeAdministration(0, erreurJournal);
        employe.minutesTeleTravail = minutesTeletravail;
        employe.verifierTeleTravail();
        
        assertTrue(erreurJournal.estVide());
    }
    
    @Test
    public void testVerifierTeleTravailInvalide() {
        int minutesTeletravail = 601;
        ErreurJournal erreurJournal = new ErreurJournal();
        
        EmployeAdministration employe = new EmployeAdministration(0, erreurJournal);
        employe.minutesTeleTravail = minutesTeletravail;
        employe.verifierTeleTravail();
        
        assertEquals(1, erreurJournal.getNombresErreurs());
        
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurEmployeTeletravailMaximum.class, erreur.getClass());
    }
    
    @Test
    public void TestAnalyserFeuilleTempsValide() {
        ErreurJournal erreurJournal = new ErreurJournal();
        
        Employe employe = new EmployeAdministration(0, erreurJournal);
        employe.minutesJoursOuvrableBureau = 2100;
        employe.minutesWeekendBureau = 200;
        
        employe.analyserFeuilleTemps();
        
        assertTrue(erreurJournal.estVide());
    }
    
    @Test
    public void TestAnalyserFeuilleTempsInvalideMinimum() {
        ErreurJournal erreurJournal = new ErreurJournal();
        
        Employe employe = new EmployeAdministration(0, erreurJournal);
        employe.minutesJoursOuvrableBureau = 100;
        employe.minutesWeekendBureau = 200;
        
        employe.analyserFeuilleTemps();
        
        assertEquals(1, erreurJournal.getNombresErreurs());
        
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurEmployeMinimumBureau.class, erreur.getClass());
    }
    
    @Test
    public void TestAnalyserFeuilleTempsInvalideMaximum() {
        ErreurJournal erreurJournal = new ErreurJournal();
        
        Employe employe = new EmployeAdministration(0, erreurJournal);
        employe.minutesJoursOuvrableBureau = 100;
        employe.minutesWeekendBureau = 2500;
        
        employe.analyserFeuilleTemps();
        
        assertEquals(1, erreurJournal.getNombresErreurs());
        
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurEmployeMaximumBureau.class, erreur.getClass());
    }

    @Test
    public void TestMaximumTempsTransport() {
        ErreurJournal erreurJournal = new ErreurJournal();
        
        Employe employe = new EmployeAdministration(500, erreurJournal);
        int minuteInvalide = 400;
        
        employe.validerMinutesTransport(minuteInvalide);
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurTempsMaximaleTransport.class, erreur.getClass());
    }

    @Test
    public void TestValideTempsTransport() {
        ErreurJournal erreurJournal = new ErreurJournal();
        
        Employe employe = new EmployeAdministration(500, erreurJournal);
        int minuteInvalide = 200;
        
        employe.validerMinutesTransport(minuteInvalide);
        
        assertEquals(erreurJournal.getNombresErreurs(), 0);
    }
     @Test
    public void testVerifierAjoutMinutesBureauTransport() {
        ErreurJournal erreurJournal = new ErreurJournal();
       int minutes=200;
        Employe employe = new EmployeAdministration(500, erreurJournal);   
        employe.minutesJoursOuvrableBureau = 200;
        employe.ajusterLesMinutes(200);
        assertEquals(employe.minutesJoursOuvrableBureau,400);
    }
          @Test
    public void testVerifierPasAjouterMinutesBureauTransport() {
        ErreurJournal erreurJournal = new ErreurJournal();
       int minutes=200;
        Employe employe = new EmployeAdministration(500, erreurJournal);   
        employe.minutesJoursOuvrableBureau = 200;
        employe.ajusterLesMinutes(302);
        assertEquals(employe.minutesJoursOuvrableBureau,200);
    }
      
}
