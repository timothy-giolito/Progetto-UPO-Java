package modello;

import modello.eccezioni.ListaDiArticoliException;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta una lista della spesa contenente un insieme di articoli.
 * <p>
 * Ogni lista è caratterizzata da un nome identificativo e gestisce una collezione
 * dinamica di oggetti {@link Articolo}. La classe fornisce funzionalità per aggiungere,
 * rimuovere e conteggiare gli elementi, assicurando che il nome della lista sia valido.
 * </p>
 * @author Luca Franzon 20054744
 */

public class ListaDiArticoli {
    private String nome;
    private List<Articolo> articoli;

    //costruttore
    
    /**
     * Rappresenta una lista della spesa che contiene un elenco di articoli.
     * Permette di aggiungere e rimuovere articoli e gestire il nome della lista stessa.
     * Crea una nuova lista di articoli vuota.
     * @param nome Il nome della lista (es. "Spesa Settimanale").
     * @throws ListaDiArticoliException Se il nome fornito è null o vuoto.
     */
    
    public ListaDiArticoli(String nome) throws ListaDiArticoliException
    {
        if (nome == null || nome.trim().isEmpty())
        {
            throw new ListaDiArticoliException("Errore, è necessario il nome");
        }

        this.nome = nome;
        this.articoli = new ArrayList<>();
    }

    //Metodi

    /**
     * Permette di resituire il nome della lista appena creata.
     */
    
    public String getListaNome()
    {
        return nome;
    }

    
    /**
     * Permette di settare un nome che rappresenti una lista di articoli.
     * @param nome Il nome della lista (es. "Spesa Settimanale").
     * @throws ListaDiArticoliException Se il nome fornito è null o vuoto.
     */
    
    public void setListaNome(String nome) throws ListaDiArticoliException
    {
        if (nome == null || nome.trim().isEmpty())
        {
            throw new ListaDiArticoliException("Errore, serve il nome della lista");
        }
        else
        {
            this.nome = nome;
        }
    }

    public List<Articolo> getArticoli()
    {
        return articoli;
    }

    /**
     * Aggiunge un articolo alla lista corrente.
     * @param articolo L'oggetto {@link Articolo} da aggiungere.
     * @throws ListaDiArticoliException Se l'articolo passato è null.
     */
    
    public void AggiungiArticolo(Articolo articolo) throws ListaDiArticoliException
    {
        if (articolo == null)
        {
            throw new ListaDiArticoliException("Errore, e' necessario il nome dell'articolo");     
        }
        this.articoli.add(articolo);
    }
    
    /**
     * Rimuove un articolo specificato dalla lista.
     * @param articolo L'articolo da rimuovere.
     */

    public void RimuoviArticolo(Articolo articolo)
    {
        this.articoli.remove(articolo);
    }
    
    /**
     * Restituisce il numero totale di articoli presenti nella lista.
     * @return Un intero che rappresenta la dimensione della lista.
     */

    public int getNumeroArticoli()
    {
        return this.articoli.size();
    }

    /**
     * Restituisce una rappresentazione testuale della lista.
     * @return Una stringa formattata contenente nome (della lista) e il totale degli articoli.
     */
    
    @Override
    public String toString()
    {
        return "Lista: " + nome + "\n Totale articoli: " + articoli.size();
    }

} 
