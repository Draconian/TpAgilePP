/**
 * TPAgile - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp;

public class TpAgile {
    
    public static void main(String[] args) {
        
        if(args.length != 2)
        {
            System.out.println("Erreur dans les arguments passés au programme: TpAgile [chemin feuille temps] [chemin erreur sortie]");
            System.exit(-1);
        }   
        
        Employe employer = new Employe(args[0], args[1]);
        employer.approuverFeuilleDeTemps();
    }
    
}
