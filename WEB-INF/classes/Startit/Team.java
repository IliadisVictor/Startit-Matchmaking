package Startit;
//TODO find all instance vars nescessary
import java.util.*; // import the ArrayList class
import java.lang.Math.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Team{
	private ArrayList<User> members = new ArrayList<User>();
	private double diversity = 0;
	private static final AtomicInteger ai = new AtomicInteger(0); 
	private final int id;
	
	/**private ArrayList<String> fields = new ArrayList<String>();
    private double risk_tolerance = 0;
	private boolean isRisky = false;
	private double soft_skills = 0;
	private boolean isSkilled = false;
	private double leadership = 0;
	private boolean isLeaded = false;
	private double contribution = 0;
	private double optimism = 0;
	private boolean isOptimistic = false;
	private double time_management = 0;
	private double involvement = 0;**/

	
	public boolean teamNotInThere(ArrayList<Team> list){
		int id = getId();
		boolean there = true;
		for (Team tm : list){
			if (tm.getId() == id){
				there = false;
				break;
			}
		}
		return there;
	}
	
	
	public Team(){
		this.id = ai.incrementAndGet();		
	}
	
    public Team(ArrayList<User> members) {
        this.members = members;
		this.diversity = this.team_diversity(this);
		this.id = ai.incrementAndGet();		
    }
	
	public ArrayList<User> getMembers(){
		return members;
	}
	
	public double getDiversity(){
		return diversity;
	}
	
	public int getId(){
		return id;
	}
	
	public void removeMember(User user){
		this.members.remove(user);
		this.diversity = this.team_diversity(this);
	}
	
	public double team_diversity(Team team){
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<User> members = team.getMembers();			
		double risk_tolerance = 0; //mixed- at leas one
		double soft_skills = 0; //mixed- at least one
		double leadership = 0; //mixed- at least one
		//System.out.print("USERS IN TEAM    ");
		//System.out.println(members.size());
		double contribution = 0; //same
		double optimism = 0; //mixed- at least one
		double time_management = 0; //same
		double involvement[] = {0,0,0,0,0,0}; //mixed
		double oainvolvement = 0;
		double diversity = 0;
		int examined = 0;
		if (members.size() == 1){
			return 0;
		}
		for (User user: members){
			String field = user.getField();
			fields.add(field);
			double user_risk_tolerance = user.getRiskTolerance();
			int user_soft_skills = user.getSoftSkills();
			int user_leadership = user.getLeadership();
			int user_contribution = user.getContribution();
			double user_optimism = user.getOverallOptimism();
			double user_involvement[] = user.getInvolvementMatrix();
			double user_time_management = user.getTimeManagementOverall();
			time_management += user_time_management;
			risk_tolerance += user_risk_tolerance;
			soft_skills += user_soft_skills;
			leadership += user_leadership;
			contribution += user_contribution;
			involvement = team.updateInvolvement(involvement, user_involvement, members.size());
			optimism += user_optimism;
		}	
			/**
			if (fieldInFields(field, fields) == 0){
				diversity += 5/members.size();
			}else if (fieldInFields(field, fields) == 1){
				diversity += 2/members.size();
			}
			String isRisky = isTrue(members, examined, "risk");
			if (isRisky.equals("No")){
				risk_tolerance += user_risk_tolerance;
			}else if (isRisky.equals("Yes")){
				risk_tolerance += 6 - user_risk_tolerance;
			}else{
				double rt_diff = Math.abs((risk_tolerance - user_risk_tolerance*examined)/examined);
				risk_tolerance += user_risk_tolerance - rt_diff;
			}
			String isLeaded = isTrue(members, examined,"leadership");
			if (isLeaded.equals("No")){
				leadership += user_leadership;
			}else if (isLeaded.equals("Yes")){
				leadership += 6 - user_leadership;
			}else{
				double l_diff = Math.abs((leadership - user_leadership*examined)/examined);
				leadership += user_leadership - l_diff;
			}
			String isOptimistic = isTrue(members, examined, "optimism");
			if (isOptimistic.equals("No")){
				optimism += user_optimism;
			}else if (isOptimistic.equals("Yes")){
				optimism += 6 - user_optimism;
			}else{
				double o_diff = Math.abs((optimism - user_optimism*examined)/examined);
				leadership += user_optimism - o_diff;
			}
			String isSkilled = isTrue(members, examined, "soft skills");
			if (isSkilled.equals("No")){
				soft_skills += user_soft_skills;
			}else if (isSkilled.equals("Yes")){
				soft_skills += 6 - user_soft_skills;
			}else{
				double ss_diff = Math.abs((soft_skills - user_soft_skills*examined)/examined);
				soft_skills += user_soft_skills - ss_diff;
			}
			involvement = team.updateInvolvement(involvement, user_involvement, members.size());
			double cont_diff = Math.abs((contribution - user_contribution*examined)/examined);
			contribution += user_contribution - cont_diff;
			double tm_diff = Math.abs((time_management - user_time_management*examined)/examined);
			time_management += user_time_management - tm_diff;	
			fields.add(field);
			examined += 1;

		}
		**/
		oainvolvement = team.countInvolvement(involvement);
		oainvolvement /= members.size();
		risk_tolerance /= members.size();
		leadership /= members.size();
		optimism /= members.size();
		soft_skills /= members.size();
		contribution /= members.size();
		time_management /= members.size();
		String isLeaded = isTrue(members, members.size(), "leadership");
		String isOptimistic = isTrue(members, members.size(), "optimism");
		String isRisky = isTrue(members, members.size(), "risk");
		String isSkilled  = isTrue(members, members.size(), "soft skills");
		double rt_mean = 0;
		double lead_mean = 0;
		double opt_mean = 0;
		double contr_mean = 0;
		double sk_mean = 0;
		double inv_mean = 0;
		double tm_mean = 0;
				
		for (User user: members){
			String field = user.getField();
			int howmany = fieldInFields(field, fields);
			if (howmany == 1){
				diversity += 100;
			}else if (howmany == 2){
				diversity += 40;
			}
			double user_risk_tolerance = user.getRiskTolerance();
			double rt_diff = Math.abs(risk_tolerance - user_risk_tolerance);
			rt_mean += rt_diff;
			double user_soft_skills = user.getSoftSkills();
			double sk_diff = Math.abs(soft_skills - user_soft_skills);
			sk_mean += sk_diff;
			double user_leadership = user.getLeadership();
			double lead_diff = Math.abs(leadership - user_leadership);
			lead_mean += lead_diff;
			double user_contribution = user.getContribution();
			double contr_diff = Math.abs(contribution - user_contribution);
			contr_mean += contr_diff;
			double user_optimism = user.getOverallOptimism();
			double opt_diff = Math.abs(optimism - user_optimism);
			opt_mean += opt_diff;
			double user_involvement[] = user.getInvolvementMatrix();
			double user_oainvolvement = team.countInvolvement(user_involvement);
			double inv_diff = Math.abs(oainvolvement - user_oainvolvement);
			inv_mean += inv_diff;
			double user_time_management = user.getTimeManagementOverall();
			double tm_diff = Math.abs(time_management - user_time_management);
			tm_mean += tm_diff;
		}
		tm_mean /= members.size();
		time_management = (4 - tm_mean)*25;
		contr_mean /= members.size();
		contribution = (4 - contr_mean)*25;
		inv_mean /= members.size();
		oainvolvement = inv_mean*25;
		lead_mean /= members.size();
		leadership = atLeatOne(isLeaded, lead_mean);
		opt_mean /= members.size();
		optimism = atLeatOne(isOptimistic, opt_mean);
		sk_mean /= members.size();
		soft_skills = atLeatOne(isSkilled, sk_mean);
		rt_mean /= members.size();
		risk_tolerance = atLeatOne(isLeaded, rt_mean);
		diversity /= members.size();
		diversity = (diversity + time_management*1.5 + contribution + risk_tolerance*2 + oainvolvement*2.5 + leadership*2.5 + 
							optimism + soft_skills*1.5 + members.size()) / 14;
		
		//System.out.print("diversity is:");
		//System.out.println(diversity);
		return diversity;
	}
	
	public static int fieldInFields(String user_field, ArrayList<String> fields){
		int count = 0;
		for (String field: fields){
			if (field.equals(user_field)){
				count += 1;
			}
		}
		return count;
	}
	
	public static double atLeatOne(String isTrue, double mean){
		double attribute = 0;
		if (isTrue.equals("Yes")){
			attribute = mean*25;
		}else if (isTrue.equals("Somewhat")){
			attribute = (4 - mean)*25;
		}
		return attribute;
			
	}
	
	public static String isTrue(ArrayList<User> members, int examined, String attribute){
		String isTrue = "No";
		double score = 0;
		if (examined > 0){
			boolean exists = false;
			ArrayList<User> exmembs = new ArrayList<User>();
			for (int i = 0; i < examined; i++)
				exmembs.add(members.get(i));
			for (User u: exmembs){
				double user_score = 0;
				switch(attribute){
					case "leadership":
						user_score = u.getLeadership();
						break;
					case "risk":
						user_score = u.getRiskTolerance();
						break;
					case "optimism":
						user_score = u.getOverallOptimism();
						break;
					case "soft skills":
						user_score = u.getSoftSkills();
						break;
				}
				if (user_score >= 4)
					exists = true;
				score += user_score/examined;
			}
			if (exists){
				isTrue = "Yes";
			}else{
				if (score >= 3 && score < 4){
					isTrue = "Somewhat";
				}else{
					isTrue = "No";
				}
			}
		}
		return isTrue;
	}
	
	public double[] updateInvolvement(double[] involvement, double[] user_involvement, int members){
		for (int i=0; i < involvement.length; i++){
			involvement[i] += user_involvement[i]/members;
		}
		return involvement;
	}
	
	public double countInvolvement(double[] involvement){
		double oainvolvement = 0;
		for (int i=0; i < involvement.length; i++){
			oainvolvement += involvement[i]/(involvement.length);
		}
		return oainvolvement;
	}
	
	public boolean teamComplete(int max_members, int min_members, double sufficient){
		if (this.members.size() < max_members){
			if (this.members.size() < min_members){
				return false;
			}else{
				if (diversity >= sufficient){
					return true;
				}else{
					return false;
				}
			}
		}else{
			//System.out.println("TEAM OVER 6 MEMBERS");
			return true;
		}
	}
	
	/*
    public static void findTeams(User[] users){
        ArrayList<Team> teams = new ArrayList<Team>(); 
        double[][] similarity=new double[users.length][users.length];
        for(int i=0 ; i < users.length;i++){
            for(int j=0; j<users.length;j++){
                similarity[i][j]=findSimilarity(users[i],users[j]);
                System.out.println("The similarity of " + i + " and " + j + " is " + similarity[i][j]);
            }
        }

    }
    public static double findSimilarity(User user1,User user2){
        double agediff= Double.parseDouble(user1.getAge())-Double.parseDouble(user2.getAge());
        //find difference in education lvls devotion and experience and the average of all is similarity, then make teams by similarity
        return agediff;
    }
	*/
}