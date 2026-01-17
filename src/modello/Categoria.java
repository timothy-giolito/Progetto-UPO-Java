package modello;

/**
 * Enumerazione che definisce le diverse categorie merceologiche (reparti o corsie) disponibili nel negozio.
 * <p>
 * Questa enum viene utilizzata per classificare gli articoli e facilitarne l'organizzazione nelle liste.
 * Ogni costante è associata a una descrizione testuale leggibile (es. "Reparto Ortofrutticolo")
 * utile per la visualizzazione nell'interfaccia utente.
 * </p>
 * @author Timothy Giolito 20054431
 * @author Luca Franzon 20054744
 */

public enum Categoria {

	// valore di default
		ALTRO("Nessuna Corsia"),
		
		// reparti supermercato
		ORTOFRUTTA("Reparto Ortofrutticolo"),
		PANETTERIA("Reparto Panetteria"),
		GASTRONOMIA("Reparto Gastronomia"),
		MACELLERIA("Reparto Macelleria"),
		PESCHERIA("Reparto Pescheria"),
		CAFFE_INFUSI("Corsia Caffe' e Infusi"),
		SALSE_SUGHI("Corsia Salse e Sughi"),
		PASTA_BISCOTTI_BRIOCHE("Corsia Pasta, Biscotti, Brioche"),
		SNACK("Reparto Snack"),
		GIOCATTOLI("Corsia Giocattoli"),
		IGIENE_PERSONA("Corsia Igiene e Persona"),
		PRODOTTI_CASA("Corsia prodotti per la casa"),
		PRODOTTI_AUTO("Corsia prodotti auto"),
		BEVANDE("Corsia Bevande"),
		PRODOTTI_ANIMALI("Corsia Prodotti Animali"),
		SURGELATI("Corsia Surgelati"),
		SPORTIVO("Reparto Sport"),
		
		// reparti bricolage
		FAI_DA_TE("Reparto Fai Da Te"),
		ELETTRONICA("Reparto Elettronica"),
		GIARDINAGGIO("Reparto Giardinaggio"),
		LEGNO("Reparto Legno"),
		UTENSILI("Reparto Utensili"),
		RIPARAZIONI("Reparto Riparazioni"),
		VERNICI("Reparto Vernici"),
		PIASTRELLE("Reparto Piastrelle"),
		PISCINA("Reparto Piscina"),
		FERRAMENTA("Reparto Ferramenta"),
		IDRAULICA("Reparto Idraulica"),
		ELETTRICITA("Reparto Elettricita'"),
		SPECCHI("Reparto Specchi"),
		
		// reparti negozi casa
		CUCINA("Reparto Cucina"),
		BAGNO("Reparto Bagno"),
		SALOTTO("Reparto Salotto"),
		CAMERA_DA_LETTO("Reparto Camera Da Letto"),
		TESSILE("Reparto Tessile"),
		
		// reparti negozi di elettronica
		ASPIRAPOLVERI("Reparto Aspirapolveri"),
		LAVATRICI("Reparto Lavatrici"),
		FRIGORIFERI("Reparto Frigoriferi"),
		LAVASTOVIGLIE("Reparto Lavastoviglie"), 
		TELEFONIA("Reparto Telefonia"), 
		VIDEOGIOCHI("Reparto Videogiochi"),
		TELEVISORI("Reparto Televisori"),
		AUDIO("Reparto Audio"), 
		FILM_DVD("Reparto Film e DVD"), 
		INFORMATICA("Reparto Informatica"),
		ACCESSORI_GENERICI("Reparto Accessori Generici");
			
		private final String descrizione;
		
		Categoria(String descrizione) {
			
			this.descrizione = descrizione;
		}
		
		public String getDescrizione() {
			
			return descrizione;
		}
}
