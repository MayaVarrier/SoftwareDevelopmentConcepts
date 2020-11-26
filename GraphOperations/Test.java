package graphOperation;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		VertexCluster vc = new VertexCluster();
		vc.addEdge("A", "B", 3);
		//vc.addEdge("a", "b", 4);
		vc.addEdge("A", "H", 1);
		vc.addEdge("I", "H", 1);
		vc.addEdge("I", "B", 4);
		vc.addEdge("B", "C", 7);
		vc.addEdge("I", "C", 8);
		vc.addEdge("G", "H", 7);
		vc.addEdge("H", "J", 10);
		vc.addEdge("D", "I", 12);
		vc.addEdge("D", "C", 14);
		vc.addEdge("D", "E", 8);
		vc.addEdge("J", "F", 3);
		vc.addEdge("E", "F", 2);
		vc.addEdge("F", "G", 7);
		vc.addEdge("J", "D", 1);
		
		vc.clusterVertices(3);
		
	}

}
