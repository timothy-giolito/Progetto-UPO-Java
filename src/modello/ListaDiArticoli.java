package modello;

import modello.eccezioni.ListaDiArticoliException;
import java.util.ArrayList;
import java.util.List;

public class ListaDiArticoli {
    private String nome;
    private List<Articolo> articoli;

    //costruttore
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

    public String getListaNome()
    {
        return nome;
    }

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

    public void AggiungiArticolo(Articolo articolo) throws ListaDiArticoliException
    {
        if (articolo == null)
        {
            throw new ListaDiArticoliException("Errore, e' necessario il nome dell'articolo");     
        }
        this.articoli.add(articolo);
    }

    public void RimuoviArticolo(Articolo articolo)
    {
        this.articoli.remove(articolo);
    }

    public int getNumeroArticoli()
    {
        return this.articoli.size();
    }

    @Override
    public String toString()
    {
        return "Lista: " + nome + "\n Totale articoli: " + articoli.size();
    }

} 
