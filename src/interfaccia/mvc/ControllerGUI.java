package interfaccia.mvc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

import modello.*;
import modello.eccezioni.GestioneListeException;

public class ControllerGUI implements ActionListener, ListSelectionListener {

    private VistaGUI vista;
    
    // Stato dell'applicazione
    private ListaDiArticoli listaCorrente = null;
    private boolean modalitaCatalogo = false;
    private boolean visualizzaCestino = false;

    public ControllerGUI(VistaGUI vista) {
    	
        this.vista = vista;
        
        // Inizializza la vista
        vista.registraAscoltatori(this, this);
        vista.aggiornaElencoListe(GestioneListe.getListeArticoli().keySet());
        impostaStatoVuoto();
        vista.setVisible(true);
    }

    // Gestione Eventi Bottoni (ActionListener)
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        try {
            switch (comando) {
                case "VEDI_CATALOGO":
                    attivaModalitaCatalogo();
                    break;
                case "NUOVA_LISTA":
                    azioneCreaLista();
                    break;
                case "ELIMINA_LISTA":
                    azioneEliminaLista();
                    break;
                case "VEDI_CESTINO":
                    visualizzaCestino = true;
                    aggiornaVista();
                    break;
                case "INDIETRO":
                    visualizzaCestino = false;
                    aggiornaVista();
                    break;
                case "AGGIUNGI":
                    azioneAggiungi();
                    break;
                case "AGGIUNGI_DA_CATALOGO":
                    azioneCopiaDaCatalogo();
                    break;
                case "CERCA":
                    azioneCerca();
                    break;
                case "RIMUOVI":
                    azioneRimuovi();
                    break;
                case "RIPRISTINA":
                    azioneRipristina();
                    break;
                case "SVUOTA_CESTINO":
                    azioneSvuotaCestino();
                    break;
            }
        } catch (Exception ex) {
            vista.mostraMessaggioErrore(ex.getMessage());
        }
    }

    // Gestione Eventi Selezione Lista (ListSelectionListener)
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String nomeLista = vista.getListaSelezionata();
            if (nomeLista != null) {
                caricaLista(nomeLista);
            }
        }
    }

    // --- LOGICA DI NAVIGAZIONE E AGGIORNAMENTO ---

    private void impostaStatoVuoto() {
        listaCorrente = null;
        modalitaCatalogo = false;
        visualizzaCestino = false;
        
        vista.setTitolo("Benvenuto! Seleziona una lista", Color.BLACK);
        vista.aggiornaTabella(List.of()); 
        vista.configBottoniVuoto();
        vista.nascondiTotale();
    }
    
    private void attivaModalitaCatalogo() {
        modalitaCatalogo = true;
        listaCorrente = null;
        visualizzaCestino = false;
        
        vista.clearSelezioneLista();
        vista.setTitolo("VISUALIZZAZIONE CATALOGO GLOBALE", new Color(0, 100, 0));
        vista.configBottoniPerCatalogo();
        vista.aggiornaTabella(GestioneListe.getArticoli());
        vista.nascondiTotale();
    }
    
    private void caricaLista(String nomeLista) {
        modalitaCatalogo = false;
        listaCorrente = GestioneListe.getListeArticoli().get(nomeLista);
        visualizzaCestino = false; // Reset al default
        aggiornaVista();
    }
    
    private void aggiornaVista() {
        if (modalitaCatalogo) {
            attivaModalitaCatalogo();
            return;
        }
        
        if (listaCorrente == null) {
            impostaStatoVuoto();
            return;
        }

        vista.configBottoniPerLista(visualizzaCestino);
        
        if (visualizzaCestino) {
            vista.setTitolo("Cestino di: " + listaCorrente.getListaNome(), Color.GRAY);
            vista.aggiornaTabella(listaCorrente.getCancellati());
            vista.setTotale(listaCorrente.getPrezzoTotaleCestino());
        } else {
            vista.setTitolo("Lista: " + listaCorrente.getListaNome(), Color.BLACK);
            vista.aggiornaTabella(listaCorrente.getArticoli());
            vista.setTotale(listaCorrente.getPrezzoTotale());
        }
    }

    // --- LOGICA DELLE AZIONI ---

    private void azioneCreaLista() throws GestioneListeException {
        String nome = vista.chiediInput("Nome lista:");
        if (nome != null && !nome.trim().isEmpty()) {
            GestioneListe.creaLista(nome);
            vista.aggiornaElencoListe(GestioneListe.getListeArticoli().keySet());
        }
    }

    private void azioneEliminaLista() throws GestioneListeException {
        String nome = vista.getListaSelezionata();
        if (nome != null) {
            if (vista.chiediConferma("Eliminare " + nome + "?", "Conferma") == JOptionPane.YES_OPTION) {
                GestioneListe.cancellaLista(nome);
                vista.aggiornaElencoListe(GestioneListe.getListeArticoli().keySet());
                impostaStatoVuoto();
            }
        }
    }

    private void azioneAggiungi() throws Exception {
        Object[] dati = vista.chiediDatiArticolo(modalitaCatalogo);
        if (dati == null) return;
        
        String nome = (String) dati[0];
        String cat = (String) dati[1];
        double prezzo = 0.0;
        try {
            prezzo = Double.parseDouble((String) dati[2]);
        } catch (NumberFormatException e) { /* default 0 */ }
        String nota = (String) dati[3];
        Reparto reparto = (Reparto) dati[4];

        Articolo nuovo = new Articolo(nome, cat, prezzo, nota, reparto);

        if (modalitaCatalogo) {
            GestioneListe.inserisciArticolo(nuovo);
            vista.mostraMessaggio("Aggiunto al Catalogo!");
        } else if (listaCorrente != null) {
            listaCorrente.AggiungiArticolo(nuovo);
        }
        aggiornaVista();
    }
    
    private void azioneRimuovi() throws GestioneListeException {
        int riga = vista.getRigaSelezionata();
        if (riga == -1) {
            vista.mostraMessaggio("Seleziona un articolo.");
            return;
        }

        if (modalitaCatalogo) {
            Articolo a = GestioneListe.getArticoli().get(riga);
            if (vista.chiediConferma("Eliminare definitivamente '" + a.getNome() + "'?", "Elimina") == JOptionPane.YES_OPTION) {
                GestioneListe.cancellaArticolo(a);
            }
        } else if (listaCorrente != null) {
            Articolo a = listaCorrente.getArticoli().get(riga);
            listaCorrente.RimuoviArticolo(a);
        }
        aggiornaVista();
    }

    private void azioneCopiaDaCatalogo() {
        if (listaCorrente == null) return;
        List<Articolo> catalogo = GestioneListe.getArticoli();
        if (catalogo.isEmpty()) {
            vista.mostraMessaggio("Il catalogo è vuoto.");
            return;
        }
        
        Articolo scelto = vista.chiediSelezioneDaCatalogo(catalogo.toArray());
        if (scelto != null) {
            try {
                // Clona l'articolo
                Articolo copia = new Articolo(scelto.getNome(), scelto.getCategoria(), 
                                              scelto.getPrezzo(), scelto.getNota(), scelto.getReparto());
                listaCorrente.AggiungiArticolo(copia);
                aggiornaVista();
            } catch (Exception e) {
                vista.mostraMessaggioErrore(e.getMessage());
            }
        }
    }
    
    private void azioneRipristina() {
        int riga = vista.getRigaSelezionata();
        if (riga != -1 && listaCorrente != null && visualizzaCestino) {
            Articolo a = listaCorrente.getCancellati().get(riga);
            listaCorrente.RipristinaArticolo(a);
            aggiornaVista();
            vista.mostraMessaggio("Articolo ripristinato!");
        }
    }
    
    private void azioneSvuotaCestino() {
        if (listaCorrente != null && vista.chiediConferma("Svuotare il cestino?", "Conferma") == JOptionPane.YES_OPTION) {
            listaCorrente.SvuotaCestino();
            aggiornaVista();
        }
    }
    
    private void azioneCerca() {
        if (listaCorrente == null) {
            vista.mostraMessaggio("Seleziona prima una lista.");
            return;
        }
        String prefisso = vista.chiediInput("Inserisci il prefisso da cercare:");
        if (prefisso != null && !prefisso.trim().isEmpty()) {
            List<Articolo> risultati = listaCorrente.TrovaArticoliPerPrefisso(prefisso);
            if (risultati.isEmpty()) {
                vista.mostraMessaggio("Nessun articolo trovato.");
            } else {
                StringBuilder sb = new StringBuilder("Trovati " + risultati.size() + " articoli:\n");
                for (Articolo a : risultati) sb.append("- ").append(a.getNome()).append("\n");
                vista.mostraMessaggio(sb.toString());
            }
        }
    }
}