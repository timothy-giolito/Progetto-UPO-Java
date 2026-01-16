package modello;

import modello.eccezioni.ArticoloException;

/**
 * Rappresenta un singolo articolo acquistabile o inseribile in una lista della spesa.
 * <p>
 * Ogni articolo è caratterizzato da un nome, una categoria, un prezzo, una nota opzionale
 * e un reparto di appartenenza. La classe gestisce valori di default per campi mancanti
 * o non validi.
 * </p>
 * @author Timothy Giolito 20054431
 */

public class Articolo {

    // Valori di default
    private static final String DEFAULT_CATEGORIA = "Non categorizzato";
    private static final double DEFAULT_PREZZO = 0.0;
    private static final String DEFAULT_NOTA = "";
    private static final Reparto DEFAULT_REPARTO = Reparto.ALTRO; 
    
    private String nome; 
    private String categoria;
    private double prezzo;
    private String nota;
    private Reparto reparto;
    
    /**
     * Costruisce un nuovo oggetto Articolo con i dettagli specificati.
     * @param nome: Il nome dell'articolo (non può essere null o vuoto).
     * @param categoria: La categoria dell'articolo (se null o vuota, viene assegnato un valore di default).
     * @param prezzo: Il prezzo dell'articolo (se negativo, viene impostato a 0.0).
     * @param nota: Una nota aggiuntiva (opzionale).
     * @param reparto: Il reparto di appartenenza (se null, viene assegnato {@link Reparto#NESSUNO}).
     * @throws ArticoloException: Se il nome dell'articolo è null o vuoto.
     */
    
    // Costruttore della classe aggiornato con il parametro Corsia
    public Articolo(String nome, String categoria, double prezzo, String nota, Reparto reparto) throws ArticoloException {
        
        // Controllo sul nome del prodotto
    	
        if(nome == null || nome.trim().isEmpty()) {
        	
            throw new ArticoloException("ERRORE: il nome dell'articolo non può essere non specificato!");
            
        } else {
<<<<<<< HEAD

            this.nome = nome.trim();

=======
            
            this.nome = nome.trim();
            
>>>>>>> 6ee1a2f5a66b621cbc97c024fca76699626d4f88
        }
        
        // Gestione della categoria
        if(categoria == null || categoria.trim().isEmpty()) {
            this.categoria = DEFAULT_CATEGORIA;
        } else {
            this.categoria = categoria;
        }
        
        // Gestione del prezzo
        if(prezzo < 0) {
            this.prezzo = DEFAULT_PREZZO;
        } else {
            this.prezzo = prezzo;
        }
        
        // Gestione della nota
        if(nota == null) {
            this.nota = DEFAULT_NOTA;
        } else {
            this.nota = nota;
        }    
        
        // Gestione della corsia senza operatore ternario
        if(reparto == null) {
            this.reparto = DEFAULT_REPARTO;
        } else {
            this.reparto = reparto;
        }
    }
    
    // --- METODI GETTER E SETTER ---
    
    /**
     * Restituisce il reparto di appartenenza dell'articolo.
     * @return Il reparto corrente.
     */

    // Getter e Setter per Corsia (Senza operatore ternario)
    public Reparto getReparto() {
        return reparto;
    }

    /**
     * Imposta il reparto dell'articolo.
     * @param reparto Il nuovo reparto. Se null, viene impostato il valore di default.
     */
    
    public void setReparto(Reparto reparto) {
        if (reparto == null) {
            this.reparto = DEFAULT_REPARTO;
        } else {
            this.reparto = reparto;
        }
    }

    // Altri Getter e Setter esistenti
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ArticoloException {
        if(nome == null || nome.trim().isEmpty()) {
            throw new ArticoloException("Il nome non puo' essere vuoto!");
        } else {
            this.nome = nome;
        }
    }
    
    /**
     * Permette di settare la categoria attribuita ad un articolo della lista.
     */
    public void setCategoria(String categoria) {
    	
        if(categoria == null || categoria.trim().isEmpty()) {
        	
            this.categoria = DEFAULT_CATEGORIA;
            
        } else {
        	
            this.categoria = categoria;
        }
    }
    
    /**
     * Permette di ritornare il contenuto di una nota attribuita ad un articolo della lista.
     */
    public String getCategoria() {
        return categoria;
    }

    public double getPrezzo() {
        return prezzo;
    }

    /**
     * Imposta il prezzo dell'articolo.
     * @param prezzo Il nuovo prezzo.
     * @throws ArticoloException Se il prezzo fornito è negativo.
     */
    
    public void setPrezzo(double prezzo) throws ArticoloException {
        if(prezzo < 0) {
            throw new ArticoloException("Il prezzo non puo' essere negativo!");
        } else {
            this.prezzo = prezzo;
        }
    }
    
   
    /**
     * Permette di settare il contenuto di una nota attribuita ad un articolo della lista.
     */
    public void setNota(String nota) {
        if(nota == null) {
            this.nota = DEFAULT_NOTA;
        } else {
            this.nota = nota;
        }
    }
    
    /**
     * Permette di ritornare il contenuto di una nota attribuita ad un articolo della lista.
     */
    
    public String getNota() {
    	
        return nota;
    }
    
    
    /**
     * Controllo sui nomi degli articoli inseriti per vedere se sono uguali.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Articolo articolo = (Articolo) o;
        // Esempio: due articoli sono uguali se hanno lo stesso nome (case-insensitive)
        return nome.equalsIgnoreCase(articolo.nome);
    }

    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode();
    }
    
    // Metodo toString per rappresentazione visiva aggiornato
    
    /**
     * Restituisce una rappresentazione testuale dell'articolo.
     * @return Una stringa formattata contenente nome, reparto, categoria, prezzo e nota.
     */
    
    @Override
    public String toString() {
        return "Articolo: " + nome + " | Reparto: " + reparto + " | Categoria: " + categoria + " | Prezzo: " + prezzo + "€ | Nota: " + nota;
    }
}
