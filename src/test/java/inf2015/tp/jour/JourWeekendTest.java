/**
 * JourWeekendTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.jour;

import inf2015.tp.Projet;
import inf2015.tp.erreur.Erreur;
import inf2015.tp.erreur.ErreurJourCongeWeekend;
import inf2015.tp.erreur.ErreurJourDepasseMinute;
import inf2015.tp.erreur.ErreurJournal;
import static org.junit.Assert.*;
import org.junit.Test;

public class JourWeekendTest {
    
    @Test
    public void testEstJourOuvrable() {
        Jour jour = new JourWeekend(null,null);
        
        assertFalse(jour.estJourOuvable());
    }
    
    @Test
    public void testVerifierMaxMinutesJourWeekendValide(){
         ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourWeekend("jour1", erreurJournal);

        Projet p1 = new Projet(500, Jour.MAX_MINUTES_PAR_JOURS - 1);
        jour.ajoutProjet(p1);

        jour.verifierMaxMinutesJour();

        assertTrue(erreurJournal.estVide());
    }
    
     @Test
    public void testVerifierMaxMinutesJourWeekendInvalide(){
         ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourWeekend("jour1", erreurJournal);

        Projet p1 = new Projet(500, Jour.MAX_MINUTES_PAR_JOURS + 2);
        jour.ajoutProjet(p1);

        jour.verifierMaxMinutesJour();

        assertEquals(1, erreurJournal.getNombresErreurs());
        
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurJourDepasseMinute.class, erreur.getClass());
    }
    
    @Test
    public void testAnalyserJourFerie() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourWeekend("weekend1", erreurJournal);
        
        jour.analyserJourFerie();
        
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurJourCongeWeekend.class, erreur.getClass());
    }

    @Test
    public void testAnalyserJourMaladie() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourWeekend("weekend1", erreurJournal);
        
        jour.analyserJourMaladie();
        
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurJourCongeWeekend.class, erreur.getClass());
    }

    @Test
    public void testAnalyserJourVacances() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourWeekend("weekend1", erreurJournal);
        
        jour.analyserJourVacances();
        
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurJourCongeWeekend.class, erreur.getClass());
    }

    @Test
    public void testAnalyserJourParental() {
        ErreurJournal erreurJournal = new ErreurJournal();
        Jour jour = new JourWeekend("weekend1", erreurJournal);
        
        jour.analyserJourParental();
        
        Erreur erreur = erreurJournal.getErreurAIndex(0);
        assertEquals(ErreurJourCongeWeekend.class, erreur.getClass());
    }
}
