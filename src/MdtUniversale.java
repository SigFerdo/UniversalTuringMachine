import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MdtUniversale /* extends HashMap<Integer,String> */ {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5171164049513756757L;
	public HashMap<Integer, String> file;
	private List<HashMap<String, String>> nastro1;
	private String statoAttuale;
	private int testa;
	private String stringaInserita;
	private int maxComputazioni;
	private LinkedList<String> nastroStringa;
	private BinarieTransizioni bt;

	public MdtUniversale() {
		this.file = (new FakeFile()).getFile();
		bt = new BinarieTransizioni();

		this.nastro1 = bt.convertiTransizioniInBinario(bt.leggi_transizioni(file));
		if (this.nastro1.size() == 0) {
			System.out.println("Non è presente alcuna descrizione nel file");
		}
		this.statoAttuale = this.nastro1.get(0).get("stato_partenza");
		this.testa = 0;
		this.maxComputazioni = 100000;
		this.stringaInserita = " ";
	}

	public HashMap<String, String> calcoloConfigurazioneSuccessiva(String stato, String simbolo) {
		int i = 0;
		boolean trovato = false;
		HashMap<String, String> configurazione = new HashMap<String, String>();
		while ((i < nastro1.size()) && (trovato == false)) {
			if(this.nastro1.get(i).get("simbolo_letto").equals(this.nastroStringa.get(this.testa)) && this.statoAttuale.equals(this.nastro1.get(i).get("stato_partenza"))) {
				configurazione.put("simbolo", this.nastro1.get(i).get("simbolo_scritto"));
				configurazione.put("stato", this.nastro1.get(i).get("stato_arrivo"));
				configurazione.put("movimento", this.nastro1.get(i).get("movimento"));
				
				trovato = true;
			}
			i = i+1;
		}
		return configurazione;
	}

	public void computaStringa(String input) throws Exception {
		if (this.testa == 0 || this.statoAttuale.equals(this.nastro1.get(0).get("stato_partenza"))) {
			this.reset();
		}

		this.stringaInserita = input;
		this.nastroStringa = bt.stringaALista(bt.convertiStringaInBinario(input));
		int comp = this.computa();
		this.stampaRisultati(comp);
	}
	
	private int computa() throws Exception {
		boolean stop = false;
		int comp = 0;
		
		while(!stop) {
			if(this.statoAttuale.equals(bt.getAccetta())) {
				stop = true;
			}
			
			HashMap<String, String> configurazione = this.calcoloConfigurazioneSuccessiva(this.statoAttuale, this.nastroStringa.get(this.testa));
			
			if(configurazione.size() == 0) {
				stop = true;
			} else {
				this.statoAttuale = configurazione.get("stato");
				this.nastroStringa.set(this.testa, configurazione.get("simbolo"));

				// calcolo il movimento da fare sul nastro della stringa
				String movimentoLetto = configurazione.get("movimento");
				
				if(movimentoLetto.equals(bt.getSinistra())) {
					if(this.testa == 0) {
						this.nastroStringa.add(0, bt.getVuoto());
					} else {
						this.testa--;
					}
				} else if(movimentoLetto.equals(bt.getDestra())) {
					if(this.testa + 1 == this.nastroStringa.size()) {
						this.nastroStringa.add(bt.getVuoto());
						this.testa++;
					} else {
						this.testa++;
					}
				} else {
					throw new Exception("MdtUniversale.computa(): Qualcosa è andato storto");
				}
			}
			
			comp++;
			
			if(comp == this.maxComputazioni) {
				stop = true;
			}
		}
		return comp;
	}
	
	private void reset() {
		this.stringaInserita = " ";
		this.testa = 0;
		this.nastroStringa = new LinkedList<String>();
		this.statoAttuale = this.nastro1.get(0).get("stato_partenza");
	}

	public void stampaRisultati(int nComputazioni) {
		System.out.println("Macchina di Turing in input:");
		bt.stampaListaTransizioni(bt.leggi_transizioni(this.file));

		System.out.println("Macchina di Turing in binario:");
		bt.stampaListaTransizioni(this.nastro1);

		System.out.println("Stringa in input: " + this.stringaInserita);
		System.out.println(
				"Stringa in binario computata dalla macchina: " + bt.convertiStringaInBinario(stringaInserita));

		// stato_stampato = self.stato_attuale
		this.stampaNastro();

		if (nComputazioni == this.maxComputazioni)
			System.out.println("La macchina e' andata in loop");
		else
			System.out.println("Numero di computazioni: " + nComputazioni);

		if (this.statoAttuale.equals(bt.getAccetta()))
			System.out.println("La stringa e' stata accettata");
		else
			System.out.println("La stringa e' stata rifiutata");
	}

	public void stampaNastro() {
		LinkedList<String> nastroStampato = new LinkedList<String>();
		for(int i = 0; i< nastroStringa.size(); i++) {
			if(!nastroStringa.get(i).equals(bt.getVuoto())) {
				nastroStampato.add(nastroStringa.get(i));
			}
		}
		
		String stringaStampata = "";
		for(String s : nastroStampato) {
			String sASCII = bt.convertiBinarioInStringa(s);
			stringaStampata += sASCII;
			stringaStampata += " ";
		}
		System.out.println(stringaStampata);
	}
	
	public String toString() {
		return file.get(1) + "\n" + file.get(2) + "\n" + file.get(3) + "\n" + file.get(4) + "\n" + file.get(5) + "\n"
				+ file.get(6) + "\n" + file.get(7);
	}

}
