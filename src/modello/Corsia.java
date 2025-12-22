package modello;

public enum Corsia {

	ORTO_FRUTTA("Reparto Ortofrutticolo"),
	PANETTERIA("Reparto Panetteria"),
	GASTRONOMIA("Reparto Gastronomia"),
	MACELLERIA("Reparto Macelleria"),
	PESCHERIA("Reparto Pescheria"),
	CORSIA_CAFFE_INFUSI("Corsia Caffe' e Infusi"),
	CORSIA_SALSE_SUGHI("Corsia Salse e Sughi"),
	CORSIA_PASTA_BISCOTTI_BRIOCHE("Corsia Pasta, Biscotti, Brioche"),
	CORSIA_GIOCATTOLI("Corsia Giocattoli"),
	CORSIA_IGIENE_PERSONA("Corsia Igiene e Persona"),
	CORSIA_PRODOTTI_CASA("Corsia prodotti per la casa"),
	CORSIA_PRODOTTI_AUTO("Corsia prodotti auto"),
	CORSIA_BEVANDE("Corsia Bevande"),
	CORSIA_PRODOTTI_ANIMALI("Corsia Prodotti Animali"),
	CORSIA_SURGELATI("Corsia Surgelati");
	
	private final String descrizione;
	
	Corsia(String descrizione) {
		
		this.descrizione = descrizione;
	}
	
	public String getDescrizione() {
		
		return descrizione;
	}
	
}
