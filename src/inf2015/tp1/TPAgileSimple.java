/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp1;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fdufault
 */
public class TPAgileSimple {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Employer employer = new Employer();
        
        try {
            employer.AnalyserFeuilleTemps(args[0], args[1]);
        } catch (IOException ex) {
            Logger.getLogger(TPAgileSimple.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
