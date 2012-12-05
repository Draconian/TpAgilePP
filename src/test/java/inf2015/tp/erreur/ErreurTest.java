/**
 * ErreurTest - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.jour.Jour;
import inf2015.tp.jour.JourOuvrable;
import static org.junit.Assert.*;
import org.junit.Test;

public class ErreurTest {

    @Test
    public void testGetJour() {
        Jour jour = new JourOuvrable(null,null);
        Erreur erreur = new ErreurJourCongeWeekend(jour, null);
        
        assertSame(jour, erreur.getJourErreur());
    }
}
