import java.util.ArrayList;
import java.util.List;

public class Node {
	/*Toda vez que incializa o construtor de node, cria um id unico.
	 * */
	public Node () {
		instanceCounter++;
		id = instanceCounter;
		connectedNodes = new ArrayList<Node>();
	}
	
	List<Node> connectedNodes;
	private int id = 0;
	static int instanceCounter = 0;
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return "n"+id;
	}
}
