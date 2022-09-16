import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BinarieTransizioni extends HashMap<Integer,String>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 364594177366352941L;
	private String vuoto="0000";
	private String accetta="$";
	private String errore="$$";
	private String sinistra="110";
	private String destra="101";
	
	public String getVuoto() {
		return vuoto;
	}

	public String getAccetta() {
		return accetta;
	}

	public String getErrore() {
		return errore;
	}

	public String getSinistra() {
		return sinistra;
	}

	public String getDestra() {
		return destra;
	}
	
	public ArrayList<HashMap<String, String>> leggi_transizioni(HashMap<Integer, String> dati) {
		
		//righe = file_transizioni.readlines()======chiave
		ArrayList<HashMap<String, String>> listaTransizioneDizionario =new ArrayList<HashMap<String, String>>();

		for(int i=1;i<=7;i++) {
			String sp=dati.get(i);
			HashMap<String, String> transizione_dizionario= new HashMap<String, String>(); //{"stato_partenza","simbolo_letto","simbolo_scritto","movimento","stato_arrivo"};
			String[] listaSimboli =null;
			listaSimboli=sp.split("-");

			transizione_dizionario.put("stato_partenza", listaSimboli[0]);
			transizione_dizionario.put("simbolo_letto", listaSimboli[1]);
			transizione_dizionario.put("simbolo_scritto", listaSimboli[2]);
			transizione_dizionario.put("movimento", listaSimboli[3]);

			listaSimboli[4]=listaSimboli[4].replace("\n","");

			transizione_dizionario.put("stato_arrivo", listaSimboli[4]);
			
			listaTransizioneDizionario.add(transizione_dizionario);
		}
	return listaTransizioneDizionario;
	}
	
	public String convertiStringaInBinario(String str) {
		String bin = "";
		
		if(str.equals("empty")) {
			bin += vuoto;
		}
		else {
			char[] chars = str.toCharArray();
			boolean first = true;
			for(char c: chars) {
				if(first) {
					bin +=Integer.toBinaryString(c);
					first = !first;
				}
				else
					bin += " " + Integer.toBinaryString(c);
			}
		}
		return bin;
	}
	
	public String convertiBinarioInStringa(String bin) {
		String str = "";
		
		String[] split = bin.split(" ");
		
		for(String s :split) {
			int n = Integer.parseInt(s,2);
			char c = (char)n;
			str += c;
		}
		return str;
	}
	
	public int numeroStato(String stato) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(stato);
        int n = 0;
        if(m.find()) {
        	try {
            n = Integer.parseInt(m.group(0));
        	} catch (Exception ex) {
        		System.out.println(ex.getLocalizedMessage());
        		ex.printStackTrace();
        	}
        }
        return n;
	}
	
	public String convertiStatoInBinario(String stato) {
		String bin = "";
		if(stato.equals("qaccept")) {
			bin = accetta;
		} else {
			int num_letto = numeroStato(stato);
			
			for(int i = 0; i <= num_letto; i++) {
				bin += "1";
			}
		}
		return bin;
	}
	
	public String convertiMovimentoInBinario(String movimento) {
		if(movimento.equals("L"))
			return sinistra;
		if(movimento.equals("R"))
			return destra;
		return "";
	}
	
	public List<HashMap<String, String>> convertiTransizioniInBinario(List<HashMap<String, String>> lista_transizioni) {
		LinkedList<HashMap<String, String>> lista_transizioni_binarie = new LinkedList<HashMap<String, String>>();
		
		for(HashMap<String, String> tr : lista_transizioni) {
			HashMap<String, String> transizione_binaria = new HashMap<String, String>();
			transizione_binaria.put("stato_partenza", convertiStatoInBinario(tr.get("stato_partenza")));
			transizione_binaria.put("simbolo_letto", convertiStringaInBinario(tr.get("simbolo_letto")));
			transizione_binaria.put("simbolo_scritto", convertiStringaInBinario(tr.get("simbolo_scritto")));
			transizione_binaria.put("movimento", convertiMovimentoInBinario(tr.get("movimento")));
			transizione_binaria.put("stato_arrivo", convertiStatoInBinario(tr.get("stato_arrivo")));
		
			lista_transizioni_binarie.add(transizione_binaria);
		}
		return lista_transizioni_binarie;
	}
	
	public String listaAString(List<String> l) {
		return String.join("", l);
	}
	
	public LinkedList<String> stringaALista(String s){
		return new LinkedList<String>(List.of(s.split(" ")));
	}
	
	public void stampaListaTransizioni(List<HashMap<String, String>> ltr) {
		for(HashMap<String, String> dict : ltr) {
			for(String key : dict.keySet()) {
				System.out.println(key + " : " + dict.get(key));
			}
			System.out.println();
		}
	}
}
