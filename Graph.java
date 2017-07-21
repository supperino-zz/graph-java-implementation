import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Set;

//Implementação de um Digrafo (um grafo orientado) 
public class Graph {
	public Graph() {
		nodes  = new ArrayList<Node>();
	}
	
	public Graph(List<Node> nodes) {
		this.nodes = nodes;
	}
	List<Node> nodes;
	
	/*
	 * Adiciona o nodo
	 * @param Node - Nodo a ser adicionado
	 * */
	void addNode(Node a) {
		nodes.add(a);
	}
	/*
	 * Remove o nodo
	 * @param Node a
	 * */
	void removeNode(Node a) {
		nodes.remove(a);
	}
	/* Conecta dois nodos
	 * @param - os dois nodos a serem conectados
	 * */
	void connectNode(Node a, Node b, int weight) {
	    if(weight <= 0) {
	        a.connectedNodes.put(b, 1);
	    } else {
	        a.connectedNodes.put(b, weight);
	    }    
	}
	/*Disconecta nodos
	 * @param - Dois nodos para serem disconectados
	 * */
	void disconnectNode(Node a, Node b) {
		if(a.connectedNodes.containsValue(b)) {
			a.connectedNodes.remove(b);
		} else if (b.connectedNodes.containsValue(a)) {
			b.connectedNodes.remove(b);
		} else {
			System.out.println("os dois nodos não estao conectados");
		}
	}
	/* Retorna o grau do grafo
	 * @return - Retorna o grau do grafo
	 * */
	int graphOrder() {
		return nodes.size();
	}
	/* Retorna uma lista com os nodos 
	 * @return - Uma lista com os nodos do grafo
	 * */
	List<Node> getNodes() {
		return nodes;
	}
	/* Retorna um nodo dado o ID
	 * @param - ID do nodo
	 * @return - o Nodo desejado
	 * */
	Node getNode(int id) {
		if(nodes.isEmpty()) {
			System.out.println("lista vazia");
			return null;
		} else {
			for(int i = 0; i < nodes.size(); i++) {
				if(nodes.get(i).getId() == id) {
					return nodes.get(i);
				}
			}
			return null;
		}
	}
	/* Retorna uma lista de nodos adjacentes
	 * @param - Nodo para comparar nodos adjacentes
	 * @return - Lista de nodos adjacentes
	 * */
	List<Node> adjacentNodes(Node a) {
	    List<Node> adjacentNodes ;
		if(nodes.contains(a)) {
		    Set<Node> setNodes = a.connectedNodes.keySet();
		    adjacentNodes = new ArrayList<Node>(setNodes);
			return adjacentNodes;
		}
		return null;
	}
	/* Retorna o grau do nodo
	 * */
	int degreeNode (Node a) {
		if(nodes.contains(a)) {
			return a.connectedNodes.size();
		}
		return 0;
	}
	/* Retorna se o grafo é regular ou não
	 * Um grafo é dito ser regular quando todos os seus vértices tem o mesmo grau.
	 * */
	boolean regularGraph() {
		Node n = nodes.get(0);
		for(Node i : adjacentNodes(n)) {
			if(degreeNode(i) != degreeNode(n)) {
				return false;
			}
		}
		return true;
	} 
	/* Avalia se um grafo é completo
	 * Um grafo é dito ser completo quando há uma aresta entre cada par de seus vértices.
	 * */
	boolean completeGraph() {
		if(nodes.isEmpty()) {
			System.out.println("não há nodos");
			return false;
		} else {
			int n = graphOrder()-1;
			for(int i = 0; i < nodes.size(); i++) {
				if(degreeNode(nodes.get(i)) != n) {
					return false;
				}
			}
		}
		return true;
	}
	/* Avalia o fecho transitividade de um nodo
	 * @param - Nodo para testar sua transitivdade
	 * @return - Retorna uma lista dos nodos que consegue alcançar
	 * */
	List<Node> transitiveClosure(Node a){
		List<Node> visited = new ArrayList<Node>();
		return searchTransitiveClosures(a, visited);
	}
	/*Verifica se existe pelo menos um caminho que entre cada par de nodos do grafo
	 * */
	boolean isConnected() {
		return nodes.containsAll(transitiveClosure(nodes.get(0)));
	}
	
	/*
	 * Verifica se o grafo é uma árvore, ou seja, se não possue ciclos se é conexo
	 * */
	boolean isTree() {
		Node a = nodes.get(1);
		List<Node> visited = new ArrayList<Node>();
        return isConnected() && !isCyclic(a, a, visited);
	}
	/*
	 * Faz a busca de fechos transitivos
	 * @param Nodo relativo da busca,
	 * @param Lista de nodos ja buscados, salvo no metodo que chama esta
	 * função
	 * @return - retorna a lista de nodos que são fechos transitivos
	 * 
	 * Pensado em usar recursão;
	 * */
	List<Node> searchTransitiveClosures(Node a, List<Node> visited){
		visited.add(a);
		for(Node n : adjacentNodes(a)) {
			if(!visited.contains(n)) {
				searchTransitiveClosures(n, visited);
			}
		}
		return visited;
	}
	/*
	 * Checa se há ciclos no grafo
	 * @param a -  Nodo principal
	 * @param prev - Nodo anterior ao nodo principal
	 * @param visited - Lista de nodos ja buscados.
	 * @return - true caso haja ciclos no grafo
	 * */
	boolean isCyclic(Node a, Node prev, List<Node> visited){
		if(visited.contains(a)) {
			return true;
		}
		visited.add(a);
		for(Node n : adjacentNodes(a)) {
			if(n != prev) {
				if(isCyclic(n, a, visited)) {
					return true;
				}
			}
		}
		visited.remove(a);
		return false;
	}
	/*
	 * Busca em profundidade
	 * */
	Node search(int id) {
	    Deque<Node> status = new ArrayDeque<Node>();
	    //pega o nodo com ordem de entrada = 0
	    List<Node> sentinel = new ArrayList<Node>();
	    List<Node> visited = new ArrayList<Node>();
	    
	    for(Node n : nodes) {
	        for(Node m : nodes ) {
	            if(!m.connectedNodes.containsValue(n)) {
	                sentinel.add(n);
	            }
	        }
	    }
	    
	    for(Node n : sentinel) {
	        for(Node m : depthFirstSearch(n, status, visited)) {
	            if(m.getId() == id) {
	                return m;
	            }
	        }
	    }
	    return null;
	}
	
	List<Node> depthFirstSearch(Node n, Deque<Node> status, List<Node> visited) {
	    status.push(n);
	    visited.add(n);
	    Set<Node> setConnected = n.connectedNodes.keySet();
	    List<Node> ListConnected = new ArrayList<Node>(setConnected);
	    for(Node vs : ListConnected) {
	        if(!visited.contains(vs)) {
	            status.push(vs);
	            visited.add(vs);
	            depthFirstSearch(status.pop(), status, visited);
	        } else {
	            status.pop();
	        }
	    }
	    return visited;
	}
}
