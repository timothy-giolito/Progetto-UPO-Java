package modello.eccezioni;

public class ArticoloException extends Exception {
	private static final long serialVersionUID = 1L;

	// costruttore senza parametro
	public ArticoloException() {
		
		super();
	}
	
	//costruttore con messaggio di errore
	public ArticoloException(String messaggio) {
		
		super(messaggio);
	}
	
	//costruttore con messaggio e causa
	public ArticoloException(String messaggio, Throwable causa) {
		
		super(messaggio, causa);
	}
	
}
