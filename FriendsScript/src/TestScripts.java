import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TestScripts {
	
	static ArrayList<String> script;
	static String[] cast = {"Rachel", "Ross", "Monica", "Joey", "Phoebe", "Chandler", "Everyone"};
	public enum friends {
		Rachel(0), Ross(1), Monica(2), Joey(3), Phoebe(4), Chandler(5), Everyone(6);
		
		private int frdNo;
		
		private static Map<Integer, friends> map = new HashMap<Integer, friends>();
		
		static {
			for (friends frdEnum : friends.values()){
				map.put(frdEnum.frdNo, frdEnum);
			}
		}
		
		private friends(final int frd) { frdNo = frd; }
		
		public static friends valueOf(int frdNo) {
			return map.get(frdNo);
		}
	}
	
	public static void main(String[] args) throws IOException {
		script = new ArrayList<String>();
		ArrayList<String> fullScript = new ArrayList<String>();
		ArrayList<String> Rachel = new ArrayList<String>();
		ArrayList<String> Ross = new ArrayList<String>();
		ArrayList<String> Monica = new ArrayList<String>();
		ArrayList<String> Joey = new ArrayList<String>();
		ArrayList<String> Phoebe = new ArrayList<String>();
		ArrayList<String> Chandler = new ArrayList<String>();
		ArrayList<ArrayList<String>> eachScript = new ArrayList<ArrayList<String>>();
		
		eachScript.add(Rachel);
		eachScript.add(Ross);
		eachScript.add(Monica);
		eachScript.add(Joey);
		eachScript.add(Phoebe);
		eachScript.add(Chandler);
		eachScript.add(fullScript);
		
		
		
		fullScript = OpenFile("./Friendstranscript/Friendstranscript101.txt");
		
		for(String ret : fullScript) {
			boolean isCast = false;
			
			String[] parts = ret.split(":");
			
			for(String name : cast) {
				//System.out.println();
				//System.out.println("parts[0] = " + parts[0] + " :: name = " + name + " :: isCast = " + isCast);
				if (name.equals(parts[0])){
					isCast = true;
					
					eachScript.get(
							friends.valueOf(name).ordinal())
							.add(parts[1].replaceAll("\\(.*\\)", "").replaceAll("\\[.*\\]", "").trim());
					
					break;
				}
				//System.out.println("parts[0] = " + parts[0] + " :: name = " + name + " :: isCast = " + isCast);
			}
			
			//System.out.println(parts[0]);
			//System.out.println(isCast);
		}
		
		Comparator<String> x =  new Comparator<String>() {
			@Override
			public int compare (String o1, String o2){
				if (o1.length() > o2.length())
					return 1;
				if (o1.length() < o2.length())
					return -1;
				return 0;
			}
		};
		
		for (int i = 0 ; i < eachScript.size(); i++){
			Collections.sort(eachScript.get(i), x);
		}
		
/*		
		int i = 0;
		for(ArrayList<String> each : eachScript){
			System.out.println("=============== This is the Script of " + friends.values()[i]);
			for(String ret : each) {
				System.out.println(ret);
			}
			i++;
		}
		*/
		startExercise();
		
		Scanner scan = new Scanner(System.in);
		int a = -1;
		try {
			a = Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("now please repeat after the example and check your pronunciation");
		

		for(String ret : eachScript.get(a-1)){
			System.out.println(friends.valueOf(a - 1) + " : ");
			System.out.println("    " + ret);	
			
			String b = "";
			//System.out.println("Press \'ENTER\' if you want to listen to the sentence. Otherwise, repeat after the sentence.");
			Text2Speech speak = new Text2Speech();
			while(true){
				speak.dospeak(ret, "kevin16");
				b = scan.nextLine();
				StringSimilarity.printSimilarity(ret.replaceAll("[^A-Za-z0-9 ]", ""), b);
				if (StringSimilarity.similarity(ret.replaceAll("[^A-Za-z0-9 ]", ""), b) > 0.8)
					break;
			}
			
		}

		
		
	}
	
	private static void startExercise() {
		System.out.println("===========================================");
		System.out.println("Now let\'s start an exercise");
		System.out.println("First, Please choose your favorite character from \'Friends\'");
		for(int i = 0; i < cast.length ; i++){
			int num = i + 1;
			System.out.println(num + ". " + cast[i]);
		}
	}
	
	
	private static ArrayList<String> OpenFile(String path) throws IOException {
		
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		
		ArrayList<String> lines = new ArrayList<String>();
		
		String line;
		while((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();
		return lines;
		
	}

}
