/**
 * Projet - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

public class Projet {

    protected static final int PROJET_ID_CONGE_FERIE = 998;
    protected static final int PROJET_ID_CONGE_MALADIE = 999;
    protected static final int PROJET_ID_CONGE_VACANCE = 997;
    protected static final int PROJET_ID_TELETRAVAIL = 900;
    protected static final int PROJET_ID_CONGE_PARENTAL = 996;
    protected int minutes = 0;
    protected int projetID = 0;

    public Projet(int projetID, int minutes) {
        this.minutes = minutes;
        this.projetID = projetID;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public int getProjetID() {
        return this.projetID;
    }

    public boolean estTeleTravail() {
        return (this.projetID >= PROJET_ID_TELETRAVAIL
                && this.projetID != PROJET_ID_CONGE_MALADIE
                && this.projetID != PROJET_ID_CONGE_FERIE
                && this.projetID != PROJET_ID_CONGE_PARENTAL);
    }

    public boolean estCongeMaladie() {
        return (this.projetID == PROJET_ID_CONGE_MALADIE);
    }

    public boolean estCongeFerie() {
        return (this.projetID == PROJET_ID_CONGE_FERIE);
    }

    public boolean estCongeVacance() {
        return (this.projetID == PROJET_ID_CONGE_VACANCE);
    }

    public boolean estCongeParental() {
        return (this.projetID == PROJET_ID_CONGE_PARENTAL);
    }

    public boolean estTravailBureau() {
        return (this.projetID < PROJET_ID_TELETRAVAIL);
    }

    @Override
    public String toString() {
        return String.format("ProjetID: %d Minutes: %d Heures: %f",
                this.projetID, this.minutes,
                ((float) this.minutes / 60.0));
    }
}
