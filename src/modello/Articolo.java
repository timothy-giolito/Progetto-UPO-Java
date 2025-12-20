package modello;

import modello.eccezioni.ArticoloException;

public class Articolo {

	// valori di default
	private static final String DEFAULT_CATEGORIA = "Non categorizzato";
	private static final double DEFAULT_PREZZO = 0.0;
	private static final String DEFAULT_NOTA = "";
	
	
	private String nome; 
	private String categoria;
	private double prezzo;
	private String nota;
	
	// costruttore classe
	public Articolo(String nome, String categoria, double prezzo, String nota) throws ArticoloException {
		
		// controllo sul nome del prodotto
		if(nome == null || nome.trim().isEmpty()) {
			
			throw new ArticoloException("ERRORE: il nome dell'articolo non può essere vuoto!");
		
		} else {
			
			//assegnazione del nome dell'articolo in caso non fosse nullo o vuoto
			this.nome = nome;
		}
		
		// gestione della categoria
		if(categoria == null || categoria.trim().isEmpty()) {
			
			// se la categoria non e' specificata --> valore di default
			this.categoria = DEFAULT_CATEGORIA;
		
		} else {
			
			this.categoria = categoria;
		}
		
		// gestione del prezzo
		if(prezzo < 0) {
			
			this.prezzo = DEFAULT_PREZZO;
		
		} else {
			
			this.prezzo = prezzo;
		}
		
		//gestione della nota
		if(nota == null) {
			
			this.nota = DEFAULT_NOTA;
		
		} else {
			
			this.nota = nota;
		}		
		
	}
	
	// METODI getter e setter
	
	// setter nome
	public String getNome() {
				
		return nome;
	}
	
	// getter nome
	public void setNome(String nome) throws ArticoloException {
		
		if(nome == null || nome.trim().isEmpty()) {
			
			throw new ArticoloException("Il nome non puo' essere vuoto!");
		
		} else {
			
			this.nome = nome;
		}
	}
	
	// getter categoria
	public String getCategoria() {
		
		return categoria;
	}
	
	// setter categoria
	public void setCategoria(String categoria) {
		
		if(categoria == null || categoria.trim().isEmpty()) {
			
			this.categoria = DEFAULT_CATEGORIA;
		
		} else {
			
			this.categoria = categoria;
		}
	}
	
	// getter prezzo
	public double getPrezzo() {
		
		return prezzo;
	}
	
	// setter prezzo
	public void setPrezzo(double prezzo) throws ArticoloException {
		
		if(prezzo < 0) {
			
			throw new ArticoloException ("Il prezzo non puo' essere vuoto!");
		
		} else {
			
			this.prezzo = prezzo;
		}
	}
	
	// getter nota
	public String getNota() {
		
		return nota;
	}
	
	// setter nota
	public void setNota(String nota) {
		
		if(nota == null) {
			
			this.nota = DEFAULT_NOTA;
		
		} else {
			
			this.nota = nota;
		}
	}
	
	// metodo toString per rappresentazione visiva
	@Override
    public String toString() {
        return "Articolo: " + nome + " | Categoria: " + categoria + " | Prezzo: " + prezzo + "€ | Nota: " + nota;
    }
	
}
