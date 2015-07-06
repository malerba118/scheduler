import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * Exam scheduling heuristic.
 * 
 * Worst case - every test conflicts with every other. Let t be the number of tests, s be the number of students,
 * and k be the number of tests each student takes. Creating the input array takes O(s*k) time which reduces to O(s)
 * time because k is a constant. Populating the HashMap also takes O(s) time. The generateSchedule method is the bottleneck.
 * If every test conflicts with every other, then the outer loop runs t times. On the first iteration, the inner loop
 * also runs t times, which means the intersection method gets called t times. Only the first of these calls takes O(1)
 * time, the rest take O((s/t)*k) time, where (s/t)*k is the average number of students taking each test. On the next 
 * iteration of the outer loop the inner loop runs only t-1 times, calling the intersection method t-1 times. Again
 * all but one of these calls takes O((s/t)*k) time. So it follows that the worst case time is O(s/t*k( (t-1) + (t-2) +...+ 0) ).
 * This reduces to O(s/t*k * (t-1)*t/2), which reduces to O(s*t).
 * 
 * 
 * SAMPLE OUTPUT:
 * 
 * Number of students: 17000
 *
 * Number of tests: 750
 *
 * Num tests per student: 4
 *
 * Execution time: 0.366seconds
 *
 * Number of test groupings: 61
 *
 * First 3 test groupings: [[270, 0, 693, 583, 1, 3, 302, 7, 304, 117, 646, 9, 614, 559, 703, 728, 609], [709, 342, 2, 322, 4, 125, 720, 6, 513, 725, 208, 606, 738, 607, 739, 718, 708], [33, 442, 345, 5, 600, 8, 712, 602, 229, 406, 659, 737, 517, 429, 705, 706]]
 * 
 * 
 * @author amalerba
 *
 */
public class Scheduler {

	HashMap<String, TreeSet<Integer>> testMap;
	
	public Scheduler(File input){
		
		populateTestMap(input);
		
	}
	
	/**
	 * Map each test to an array containing the indices (students) of the inputArray at which
	 * that test exists.
	 * 
	 * @param input
	 */
	private void populateTestMap(File input){
		
		testMap = new HashMap<String, TreeSet<Integer>>();
		
		ArrayList<String[]> inputArray = generateInputArray(input);
		
		for (int i = 0; i < inputArray.size(); i++){
			
			for (String test : inputArray.get(i)){
				
				if (!testMap.containsKey(test)){
					testMap.put(test, new TreeSet<Integer>());
				}
				
				testMap.get(test).add(i);
			}
		}
	}
	
	/**
	 * Create an array of arrays that describe which tests cannot be scheduled
	 * at the same time. These lists theoretically represent the test schedules
	 * of individual students.
	 * 
	 * @param input - Input file
	 * @return created input array
	 */
	private ArrayList<String[]> generateInputArray(File input){
		
		ArrayList<String[]> inputArray = new ArrayList<String[]>();
		try{
			BufferedReader in = new BufferedReader(new FileReader(input));
			
			String line;
			while ((line = in.readLine()) != null){
				inputArray.add(line.split(" "));
			}
			
			in.close();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		return inputArray;
	}
	
	/**
	 * Greedy algorithm that chooses the largest possible non-conflicting set of
	 * tests it can find starting with the head of the list on each iteration.
	 * Continues choosing non-conflicting sets until the linked list is empty.
	 * 
	 * @return array of sets, representing the tests which should be scheduled
	 * at the same time because they do not conflict.
	 */
	public ArrayList<Set<String>> generateSchedule(){
		
		ArrayList<Set<String>> schedule = new ArrayList<Set<String>>();
		LinkedList<String> allTests = new LinkedList<String>();
		allTests.addAll(testMap.keySet());
		
		Set<String> grouping;
		while (! allTests.isEmpty()){
			
			grouping = new HashSet<String>();
			String curr;
			Iterator<String> iter = allTests.iterator();
			while (iter.hasNext()){
				curr = iter.next();
				
				if (! intersects(curr, grouping)){
					grouping.add(curr);
					iter.remove();
				}
				
			}
			
			schedule.add(grouping);
		}
		
		return schedule;
	}
	
	/**
	 * Checks whether the current test being examined conflicts with any of the
	 * tests already chosen for this time allotment. 
	 * 
	 * @param currTest - test being examined.
	 * @param grouping - current set of non-conflicting tests
	 * @return true if a time conflict is found, false otherwise.
	 */
	private boolean intersects(String currTest, Set<String> grouping){
		
		Integer[] currTestArray = testMap.get(currTest).toArray(new Integer[testMap.get(currTest).size()]);
		
		for (String test : grouping){
			
			Integer[] testArray = testMap.get(test).toArray(new Integer[testMap.get(test).size()]);
			
			int i = 0;
			int j = 0;
			
			while (i < currTestArray.length && j < testArray.length){
				if (currTestArray[i] < testArray[j]){
					i++;
				}
				else if (testArray[j] < currTestArray[i]){
					j++;
				}
				else if(currTestArray[i].equals(testArray[j])){
					return true;
				}
			}
		}
		
		return false;
	}
}
