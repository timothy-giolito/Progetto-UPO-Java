package modello.eccezioni;

/**
 * Eccezione personalizzata che segnala errori di validazione o logica relativi a un {@link modello.Articolo}.
 * <p>
 * Viene lanciata, ad esempio, quando si tenta di assegnare un nome vuoto o un prezzo negativo.
 * </p>
 * @author Luca Franzon 20054744
 */

public class ArticoloException extends Exception {
	private static final long serialVersionUID = 1L;

	
	/**
     * Costruisce un'eccezione senza messaggio di dettaglio.
     */
	public ArticoloException() {
		
		super();
	}
	
	/**
     * Costruisce un'eccezione con un messaggio descrittivo.
     * @param messaggio La descrizione dell'errore.
     */
	public ArticoloException(String messaggio) {
		
		super(messaggio);
	}
	
	
	/**
     * Costruisce un'eccezione con un messaggio e la causa originale.
     * @param messaggio La descrizione dell'errore.
     * @param causa L'eccezione originale che ha causato questo errore.
     */
	public ArticoloException(String messaggio, Throwable causa) {
		
		super(messaggio, causa);
	}
	
}
