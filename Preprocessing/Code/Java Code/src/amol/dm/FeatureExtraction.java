package amol.dm;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class FeatureExtraction {
	// used for getting counts, and the residues
	
	public static HashMap<Character, Integer> getResidues(String sequence, int position, int windowSize) throws Exception{
		int i = position;
		HashMap<Character, Integer> hMap = new LinkedHashMap<>();
		
		for (int j = i - 1; (j >= i - windowSize/2) && (j >= 0); j--) {
			addOne(hMap, sequence.charAt(j));
		}
		for (int j = i + 1; (j <= i + windowSize/2) && (j < sequence.length()); j++) {
			addOne(hMap, sequence.charAt(j));
		}

		StringBuffer tempBuffer = new StringBuffer("");
		
		for (char c : new char[]{'F','L','I','M','V','S','P','T','A','Y','H','Q','N','K','D','E','C','W','R','G'}) {
			if(hMap.containsKey(c)){
				//System.out.print(c);
				System.out.print(hMap.get(c) + " ");
				
				tempBuffer.append(c);
				tempBuffer.append(hMap.get(c));
				
			}
			else{
				//System.out.print(c);
				System.out.print("0 "); 
			}
		}
		tempBuffer.append(" ");
		System.out.print(tempBuffer.toString());

		return hMap;
	}
	
	public static void addOne(HashMap<Character, Integer> hMap, Character c) throws Exception{
		if(hMap.containsKey(c)){
			hMap.put(c, hMap.get(c)+1);
		}
		else{
			hMap.put(c, 1);
		}
	}

	public static void getCounts(String s){
		HashMap<Character, Integer> hMap = new HashMap<>();
		
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if(hMap.containsKey(c)){
				hMap.put(c, hMap.get(c)+1);
			}
			else{
				hMap.put(c,1);
			}
		}
		
		for (char c : new char[]{'F','L','I','M','V','S','P','T','A','Y','H','Q','N','K','D','E','C','W','R','G'}) {
			if(hMap.containsKey(c)){
				System.out.print(c);
				System.out.print(hMap.get(c) + " ");
			}
			else{
				System.out.print(c);
				System.out.print("0 ");
			}
		}
		
	}
}
