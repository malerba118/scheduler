import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


public class Main {

	private static int NUM_STUDENTS = 17000;
	private static int NUM_TESTS = 750;
	private static int TESTS_PER_STUDENT = 4;
	
	public static void main(String[] args){
		
		
		String root = System.getProperty("user.dir");
		String relPath = "/src/test.txt";
		
		File input = new File(root + relPath);
		
		try {
			FileWriter fw = new FileWriter(input);
			BufferedWriter bw = new BufferedWriter(fw);
			Random rand = new Random();
			
			String line = "";
			
			for (int s = 0; s < NUM_STUDENTS; s++){
				//hardcoding this in because nested for loop destroys system memory
				line = rand.nextInt(NUM_TESTS) + " " + rand.nextInt(NUM_TESTS) + " " + rand.nextInt(NUM_TESTS) + " " + rand.nextInt(NUM_TESTS) + "\n";
				bw.write(line);
			}
			
			bw.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scheduler s = new Scheduler(input);
		
		long start;
		long finish;
		
		start = System.currentTimeMillis();
		ArrayList<Set<String>> sched = s.generateSchedule();
		finish = System.currentTimeMillis();
		
		double execTime = (finish - start)/1000.0;
		
		System.out.println("Number of students: " + NUM_STUDENTS + "\n");
		System.out.println("Number of tests: " + NUM_TESTS + "\n");
		System.out.println("Num tests per student: " + TESTS_PER_STUDENT + "\n");
		
		System.out.println("Execution time: " + execTime + "seconds\n");
		System.out.println("Number of test groupings: " + sched.size() + "\n");
		System.out.println("First 3 test groupings: " + sched.subList(0, 3) + "\n");
	}
}
