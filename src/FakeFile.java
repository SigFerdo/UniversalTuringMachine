import java.util.HashMap;

public class FakeFile {

	private HashMap<Integer, String> file=new HashMap<Integer,String>();

	public FakeFile() {
		file.put(1,"q0-1-$-R-q0");
		file.put(2,"q0-empty-empty-L-q1");
		file.put(3,"q1-1-1-L-q1");
		file.put(4, "q1-$-1-R-q2");
		file.put(5,"q2-1-1-R-q2");
		file.put(6,"q2-empty-1-L-q1");
		file.put(7,"q1-empty-empty-R-qaccept");
	}
	
	public HashMap<Integer, String> getFile() {
		return this.file;
	}
}
