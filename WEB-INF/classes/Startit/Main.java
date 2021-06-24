package Startit;

import java.util.*; // import the ArrayList class
import java.lang.Math.*;
import java.util.concurrent.atomic.AtomicInteger;
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


public class Main{
	public static void runMatchmaking() throws Exception{
		
		DAO dao = new DAO();
		Database dbobj = new Database();
		Connection con = dbobj.getConnection();
		try{
		
		Matchmaking mm = new Matchmaking();
		ArrayList<User> userz = dao.getUnassignedUsers(con);
		ArrayList<ArrayList<ArrayList<ArrayList<User>>>> ela = orderedClustering(userz, "age", "edu", "employment", "time management", "startup");
		String[] orderOfClusterRelaxation  = {"startup", "time management", "employment", "edu", "age"};
		Matchmaking pxoios = createTeamsAlongLevels(ela, orderOfClusterRelaxation);
		/***ArrayList<Matchmaking> pouArr = new ArrayList<Matchmaking>();
		pouArr = pou.in_cluster_team_formation(false, "minimum damage", pou.getRemaining(), pouArr, new ArrayList<Team>(), 
					new ArrayList<Team>(), new ArrayList<User>(), 4, 6, 40);
		pou.keepOnlyComplete();
		pouArr.add(pou);
		MatchmakingResults ei = new MatchmakingResults(pouArr, "", 0);
		pou = ei.getAggregateResults(); ***/
		
		
		pxoios.ChangeAssignedStatus();
		pxoios.CreateTeams();
		}finally{}

	}
	
	public static ArrayList<User> randomUserGenerator(int no){
		User generator = new User();
		User[] users= new User[no]; 
		int id = 0;
		
		DAO dao = new DAO();
		for(int i = 0 ; i < users.length;i++){
			User randuser = generator.generateRandomUser();
			users[i] = randuser;
			
			try{
				Database dbobj = new Database();

				Connection con = dbobj.getConnection();
				dao.createUser(randuser,con);
				id = randuser.getIdFromDB(con);
			}catch (Exception e){
				e.printStackTrace();
			}
			
			randuser.setId(id);
		}
		ArrayList<User> userz = new ArrayList<>(Arrays.asList(users));
		return userz;
	}
			
	public static Matchmaking createTeamsAlongLevels(ArrayList<ArrayList<ArrayList<ArrayList<User>>>> orderedClustering , String[] orderOfClusterRelaxation){
		String basedOn  = "";
		ArrayList<User> previouslyassigned = new ArrayList<User>();
		ArrayList<MatchmakingResults> prevresults = new ArrayList<MatchmakingResults>();
		Matchmaking mm = new Matchmaking();
		MatchmakingResults overallLevelResults = new MatchmakingResults();
		int level = orderedClustering.size() - 1;
		String method = "";
		ArrayList<Matchmaking> thislevelResults = new ArrayList<Matchmaking>();
		ArrayList<Team> incompletepassed = new ArrayList<Team>();
		ArrayList<Team> completepassed = new ArrayList<Team>();
		int rc = 0;
		int maxmemb = 5;
		int minmemb = 4;
		boolean fillComplete = false;
		boolean returning = false;
		int returns = 0;
		int relax = 3;
		while ((!allTeamsComplete(overallLevelResults.getAggregateResults())||returning) && level >= relax){
			int counterOfPain = 0;
			ArrayList<ArrayList<ArrayList<User>>> clusteringLevel = orderedClustering.get(rc);
			if (level != 0){ 
				basedOn = orderOfClusterRelaxation[rc];
			}else{
				basedOn = "ALL";
			}
			int i = 0;
			System.out.println("WE ARE IN LEVEL: " +level);
			if (!returning){
				thislevelResults = new ArrayList<Matchmaking>();
			}
			method = "best-fit";
			if (level == 0){
				fillComplete = true;
				method = "minimum damage";
			}
			for (ArrayList<ArrayList<User>> clustercat : clusteringLevel){
				//System.out.println("---------------------CLUSTER CATEGORY-------------------------------------------------------------------------");
				for (ArrayList<User> cluster : clustercat){
					//System.out.println("---------------------CLUSTER CLUSTER CLUSTER CLUSTER CLUSTER CLUSTER CLUSTER CLUSTER---------------");
					if (!prevresults.isEmpty()){
						Matchmaking prevr = prevresults.get(i).getAggregateResults();
						prevr = mm.mergeIncompleteTeams(prevr, maxmemb, minmemb, 70-(level*5), method);
						incompletepassed = prevr.getIncomplete();
						completepassed = prevr.getComplete();
						
					}
					thislevelResults = mm.in_cluster_team_formation(fillComplete, method, cluster, thislevelResults, incompletepassed, 
					completepassed, previouslyassigned, maxmemb, minmemb, 70-(level*5));
					counterOfPain++;
					i++;	
				}
			}
			i = 0;
			overallLevelResults = new MatchmakingResults(thislevelResults, basedOn, level);
			checkResults(overallLevelResults.getAggregateResults(), clusteringLevel);
			method = "minimum damage";
			System.out.println("LEVEL RESULTS OF SIZE " + thislevelResults.size());
			if (!allTeamsComplete(overallLevelResults.getAggregateResults())){
				for (ArrayList<ArrayList<User>> clustercat : clusteringLevel){
					//System.out.println("---------------------CLUSTER CATEGORY-------------------------------------------------------------------------");
					
					//System.out.println("------------MINIMUM DAMAGE---------");
					for (ArrayList<User> cluster: clustercat){
						//System.out.println("INCOMPLETE TEAMS IN CLUSTER: " + thislevelResults.get(i).getIncomplete().size());
						//System.out.println("COMPLETE TEAMS IN CLUSTER: " + thislevelResults.get(i).getComplete().size());
						Matchmaking merged = thislevelResults.get(i);
						merged = mm.mergeIncompleteTeams(thislevelResults.get(i), maxmemb, minmemb, 45, method);
						thislevelResults.set(i, merged);
						//System.out.println("MERGED INCOMPLETE TEAMS IN CLUSTER: " + thislevelResults.get(i).getIncomplete().size());
						//System.out.println("MERGED COMPLETE TEAMS IN CLUSTER: " + thislevelResults.get(i).getComplete().size());
						thislevelResults = mm.in_cluster_team_formation(fillComplete, method, cluster, thislevelResults, new ArrayList<Team>(),
												new ArrayList<Team>(), new ArrayList<User>(), maxmemb, minmemb, 45);
						i++;
						counterOfPain++;
					}
				}
			}else{
				break;
			}
			overallLevelResults = new MatchmakingResults(thislevelResults, basedOn, level);
			checkResults(overallLevelResults.getAggregateResults(), clusteringLevel);
			System.out.println("LEVEL RESULTS OF SIZE " + thislevelResults.size());
			
			/***if (level != 0){ 

				for (ArrayList<ArrayList<User>> clustercat : clusteringLevel){
					//System.out.println("---------------------CLUSTER CATEGORY-------------------------------------------------------------------------");
					ArrayList<ArrayList<User>> relaxedClusters = mm.getRelaxedClusteringOnLevel(clustercat, basedOn);
					method = "best-fit";
					if (!allTeamsComplete(overallLevelResults.getAggregateResults())){
						//System.out.println("------BEST - FIT RELAXED------");
						for (ArrayList<User> cluster: relaxedClusters){
							thislevelResults = mm.in_cluster_team_formation(fillComplete, method, cluster, thislevelResults, new ArrayList<Team>(),
												new ArrayList<Team>(), new ArrayList<User>(), maxmemb, minmemb, 70 - (level*5));
						}
					}
				}
				overallLevelResults = new MatchmakingResults(thislevelResults, basedOn, level);
				checkResults(overallLevelResults.getAggregateResults(), clusteringLevel);
				for (ArrayList<ArrayList<User>> clustercat : clusteringLevel){
					//System.out.println("---------------------CLUSTER CATEGORY-------------------------------------------------------------------------");
					ArrayList<ArrayList<User>> relaxedClusters = mm.getRelaxedClusteringOnLevel(clustercat, basedOn);
					method = "minimum-damage";
					if (!allTeamsComplete(overallLevelResults.getAggregateResults())){
						//System.out.println("------MINIMUM DAMAGE RELAXED------");

						for (ArrayList<User> cluster: relaxedClusters){
							thislevelResults = mm.in_cluster_team_formation(fillComplete, method, cluster, thislevelResults, new ArrayList<Team>(),
												new ArrayList<Team>(), new ArrayList<User>(), maxmemb, minmemb, 70 - (level*5));
						}
					}
				}
				overallLevelResults = new MatchmakingResults(thislevelResults, basedOn, level);
			}
			checkResults(overallLevelResults.getAggregateResults(), clusteringLevel);***/
			thislevelResults = prepareToBeMadePreviousResults(counterOfPain, thislevelResults);
			previouslyassigned = overallLevelResults.findPreviouslyAssigned();
			System.out.println("THERE ARE " + previouslyassigned.size() + " ASSIGNED USERS!!!");
			i = -1;
			int j = -1;
			prevresults = new ArrayList<MatchmakingResults>();
			ArrayList<ArrayList<Matchmaking>> prevCatMatchmaking = new ArrayList<ArrayList<Matchmaking>>();
			for (ArrayList<ArrayList<User>> clustercat : clusteringLevel){
				ArrayList<Matchmaking> ei = new ArrayList<Matchmaking>();
				prevCatMatchmaking.add(ei);
				i++;
				for (ArrayList<User> cluster : clustercat){
					j++;
					prevCatMatchmaking.get(i).add(thislevelResults.get(j));
				}
				MatchmakingResults catResult = new MatchmakingResults(prevCatMatchmaking.get(i), basedOn, level);
				prevresults.add(catResult);
			}
			
			if (level == relax && !allTeamsComplete(overallLevelResults.getAggregateResults())){
				thislevelResults = new ArrayList<Matchmaking>();
				Matchmaking lvlRslts = overallLevelResults.getAggregateResults();
				lvlRslts.keepOnlyComplete();
				thislevelResults.add(lvlRslts);
				prevresults = new ArrayList<MatchmakingResults>();
				level = orderedClustering.size() - 1; 
				rc = 0;
				incompletepassed = new ArrayList<Team>();
				completepassed = new ArrayList<Team>();
				returning = true;
				previouslyassigned = lvlRslts.findAssigned();
				returns ++ ;
				if (returns == 10){
					relax = 2;
				}if (returns == 15){
					relax = 1;
				}if (returns == 20 ){
					relax =0;
					minmemb = maxmemb;
				}if (returns == 23){
					maxmemb--;
					minmemb--;
				}if (returns == 25){
					maxmemb--;
					minmemb--;
				}
					
			}else {
				level--;
				rc++;
				returning = false;
			}
			if (returns == 26){
				Matchmaking lvlRslts = overallLevelResults.getAggregateResults();
				lvlRslts.keepOnlyComplete();
				return lvlRslts;
			}
		}
	
		return overallLevelResults.getAggregateResults();
	
	}
	
	public static boolean allTeamsComplete(Matchmaking mm){
		boolean all = false;
		if (mm.getIncomplete().isEmpty() && mm.getUnassigned().isEmpty()){
			if (!mm.getComplete().isEmpty()){
				all = true;
			}	
		}
		return all;
	}
	
	public static ArrayList<Matchmaking> prepareToBeMadePreviousResults(int counter, ArrayList<Matchmaking> lvlRslts){
		counter /= 2;
		ArrayList<Matchmaking> toReturn = new ArrayList<Matchmaking>();
		for (int i = 0; i < counter; i++){
			ArrayList<Matchmaking> pair = new ArrayList<Matchmaking>();
			pair.add(lvlRslts.get(i));
			pair.add(lvlRslts.get(i+counter));
			MatchmakingResults mmr = new MatchmakingResults(pair, "", 0);
			Matchmaking mm = mmr.getAggregateResults();
			toReturn.add(mm);
		}
		return toReturn;
	}
	
	public static ArrayList<ArrayList<ArrayList<ArrayList<User>>>> orderedClustering(ArrayList<User> users, String level1, String level2, String level3, String level4, String level5){
		ArrayList<ArrayList<ArrayList<ArrayList<User>>>> toReturn = new ArrayList<ArrayList<ArrayList<ArrayList<User>>>>();
		Matchmaking mm = new Matchmaking();
		ArrayList<ArrayList<ArrayList<User>>> userz = new ArrayList<ArrayList<ArrayList<User>>>();
		userz.add(new ArrayList<ArrayList<User>>());
		userz.get(0).add(users);
		if (!level1.equals("")){
			ArrayList<ArrayList<ArrayList<User>>> clust1 = mm.nextLevelClusteringFromExisting(userz, level1);
			if (!level2.equals("")){
				ArrayList<ArrayList<ArrayList<User>>> clust2 = mm.nextLevelClusteringFromExisting(clust1, level2);
				if (!level3.equals("")){
					ArrayList<ArrayList<ArrayList<User>>> clust3 = mm.nextLevelClusteringFromExisting(clust2, level3);
					if (!level4.equals("")){
						ArrayList<ArrayList<ArrayList<User>>> clust4 = mm.nextLevelClusteringFromExisting(clust3, level4);
						if (!level5.equals("")){
							ArrayList<ArrayList<ArrayList<User>>> clust5 = mm.nextLevelClusteringFromExisting(clust4, level5);
							toReturn.add(clust5);
						}
						toReturn.add(clust4);
					}
					toReturn.add(clust3);
				}
				toReturn.add(clust2);
			}
			toReturn.add(clust1);
		}
		toReturn.add(userz);
		return toReturn;
	}
	
	public static void checkResults(Matchmaking joined, ArrayList<ArrayList<ArrayList<User>>> clustering){
		System.out.println("---------------------R     E       S       U        L     T        S---------------");		
		ArrayList<Team> completeteams = joined.getComplete();
		ArrayList<Team> incompleteteams = joined.getIncomplete();
		ArrayList<User> unassigned = joined.getUnassigned();
		ArrayList<User> faulty = new ArrayList<User>();
		
		
		int uic = 0;
		int uii = 0;
		for (Team tim : completeteams){
			uic += tim.getMembers().size();
			if (tim.getMembers().size() < 3){
				System.out.println("HEy YAW THAT SHOULDNT HAPPEN");
			}
		}
		
		for (Team tim: incompleteteams){
			uii += tim.getMembers().size();
			if (tim.getMembers().size() < 2){
				System.out.println("HEy YAW THAT SHOULDNT HAPPEN EITHER in team " + tim.getId() + " with " + tim.getMembers().size() + " ");
			}
		}
		
		for (User u : faulty){
			if (u.userNotInThere(unassigned)){
				System.out.print("PROBLEM!");
			}
		}
		
		Matchmaking mm = new Matchmaking();
		findClusteringMMDiff(clustering, joined);
		int shouldHave = mm.findUniqueUsersInClusterLevel(clustering);
		System.out.println(uic + "  users in complete teams");
		System.out.println(uii + "  users in incomplete teams");
		System.out.println(unassigned.size() + "  unassigned users " );
		System.out.println("There should be " + shouldHave + "  users in total");
	}
	
	public static void findClusteringMMDiff(ArrayList<ArrayList<ArrayList<User>>> clustering, Matchmaking joined){
		ArrayList<User> mmresults = new ArrayList<User>();
		ArrayList<User> clusteringusers = new ArrayList<User>();
		ArrayList<User> ctusers = new ArrayList<User>();
		ArrayList<User> itusers = new ArrayList<User>();
		for (Team t : joined.getComplete()){
			System.out.println("COMPLETE team "+ t.getId() +"  formed with " + t.getMembers().size() + " members and " + t.getDiversity() + " div");
			for (User u: t.getMembers()){	
				if (u.userNotInThere(ctusers)){
					mmresults.add(u);
					ctusers.add(u);
				} else{
					System.out.println("THIS SON OF A B*TCH " + u.getId() + " MANAGED TO GET INTO MORE THAN ONE COMPLETE TEAMS");
				}
			}
		}
		for (Team t : joined.getIncomplete()){
			System.out.println("INCOMPLETE team "+ t.getId() +" formed with " + t.getMembers().size() + " members and " + t.getDiversity() + " div");
			for (User u: t.getMembers()){
				if (u.userNotInThere(itusers)){
					mmresults.add(u);
					itusers.add(u);
				} else{
					System.out.println("THIS SON OF A B*TCH " + u.getId() + " MANAGED TO GET INTO MORE THAN ONE INCOMPLETE TEAMS");
				}
			}
		}
		
		for (User u: joined.getUnassigned()){
			mmresults.add(u);
		}
		
		for (ArrayList<ArrayList<User>> cat: clustering){
			for (ArrayList<User> clust: cat){
				for (User u: clust){
					if (u.userNotInThere(clusteringusers)){
						clusteringusers.add(u);
					}
				}
			}
		}
		
		for (User u: ctusers){
			if (!u.userNotInThere(itusers)){
				System.out.println("THIS MOTHERFUCKER "+u.getId()+ " SOMEHOW GOT INTO TWO TEAMS FFS");
			}
		}
		
		for (User u: mmresults){
			if (u.userNotInThere(clusteringusers)){
				System.out.println("USER "+ u.getId()+ " SOMEHOW GOT THERE!!!");
			}
		}
		
		for (User u: clusteringusers){
			if (u.userNotInThere(mmresults)){
				System.out.println("MISSED USER "+ u.getId()+ " !!!");
			}
		}
	}
}