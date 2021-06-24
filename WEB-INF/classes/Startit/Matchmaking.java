package Startit;
import java.util.*;
import java.util.Arrays;
import java.util.Random;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Matchmaking{
	private ArrayList<Team> complete;
	private ArrayList<Team> incomplete;
	private ArrayList<User> unassigned;
	
	
	public Matchmaking(){
		this.complete = new ArrayList<Team>();
		this.incomplete = new ArrayList<Team>();
		this.unassigned = new ArrayList<User>();
	}
	
	public Matchmaking(ArrayList<Team> complete, ArrayList<Team> incomplete, ArrayList<User> unassigned){
		this.complete = complete;
		this.incomplete = incomplete;
		this.unassigned = unassigned;
	}
	
	public ArrayList<Team> getComplete(){
		return complete;
	}
	
	public ArrayList<Team> getIncomplete(){
		return incomplete;
	}
	
	public ArrayList<User> getUnassigned(){
		return unassigned;
	}
	
	public void removeComplete(Team team){
		this.complete.remove(team);
	}
	
	public void removeIncomplete(Team team){
		this.incomplete.remove(team);
	}
	
	public Team getTeamById(int id){
		boolean toBreak = false;
		Team wanted = new Team(); 
		for (Team team: complete){
			if (team.getId() == id){
				wanted = team;
				toBreak = true;
				break;
			}
		}
		if (!toBreak){
			for (Team team: incomplete){
				if (team.getId() == id){
					wanted = team;
					break;
				}
			}
		}
		return wanted;
	}
	
	public void keepOnlyComplete(){
		unassigned = new ArrayList<User>();
		incomplete = new ArrayList<Team>();
	}
	
	public ArrayList<User> findAssigned(){
		ArrayList<User> previouslyassigned = new ArrayList<User>();
		ArrayList<Team> completeteams = getComplete();
		ArrayList<Team> incompleteteams = getIncomplete();
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
	
	public void addIncomplete(Team team){
		this.incomplete.add(team);
	}
	
	public void addUnassigned(User user){
		this.unassigned.add(user);
	}
	
	public ArrayList<User> getRemaining(){
		ArrayList<User> rem = new ArrayList<User>();
		for (Team t: incomplete){
			for (User u : t.getMembers()){
				rem.add(u);
			}
		}
		rem.addAll(unassigned);
		return rem;
	}
	
	/*---------------------- CLUSTERING -------------------------*/
	public int findUniqueUsersInClusterLevel(ArrayList<ArrayList<ArrayList<User>>> clustering){
		ArrayList<User> all = new ArrayList<User>();
		for (ArrayList<ArrayList<User>> clustercat: clustering){
			for (ArrayList<User> clust: clustercat){
				for (User us: clust){
					if (us.userNotInThere(all)){
						all.add(us);
					}
				}
			}
		}
		
		return all.size();
			
	}
	
	public ArrayList<ArrayList<ArrayList<User>>> nextLevelClusteringFromExisting(ArrayList<ArrayList<ArrayList<User>>> existing, String method){
		ArrayList<ArrayList<ArrayList<User>>> next = new ArrayList<ArrayList<ArrayList<User>>>();
		for (ArrayList<ArrayList<User>> clustercat : existing){
			for (ArrayList<User> cluster : clustercat){
				switch(method) {
					case "edu":
						next.add(edulevel_clustering(cluster));
						break;
					case "age":
						next.add(age_clustering(cluster));
						break;
					case "employment":
						next.add(employment_clustering(cluster));
						break;
					case "startup":
						next.add(startup_clustering(cluster));
						break;
					case "time management":
						next.add(time_clustering(cluster));
						break;
				}
			}
		}
		return next;
	}
	
	public static ArrayList<User> findAllUsers(ArrayList<ArrayList<User>> cat){
		ArrayList<User> all = new ArrayList<User>();
		for (ArrayList<User> clust: cat){
			for (User u: clust){
				if (u.userNotInThere(all)){
					all.add(u);
				}
			}
		}
		return all;
	}
	
	public ArrayList<ArrayList<User>> getRelaxedClusteringOnLevel(ArrayList<ArrayList<User>> cat, String attribute){
		ArrayList<ArrayList<User>> relaxed = new ArrayList<ArrayList<User>>();
		switch(attribute) {
			case "edu":
				relaxed = eduClusteringRelaxed(cat);
				break;
			case "age":
				relaxed = ageClusteringRelaxed(cat);
				break;
			case "employment":
				relaxed = employmentClusteringRelaxed(cat);
				break;
			case "startup":
				relaxed = startupClusteringRelaxed(cat);
				break;
			case "time management":
				relaxed = timeClusteringRelaxed(cat);
				break;
		}
		return relaxed;
	}
	
	public static ArrayList<ArrayList<User>> timeClusteringRelaxed(ArrayList<ArrayList<User>> cat){
		ArrayList<ArrayList<User>> time_clustering = new ArrayList<ArrayList<User>>();
		ArrayList<User> all = findAllUsers(cat);
		ArrayList<User> c_1_10 = new ArrayList<User>();
		ArrayList<User> c_4_10plus = new ArrayList<User>();
		for (User user: all){
			int time = user.getDevotion();
			if (time <= 3){
				c_1_10.add(user);
			}if (time >= 2){
				c_4_10plus.add(user);
			}
		}
		time_clustering.add(c_1_10);
		time_clustering.add(c_4_10plus);
		return time_clustering;
	}
	
	public static ArrayList<ArrayList<User>> startupClusteringRelaxed(ArrayList<ArrayList<User>> cat){
		ArrayList<ArrayList<User>> startup_clusters = new ArrayList<ArrayList<User>>();
		ArrayList<User> all = findAllUsers(cat);
		ArrayList<User> c_no_incub= new ArrayList<User>();
		ArrayList<User> c_course_early = new ArrayList<User>();
		ArrayList<User> c_comp_adv = new ArrayList<User>();
		for (User user: all){
			int startupExperience = user.getStartupExperience();
			if (startupExperience <= 4){
				c_no_incub.add(user);			
			}if (startupExperience >= 2 && startupExperience <= 5){
				c_course_early.add(user);	
			}if (startupExperience >= 3 && startupExperience <= 6){
				c_comp_adv.add(user);
			}
		}
		startup_clusters.add(c_no_incub);
		startup_clusters.add(c_course_early);
		startup_clusters.add(c_comp_adv);
		return startup_clusters;
		
	}
	
	public static ArrayList<ArrayList<User>> employmentClusteringRelaxed(ArrayList<ArrayList<User>> cat){
		ArrayList<User> all = findAllUsers(cat);
		ArrayList<ArrayList<User>> employment_clusters = new ArrayList<ArrayList<User>>(4);
		ArrayList<User> c_0_4 = new ArrayList<User>();
		ArrayList<User> c_3_10plus = new ArrayList<User>();
		for (User user: all){
			String exp_Years = user.getExpYears();
			if (exp_Years.equals("0") || exp_Years.equals("1-2") || exp_Years.equals("3-4")){
				c_0_4.add(user);
			}if (exp_Years.equals("3-4") || exp_Years.equals("5-10") || exp_Years.equals("10+")){
				c_3_10plus.add(user);
			}
			
		}
		employment_clusters.add(c_0_4);
		employment_clusters.add(c_3_10plus);	
		return employment_clusters;
	}
	
	public static ArrayList<ArrayList<User>> eduClusteringRelaxed(ArrayList<ArrayList<User>> cat){
		ArrayList<User> all = findAllUsers(cat);
		ArrayList<ArrayList<User>> edu_level_clusters = new ArrayList<ArrayList<User>>(3);
		ArrayList<User> c_bachelor_master = new ArrayList<User>();
		ArrayList<User> c_master_phd = new ArrayList<User>();
		for (User user: all){
			String level = user.getLevel();
			if (level.equals("bachelor") || level.equals("master")){
				c_bachelor_master.add(user);
			}if (level.equals("master") || level.equals("phd_cand") || level.equals("phd_hold")){
				c_master_phd.add(user);
			}
		}
		edu_level_clusters.add(c_bachelor_master);
		edu_level_clusters.add(c_master_phd);
		return edu_level_clusters;
	}
	
	public static ArrayList<ArrayList<User>> ageClusteringRelaxed(ArrayList<ArrayList<User>> cat){
		ArrayList<ArrayList<User>> age_clusters = new ArrayList<ArrayList<User>>(3);
		ArrayList<User> all = findAllUsers(cat);
		ArrayList<User> c_18_35 = new ArrayList<User>();
		ArrayList<User> c_25_over35 = new ArrayList<User>();
		for (User user: all){
			int age = user.getAge();
			if (age <= 2){
				c_18_35.add(user);
			}if (age >= 2){
				c_25_over35.add(user);
			}
		}
		age_clusters.add(c_18_35);
		age_clusters.add(c_25_over35);
		return age_clusters;
	}
	
	public static ArrayList<ArrayList<User>> age_clustering(ArrayList<User> cluster){
		ArrayList<ArrayList<User>> age_clusters = new ArrayList<ArrayList<User>>(3);
		ArrayList<User> c_18_24 = new ArrayList<User>();
		ArrayList<User> c_25_35 = new ArrayList<User>();
		ArrayList<User> c_over35 = new ArrayList<User>();
		for (User user: cluster){
			int age = user.getAge();
			if (age == 1){
				c_18_24.add(user);
			}else if (age == 2){
				c_25_35.add(user);
			}else{
				c_over35.add(user);
			}
		}
		age_clusters.add(c_18_24);
		age_clusters.add(c_25_35);
		age_clusters.add(c_over35);
		return age_clusters;
	}
	
	public static ArrayList<ArrayList<User>> edulevel_clustering(ArrayList<User> cluster){
		ArrayList<ArrayList<User>> edu_level_clusters = new ArrayList<ArrayList<User>>(3);
		ArrayList<User> c_bachelor_master = new ArrayList<User>();
		ArrayList<User> c_master_phd = new ArrayList<User>();
		ArrayList<User> c_phd = new ArrayList<User>();
		for (User user: cluster){
			String level = user.getLevel();
			if (level.equals("bachelor") || level.equals("master")){
				c_bachelor_master.add(user);
			}if (level.equals("master") || level.equals("phd_cand")){
				c_master_phd.add(user);
			}if (level.equals("phd_cand") || level.equals("phd_hold")){
				c_phd.add(user);
			}
		}
		edu_level_clusters.add(c_bachelor_master);
		edu_level_clusters.add(c_master_phd);
		edu_level_clusters.add(c_phd);	
		return edu_level_clusters;
	}
	
	public static ArrayList<ArrayList<User>> employment_clustering(ArrayList<User> cluster){
		ArrayList<ArrayList<User>> employment_clusters = new ArrayList<ArrayList<User>>(4);
		ArrayList<User> c_0_2 = new ArrayList<User>();
		ArrayList<User> c_1_4 = new ArrayList<User>();
		ArrayList<User> c_3_10 = new ArrayList<User>();
		ArrayList<User> c_5_10plus = new ArrayList<User>();
		for (User user: cluster){
			String exp_Years = user.getExpYears();
			if (exp_Years.equals("0") || exp_Years.equals("1-2")){
				c_0_2.add(user);
			}if (exp_Years.equals("1-2") || exp_Years.equals("3-4")){
				c_1_4.add(user);
			}if (exp_Years.equals("3-4") || exp_Years.equals("5-10")){
				c_3_10.add(user);
			}if (exp_Years.equals("5-10") || exp_Years.equals("10+")){
				c_5_10plus.add(user);
			}
			
		}
		employment_clusters.add(c_0_2);
		employment_clusters.add(c_1_4);
		employment_clusters.add(c_3_10);
		employment_clusters.add(c_5_10plus);	
		return employment_clusters;
	}
	
	public static ArrayList<ArrayList<User>> startup_clustering(ArrayList<User> cluster){
		ArrayList<ArrayList<User>> startup_clusters = new ArrayList<ArrayList<User>>(5);
		ArrayList<User> c_no_course = new ArrayList<User>();
		ArrayList<User> c_course_comp = new ArrayList<User>();
		ArrayList<User> c_comp_incub = new ArrayList<User>();
		ArrayList<User> c_incub_early = new ArrayList<User>();
		ArrayList<User> c_early_adv = new ArrayList<User>();
		for (User user: cluster){
			int startupExperience = user.getStartupExperience();
			if (startupExperience == 1 || startupExperience == 2){
				c_no_course.add(user);			
			}if (startupExperience == 2 || startupExperience == 3){
				c_course_comp.add(user);	
			}if (startupExperience == 3 || startupExperience == 4){
				c_comp_incub.add(user);
			}if (startupExperience == 4 || startupExperience == 5){
				c_incub_early.add(user);
			}if (startupExperience == 5 || startupExperience == 6){
				c_early_adv.add(user);
			}
		}
		startup_clusters.add(c_no_course);
		startup_clusters.add(c_course_comp);
		startup_clusters.add(c_comp_incub);
		startup_clusters.add(c_incub_early);	
		startup_clusters.add(c_early_adv);
		return startup_clusters;
	}
	
	public static ArrayList<ArrayList<User>> time_clustering(ArrayList<User> cluster){
		ArrayList<ArrayList<User>> time_management_clusters = new ArrayList<ArrayList<User>>(3);
		ArrayList<User> c_1_5 = new ArrayList<User>();
		ArrayList<User> c_4_10 = new ArrayList<User>();
		ArrayList<User> c_5_10plus = new ArrayList<User>();
		for (User user: cluster){
			int time = user.getDevotion();
			if (time == 1 || time == 2){
				c_1_5.add(user);
			}if (time == 2 || time == 3){
				c_4_10.add(user);
			}if (time == 3 || time == 4){
				c_5_10plus.add(user);
			}
		}
		time_management_clusters.add(c_1_5);
		time_management_clusters.add(c_4_10);
		time_management_clusters.add(c_5_10plus);	
		return time_management_clusters;
	}
	
	
	/*---------------------- MATCHMAKING -------------------------*/
	
	public static ArrayList<Matchmaking> in_cluster_team_formation(boolean fillComplete, String method, ArrayList<User> cluster, ArrayList<Matchmaking> levelResults, 
												ArrayList<Team> previousincomplete, ArrayList<Team> previouscomplete, ArrayList<User> previouslyassigned, int maxmemb, int minmemb, double mindiv){
		ArrayList<Team> complete = new ArrayList<Team>(); 
		ArrayList<User> remaining = new ArrayList<User>();
		ArrayList<User> available = new ArrayList<User>();
		ArrayList<Team> incomplete = new ArrayList<Team>();
		ArrayList<User> unassigned = new ArrayList<User>();
		ArrayList<Team> levelcomplete = findPreviousComplete(levelResults);
		ArrayList<Team> levelincomplete = findPreviousIncomplete(levelResults);
		Team prevteam = new Team();
		int actualprevteam = 0;
		String alterMethod = "";
		
		//System.out.println("ON THIS LEVEL THERE ARE SO FAR " + levelcomplete.size() + " COMPLETE TEAMS");
		//System.out.println("ON THIS LEVEL THERE ARE SO FAR " + levelincomplete.size() + " INCOMPLETE TEAMS");
		
		
		boolean tryingToFillPreviouslyIncomplete = false;
		boolean clusterNotExhausted = true;
		
		for (User u: cluster){
			if (u.userNotInThere(previouslyassigned)){
				available.add(u);
				remaining.add(u);
			}
		}
		
		//System.out.println("THERE ARE " +available.size()+ " AVAILABLE USERS FROM " +cluster.size()+ " IN CLUSTER");
		
		boolean shouldBreak = false;
		boolean added = true;
		while (!available.isEmpty()){
			ArrayList<User> teamMembers = new ArrayList<User>();
			Team team = new Team();
			User best = new User();
			if (!previousincomplete.isEmpty()){
				//System.out.println("I TRYNNA FILL PREVIOUSLY INCOMPLETE TEAM!");
				team = previousincomplete.get(0);
				previousincomplete.remove(team);
			}else if (fillComplete && !previouscomplete.isEmpty()){
				team = previouscomplete.get(0);
				previouscomplete.remove(team);
			}else if (!remaining.isEmpty()){
				best = remaining.get(0);
				//System.out.println("Tried starting with user: " + best.getId());

				while (!covered(best, levelcomplete, levelincomplete).equals("No")){
					//System.out.println("User " +best.getId() + " is elsewhere so no selected!");
					remaining.remove(best);
					if (!remaining.isEmpty()){
						best = remaining.get(0);
						//System.out.println("Tried starting with user: " + best.getId());

					}else{
						shouldBreak = true;
						break;
					}
				}
				if (shouldBreak){
					shouldBreak = false;
					break;
				}
				available.remove(best);
				teamMembers.add(best);
				remaining.remove(best);
				//System.out.println("Started with user: " + best.getId());
				team = new Team(teamMembers);
			}else{
				break;
			}
			
			while (!available.isEmpty() && !team.teamComplete(maxmemb, minmemb, mindiv) && added){
				levelcomplete = findPreviousComplete(levelResults);
				levelincomplete = findPreviousIncomplete(levelResults);
				double maxdiv = 0;
				if (method.equals("best-fit")){
					maxdiv = team.getDiversity();
				}
				added = false;
				teamMembers = team.getMembers();
				boolean shouldAlter = false;
				
				for (User user: available){
					ArrayList<User> newTeamMembers = (ArrayList<User>)teamMembers.clone();
					newTeamMembers.add(user);
					Team newteam = new Team(newTeamMembers);
					//System.out.println("NEW TEAM HAS " + newteam.getMembers().size() + " USERS");
					//System.out.println("UNDER FORMATION TEAM HAS " + team.getMembers().size() + " USERS");						
					if (newteam.getDiversity() > maxdiv){
						String belongsElsewhere = covered(user, levelcomplete, levelincomplete);
						if (belongsElsewhere.equals("No")){
							shouldAlter = false;
							added = true;
							best = user;
							maxdiv = newteam.getDiversity();
						}else {
							//System.out.println(belongsElsewhere);
							prevteam = findHisTeam(user, levelcomplete, levelincomplete);
							//System.out.println("Belongs elsewhere that mf*cker!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! User: " + user.getId() + " in  team: " + prevteam.getId());
							if (userShouldChangeTeam(user, prevteam, newteam, belongsElsewhere, minmemb, maxmemb , mindiv)){
								//System.out.println("COULD CHANGE team!");
								actualprevteam = prevteam.getId();
								alterMethod = belongsElsewhere;
								shouldAlter = true;
								added = true;
								best = user;
								maxdiv = newteam.getDiversity();
							}
						}	
					}
				}
				if (added){
					if (shouldAlter){
						//System.out.println("User "+ best.getId() + " changed team!");
						levelResults = alterPrevTeamOfUser(actualprevteam, best, levelResults, alterMethod);
					}
					teamMembers.add(best);
					team = new Team(teamMembers);
					available.remove(best);
					remaining.remove(best);
					//System.out.println("Added user: " + best.getId());
				}
				
			}
			if (team.teamComplete(maxmemb, minmemb, mindiv)){
				complete.add(team);
				//System.out.println("COMPLETE team "+ team.getId() +"  formed with " + team.getMembers().size() + " members and " + team.getDiversity() + " div");
			}else if(fillComplete && remaining.isEmpty() && team.getMembers().size() > 3){
				complete.add(team);
			}else if (team.getMembers().size() > 1){
				incomplete.add(team);
				if(available.size() > 0){
					//System.out.println(available.size());
					//System.out.println(available.get(0).getId());
				}
				//System.out.println("INCOMPLETE team "+ team.getId() +" formed with " + team.getMembers().size() + " members and " + team.getDiversity() + " div");
			}else if (team.getMembers().size() == 1){
				//System.out.println("USER " +team.getMembers().get(0).getId() + " got back available!");
				available.add(team.getMembers().get(0));
			}
				
		}
		for (User u: available){
			unassigned.add(u);
		}
		
		for (Team t : previousincomplete){
			incomplete.add(t);
			//System.out.println("at the end INCOMPLETE team "+ t.getId() +" formed with " + t.getMembers().size() + " members and " + t.getDiversity() + " div");
		}
		
		for (Team t: previouscomplete){
			complete.add(t);
		}
		
		//System.out.println(unassigned.size() + "  UNASSIGNED USERS");
		
		Matchmaking mm = new Matchmaking(complete, incomplete, unassigned);
		
		levelResults.add(mm);
		
		return levelResults;
	}
	
	public static ArrayList<Matchmaking> alterPrevTeamOfUser(int prevteamID, User best, ArrayList<Matchmaking> previousresults, String alterMethod){
		
		boolean toBreak = false;
		for (Matchmaking mm: previousresults){
			if (alterMethod.equals("Complete")){
				for (Team team : mm.getComplete()){	
					int teamID = team.getId();
					if (teamID == prevteamID){
						//System.out.println("HE GOT CHEFFFED!------------------------------------- "+ best.getId());
						//System.out.println("There were " + mm.getComplete().size() + " complete teams and " + mm.getIncomplete().size() + " incomplete teams!");
						//System.out.println("Complete team " +teamID + "  had " + mm.getTeamById(teamID).getMembers().size() + " members!" );
						team.removeMember(best);
						//System.out.println("Now it has " + mm.getTeamById(teamID).getMembers().size() + "!" );
						mm.removeComplete(team);
						mm.addIncomplete(team);
						//System.out.println("Now there are "+ mm.getComplete().size() + " complete teams and " + mm.getIncomplete().size() + " incomplete!");
						mm.addUnassigned(best);
						toBreak = true;
						break;
					}
				}
			}else{
				for (Team team : mm.getIncomplete()){	
					int teamID = team.getId();
					if (teamID == prevteamID){
						//System.out.println("HE GOT CHEFFFED!------------------------------------- "+ best.getId());
						//System.out.println("Incomplete team " +prevteamID + " had " + mm.getTeamById(teamID).getMembers().size() + " members!" );
						team.removeMember(best);
						//System.out.println("Team it has " + mm.getTeamById(teamID).getMembers().size() + "!" );
						mm.addUnassigned(best);
						if ((team.getMembers()).size() == 1){
							//System.out.println("There were " + mm.getIncomplete().size() + " incomplete teams!");
							mm.removeIncomplete(team);
							//System.out.println("Now there are " + mm.getIncomplete().size() + "!");
							mm.addUnassigned(team.getMembers().get(0));
						}
						toBreak = true;
						break;
					}
				}
			}
			if (toBreak){
				break;
			}
		}
		return previousresults;
	}
	
	public static boolean userShouldChangeTeam(User user, Team prevteam, Team newteam, String belongsElsewhere, 
										int minmemb, int maxmemb, double mindiv){
		ArrayList<User> prevmembers = (ArrayList<User>)prevteam.getMembers().clone();
		ArrayList<User> newmembers = (ArrayList<User>)newteam.getMembers().clone();
		double prevdiv = prevteam.getDiversity();
		double newdiv = newteam.getDiversity();
		prevmembers.remove(user);
		newmembers.remove(user);
		Team prevteamWithout = new Team(prevmembers);
		Team newteamWithout = new Team(newmembers);
		double prevdivWithout = prevteamWithout.getDiversity();
		double newdivWithout = newteamWithout.getDiversity();
		int prevsize = prevmembers.size();
		int newsize = newmembers.size();
		//System.out.println(newdiv+ "  " + prevdiv);
		if (newsize >= prevsize){
		
			if ((newdiv + prevdivWithout)/2 > (newdivWithout + prevdiv)/2){
				if (belongsElsewhere.equals("Complete")){
					if (newteam.teamComplete(maxmemb, minmemb, mindiv)){
						return true;
					}else{ 
						return false;
					}
				}else{ 
					return true;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}
		
	public static String covered(User user, ArrayList<Team> complete, ArrayList<Team> incomplete){
		String where = "No";
		for (Team team: complete){
			ArrayList<User> membs = team.getMembers();
			if (!user.userNotInThere(membs)){
					//System.out.println(user.getId()+ " " + member.getId());
				where = "Complete";
				break;
			}
		}
		
		if (where.equals("No")){
			for (Team team: incomplete){
				ArrayList<User> membs = team.getMembers();
				if (!user.userNotInThere(membs)){
					where = "Incomplete";
					break;
				}
			}
		}
		return where;
	}
	
	public static Team findHisTeam(User user, ArrayList<Team> complete, ArrayList<Team> incomplete){
		Team rteam = new Team();
		String where = "No";
		for (Team team: complete){
			ArrayList<User> membs = (ArrayList<User>)team.getMembers().clone();
			if (!user.userNotInThere(membs)){
					//System.out.println(user.getId()+ " " + member.getId());
				where = "Complete";
				rteam = team;
				break;
			}
		}
		
		if (where.equals("No")){
			for (Team team: incomplete){
				ArrayList<User> membs = (ArrayList<User>)team.getMembers().clone();
				if (!user.userNotInThere(membs)){
					where = "Incomplete";
					rteam = team;
					break;
				}
			}
		}
		return rteam;
	}
	
	public static ArrayList<Team> findPreviousComplete(ArrayList<Matchmaking> previous){
		ArrayList<Team> previous_complete = new ArrayList<Team>();
		for (Matchmaking result: previous){
			ArrayList<Team> complete = (ArrayList<Team>)result.getComplete().clone();
			for (Team team: complete){
				previous_complete.add(team);
			}
		}
		return previous_complete;
	}
	
	public static ArrayList<Team> findPreviousIncomplete(ArrayList<Matchmaking> previous){
		ArrayList<Team> previous_incomplete = new ArrayList<Team>();
		for (Matchmaking result: previous){
			ArrayList<Team> incomplete = (ArrayList<Team>)result.getIncomplete().clone();
			for (Team team: incomplete){
				//System.out.println(team.getId());
				previous_incomplete.add(team);
			}
		}
		return previous_incomplete;
	}
	
	public Matchmaking mergeIncompleteTeams(Matchmaking prevr, int maxmemb, int minmemb, double sufficient, String method){
		ArrayList<User> unassigned = prevr.getUnassigned();
		ArrayList<Team> incomplete= prevr.getIncomplete();
		ArrayList<Team> complete = prevr.getComplete();
		//System.out.println("THERE WERE " + prevr.countUsersInMatchmaking() + " USERS!");
		ArrayList<Team> available = (ArrayList<Team>)incomplete.clone();
		ArrayList<Team> remaining = (ArrayList<Team>)incomplete.clone();
		while (!remaining.isEmpty()){
			Team team = remaining.get(0);
			incomplete.remove(team);
			Team mergedTeam = new Team();
			remaining.remove(team);
			available.remove(team);
			boolean added = true;
			while (added && !team.teamComplete(maxmemb, minmemb, sufficient) && !available.isEmpty()){
				added = false;
				double maxdiv = 0;
				if (method.equals("best-fit")){
					maxdiv = team.getDiversity();
				}
				for (Team teamToAdd: available){
					Team newteam = mergeTeams(team, teamToAdd);
					double newdiv = newteam.getDiversity();
					if (newdiv > maxdiv && newteam.getMembers().size() <= maxmemb){
						added = true;
						maxdiv = newdiv;
						mergedTeam = teamToAdd;
					}
				}
				if (added){
					available.remove(team);
					team = mergeTeams(team, mergedTeam);
					available.remove(mergedTeam);
					incomplete.remove(mergedTeam);
					remaining.remove(mergedTeam);
				}
			}
			if (team.teamComplete(maxmemb, minmemb, sufficient)){
				complete.add(team);
			}else{
				incomplete.add(team);
			}
		}
		Matchmaking toReturn = new Matchmaking(complete, incomplete, unassigned);
		//System.out.println("NOW THERE ARE " + toReturn.countUsersInMatchmaking() + " USERS!");		
		return toReturn;
	}
	
	
	public Team mergeTeams(Team team1, Team team2){
		ArrayList<User> newTeamMembers = new ArrayList<User>();
		ArrayList<User> team1Members = team1.getMembers();
		ArrayList<User> team2Members = team2.getMembers();
		for (User user: team1Members){
			newTeamMembers.add(user);
		}
		for (User user: team2Members){
			newTeamMembers.add(user);
		}
		Team newTeam = new Team(newTeamMembers);
		return newTeam;
	}
	
	public int countUsersInMatchmaking(){
		ArrayList<User> unassigned = getUnassigned();
		ArrayList<Team> incomplete= getIncomplete();
		ArrayList<Team> complete = getComplete();
		int uss = 0;
		for (Team t: complete){
			uss += t.getMembers().size();
		}
		for (Team t: incomplete){
			uss += t.getMembers().size();
		}
		for (User u: unassigned){
			uss+= 1;
		}
 		return uss;	
	}
	
	public void ChangeAssignedStatus() throws Exception{
		ArrayList<Team> complete = this.complete;
		Database db = new Database();
		Connection con = db.getConnection();
		try{
			for (Team t: complete){
				for (User u: t.getMembers()){
					int id = u.getId();
					DAO dao = new DAO();
					dao.changeUserStatus(id, true,con);
				}
			}
		}finally{}
	}
	
	public void CreateTeams() throws Exception{
		DAO dao = new DAO();
		Database db = new Database();
		Connection con = db.getConnection();
		try{
			for (Team team: complete){
				dao.createTeam(team,con);
			}
		}finally{}
	}
}
	