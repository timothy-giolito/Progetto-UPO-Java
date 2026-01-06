package modello.eccezioni;

/**
 * Eccezione specifica per errori che si verificano all'interno di una {@link modello.ListaDiArticoli}.
 * <p>
 * Può essere sollevata durante la creazione di una lista (nome non valido) o l'aggiunta di elementi.
 * </p>
 * @author Luca Franzon 20054744
 */
public class ListaDiArticoliException extends Exception{

    private static final long serialVersionUID = 1L;

    /**
     * Costruttore di default.
     */
    public ListaDiArticoliException()
    {
        super();
    }

    /**
     * Costruisce un'eccezione con un messaggio di errore specifico.
     * @param messaggio Il dettaglio dell'errore riscontrato.
     */
    public ListaDiArticoliException(String messaggio)
    {
        super(messaggio);
    }

    /**
     * Costruisce un'eccezione concatenando un messaggio e una causa precedente.
     * @param messaggio Il dettaglio dell'errore.
     * @param causa L'eccezione che ha provocato questo errore.
     */
    public ListaDiArticoliException(String messaggio, Throwable causa)
    {
        super(messaggio, causa);
    }
}