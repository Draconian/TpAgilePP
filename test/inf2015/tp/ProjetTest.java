/**
 * ProjetTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProjetTest {

    @Test
    public void testEstCongeParental() {
        Projet projet = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 0);

        assertTrue(projet.estCongeParental());
    }
    
    @Test
    public void testCongeParentalEstPasTeleTavail()
    {
        Projet projet = new Projet(Projet.PROJET_ID_CONGE_PARENTAL, 0);

        assertFalse(projet.estTeleTravail());
    }
    
    
}
