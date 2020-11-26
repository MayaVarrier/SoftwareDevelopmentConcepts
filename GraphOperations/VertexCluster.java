package graphOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class VertexCluster {

	HashMap<Edge, Integer> edgeValues = new HashMap<Edge, Integer>();
	Map<Edge, Integer> sortedEdgeValues = new LinkedHashMap<Edge, Integer>();
	Set<Set<String>> groupCluster = new LinkedHashSet<Set<String>>();
	Set<String> singleCluster = new LinkedHashSet<String>();
	ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	ArrayList<String> vertexName = new ArrayList<String>();
	String str = "";
	int cluster = 0;

	// Records an edge with weight between two vertices
	boolean addEdge(String vertex1, String vertex2, int weight) {

		boolean ret = false;
		String temp = null;

		if ((vertex1 != null && vertex2 != null) && (!vertex1.isEmpty() && !vertex2.isEmpty()) && weight >= 0) {

			if (vertex1.compareToIgnoreCase(vertex2) > 0) {
				temp = vertex2;
				vertex2 = vertex1;
				vertex1 = temp;
			}

			// Store all the vertices along with their unique cluster distinguishing ID to
			// an ArrayList
			Vertex v1 = new Vertex();
			Vertex v2 = new Vertex();
			v1.setValue(vertex1);
			v2.setValue(vertex2);

			Edge edge1 = new Edge(v1, v2);

			// If an edge between the given two vertices does not exist, then create one and
			// assign it the given weight
			ArrayList<Entry<Edge, Integer>> list1 = new ArrayList<>(edgeValues.entrySet());
			String str1 = null;
			for (Entry<Edge, Integer> entry : list1) {
				str1 = new String(
						entry.getKey().getVertex1().getValue() + entry.getKey().getVertex2().getValue() + "\n");
				str = str.concat(str1);
			}
			String str2 = new String(edge1.getVertex1().getValue() + edge1.getVertex2().getValue());

			if (str1 == null || !str.toLowerCase().contains(str2.toLowerCase())) {
				edgeValues.put(edge1, weight);
				ret = true;
			}
			// If an edge between the given two vertices exists already, then do not create
			// an edge
			else {
				System.out.println("The edge is already present in the graph!");
				return ret;
			}

			// Sort the edges according to their weights in the ascending order
			// Hashmap sorting logic from
			// https://javaconceptoftheday.com/java-8-sort-hashmap-by-values/
			ArrayList<Entry<Edge, Integer>> list = new ArrayList<>(edgeValues.entrySet());

			Collections.sort(list, new Comparator<Entry<Edge, Integer>>() {
				@Override
				public int compare(Entry<Edge, Integer> c1, Entry<Edge, Integer> c2) {
					int a = 0;
					if (c1.getValue() != c2.getValue()) {
						a = c1.getValue().compareTo(c2.getValue());
					} else {
						a = c1.getKey().getVertex1().getValue().compareTo(c2.getKey().getVertex1().getValue());
					}
					return a;
				}
			});

			sortedEdgeValues.clear();

			// Add the edges and their weights into the hashmap
			for (Entry<Edge, Integer> entry : list) {
				sortedEdgeValues.put(entry.getKey(), entry.getValue());
			}

			// Hashmap sorting done

		} else {
			System.out.println("The vertex names should be a string and weight should be a positive integer..");
		}
		return ret;

	}

	// Clusters are formed
	Set<Set<String>> clusterVertices(float tolerance) {

		int count = 0;
		int clusterWeight = 0;
		float criteria = 0;
		int counter = 0;
		int singleCounter1 = 0;
		int singleCounter2 = 0;
		float minWeight = 0;
		boolean flag = false;
		ArrayList<String> singles = new ArrayList<String>();
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		ArrayList<Entry<Edge, Integer>> sortedEdgeValuesList = new ArrayList<>(sortedEdgeValues.entrySet());
		ArrayList<Entry<String, Integer>> hmapList = new ArrayList<>(hmap.entrySet());

		// Parse through the hashmap where edges are stored and take edges one by one
		for (Entry<Edge, Integer> entry1 : sortedEdgeValuesList) {
			// If hashmap that stores clusters is empty, then check if the first edge meets
			// the criteria. If so, add it
			if (hmap.isEmpty()) {
				// Find the minimum weight between the 2 cluster(edges)
				if (entry1.getKey().getVertex1().getClusterWeight() < entry1.getKey().getVertex2().getClusterWeight()) {
					minWeight = entry1.getKey().getVertex1().getClusterWeight();
				} else {
					minWeight = entry1.getKey().getVertex2().getClusterWeight();
				}
				// Check if the formula is satisfied
				criteria = entry1.getValue() / minWeight;
				clusterWeight = entry1.getValue();

				// If formula is satisfied, form a cluster with the 2 vertices
				if (criteria <= tolerance) {
					hmap.put((entry1.getKey().getVertex1().getValue() + entry1.getKey().getVertex2().getValue()),
							clusterWeight);
				}
				// If formula is not satisfied, store the edges in an arraylist
				else {
					singles.add(entry1.getKey().getVertex1().getValue());
					singleCounter1++;
					singles.add(entry1.getKey().getVertex2().getValue());
					singleCounter2++;
				}

				hmapList = new ArrayList<>(hmap.entrySet());
			}

			// If there are already clusters in the hashmap, do the following
			else if (!hmap.isEmpty()) {
				hmapList = new ArrayList<>(hmap.entrySet());
				for (Entry<String, Integer> entry2 : hmapList) {

					// If both vertices are present individually in any of the elements of the
					// hashmap, then disregard the edge
					if (((entry2.getKey().contains(entry1.getKey().getVertex1().getValue()))
							&& (entry2.getKey().contains(entry1.getKey().getVertex2().getValue())))) {
						System.out.println("Disregarding the edge!");

						// If both vertices are not present in any element of the hashmap, then do the
						// following
					} else if (((!entry2.getKey().contains(entry1.getKey().getVertex1().getValue()))
							&& (!entry2.getKey().contains(entry1.getKey().getVertex2().getValue())))) {
						if (entry1.getKey().getVertex1().getClusterWeight() > entry1.getKey().getVertex1()
								.getClusterWeight()) {
							minWeight = entry1.getKey().getVertex1().getClusterWeight();
						} else {
							minWeight = entry1.getKey().getVertex2().getClusterWeight();
						}
						criteria = entry1.getValue() / minWeight;
						clusterWeight = entry1.getValue();
						// If the formula is satisfied, then make a cluster out of the two vertices and
						// add them to the hashmap
						if (criteria <= tolerance) {
							hmap.put(
									(entry1.getKey().getVertex1().getValue() + entry1.getKey().getVertex2().getValue()),
									clusterWeight);
						}
						// If formula is not satisfied, then add the vertices to an arraylist
						else {
							singles.add(entry1.getKey().getVertex1().getValue());
							singles.add(entry1.getKey().getVertex2().getValue());
						}
						// If any of the vertices are in any of the elements of the hashmap ie;a new
						// edge is being added to an existing cluster
					} else if (((entry2.getKey().contains(entry1.getKey().getVertex1().getValue()))
							|| (entry2.getKey().contains(entry1.getKey().getVertex2().getValue())))) {
						// If the first vertex is present in any of the element of the hashmap
						if (entry2.getKey().contains(entry1.getKey().getVertex1().getValue())) {

							for (Entry<String, Integer> e : hmapList) {
								// If the second vertex is present in the different element of the hashmap
								if (e.getKey().contains(entry1.getKey().getVertex2().getValue())) {
									if (!(e.getKey().contains(entry1.getKey().getVertex1().getValue())
											&& (e.getKey().contains(entry1.getKey().getVertex2().getValue())))) {
										if (e.getValue() < entry2.getValue()) {
											minWeight = e.getValue();
										} else {
											minWeight = entry2.getValue();
										}

										criteria = entry1.getValue() / minWeight;
										if (e.getValue() > entry1.getValue() && e.getValue() > entry2.getValue()) {
											clusterWeight = e.getValue();
										} else if (entry1.getValue() > e.getValue()
												&& entry1.getValue() > entry2.getValue()) {
											clusterWeight = entry1.getValue();
										} else if (entry2.getValue() > e.getValue()
												&& entry2.getValue() > entry1.getValue()) {
											clusterWeight = entry2.getValue();

										}
										// If formula is satisfied, merge the 2 clusters together. Update the weight of
										// the cluster as the lowest weight between the two clusters
										if (criteria <= tolerance) {
											hmap.put((entry2.getKey() + e.getKey()), clusterWeight);
											hmap.remove(entry2.getKey());
											hmap.remove(e.getKey());
											counter++;
											flag = true;
										}
									}
								}
							}

							// If the second element is not present in any of the elements in the hashmap
							if (flag == false) {
								if (entry2.getValue() < entry1.getKey().getVertex2().getClusterWeight()) {
									minWeight = entry2.getValue();
								} else {
									minWeight = entry1.getKey().getVertex2().getClusterWeight();
								}

								criteria = entry1.getValue() / minWeight;

								// If formula is satisfied, add the vertex to the cluster
								if (criteria <= tolerance) {
									if (entry2.getValue() > entry1.getValue()) {
										clusterWeight = entry2.getValue();
									} else {
										clusterWeight = entry1.getValue();
									}
									hmap.put((entry2.getKey() + entry1.getKey().getVertex2().getValue()),
											clusterWeight);
									hmap.remove(entry2.getKey());
									counter++;
								}
								// If formula is not satisfied, then add the vertex to the arraylist
								else {
									if (!singles.contains(entry1.getKey().getVertex2().getValue())) {
										singles.add(entry1.getKey().getVertex2().getValue());
									} else {
										singleCounter2++;
									}
								}
							}
						}
						// If the second vertex is present in any of the element of the hashmap
						else if (entry2.getKey().contains(entry1.getKey().getVertex2().getValue())) {
							hmapList = new ArrayList<>(hmap.entrySet());
							for (Entry<String, Integer> e : hmapList) {
								// If the first vertex is present in a different element of the hashmap
								if (e.getKey().contains(entry1.getKey().getVertex1().getValue())) {
									if (!(e.getKey().contains(entry1.getKey().getVertex1().getValue())
											&& (e.getKey().contains(entry1.getKey().getVertex2().getValue())))) {
										if (e.getValue() < entry2.getValue()) {
											minWeight = e.getValue();
										} else {
											minWeight = entry2.getValue();
										}

										criteria = entry1.getValue() / minWeight;
										if (e.getValue() > entry1.getValue() && e.getValue() > entry2.getValue()) {
											clusterWeight = e.getValue();
										} else if (entry1.getValue() > e.getValue()
												&& entry1.getValue() > entry2.getValue()) {
											clusterWeight = entry1.getValue();
										} else if (entry2.getValue() > e.getValue()
												&& entry2.getValue() > entry1.getValue()) {
											clusterWeight = entry2.getValue();

										}
										// If formula is satisfied. then merge the clusters
										if (criteria <= tolerance) {
											hmap.put((entry2.getKey() + e.getKey()), clusterWeight);
											hmap.remove(entry2.getKey());
											hmap.remove(e.getKey());
											counter++;
											flag = true;
										}
									}
								}
							}
							// If the second element is not present in any of the hashmap elements
							if (flag == false) {

								if (entry2.getValue() < entry1.getKey().getVertex1().getClusterWeight()) {
									minWeight = entry2.getValue();
								} else {
									minWeight = entry1.getKey().getVertex1().getClusterWeight();
								}

								criteria = entry1.getValue() / minWeight;
								// If formula is satisfied, add the vertex to the existing cluster
								if (criteria <= tolerance) {
									if (entry2.getValue() > entry1.getValue()) {
										clusterWeight = entry2.getValue();
									} else {
										clusterWeight = entry1.getValue();
									}
									hmap.put((entry2.getKey() + entry1.getKey().getVertex1().getValue()),
											clusterWeight);
									hmap.remove(entry2.getKey());
									counter++;
								}
								// If formula is not satisfied, then add the edge to the arraylist
								else {
									if (!singles.contains(entry1.getKey().getVertex1().getValue())) {
										singles.add(entry1.getKey().getVertex1().getValue());
									} else {
										singleCounter1++;
									}
								}
							}
						}
					}
					// Removing duplicate values from the hashmap and the arraylist
					if (counter > 0) {
						hmap.remove(
								(entry1.getKey().getVertex1().getValue() + entry1.getKey().getVertex2().getValue()));
					}
					if (singleCounter1 < 1) {
						singles.remove(entry1.getKey().getVertex1().getValue());
					} else if (singleCounter2 < 1) {
						singles.remove(entry1.getKey().getVertex2().getValue());
					}
				}

				hmapList = new ArrayList<>(hmap.entrySet());
				counter = 0;
				clusterWeight = 0;
				singleCounter1 = 0;
				singleCounter2 = 0;
				flag = false;
			}
		}

		// Put the elements of the arraylist of vertices which couldn't make a cluster
		// with other vertices into the hashmap
		ArrayList<String> al = new ArrayList<String>();
		for (String s : singles) {
			if (!al.contains(s)) {
				al.add(s);
			}
		}
		for (String a : al) {
			for (Entry<String, Integer> el : hmapList) {
				if (el.getKey().contains(a)) {
					count++;
				}
			}
			if (count == 0) {
				hmap.put(a, 1);
				count = 0;
			}
		}

		// Iterate through the hashmap and merge the elements if they have anything in
		// common
		hmapList = new ArrayList<>(hmap.entrySet());
		int len = 0;
		int con = 0;
		for (Entry<String, Integer> merge1 : hmapList) {
			for (Entry<String, Integer> merge2 : hmapList) {
				if (merge1.getKey() != merge2.getKey()) {

					if (merge1.getKey().length() > merge2.getKey().length()) {
						len = merge1.getKey().length();
					} else {
						len = merge2.getKey().length();
					}

					for (int i = 0; i < merge1.getKey().length(); i++) {
						char c = merge1.getKey().charAt(i);
						for (int j = 0; j < merge2.getKey().length(); j++) {
							char h = merge2.getKey().charAt(j);
							if (c == h) {
								con++;
							}
						}
					}

					if (con > 0) {
						if (merge1.getValue() < merge2.getValue()) {
							hmap.put(merge1.getKey() + merge2.getKey(), merge1.getValue());
							hmap.remove(merge1.getKey());
							hmap.remove(merge2.getKey());
						} else {
							hmap.put(merge1.getKey() + merge2.getKey(), merge2.getValue());
							hmap.remove(merge1.getKey());
							hmap.remove(merge2.getKey());
						}
						hmapList = new ArrayList<>(hmap.entrySet());
					}
				}
			}
			hmapList = new ArrayList<>(hmap.entrySet());
			con = 0;
		}

		// Put the values into Set
		hmapList = new ArrayList<>(hmap.entrySet());
		ArrayList<Entry<String, Integer>> l = new ArrayList<>(hmap.entrySet());
		String string = new String();
		// Add the sets of clusters to a set
		for (Entry<String, Integer> ent : hmapList) {
			// Add each value in a cluster as distinct element in a set
			for (int i = 0; i < ent.getKey().length(); i++) {
				string = Character.toString(ent.getKey().charAt(i));
				singleCluster.add(string);
			}
			groupCluster.add(singleCluster);
			singleCluster = new LinkedHashSet<String>();
		}

		if (groupCluster.isEmpty()) {
			return null;
		} else {
			return groupCluster;
		}
	}

}
