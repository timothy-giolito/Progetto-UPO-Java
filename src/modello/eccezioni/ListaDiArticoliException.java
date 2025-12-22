package modello.eccezioni;

public class ListaDiArticoliException extends Exception{

    private static final long serialVersionUID = 1L;

    //costruttore senza parametro
    public ListaDiArticoliException()
    {
        super();
    }

    //costruttore con messaggio di errore
    public ListaDiArticoliException(String messaggio)
    {
        super(messaggio);
    }

    //costruttore con messaggio e causa 
    public ListaDiArticoliException(String messaggio, Throwable causa)
    {
        super(messaggio, causa);
    }

} 
