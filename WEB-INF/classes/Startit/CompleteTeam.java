package Startit;
//TODO find all instance vars nescessary
import java.util.*; // import the ArrayList class
import java.lang.Math.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CompleteTeam{
	private ArrayList<User> members = new ArrayList<User>();
	private double diversity = 0;
	private int id;
	private int status;
	
	//SEPARATE DIVERSITY METRICS
    private double risk_tolerance;
	private double soft_skills;
	private double leadership;
	private double contribution;
	private double optimism;
	private double time_management;
	private double involvement;
	
	public CompleteTeam(ArrayList<User> members) {
        this.members = members;
		this.diversity = this.team_diversity(this);
		this.id = 0;	
		this.status = 0;
    }
	
	public CompleteTeam(){}
	
	public double getRiskTolerance(){
		return roundDouble(risk_tolerance);
	}
	
	public double getSoftSkills(){
		return roundDouble(soft_skills);
	}
	
	public double getLeadership(){
		return roundDouble(leadership);
	}	
	
	public double getContribution(){
		return roundDouble(contribution);
	}
	
	public double getOptimism(){
		return roundDouble(optimism);
	}
	
	public double getTimeManagement(){
		return roundDouble(time_management);
	}
	
	public double getInvolvement(){
		return roundDouble(involvement);
	}
		
	public ArrayList<User> getMembers(){
		return members;
	}
	
	public double getDiversity(){
		return roundDouble(diversity);
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public void removeMember(User user){
		this.members.remove(user);;
		this.diversity = this.team_diversity(this);
	}
	
	public void addMember(User user){
		this.members.add(user);
		this.diversity = this.team_diversity(this);		
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	
	public static double roundDouble(double d){
        double roundedDouble = Math.round(d * 10.0) / 10.0;
		return roundedDouble;
	}
	
	public String getStatusPreety() {
		String prettyStatus = "";
		if (status == 0){
			prettyStatus = "Pending";
		}else{
			prettyStatus = "Approved";
		}
		return prettyStatus;
	}
	
	public double showChangeOnDiversityIf(User u, String condition){
		ArrayList<User> membs = this.getMembers();
		CompleteTeam t = new CompleteTeam();
		double newdiv = 0;
		if (condition.equals("added")){
			membs.add(u);
			t = new CompleteTeam(membs);
			newdiv = t.getDiversity();
			membs.remove(u);
		}
		else if (condition.equals("removed")){
			membs.remove(u);
			t = new CompleteTeam(membs);
			newdiv = t.getDiversity();
			membs.add(u);			
		}
		double diff = newdiv - this.getDiversity();
		return roundDouble(diff);
	}
		
	
	public double team_diversity(CompleteTeam team){
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
		
		this.soft_skills = soft_skills;
		this.involvement = oainvolvement;
		this.leadership = leadership;
		this.risk_tolerance = risk_tolerance;
		this.contribution = contribution;
		this.time_management = time_management;
		this.optimism = optimism;
		
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
	
	
	//TAGS
	public String ageTag(){
		boolean is18_24 = false;
		boolean is25_35 = false;
		boolean isOver35 = false;
		for (User u: this.members){
			int age = u.getAge();
			if (age == 1){
				is18_24 = true;
			}else if (age == 2) {
				is25_35 = true;
			}else if (age == 3) {
				isOver35 = true;
			}
		}
		String ageTag = "   ";
		if (is18_24){
			ageTag = "18-24";
			if (is25_35){
				ageTag = "18-35";
			}if (isOver35){
				ageTag = "18-35+";
			}
		}else if (is25_35){
			ageTag = "25-35";
			if (isOver35){
				ageTag = "25-35+";
			}
		}else if (isOver35){
			ageTag = "+35";
		}
		return ageTag;
	}
	
	public String eduLevelTag(){
		boolean isBachelor = false;
		boolean isMaster = false;
		boolean isCandidate = false;
		boolean isHolder = false;
		for (User u: this.members){
			String lvl = u.getLevel();
			if (lvl.equals("bachelor")){
				isBachelor = true;
			}else if (lvl.equals("master")) {
				isMaster = true;
			}else if (lvl.equals("phd_cand")) {
				isCandidate = true;
			}else if (lvl.equals("phd_hold")){
				isHolder = true;
			}
		}
		String eduLevelTag = "   ";
		if (isBachelor){
			eduLevelTag = "Bachelor";
			if (isMaster){
				eduLevelTag = "Bachelor-Master";
			}if (isCandidate||isHolder){
				eduLevelTag = "Bachelor-Phd";
			}
		}else if (isMaster){
			eduLevelTag = "Master";
			if (isCandidate||isHolder){
				eduLevelTag = "Master-Phd";
			}
		}else if(isCandidate||isHolder){
			eduLevelTag = "Phd";
		}
		return eduLevelTag;
	}
	
	public String expYearsTag(){
		boolean is0 = false;
		boolean is1_2 = false;
		boolean is3_4 = false;
		boolean is5_10 = false;
		boolean is10plus = false;
		for (User u: this.members){
			String exp = u.getExpYears();
			if (exp.equals("0")){
				is0 = true;
			}else if (exp.equals("1-2")) {
				is1_2 = true;
			}else if (exp.equals("3-4")) {
				is3_4 = true;
			}else if (exp.equals("5-10")){
				is5_10 = true;
			}else{
				is10plus = true;
			}
		}
		String expYearsTag = "   ";
		if (is0){
			expYearsTag = "0";
			if (is1_2){
				expYearsTag = "0-2";
			}if (is3_4){
				expYearsTag = "0-4";
			}if (is5_10){
				expYearsTag = "0-10";
			}if (is10plus){
				expYearsTag = "0-10+";
			}
		}else if (is1_2){
			expYearsTag = "1-2";
			if (is3_4){
				expYearsTag = "1-4";
			}if (is5_10){
				expYearsTag = "1-10";
			}if (is10plus){
				expYearsTag = "1-10+";
			}
		}else if (is3_4){
			expYearsTag = "3-4";
			if (is5_10){
				expYearsTag = "3-10";
			}if (is10plus){
				expYearsTag = "3-10+";
			}
		}else if (is5_10){
			expYearsTag = "5-10";
			if (is10plus){
				expYearsTag = "5-10+";
			}
		}else if(is10plus){
			expYearsTag = "10+";
		}
		return expYearsTag;
	}
	
	public ArrayList<String[]> getAgeProfile(){
		ArrayList<String[]> ageProfile = new ArrayList<String[]>();
		int _18_24 = 0;
		int _25_35 = 0;
		int over_35 = 0;
		for (User u: members){
			String age = u.getAgePretty();
			if (age.equals("18-24")){
				_18_24++;
			}else if (age.equals("25_35")){
				_25_35++;
			}else{
				over_35++;
			}	
		}
		if (_18_24 > 0){
			String[] ela = {"18-24", String.valueOf(_18_24)};
			ageProfile.add(ela);
		}
		if (_25_35 > 0){
			String[] pxoios = {"25-35", String.valueOf(_25_35)};
			ageProfile.add(pxoios);
		} 
		if (over_35 > 0){
			String[] pou = {"35+", String.valueOf(over_35)};
			ageProfile.add(pou);
		}
		return ageProfile;
	}
	
	public ArrayList<String[]> getLevelProfile(){
		ArrayList<String[]> lvlProfile = new ArrayList<String[]>();
		int bachelor = 0;
		int master = 0;
		int phd_cand = 0;
		int phd_hold= 0;
		for (User u: members){
			String lvl = u.getLevel();
			if (lvl.equals("bachelor")){
				bachelor++;
			}else if (lvl.equals("master")){
				master++;
			}else if (lvl.equals("phd_cand")){
				phd_cand++;
			}else{
				phd_hold++;
			}	
		}
		if (bachelor > 0){
			String[] ela = {"Bachelor", String.valueOf(bachelor)};
			lvlProfile.add(ela);
		}
		if (master > 0){
			String[] pxoios = {"Master", String.valueOf(master)};
			lvlProfile.add(pxoios);
		} 
		if (phd_cand > 0){
			String[] kai = {"PhD Candidate", String.valueOf(phd_cand)};
			lvlProfile.add(kai);
		}
		if (phd_hold > 0){
			String[] pou = {"PhD Candidate", String.valueOf(phd_hold)};
			lvlProfile.add(pou);
		}
		return lvlProfile;
	}
	
	public ArrayList<String[]> getExpYearsProfile(){
		ArrayList<String[]> expYearsProfile = new ArrayList<String[]>();
		int _0 = 0;
		int _1_2 = 0;
		int _3_4 = 0;
		int _5_10 = 0;
		int over_10 = 0;
		for (User u: members){
			String expYears = u.getExpYears();
			if (expYears.equals("0")){
				_0++;
			}else if (expYears.equals("1-2")){
				_1_2++;
			}else if (expYears.equals("3-4")){
				_3_4++;
			}else if (expYears.equals("5-10")){
				_5_10++;
			}else{
				over_10++;
			}	
		}
		if (_0 > 0){
			String[] ela = {"0", String.valueOf(_0)};
			expYearsProfile.add(ela);
		}
		if (_1_2 > 0){
			String[] pxoios = {"1-2", String.valueOf(_1_2)};
			expYearsProfile.add(pxoios);
		} 
		if (_3_4 > 0){
			String[] pou = {"3-4", String.valueOf(_3_4)};
			expYearsProfile.add(pou);
		}
		if (_5_10 > 0){
			String[] kai = {"5-10", String.valueOf(_5_10)};
			expYearsProfile.add(kai);
		}
		if (over_10 > 0){
			String[] pote = {"10+", String.valueOf(over_10)};
			expYearsProfile.add(pote);
		}
		return expYearsProfile;
	}
	
	
	public ArrayList<String[]> getStartupExpProfile(){
		ArrayList<String[]> startupExpProfile = new ArrayList<String[]>();
		int _1 = 0;
		int _2 = 0;
		int _3 = 0;
		int _4 = 0;
		int _5 = 0;
		int _6 = 0;
		for (User u: members){
			int startupExp = u.getStartupExperience();
			if (startupExp==4){
				_4++;
			}else if (startupExp==1){
				_1++;
			}else if (startupExp==2){
				_2++;
			}else if (startupExp==3){
				_3++;
			}else if (startupExp==5){
				_5++;
			}else{
				_6++;
			}	
		}
		if (_1 > 0){
			String[] ela = {"0%", String.valueOf(_1)};
			startupExpProfile.add(ela);
		}
		if (_2 > 0){
			String[] pxoios = {"20%", String.valueOf(_2)};
			startupExpProfile.add(pxoios);
		} 
		if (_3 > 0){
			String[] pou = {"40%", String.valueOf(_3)};
			startupExpProfile.add(pou);
		}
		if (_4 > 0){
			String[] pote = {"60%", String.valueOf(_4)};
			startupExpProfile.add(pote);
		}
		if (_5 > 0){
			String[] kai = {"80%", String.valueOf(_5)};
			startupExpProfile.add(kai);
		}
		if (_6 > 0){
			String[] giati = {"100%", String.valueOf(_6)};
			startupExpProfile.add(giati);
		}
		return startupExpProfile;
	}
	
	public ArrayList<String[]> getDevotionProfile(){
		ArrayList<String[]> devotionProfile = new ArrayList<String[]>();
		int _1_2 = 0;
		int _3_4 = 0;
		int _5_10 = 0;
		int over_10 = 0;
		for (User u: members){
			int devotion = u.getDevotion();
			if (devotion==1){
				_1_2++;
			}else if (devotion==2){
				_3_4++;
			}else if (devotion==3){
				_5_10++;
			}else if (devotion==4){
				over_10++;
			}	
		}
		if (_1_2 > 0){
			String[] pxoios = {"1-2", String.valueOf(_1_2)};
			devotionProfile.add(pxoios);
		} 
		if (_3_4 > 0){
			String[] pou = {"3-4", String.valueOf(_3_4)};
			devotionProfile.add(pou);
		}
		if (_5_10 > 0){
			String[] kai = {"5-10", String.valueOf(_5_10)};
			devotionProfile.add(kai);
		}
		if (over_10 > 0){
			String[] pote = {"10+", String.valueOf(over_10)};
			devotionProfile.add(pote);
		}
		return devotionProfile;
	}
	
	public ArrayList<CompleteTeam> sortByDiversity(ArrayList<CompleteTeam> cts){
		ArrayList<CompleteTeam> sorted = new ArrayList<CompleteTeam>();
		while (!cts.isEmpty()){
			double maxdiv = 0;
			CompleteTeam best = new CompleteTeam();
			for (CompleteTeam ct: cts){
				if (ct.getDiversity() > maxdiv){
					best = ct;
					maxdiv = ct.getDiversity();
				}
			}
			cts.remove(best);
			sorted.add(best);
		}
		return sorted;
	}
		
	
}