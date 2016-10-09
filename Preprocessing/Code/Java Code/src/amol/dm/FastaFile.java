package amol.dm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.AbstractMap.SimpleEntry;

import javax.swing.filechooser.FileNameExtensionFilter;


public class FastaFile {
	// class is used for reading data from fasta files	
	
	private BufferedReader br;
	private boolean EOFReached = false;
	private String filename;
	private String desc;
	//private String seq;
	private StringBuffer sequence;
	
	public boolean isOver(){
		return EOFReached;
	}
	
	private void setIsOver(){
		this.EOFReached = true;
	}
	
	public FastaFile(String filename) throws Exception{
		this.setFilename(filename);
		try{
			br = new BufferedReader(new FileReader(filename));
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
			return;
		}
	}
	
	public SimpleEntry<String, String> getNext() throws Exception{
		
		if(isOver()){
			return null;
		}
		
		SimpleEntry<String, String> record = null;
		
		sequence = new StringBuffer("");
		
		String line = br.readLine();
		
		while(line != null){
			if(line.equals("")){
				line = br.readLine();
				continue;
			}
			if(line.startsWith(">")){
				if((desc != null) && (!sequence.equals(""))){
					record = new SimpleEntry<String, String>(desc, sequence.toString().trim());
					
					this.desc = line.split(";")[0].substring(1);
					sequence = null;
					
					return record;
				}
				else{
					this.desc = line.split(";")[0].substring(1);
					sequence = new StringBuffer("");
				}
			}
			else{
				if(sequence != null){
					sequence.append(line);
				}
				else{
					sequence = new StringBuffer("");
					sequence.append(line);
				}	
			}
			line = br.readLine();
		}
		
		if((desc != null)){
			setIsOver();
			record = new SimpleEntry<String, String>(desc, sequence.toString().trim());
			desc = null;
			sequence = null;
		}
		
		return record;
	}

	
	public String getFilename() {
		return filename;
	}
	
	void setFilename(String filename) {
		this.filename = filename;
	}
	

	
}
