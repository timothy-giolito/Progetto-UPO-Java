package interfaccia.mvc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;
import java.util.List;
import modello.Articolo;
import modello.Reparto;

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
    private JButton btnVediCestino;
    private JButton btnIndietro;
    private JButton btnAggiungi;
    private JButton btnAggiungiCatalogo;
    private JButton btnCerca;
    private JButton btnRimuovi;
    private JButton btnRipristina;
    private JButton btnSvuotaCestino;

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

        JPanel pnlBottoniListe = new JPanel(new GridLayout(2, 1, 5, 5));
        btnNuovaLista = new JButton("Nuova Lista Spesa");
        btnNuovaLista.setActionCommand("NUOVA_LISTA");
        
        btnEliminaLista = new JButton("Elimina Lista");
        btnEliminaLista.setActionCommand("ELIMINA_LISTA");

        pnlBottoniListe.add(btnNuovaLista);
        pnlBottoniListe.add(btnEliminaLista);
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

        String[] colonne = {"Nome", "Categoria", "Prezzo (€)", "Reparto", "Nota"};
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
        btnVediCestino.addActionListener(controller);
        btnIndietro.addActionListener(controller);
        btnAggiungi.addActionListener(controller);
        btnAggiungiCatalogo.addActionListener(controller);
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
                a.getReparto().getDescrizione(),
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

    /**
     * Mostra il dialogo complesso per creare un nuovo articolo.
     * Ritorna un array di oggetti con i valori inseriti, o null se annullato.
     */
    public Object[] chiediDatiArticolo(boolean isGlobale) {
        JTextField txtNome = new JTextField();
        JTextField txtCat = new JTextField();
        JTextField txtPrezzo = new JTextField();
        JTextField txtNota = new JTextField();
        JComboBox<Reparto> cmbReparto = new JComboBox<>(Reparto.values());

        String titolo = isGlobale ? "Nuovo Prodotto Globale" : "Nuovo Articolo in Lista";
        Object[] msg = {
            "Nome:", txtNome, "Categoria:", txtCat, 
            "Prezzo (€):", txtPrezzo, "Nota:", txtNota, "Reparto:", cmbReparto
        };

        int res = JOptionPane.showConfirmDialog(this, msg, titolo, JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            return new Object[] { 
                txtNome.getText(), txtCat.getText(), txtPrezzo.getText(), 
                txtNota.getText(), cmbReparto.getSelectedItem() 
            };
        }
        return null;
    }
    
    public Articolo chiediSelezioneDaCatalogo(Object[] opzioni) {
         return (Articolo) JOptionPane.showInputDialog(this, 
            "Scegli dal catalogo:", "Catalogo", JOptionPane.PLAIN_MESSAGE, null, opzioni, opzioni[0]);
    }

    // --- GESTIONE STATO BOTTONI (Visibilità/Abilitazione) ---
    
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
        } else {
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
    }
}