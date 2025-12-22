package modello;

import java.util.ArrayList;
import java.util.List;
import modello.eccezioni.GestioneListeException;

public class GestioneListe {

	// definizione lista
	private List<ListaDiArticoli> Liste;
	
	// costruttore di ArrayList per contenere una nuova lista di articoli
	public GestioneListe() {
		
		this.Liste = new ArrayList<>();
	}
}
