import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class CourseSelector {

	ArrayList<String[]> a1 = new ArrayList<String[]>();
	String[] course = new String[50];

	int read(String filename) {
		int count = 0;

		// Erase the previous data
		a1.clear();

		try {
			// Read the input from the user
			File file = new File(filename);
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				count++;
				String line = sc.nextLine();

				// Store the courses taken by a student into an array
				course = (line.split("\\s+"));

				// Add the arrays to the ArrayList
				a1.add(course);
				course = null;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("No file found!");
		} catch (NullPointerException n) {
			System.out.println("Please pass a value that's not null!");
		}

		// Return the number of rows
		return count;

	}

	ArrayList<String> recommend(String taken, int support, int recommendations) {
		int count = 0;
		int sup = 0;
		int temp = 0;
		ArrayList<String> al1 = new ArrayList<String>();
		ArrayList<String> al2 = new ArrayList<String>();
		ArrayList<String> al3 = new ArrayList<String>();
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		ArrayList<String> f = new ArrayList<String>();
		ArrayList<Integer> list = new ArrayList<Integer>();

		
		
		try {
			// Put all the values from taken into an arraylist
			al1 = new ArrayList(Arrays.asList(taken.split("\\s+")));
			if (!a1.isEmpty()) {
				if (!taken.isEmpty()) {
					// Check if support and recommendations is greater than 0
					if (!(support < 0) && !(recommendations < 0)) {
						// If all the elements of "taken" is present in an array of the arraylist where
						// the data from file is stored, then add the rest of the elements of the array
						// to an arraylist. If not,ignore that array
						for (String a[] : a1) {
							for (String b : al1) {
								for (int i = 0; i < a.length; i++) {
									if (b.toString().equalsIgnoreCase(a[i].toString())) {
										count++;
									} 
									else if ((!al2.contains(a[i])) && (!al1.contains(a[i]))) {
										al2.add(a[i]);
									}
								}
							}
							if (count == al1.size()) {
								sup++;
								al3.addAll(al2);
								al2.clear();
								count = 0;
							} else {
								al2.clear();
								count = 0;
							}
						}
						
						// If enough arrays have all the elements from "taken",then find the frequency
						// of all the elements apart from elements in "taken" and store them in a hashmap
						if (sup >= support) {
							for (String i : al3) {
								if (!hmap.containsKey(i)) {
									hmap.put(i, Collections.frequency(al3, i));
								}
							}

							// Hashmap sorting logic from
							// https://javaconceptoftheday.com/java-8-sort-hashmap-by-values/
							ArrayList<Entry<String, Integer>> l = new ArrayList<>(hmap.entrySet());

							Collections.sort(l, new Comparator<Entry<String, Integer>>() {
								@Override
								public int compare(Entry<String, Integer> c1, Entry<String, Integer> c2) {
									return c1.getValue().compareTo(c2.getValue());
								}
							});

							Map<String, Integer> sortedhmap = new LinkedHashMap<String, Integer>();

							for (Entry<String, Integer> entry : l) {
								sortedhmap.put(entry.getKey(), entry.getValue());
							}

							// Hashmap sorting done

							
							//Sort the values of the hashmap in reverse order and store it in a list
							list.addAll(sortedhmap.values());
							Collections.sort(list, Collections.reverseOrder());
							LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(list);
							ArrayList<Integer> list1 = new ArrayList<>(hashSet);

							if (recommendations == 0) {
								System.out.println("You asked for 0 recommendations!");
								
							//Return the number recommended courses in the decreasing order of frequency 	
							} else if (list1.size() >= recommendations) {

								List<Integer> rec = list1.subList(0, recommendations);

								for (int r : rec) {
									for (HashMap.Entry<String, Integer> e : sortedhmap.entrySet()) {

										if (r == (e.getValue())) {
											f.add(e.getKey());
										}
									}
								}
							
							//If the number of recommendations asked is greater than the number of courses to provide, then return all
							} else {
								System.out.println(
										"There are not enough recomended courses! The ones you can take are : \n");
								for (int r : list1) {
								for (HashMap.Entry<String, Integer> e : sortedhmap.entrySet()) {
									if (r == (e.getValue())) {
									f.add(e.getKey());
									}
								}
							}}
						} else
							System.out.println(
									"Enough students haven't taken the courses you are taking to give recomendations!");

					} else {
						System.out.println("The value of support and reccommendations should be positive!");
					}

				}

				else {
					System.out.println("Enter a non empty string!");
				}
			} else {
				System.out.println("Please read a file!");
			}
		} catch (NullPointerException n) {
			System.out.println("Enter a non null value!");
		}
		//Return the recommended courses
		return f;
	}

	boolean showCommon(String courses) {
		int count1 = 0;
		int count2 = 0;
		int find = 0;

		try {
			//Store the input string to an array
			String ch[] = (courses.split(" "));
			int clen = ch.length;
			int course[][] = new int[clen][clen];
			if (!a1.isEmpty()) {
				if (!courses.isEmpty()) {
					//Check how many times all the possible pairs of given courses appears together
					for (int i = 0; i < ch.length; i++) {
						for (int j = i + 1; j < ch.length; j++) {
							for (String[] a : a1) {
								for (int k = 0; k < a.length; k++) {
									if (a[k].toString().equalsIgnoreCase(ch[i].toString())) {
										count1++;
									}
									if (a[k].toString().equalsIgnoreCase(ch[j].toString())) {
										count2++;
									}
								}
								if ((count1 > 0 && count2 == 0) || (count1 == 0 && count2 > 0)) {
									count1 = 0;
									count2 = 0;
								} else if (count1 > 0 && count2 > 0) {
									find++;
									count1 = 0;
									count2 = 0;
								}
							}
							//Store the number of times a pair of course is taken together in the matrix
							course[i][j] = find;
							course[j][i] = find;
							find = 0;
						}
					}
					//Print the final matrix to the screen
					for (int i = 0; i < ch.length; i++) {
						System.out.print(ch[i].toUpperCase() + "\t");
						for (int j = 0; j < ch.length; j++) {
							System.out.print(course[i][j] + "\t");
						}
						System.out.println();
					}
				} else {
					System.out.println("Please enter a non empty string!");
				}
			} else {
				System.out.println("Please read a file!");
			}

		}

		catch (NullPointerException e) {
			System.out.println("Enter a non null value!");
		}
		return true;
	}

	boolean showCommonAll(String filename) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			int clen = 0;
			int count1 = 0;
			int count2 = 0;
			int find = 0;
			ArrayList<String> c = new ArrayList<String>();

			//Add all the courses from the main arraylist to another arraylist. Remove duplicates
			if (!a1.isEmpty()) {
				for (String[] a : a1) {
					for (int i = 0; i < a.length; i++) {
						if (!c.contains(a[i])) {
							c.add(a[i]);
						}
					}
				}
				clen = c.size();
				int course[][] = new int[clen][clen];
				//Sort the courses in alphabetical order
				Collections.sort(c, String.CASE_INSENSITIVE_ORDER);
				/*System.out.println("\n");
				for (int i = 0; i < c.size(); i++) {
					System.out.println(c.get(i));
				}*/

				//Check how many times all the possible pairs of all the courses appears together
				for (int i = 0; i < c.size(); i++) {
					for (int j = i + 1; j < c.size(); j++) {
						for (String[] a : a1) {
							for (int k = 0; k < a.length; k++) {
								if (a[k].toString().equalsIgnoreCase(c.get(i).toString())) {
									count1++;
								}
								if (a[k].toString().equalsIgnoreCase(c.get(j).toString())) {
									count2++;
								}
							}
							if ((count1 > 0 && count2 == 0) || (count1 == 0 && count2 > 0)) {
								count1 = 0;
								count2 = 0;
							} else if (count1 > 0 && count2 > 0) {
								find++;
								count1 = 0;
								count2 = 0;
							}
						}
						//Store the number of times a pair of course is taken together in the matrix 
						course[i][j] = find;
						course[j][i] = find;
						find = 0;
					}
				}
				
				//Write into the file
				for (int i = 0; i < c.size(); i++) {
					writer.write(c.get(i) + "\t");
					for (int j = 0; j < c.size(); j++) {
						writer.write(course[i][j] + "\t");
					}
					writer.newLine();
				}
				writer.close();

			} else {
				System.out.println("Please read data into the file!");
			}

		} catch (IOException e) {
			System.out.println("No file present!");
			;
		} catch (NullPointerException n) {
			System.out.println("Please pass a value that's not null!");
		}
		return true;
	}

}
