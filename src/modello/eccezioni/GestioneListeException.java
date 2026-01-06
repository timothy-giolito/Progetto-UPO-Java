package modello.eccezioni;

/**
 * Eccezione generata dal gestore centrale {@link modello.GestioneListe}.
 * <p>
 * Indica errori operazioni globali, come il tentativo di creare una lista con nome duplicato
 * o rimuovere una categoria inesistente.
 * </p>
 * @author Timothy Giolito 20054431
 */
public class GestioneListeException extends Exception{
    private static final long serialVersionUID = 1L;

    /**
     * Costruisce un'eccezione con il messaggio specificato.
     * @param messaggio La descrizione dell'errore avvenuto nel gestore liste.
     */
    public GestioneListeException(String messaggio) {
        super(messaggio);
    }
}