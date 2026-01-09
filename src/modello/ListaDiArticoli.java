package modello;

import modello.eccezioni.ListaDiArticoliException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Rappresenta una lista della spesa contenente un insieme di articoli.
 * <p>
 * Gestisce sia gli articoli attivi (da comprare) che quelli cancellati (cestino).
 * Implementa Iterable per scorrere entrambe le liste in sequenza.
 * </p>
 * @author Luca Franzon 20054744
 */
public class ListaDiArticoli implements Iterable<Articolo> {
    
    private String nome;
    private List<Articolo> articoli;   // Articoli da comprare
    private List<Articolo> cancellati; // Cestino

    public ListaDiArticoli(String nome) throws ListaDiArticoliException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ListaDiArticoliException("Errore, è necessario il nome della lista");
        }
        this.nome = nome;
        this.articoli = new ArrayList<>();
        this.cancellati = new ArrayList<>();
    }

    // --- GETTER & SETTER ---

    public String getListaNome() {
        return nome;
    }

    public void setListaNome(String nome) throws ListaDiArticoliException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ListaDiArticoliException("Errore, serve il nome della lista");
        }
        this.nome = nome;
    }

    public List<Articolo> getArticoli() {
        return articoli;
    }
    
    public List<Articolo> getCancellati() {
        return cancellati;
    }

    // --- GESTIONE ARTICOLI ---

    public void AggiungiArticolo(Articolo articolo) throws ListaDiArticoliException {
        if (articolo == null) {
            throw new ListaDiArticoliException("Errore, è necessario l'articolo");     
        }
        // Evita duplicati se era nel cestino
        if(cancellati.contains(articolo)) {
            cancellati.remove(articolo);
        }
        this.articoli.add(articolo);
    }
    
    /**
     * Rimuove un articolo dalla lista della spesa e lo sposta nel cestino.
     */
    public void RimuoviArticolo(Articolo articolo) {
        if (this.articoli.remove(articolo)) {
            this.cancellati.add(articolo);
        }
    }
    
    /**
     * Ripristina un articolo dal cestino alla lista della spesa.
     */
    public boolean RipristinaArticolo(Articolo articolo) {
        if (this.cancellati.remove(articolo)) {
            this.articoli.add(articolo);
            return true;
        }
        return false;
    }
    
    public void SvuotaCestino() {
        this.cancellati.clear();
    }

    // --- FUNZIONALITÀ RICHIESTE DAL PDF ---

    /**
     * Cerca un articolo per prefisso sia nella lista attiva che nel cestino.
     */
    public Articolo TrovaArticoloPerPrefisso(String prefisso) {
        if (prefisso == null || prefisso.isEmpty()) return null;
        String search = prefisso.toLowerCase();

        // 1. Cerca nei "Da Comprare"
        for (Articolo a : articoli) {
            if (a.getNome().toLowerCase().startsWith(search)) return a;
        }
        // 2. Cerca nel "Cestino"
        for (Articolo a : cancellati) {
            if (a.getNome().toLowerCase().startsWith(search)) return a;
        }
        return null;
    }

    /**
     * Calcola il prezzo totale degli articoli attivi (da comprare).
     */
    public double getPrezzoTotale() {
        double totale = 0.0;
        for (Articolo a : articoli) {
            totale += a.getPrezzo();
        }
        return totale;
    }

    public int getNumeroArticoli() {
        return this.articoli.size();
    }
    
    public int getNumeroArticoliCancellati() {
        return this.cancellati.size();
    }

    /**
     * Iteratore che scorre PRIMA gli articoli attivi e POI quelli cancellati.
     */
    @Override
    public Iterator<Articolo> iterator() {
        List<Articolo> listaCompleta = new ArrayList<>(articoli);
        listaCompleta.addAll(cancellati);
        return listaCompleta.iterator();
    }
    
    /**
     * Calcola il prezzo totale degli articoli spostati nel cestino.
     */
    public double getPrezzoTotaleCestino() {
        double totale = 0.0;
        for (Articolo a : cancellati) {
            totale += a.getPrezzo();
        }
        return totale;
    }
    
    @Override
    public String toString() {
        return "Lista: " + nome + 
               " (Da comprare: " + articoli.size() + 
               ", Cestino: " + cancellati.size() + 
               ", Totale: " + String.format("%.2f", getPrezzoTotale()) + "€" +
               ", Totale Cestino: " + String.format("%.2f", getPrezzoTotaleCestino()) + "€)";
    }
}