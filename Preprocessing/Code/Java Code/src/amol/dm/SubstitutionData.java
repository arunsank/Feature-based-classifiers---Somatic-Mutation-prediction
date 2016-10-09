package amol.dm;

import java.util.ArrayList;

public class SubstitutionData {
	// contains all possible mutations for a given protein sequence
		
	private String sequenceName;
	private ArrayList<Mutation> mutations;
	
	class Mutation{
		// contains information about a mutation
		
		private int position;
		private String antecedent;
		private String consequent;
		
		public int getPosition() {
			return position;
		}
		public String getAntecedent() {
			return antecedent;
		}
		public String getConsequent() {
			return consequent;
		}
		public Mutation(int position, String antecedent, String consequent) {
			this.position = position;
			this.antecedent = antecedent;
			this.consequent = consequent;
		}		
	}
	
	public SubstitutionData(String sequenceName){
		this.sequenceName = sequenceName;
		mutations = new ArrayList<>();
		//System.out.println("Created: " + sequenceName);
	}
	
	public void insertMutation(int position, String antecedent, String consequent){
		this.mutations.add(new Mutation(position, antecedent, consequent));
		//System.out.println("Added " + position + " to " + sequenceName);
	}
	
	public ArrayList<Mutation> getMutations(){
		return this.mutations;
	}
	
	public String getName(){
		return this.sequenceName;
	}
}
