package modello;

import modello.eccezioni.ArticoloException;

public class Articolo {

    // Valori di default
    private static final String DEFAULT_CATEGORIA = "Non categorizzato";
    private static final double DEFAULT_PREZZO = 0.0;
    private static final String DEFAULT_NOTA = "";
    private static final Corsia DEFAULT_CORSIA = Corsia.NESSUNA; 
    
    private String nome; 
    private String categoria;
    private double prezzo;
    private String nota;
    private Corsia corsia;
    
    // Costruttore della classe aggiornato con il parametro Corsia
    public Articolo(String nome, String categoria, double prezzo, String nota, Corsia corsia) throws ArticoloException {
        
        // Controllo sul nome del prodotto
        if(nome == null || nome.trim().isEmpty()) {
            throw new ArticoloException("ERRORE: il nome dell'articolo non può essere vuoto!");
        } else {
            this.nome = nome;
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
        if(corsia == null) {
            this.corsia = DEFAULT_CORSIA;
        } else {
            this.corsia = corsia;
        }
    }
    
    // --- METODI GETTER E SETTER ---

    // Getter e Setter per Corsia (Senza operatore ternario)
    public Corsia getCorsia() {
        return corsia;
    }

    public void setCorsia(Corsia corsia) {
        if (corsia == null) {
            this.corsia = DEFAULT_CORSIA;
        } else {
            this.corsia = corsia;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        if(categoria == null || categoria.trim().isEmpty()) {
            this.categoria = DEFAULT_CATEGORIA;
        } else {
            this.categoria = categoria;
        }
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) throws ArticoloException {
        if(prezzo < 0) {
            throw new ArticoloException("Il prezzo non puo' essere vuoto!");
        } else {
            this.prezzo = prezzo;
        }
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        if(nota == null) {
            this.nota = DEFAULT_NOTA;
        } else {
            this.nota = nota;
        }
    }
    
    // Metodo toString per rappresentazione visiva aggiornato
    @Override
    public String toString() {
        return "Articolo: " + nome + " | Corsia: " + corsia + " | Categoria: " + categoria + " | Prezzo: " + prezzo + "€ | Nota: " + nota;
    }
}