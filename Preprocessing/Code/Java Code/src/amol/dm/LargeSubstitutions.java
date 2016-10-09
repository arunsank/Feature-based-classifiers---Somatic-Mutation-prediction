package amol.dm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

public class LargeSubstitutions {
	// using this class to read from a large tsv file, one entry at a time
	
	private BufferedReader br;
	private TsvParser tsvParser;
	
	public LargeSubstitutions(String filename) throws Exception{
		try{
			br = new BufferedReader(new FileReader(filename));
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
			return;
		}		
		
		TsvParserSettings tParserSettings = new TsvParserSettings();
		tParserSettings.setNumberOfRowsToSkip(1);
		tParserSettings.setLineSeparatorDetectionEnabled(true);
		tParserSettings.setSkipEmptyLines(true);
		this.tsvParser = new TsvParser(tParserSettings);
		
		
		tsvParser.beginParsing(br);
	}
	
	public String[] getNext() throws Exception{
		String[] splits;
		
		splits = tsvParser.parseNext();
		
		return splits;
	}
		
}
