package graphOperation;

public class Vertex {

	private String value = null;
	private int weight = 1;
	private int clusterWeight = 1;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getClusterWeight() {
		return clusterWeight;
	}
	public void setClusterWeight(int clusterWeight) {
		this.clusterWeight = clusterWeight;
	}
	
}
