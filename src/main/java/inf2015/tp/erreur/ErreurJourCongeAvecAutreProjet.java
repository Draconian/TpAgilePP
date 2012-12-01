/**
 * ErreurJourCongeAvecAutreProjet - INF2015 - TP Agile - EQUIPE 17
 *
 * @author Francois Dufault
 * @author Lyes Tamazouzt
 * @author Abdessamad Essakhi
 */
package inf2015.tp.erreur;

import inf2015.tp.jour.Jour;

public class ErreurJourCongeAvecAutreProjet extends Erreur {

    protected static final String ERREUR_MESSAGE = "Le jour %s qui est %s "
            + "contient d'autre code de projet dans la même journée.";
    String typeDeJour;

    public ErreurJourCongeAvecAutreProjet(Jour jourErreur, String typeDeJour) {
        super(ERREUR_MESSAGE, jourErreur);

        this.typeDeJour = typeDeJour;
    }

    @Override
    protected String afficherErreur() {
        return String.format(ERREUR_MESSAGE, jourErreur.getNomJour(),
                this.typeDeJour);
    }
}
