import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class TestMDT {

	public static void main(String[] args) throws Exception {
		
		BinarieTransizioni bt = new BinarieTransizioni();
		MdtUniversale macchina = new MdtUniversale();

		System.out.println("Inserisci la stringa in input alla macchina");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String input = in.readLine();
		
		macchina.computaStringa(input);
	}
}
