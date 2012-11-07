/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp;

import java.io.IOException;
import net.sf.json.JSONArray;

/**
 *
 * @author fdufault
 */
public class ErreurLog {
    private static ErreurLog ERREUR_LOG_INSTANCE = new ErreurLog();
    
    private JSONArray _erreurs = new JSONArray();
    
    
    
    public void ajoutErreur(String aErreurMessage)
    {
        this._erreurs.add(aErreurMessage);
        System.out.println(aErreurMessage);
    }
    
    public void ecrireErreur(String aCheminFichier) throws IOException
    {
        JSONUtil.writeJSONArray(this._erreurs, aCheminFichier);
    }
    
    @Override
    public String toString()
    {
        //toString(5) => 5 espaces pour chaque indentation.
       return this._erreurs.toString(5);
    }
    
    public void effacerTout()
    {
        this._erreurs.clear();
    }
    
    public static ErreurLog Instance()
    {
        return ERREUR_LOG_INSTANCE;
    }
    
    
    
}
