package amol.dm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import amol.dm.SubstitutionData.Mutation;

public class Main {

	public static void main(String[] args) throws Exception{
		//String name1 = "/home/amol/Documents/Data Mining/Project/Data/COSMIC All/All_COSMIC_Genes.fasta";
		String name2 = "/home/amol/Documents/Data Mining/Project/Data/COSMIC All/All_COSMIC_Proteins.fasta";
		//String name3 = "/home/amol/Documents/Data Mining/Project/Data/SomaticMutations/neutral.fa";
		//String name4 = "/home/amol/Documents/Data Mining/Project/Data/SomaticMutations/neutral.sub";
		
		// -> The fasta file containing the mutations
		FastaFile fastaFile = new FastaFile(name2);
		
		// Uncomment the below block while working with the neutral data
		/*
		Substitutions substitutions = new Substitutions(name4);
		processFromFasta(fastaFile, substitutions);
		*/
		
		
		// Uncomment the below block while working with the cosmic data
		/*
		LargeSubstitutions cFile = new LargeSubstitutions("/home/amol/Documents/Data Mining/Project/Data/Cosmic Genome Screens/CosmicGenomeScreensMutantExport.tsv");
		process(loadFasta(fastaFile), cFile);
		*/
		
	}
	
	static void process(HashMap<String, String> proteinSequences, LargeSubstitutions substitutionDetails) throws Exception{
		// used for the cosmic dataset
		
		String[] splits;
		long mutationNumber = 0;
		
		HashSet<String> visited = new HashSet<>();
		
		long missingMutationsCount = 0;				// tsv didn't have a mutation mentioned
		long sequencesNotFoundCount = 0;			// gene sequence was not present in dataset
		long duplicateMutationsCount = 0;			// same mutation mentioned again
		long inconsistentMutationFormatCount = 0;	// those with wildcards or multiple mutations
		long notValidMutationInfoCount = 0;			// if the position exceeds the sequence length of if the antecedents differ
		long totalRecordsCount = 0; 				// total number of mutations encountered (including all above)
		
		while( (splits = substitutionDetails.getNext()) != null){
			totalRecordsCount++;
			
			String sequence = proteinSequences.get(splits[0] + " " + splits[1]);
			if(sequence == null){sequencesNotFoundCount++; continue;}
			if(visited.contains(splits[16])){
				duplicateMutationsCount++;
				continue;
			}
			else{
				visited.add(splits[16]);
			}
			
			if(splits[18] != null){
				if(splits[18].matches("p[\\.][A-Z][0-9]+[A-Z]")){
					char antecedent = splits[18].charAt(2);
					char consequent = splits[18].charAt(splits[18].length()-1);
					Integer position = Integer.parseInt(splits[18].substring(3, splits[18].length()-1));
					
					if(position > sequence.length()){
						notValidMutationInfoCount++;
						continue;
					}
					if(sequence.charAt(position-1) != antecedent){
						notValidMutationInfoCount++;
						continue;
					}
					
					System.out.print(
							++mutationNumber + " " +
							splits[0] + "\t" + splits[1] + " " +
							//sequence + " " +
							position + " " +
							antecedent + " " +
							consequent + " "
							);
					for(int windowSize : new int[]{5, 7, 9}){
						HashMap<Character, Integer> counts = FeatureExtraction.getResidues(sequence, position-1, windowSize);
						System.out.print(getEntropy(counts, windowSize) + " ");
					}
					System.out.println();
			    }
				else{
					inconsistentMutationFormatCount++;
				}
			}		
			else{
				missingMutationsCount++;
			}
		}
		
		/*
		System.out.println("Number of missing   mutations: " + missingMutationsCount);
		System.out.println("Number of missing   sequences: " + sequencesNotFoundCount);
		System.out.println("Number of duplicate mutations: " + duplicateMutationsCount);
		System.out.println("Number of inconsistent  descs: " + inconsistentMutationFormatCount);
		System.out.println("Number of invalid   mutations: " + notValidMutationInfoCount);
		System.out.println("Number of valid     mutations: " + mutationNumber);
		System.out.println("Number of total     mutations: " + totalRecordsCount);
		*/

	}

	public static HashMap<String, String> loadFasta(FastaFile fastaFile) throws Exception{
		// returns a hashmap with the contents of the fasta file
		
		SimpleEntry<String, String> entry;
		HashMap<String, String> all = new HashMap<>();
		
		while((entry = fastaFile.getNext()) != null){
			all.put(entry.getKey(), entry.getValue());
		}
		
		for(Map.Entry<String, String> aRecord : all.entrySet()){
			//System.out.println(aRecord.getKey() + " " + aRecord.getValue());
		}
		
		return all;
		
	}
	
	public static double getEntropy(HashMap<Character, Integer> counts, int windowSize) throws Exception{
		// calculates the entropy
		
		double entropy = 0.0;
		
		for (char c : new char[]{'F','L','I','M','V','S','P','T','A','Y','H','Q','N','K','D','E','C','W','R','G'}) {
			if(counts.containsKey(c)){
				double d = ((double) counts.get(c))/windowSize;
				d *= Math.log(((double) counts.get(c))/windowSize);
				d /= Math.log(2);
				entropy -= d;
			}
		}
		
		return entropy;
	}
	
	static void processFromFasta(FastaFile file, Substitutions substitutions) throws Exception{
		// used for the neutral mutation set
		
		SimpleEntry<String, String> entry;
		
		while((entry = file.getNext()) != null){
			String seqID = entry.getKey();
			
			if(substitutions.getSubstitutions().containsKey(seqID)){
				for(Mutation mutation : substitutions.getSubstitutions().get(seqID).getMutations()){
					System.out.print(
							entry.getKey() + " " +
							entry.getValue() + " " +
							(entry.getValue().length()) + " " +
							mutation.getPosition() + " " +
							mutation.getAntecedent() + " " +
							mutation.getConsequent() + " "
							);
					FeatureExtraction.getCounts(entry.getValue());
					
					for(int windowSize : new int[]{5, 7, 9}){
						HashMap<Character, Integer> counts = FeatureExtraction.getResidues(entry.getValue(), mutation.getPosition()-1, windowSize);
						System.out.print(getEntropy(counts, windowSize) + " ");
					}
					System.out.println();
					
				}
			}
			
		}
		
	}
		
	public static void showRecords(FastaFile file) throws Exception{
		// displays the name and the sequence, used only for debugging purposes
		
		long i = 0;
		SimpleEntry<String, String> entry;
		
		while( (entry = file.getNext()) != null){	
			++i;
			System.out.println("Record number: " + i);
			System.out.println(entry.getKey());
			System.out.println(entry.getValue() + "\n");
		}
		
		System.out.println(i + " records read from file.");
		
	}
	
}
