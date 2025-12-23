package modello;

public enum Reparto {

	// valore di default
	NESSUNO("Nessuna Corsia"),
	
	// reparti supermercato
	ORTOFRUTTA("Reparto Ortofrutticolo"),
	PANETTERIA("Reparto Panetteria"),
	GASTRONOMIA("Reparto Gastronomia"),
	MACELLERIA("Reparto Macelleria"),
	PESCHERIA("Reparto Pescheria"),
	REPARTO_CAFFE_INFUSI("Corsia Caffe' e Infusi"),
	REPARTO_SALSE_SUGHI("Corsia Salse e Sughi"),
	REPARTO_PASTA_BISCOTTI_BRIOCHE("Corsia Pasta, Biscotti, Brioche"),
	REPARTO_GIOCATTOLI("Corsia Giocattoli"),
	REPARTO_IGIENE_PERSONA("Corsia Igiene e Persona"),
	REPARTO_PRODOTTI_CASA("Corsia prodotti per la casa"),
	REPARTO_PRODOTTI_AUTO("Corsia prodotti auto"),
	REPARTO_BEVANDE("Corsia Bevande"),
	REPARTO_PRODOTTI_ANIMALI("Corsia Prodotti Animali"),
	REPARTO_SURGELATI("Corsia Surgelati"),
	REPARTO_SPORTIVO("Reparto Sport"),
	
	// reparti bricolage
	REPARTO_FAI_DA_TE("Reparto Fai Da Te"),
	REPARTO_ELETTRONICA("Reparto Elettronica"),
	REPARTO_GIARDINAGGIO("Reparto Giardinaggio"),
	REPARTO_LEGNO("Reparto Legno"),
	REPARTO_UTENSILI("Reparto Utensili"),
	REPARTO_RIPARAZIONI("Reparto Riparazioni"),
	REPARTO_VERNICI("Reparto Vernici"),
	REPARTO_PIASTRELLE("Reparto Piastrelle"),
	REPARTO_PISCINA("Reparto Piscina"),
	REPARTO_FERRAMENTA("Reparto Ferramenta"),
	REPARTO_IDRAULICA("Reparto Idraulica"),
	REPARTO_ELETTRICITA("Reparto Elettricita'"),
	
	// reparti negozi casa
	REPARTO_CUCINA("Reparto Cucina"),
	REPARTO_BAGNO("Reparto Bagno"),
	REPARTO_SALOTTO("Reparto Salotto"),
	REPARTO_CAMERA_DA_LETTO("Reparto Camera Da Letto"),
	REPARTO_TESSILE("Reparto Tessile");
	
	private final String descrizione;
	
	Reparto(String descrizione) {
		
		this.descrizione = descrizione;
	}
	
	public String getDescrizione() {
		
		return descrizione;
	}
	
}
