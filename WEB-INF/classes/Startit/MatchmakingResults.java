package Startit;
import java.util.*;


public class MatchmakingResults{

	private ArrayList<Matchmaking> results = new ArrayList<Matchmaking>();
	private String basedOn;
	private int level;
	private Matchmaking aggregateResults;
	
	public MatchmakingResults(){
		this.aggregateResults = new Matchmaking();
	}
	
	public MatchmakingResults(ArrayList<Matchmaking> results, String basedOn, int level){
		this.results = results;
		this.basedOn = basedOn;
		this.level = level;
		this.aggregateResults = getLevelResults();
	}
	
	public ArrayList<Matchmaking> getResults(){
		return results;
	}
	
	public String getBasedOn(){
		return basedOn;
	}
	
	public int getLevel(){
		return level;
	}
	
	public Matchmaking getAggregateResults(){
		return aggregateResults;
	}		
	
	public Matchmaking getLevelResults(){
		ArrayList<Team> completeteams = new ArrayList<Team>();
		ArrayList<Team> incompleteteams = new ArrayList<Team>();
		ArrayList<User> unassigned = new ArrayList<User>();
		for (Matchmaking much: results){
			for (Team team: much.getComplete()){
				if (team.teamNotInThere(completeteams)){
					completeteams.add(team);
				}
			}
			for (Team team: much.getIncomplete()){
				if (team.teamNotInThere(incompleteteams)){
					incompleteteams.add(team);
				}
			}
		}
		for (Matchmaking much: results){		
			for (User us : much.getUnassigned()){
				if (much.covered(us, completeteams, incompleteteams).equals("No")){
					if (us.userNotInThere(unassigned)){
						unassigned.add(us);
					}
				}
			}
		}
		Matchmaking toReturn = new Matchmaking(completeteams, incompleteteams, unassigned);
		return toReturn;
	}
	
	public ArrayList<User> findPreviouslyAssigned(){
		Matchmaking omm = getAggregateResults();
		ArrayList<User> previouslyassigned = new ArrayList<User>();
		ArrayList<Team> completeteams = omm.getComplete();
		ArrayList<Team> incompleteteams = omm.getIncomplete();
		for (Team t: completeteams){
			for (User u : t.getMembers()){
				previouslyassigned.add(u);
			}
		}
		for (Team t: incompleteteams){
			for (User u : t.getMembers()){
				previouslyassigned.add(u);
			}
		}
		return previouslyassigned;
	}
				
					
}