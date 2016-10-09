package amol.dm;

import java.util.AbstractMap.SimpleEntry;

import org.biojava.nbio.core.sequence.DNASequence;


public class Translator {
	// used to get protein sequences from bases
	
	public static void main(String args[]) throws Exception{
		String name2 = "/home/amol/Documents/Data Mining/Project/Data/COSMIC All/All_COSMIC_Genes.fasta";
		
		FastaFile fastaFile = new FastaFile(name2);
		convertDNAToProtein(fastaFile);
		
	}
	
	public static void convertDNAToProtein(FastaFile file) throws Exception{
		SimpleEntry<String, String> entry;
		
		while((entry = file.getNext()) != null){
			System.out.println(
					">" +
					entry.getKey() +
					"\n" +
					new DNASequence(entry.getValue()).getRNASequence().getProteinSequence().toString()
			);
			
			System.out.println();
		}
	}
	
}
