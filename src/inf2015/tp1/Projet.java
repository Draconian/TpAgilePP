/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inf2015.tp1;

/**
 *
 * @author fdufault
 */
public class Projet {
    private static final int PROJET_ID_CONGE_FERIE = 998;
    private static final int PROJET_ID_CONGE = 999;
    private static final int PROJET_ID_TELETRAVAIL = 900;
    
    private int _minutes = 0;
    private int _projetID = 0;
    
    public Projet(int aProjetID, int aMinutes)
    {
        this._minutes = aMinutes;
        this._projetID = aProjetID;
    }
    
    
    public int getMinutes()
    {
        return this._minutes;
    }
    
    public int getProjetID()
    {
        return this._projetID;
    }
    
    public boolean estTeleTravail()
    {
        return (this._projetID >= PROJET_ID_TELETRAVAIL && 
                this._projetID != PROJET_ID_CONGE &&
                this._projetID != PROJET_ID_CONGE_FERIE);
    }
    
    public boolean estCongeFerie()
    {
        return (this._projetID == PROJET_ID_CONGE_FERIE);
    }
    
    public boolean estTravailBureau()
    {
        return (this._projetID < PROJET_ID_TELETRAVAIL);
    }
    
    @Override
    public String toString()
    {
        return String.format("ProjetID: %d Minutes: %d Heures: %f", this._projetID,this._minutes, ((float)this._minutes/60.0));
    }
}
