package modello.eccezioni;

public class GestioneListeException extends Exception{
	private static final long serialVersionUID = 1L;

	// costruttore con parametro
	public GestioneListeException(String messaggio) {
		
		super(messaggio);
		
	}
}
