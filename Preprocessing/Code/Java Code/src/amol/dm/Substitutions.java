package amol.dm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Substitutions {
	// used to store all substitutions
	
	private HashMap<String, SubstitutionData> substitutions;
	
	public Substitutions(String filename) throws Exception{
		loadSubstitutionsFromFile(filename);
	}
	
	public HashMap<String, SubstitutionData> getSubstitutions(){
		return substitutions;
	}
	
	private void loadSubstitutionsFromFile(String filename) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		
		substitutions = new HashMap<>();
		
		line = br.readLine();
		while( (line!=null) && !line.equals("")){
			String splits[] = line.split("\t");
			
			if(!substitutions.containsKey(splits[0])){
				substitutions.put(splits[0], new SubstitutionData(splits[0]));
			}
			substitutions.get(splits[0]).insertMutation(new Integer(splits[1]), splits[2], splits[3]);
						
			line = br.readLine();
		}
		br.close();
	}

	public Substitutions(){}

}
