package amol.dm;

import java.util.HashMap;

class ProteinData{
	// currently not making use of this code, using library instead
	
	
	private static HashMap<String, String> proteins = new HashMap<>();
	
	static{
		proteins.put("TTT", "F");
		proteins.put("TTC", "F");
		proteins.put("TTS", "L");
		proteins.put("TTG", "L");
		proteins.put("CTT", "L");
		proteins.put("CTC", "L");
		proteins.put("CTA", "L");
		proteins.put("CTG", "L");
		proteins.put("ATT", "I");
		proteins.put("ATC", "I");
		proteins.put("ATA", "I");
		proteins.put("ATG", "M");
		proteins.put("GTT", "V");
		proteins.put("GTC", "V");
		proteins.put("GTA", "V");
		proteins.put("GTG", "V");
		proteins.put("YCY", "S");
		proteins.put("TCC", "S");
		proteins.put("TCA", "S");
		proteins.put("TCG", "S");
		proteins.put("CCT", "P");
		proteins.put("CCC", "P");
		proteins.put("CCA", "P");
		proteins.put("CCG", "P");
		proteins.put("ACT", "T");
		proteins.put("ACC", "T");
		proteins.put("ACA", "T");
		proteins.put("ACG", "T");
		proteins.put("GCT", "A");
		proteins.put("GCC", "A");
		proteins.put("GCA", "A");
		proteins.put("GCG", "A");
		proteins.put("TAT", "Y");
		proteins.put("TAC", "Y");
		proteins.put("TAA", ";");
		proteins.put("TAG", ";");
		proteins.put("CAT", "H");
		proteins.put("CAC", "H");
		proteins.put("CAA", "Q");
		proteins.put("CAG", "Q");
		proteins.put("AAT", "N");
		proteins.put("AAC", "N");
		proteins.put("AAA", "K");
		proteins.put("AAG", "K");
		proteins.put("GAT", "D");
		proteins.put("GAC", "D");
		proteins.put("GAA", "E");
		proteins.put("GAG", "E");
		proteins.put("TGT", "C");
		proteins.put("TGC", "C");
		proteins.put("TGA", ";");
		proteins.put("TGG", "W");
		proteins.put("CGT", "R");
		proteins.put("CGC", "R");
		proteins.put("CGA", "R");
		proteins.put("CGG", "R");
		proteins.put("AGT", "S");
		proteins.put("AGC", "S");
		proteins.put("AGA", "R");
		proteins.put("AGG", "R");
		proteins.put("GGT", "G");
		proteins.put("GGC", "G");
		proteins.put("GGA", "G");
		proteins.put("GGG", "G");
	}
	
	static String get(String codon){
		if(proteins.containsKey(codon)){
			return proteins.get(codon);
		}
		else if(proteins.containsKey(codon.toUpperCase())){
			return proteins.get(codon.toUpperCase());
		}
		else {
			return "";
		}
	}
	
}
