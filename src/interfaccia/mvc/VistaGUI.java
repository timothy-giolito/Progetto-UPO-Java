package interfaccia.mvc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;
import java.util.List;

import modello.Articolo;
import modello.GestioneListe; 

/**
 * Classe che gestisce i componenti grafici della GUI rispettando le specifiche di Java Swing.
 * <br>
 * E' stata utilizzata un'impostazione MVC, in modo da tenere divisi:
 * <ul>
 * <li>Dati (Model)</li>
 * <li>Interfaccia (View)</li>
 * <li>Controllo (Controller)</li>
 * </ul>
 * 
 * </ul>
 * @author Timothy Giolito 20054431
 * @author Luca Franzon 20054744
 * */

public class VistaGUI extends JFrame {
    
    private static final long serialVersionUID = 1L;

    // Componenti Dati
    private DefaultListModel<String> listModelListe;
    private JList<String> jListListe;
    private DefaultTableModel tableModelArticoli;
    private JTable tabellaArticoli;
    private JLabel lblTotale;
    private JLabel lblTitoloVista;
    
    // Bottoni
    private JButton btnVediCatalogo;
    private JButton btnNuovaLista;
    private JButton btnEliminaLista;
    private JButton btnNuovaCategoria;
    private JButton btnEliminaCategoria; 
    private JButton btnVediCestino;
    private JButton btnIndietro;
    private JButton btnAggiungi;
    private JButton btnAggiungiCatalogo;
    private JButton btnCerca;
    private JButton btnRimuovi;
    private JButton btnRipristina;
    private JButton btnSvuotaCestino;
    private JButton btnModifica;

    public VistaGUI() {
        super("Gestore Liste della Spesa - UPO Java (MVC)");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inizializzaComponenti();
    }

    private void inizializzaComponenti() {
        
        // --- PANNELLO SINISTRO (Navigazione Liste) ---
        JPanel pnlSinistra = new JPanel(new BorderLayout(5, 5));
        pnlSinistra.setBorder(BorderFactory.createTitledBorder("Navigazione"));
        pnlSinistra.setPreferredSize(new Dimension(220, 0));

        btnVediCatalogo = new JButton("VEDI CATALOGO GLOBALE");
        btnVediCatalogo.setFont(new Font("Arial", Font.BOLD, 12));
        btnVediCatalogo.setBackground(new Color(230, 240, 255));
        btnVediCatalogo.setActionCommand("VEDI_CATALOGO");
        pnlSinistra.add(btnVediCatalogo, BorderLayout.NORTH);

        listModelListe = new DefaultListModel<>();
        jListListe = new JList<>(listModelListe);
        jListListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pnlSinistra.add(new JScrollPane(jListListe), BorderLayout.CENTER);

        // MODIFICA: Grid layout a 4 righe
        JPanel pnlBottoniListe = new JPanel(new GridLayout(4, 1, 5, 5));
        
        btnNuovaLista = new JButton("Nuova Lista Spesa");
        btnNuovaLista.setActionCommand("NUOVA_LISTA");
        
        btnEliminaLista = new JButton("Elimina Lista");
        btnEliminaLista.setActionCommand("ELIMINA_LISTA");

        btnNuovaCategoria = new JButton("Nuova Categoria");
        btnNuovaCategoria.setActionCommand("NUOVA_CATEGORIA");
        
        // NUOVO BOTTONE
        btnEliminaCategoria = new JButton("Elimina Categoria");
        btnEliminaCategoria.setActionCommand("ELIMINA_CATEGORIA");

        pnlBottoniListe.add(btnNuovaLista);
        pnlBottoniListe.add(btnEliminaLista);
        pnlBottoniListe.add(btnNuovaCategoria);
        pnlBottoniListe.add(btnEliminaCategoria);
        
        pnlSinistra.add(pnlBottoniListe, BorderLayout.SOUTH);

        add(pnlSinistra, BorderLayout.WEST);

        // --- PANNELLO CENTRALE ---
        JPanel pnlCentro = new JPanel(new BorderLayout());
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel pnlTitoloNav = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnIndietro = new JButton("Indietro");
        btnIndietro.setFont(new Font("Arial", Font.BOLD, 12));
        btnIndietro.setVisible(false);
        btnIndietro.setActionCommand("INDIETRO");
        
        lblTitoloVista = new JLabel("Seleziona una lista o il catalogo");
        lblTitoloVista.setFont(new Font("Arial", Font.BOLD, 18));
        
        pnlTitoloNav.add(btnIndietro);
        pnlTitoloNav.add(lblTitoloVista);
        pnlHeader.add(pnlTitoloNav, BorderLayout.WEST);
        
        btnVediCestino = new JButton("Vedi Cestino");
        btnVediCestino.setActionCommand("VEDI_CESTINO");
        pnlHeader.add(btnVediCestino, BorderLayout.EAST);
        
        pnlCentro.add(pnlHeader, BorderLayout.NORTH);

        String[] colonne = {"Nome", "Categoria", "Prezzo (€)", "Nota"};
        tableModelArticoli = new DefaultTableModel(colonne, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabellaArticoli = new JTable(tableModelArticoli);
        tabellaArticoli.setRowHeight(25);
        pnlCentro.add(new JScrollPane(tabellaArticoli), BorderLayout.CENTER);

        add(pnlCentro, BorderLayout.CENTER);

        // --- PANNELLO INFERIORE ---
        JPanel pnlAzioni = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnlAzioni.setBorder(BorderFactory.createEtchedBorder());

        btnAggiungi = new JButton("Aggiungi Nuovo");
        btnAggiungi.setActionCommand("AGGIUNGI");
        
        btnAggiungiCatalogo = new JButton("Copia da Catalogo");
        btnAggiungiCatalogo.setActionCommand("AGGIUNGI_DA_CATALOGO");
        
        btnCerca = new JButton("Cerca");
        btnCerca.setActionCommand("CERCA");
        
        btnModifica = new JButton("Modifica");
        btnModifica.setActionCommand("MODIFICA");
        
        btnRimuovi = new JButton("Rimuovi");
        btnRimuovi.setActionCommand("RIMUOVI");
        
        btnRipristina = new JButton("Ripristina");
        btnRipristina.setActionCommand("RIPRISTINA");
        
        btnSvuotaCestino = new JButton("Svuota Cestino");
        btnSvuotaCestino.setActionCommand("SVUOTA_CESTINO");
        
        lblTotale = new JLabel("Totale: 0.00 €");
        lblTotale.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotale.setForeground(Color.BLUE);

        pnlAzioni.add(btnAggiungi);
        pnlAzioni.add(btnAggiungiCatalogo);
        pnlAzioni.add(btnModifica);
        pnlAzioni.add(btnCerca);
        pnlAzioni.add(Box.createHorizontalStrut(20));
        pnlAzioni.add(btnRimuovi);
        pnlAzioni.add(btnRipristina);
        pnlAzioni.add(btnSvuotaCestino);
        pnlAzioni.add(Box.createHorizontalStrut(20));
        pnlAzioni.add(lblTotale);

        add(pnlAzioni, BorderLayout.SOUTH);
    }

    // --- METODI PER IL CONTROLLER (Registrazione Listener) ---
    
    public void registraAscoltatori(ActionListener controller, ListSelectionListener listListener) {
        btnVediCatalogo.addActionListener(controller);
        btnNuovaLista.addActionListener(controller);
        btnEliminaLista.addActionListener(controller);
        btnNuovaCategoria.addActionListener(controller);
        btnEliminaCategoria.addActionListener(controller);
        btnVediCestino.addActionListener(controller);
        btnIndietro.addActionListener(controller);
        btnAggiungi.addActionListener(controller);
        btnAggiungiCatalogo.addActionListener(controller);
        btnModifica.addActionListener(controller);
        btnCerca.addActionListener(controller);
        btnRimuovi.addActionListener(controller);
        btnRipristina.addActionListener(controller);
        btnSvuotaCestino.addActionListener(controller);
        
        jListListe.addListSelectionListener(listListener);
    }

    // --- METODI DI AGGIORNAMENTO VISTA ---

    public void aggiornaElencoListe(java.util.Set<String> nomiListe) {
        listModelListe.clear();
        for (String nome : nomiListe) {
            listModelListe.addElement(nome);
        }
    }

    public void aggiornaTabella(List<Articolo> articoli) {
        tableModelArticoli.setRowCount(0);
        for (Articolo a : articoli) {
            Object[] riga = {
                a.getNome(),
                a.getCategoria(), 
                String.format("%.2f", a.getPrezzo()),
                a.getNota()
            };
            tableModelArticoli.addRow(riga);
        }
    }

    public void setTotale(double totale) {
        lblTotale.setText("Totale: " + String.format("%.2f", totale) + " €");
        lblTotale.setVisible(true);
    }
    
    public void nascondiTotale() {
        lblTotale.setVisible(false);
    }

    public void setTitolo(String titolo, Color colore) {
        lblTitoloVista.setText(titolo);
        lblTitoloVista.setForeground(colore);
    }

    public void clearSelezioneLista() {
        jListListe.clearSelection();
    }
    
    public String getListaSelezionata() {
        return jListListe.getSelectedValue();
    }
    
    public int getRigaSelezionata() {
        return tabellaArticoli.getSelectedRow();
    }

    // --- METODI DI INPUT/OUTPUT (Dialoghi) ---

    public String chiediInput(String messaggio) {
        return JOptionPane.showInputDialog(this, messaggio);
    }

    public int chiediConferma(String messaggio, String titolo) {
        return JOptionPane.showConfirmDialog(this, messaggio, titolo, JOptionPane.YES_NO_OPTION);
    }

    public void mostraMessaggio(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio);
    }
    
    public void mostraMessaggioErrore(String errore) {
        JOptionPane.showMessageDialog(this, errore, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    public Object[] chiediDatiArticolo(boolean isGlobale) {
        JTextField txtNome = new JTextField();
        JComboBox<String> cmbCategoria = new JComboBox<>(GestioneListe.getCategorie().toArray(new String[0]));
        cmbCategoria.setEditable(true);
        JTextField txtPrezzo = new JTextField();
        JTextField txtNota = new JTextField();
       
        String titolo = isGlobale ? "Nuovo Prodotto Globale" : "Nuovo Articolo in Lista";
        Object[] msg = {
            "Nome:", txtNome, 
            "Categoria:", cmbCategoria, 
            "Prezzo (€):", txtPrezzo, 
            "Nota:", txtNota
        };

        int res = JOptionPane.showConfirmDialog(this, msg, titolo, JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            return new Object[] { 
                txtNome.getText(), 
                txtPrezzo.getText(), 
                txtNota.getText(), 
                cmbCategoria.getSelectedItem() 
            };
        }
        return null;
    }
    
    public Articolo chiediSelezioneDaCatalogo(Object[] opzioni) {
         return (Articolo) JOptionPane.showInputDialog(this, 
            "Scegli dal catalogo:", "Catalogo", JOptionPane.PLAIN_MESSAGE, null, opzioni, opzioni[0]);
    }

    // --- GESTIONE STATO BOTTONI ---
    
    public void configBottoniPerCatalogo() {
        btnVediCestino.setVisible(false);
        btnIndietro.setVisible(false);
        btnAggiungi.setText("Crea Nuovo Articolo Globale");
        btnAggiungi.setEnabled(true);
        btnAggiungi.setVisible(true);
        btnAggiungiCatalogo.setVisible(false);
        btnCerca.setEnabled(false);
        btnRimuovi.setText("Elimina da Catalogo");
        btnRimuovi.setEnabled(true);
        btnRimuovi.setVisible(true);
        btnRipristina.setEnabled(false);
        btnSvuotaCestino.setEnabled(false);
        btnModifica.setEnabled(true);
        btnModifica.setVisible(true);
        btnModifica.setEnabled(true);
        btnModifica.setVisible(true);
    }
    
    public void configBottoniPerLista(boolean vistaCestino) {
        if (vistaCestino) {
            btnIndietro.setVisible(true);
            btnVediCestino.setVisible(false);
            btnAggiungi.setEnabled(false);
            btnAggiungiCatalogo.setEnabled(false);
            btnRimuovi.setEnabled(false);
            btnRipristina.setEnabled(true);
            btnSvuotaCestino.setEnabled(true);
            btnModifica.setEnabled(false);
        } else {
        	btnModifica.setEnabled(true);
        	btnModifica.setVisible(true);
            btnIndietro.setVisible(false);
            btnVediCestino.setVisible(true);
            btnAggiungi.setText("Aggiungi Articoli");
            btnAggiungi.setEnabled(true);
            btnAggiungiCatalogo.setText("Aggiungi da Catalogo");
            btnAggiungiCatalogo.setVisible(true);
            btnAggiungiCatalogo.setEnabled(true);
            btnRimuovi.setText("Sposta nel Cestino");
            btnRimuovi.setEnabled(true);
            btnRipristina.setEnabled(false);
            btnSvuotaCestino.setEnabled(false);
        }
        btnCerca.setEnabled(true);
    }
    
    public void configBottoniVuoto() {
        btnVediCestino.setVisible(false);
        btnIndietro.setVisible(false);
        btnAggiungi.setEnabled(false);
        btnAggiungiCatalogo.setEnabled(false);
        btnCerca.setEnabled(false);
        btnRimuovi.setEnabled(false);
        btnRipristina.setEnabled(false);
        btnSvuotaCestino.setEnabled(false);
        btnModifica.setEnabled(false);
    }
    
    /**
     * Mostra un dialog con i dati attuali dell'articolo per permetterne la modifica.
     */
    public Object[] chiediModificaArticolo(Articolo a) {
        JTextField txtNome = new JTextField(a.getNome());
        txtNome.setEditable(false); // Il nome non si cambia solitamente per coerenza
        
        JComboBox<String> cmbCategoria = new JComboBox<>(GestioneListe.getCategorie().toArray(new String[0]));
        cmbCategoria.setSelectedItem(a.getCategoria());
        
        JTextField txtPrezzo = new JTextField(String.valueOf(a.getPrezzo()));
        JTextField txtNota = new JTextField(a.getNota());
       
        Object[] msg = {
            "Nome (non modificabile):", txtNome, 
            "Categoria:", cmbCategoria, 
            "Prezzo (€):", txtPrezzo, 
            "Nota:", txtNota
        };

        int res = JOptionPane.showConfirmDialog(this, msg, "Modifica Articolo", JOptionPane.OK_CANCEL_OPTION);
        
        if (res == JOptionPane.OK_OPTION) {
            return new Object[] { 
                cmbCategoria.getSelectedItem(), // Indice 0: Categoria
                txtPrezzo.getText(),            // Indice 1: Prezzo
                txtNota.getText()               // Indice 2: Nota
            };
        }
        return null;
    }
}