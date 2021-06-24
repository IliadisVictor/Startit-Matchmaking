package Startit;
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

/**
 * TODO add functionality lol
 */

public class User {
        private int id;
        private String name;
        private String surname;
        private String email;
        private int age;
        private String uni;
        private String level; // TODO currently 4 choices should there be 4?
        private String field;
        private String occupationType; // 0 = full time, 1 = part-time, 2 = occasional, 3 = unemployed
        private String expYears; // 0 = 0years, 1 = 1-2 years etc.
        private String desire; // ..to become an entrepreneur 0 = no , 1 = maybe , 2 = yes
        private String course; // 0 = havent attended and wont, 1 = havent but will etc.

        // activities
        private int designCompetition; // one for each activity in question 12
        private int speech;
        private int visit;
        private int conference;
        private int simulator;
        private int incubator; // activities end

        private int biznamanOrNo; // again 0-4 likert as explained in the
        // soft skills
        /**private int communication; // one for each soft skill like activities q 14
        private int critical;
        private int teamwork;
        private int leadershipskills;
        private int positivity;**/
		private int soft_skills;
        // influences
        private int knowledge;
        private int financial;
        private int social; // TODO **should this be ranked insted of likert?** what influences carreer

        private int startupExperience; // 6 choices
        private int virtualExp; // likert 0-4
        private int leadership; // likert 0-4
        private int contribution; // likert 0-4
        private int startupStage; // likert 0-4 which would u like to join

        // risk tolerance
        private int caution; // likert 0-4 x 5
        private int motto;
        private int chances;
        private int smallgoal;
        private int risks;

        // optimism
        private int optimism; // likert 0-4 x 4
        private int expect;
        private int count;
        private int overall;

        private int personalDevelopment; // likert 0-4
        private int devotion; // likert 0-3

        // time managment
        private int prioritize;
        private int achieve;
        private int deliver;
        private int time;
        private int handle;
		private int to_do;
        private int planning; // likert 0-4 x 7
		private boolean isAssigned;

        private int importance; // likert 0-4

        public User() {}

        

        public User generateRandomUser() { // works well, tested
                Random rand = new Random();

                String[] names = { "Makis", "Akis", "Mixalhs", "Nikos", "Giannis", "Vic", "Antrikos", "Oresths",
                                "Tasos", "Filippos", "Giorgos", "Manos", "Alanos", "Mpelafon", "Yung", "Xristoforos",
                                "Xrhstos", "Giorgos", "Stathis", " Kuriakos", "Alekshs", "Aristotelhs", "Leonardo",
                                "John", "Vaggelas" };
                String[] surnames = { "Georgakopoulos", "Poulopoulos", "Dhmhtriadhs", "Makopoulos", "Iliadis", "SOfos",
                                "Vagionakis", "Xristakhs", "Karaiskos", "Nikolopoulos", "Vouladelhs", "Mitsotakis",
                                "Tsipras", "Papadopoulos", "Kolios", "Nikolopoulos", "Dollanhs", "Athinarezos",
                                "Davincis", "Smith", "Xatzhs", "Xatzhdhmhtriou", "Poulopoulos", "Papakwstantinou",
                                "Tsakirdis", "Karalis", "Psuxogios", "Pramatarhs", "Louridas" };
                String[] unis = { "assoara", "pamak", "apouthou", "ekpa", "emp", "papi", "gewponiko", "harvard", "yale",
                                "stanford" };
                String[] levels = { "bachelor", "master", "phd_cand", "phd_hold" };
                String[] fields = { "business", "marketing", "cs", "engineering", "economics", "law", "health",
                                "teaching", "other" };
                String[] occupationTypes = { "fulltime", "partime", "occasional", "unemployed" };
                String[] expYearss = { "0", "1-2", "3-4", "5-10", "10+" };
                String[] desires = { "no", "maybe", "yes" };
                String[] courses = { "havent and wont", "havent and will ", " currently", "finished" };
                int[] likert3 = { 1, 2, 3 };
                int[] likert4 = { 1, 2, 3, 4 };
                int[] likert5 = { 1, 2, 3, 4, 5 };
                int[] likert6 = { 1, 2, 3, 4, 5, 6 };

                String name = names[rand.nextInt(names.length)];
                String surname = surnames[rand.nextInt(surnames.length)];
                int age = likert3[rand.nextInt(3)];
                String email = name + surname + " @gmail.com";
                String uni = unis[rand.nextInt(unis.length)];
                String level = levels[rand.nextInt(4)];
                String field = fields[rand.nextInt(fields.length)];
                String occupationType = occupationTypes[rand.nextInt(occupationTypes.length)];
                String expYears = expYearss[rand.nextInt(expYearss.length)];
                String desire = desires[rand.nextInt(desires.length)];
                String course = courses[rand.nextInt(courses.length)];
                // activities
                int designCompetition = likert5[rand.nextInt(5)];
                int speech = likert5[rand.nextInt(5)];
                int visit = likert5[rand.nextInt(5)];
                int conference = likert5[rand.nextInt(5)];
                int simulator = likert5[rand.nextInt(5)];
                int incubator = likert5[rand.nextInt(5)];

                int leaderOrNo = likert5[rand.nextInt(5)];

                // soft skills
                /**int communication = likert5[rand.nextInt(5)];
                int critical = likert5[rand.nextInt(5)];
                int teamwork = likert5[rand.nextInt(5)];
                int leadershipskills = likert5[rand.nextInt(5)];
                int positivity = likert5[rand.nextInt(5)];**/
				int soft_skills = likert5[rand.nextInt(5)];

                // influences
                int knowledge = likert5[rand.nextInt(5)];
                int financial = likert5[rand.nextInt(5)];
                int social = likert5[rand.nextInt(5)];

                int startupExperience = likert6[rand.nextInt(6)];
                int virtualExp = likert5[rand.nextInt(5)];
                int leadership = likert5[rand.nextInt(5)];
                int contribution = likert5[rand.nextInt(5)];
                int startupStage = likert5[rand.nextInt(5)];

                // risk tolerance
                int caution = likert5[rand.nextInt(5)]; // likert 0-4 x 5
                int motto = likert5[rand.nextInt(5)];
                int chances = likert5[rand.nextInt(5)];
                int smallgoal = likert5[rand.nextInt(5)];
                int risks = likert5[rand.nextInt(5)];

                int personalDevelopment = likert5[rand.nextInt(5)];
                int devotion = likert4[rand.nextInt(4)];
                // optimism
                int optimism = likert5[rand.nextInt(5)];// likert 0-4 x 4
                int expect = likert5[rand.nextInt(5)];
                int count = likert5[rand.nextInt(5)];
                int overall = likert5[rand.nextInt(5)];
                // time managment
                int prioritize = likert5[rand.nextInt(5)];
                int achieve = likert5[rand.nextInt(5)];
                int deliver = likert5[rand.nextInt(5)];
                int time = likert5[rand.nextInt(5)];
                int handle = likert5[rand.nextInt(5)];
                int to_do = likert5[rand.nextInt(5)];

                int importance = likert5[rand.nextInt(5)];
				

                User randUser = new User(name, surname, email, age, uni, level, field, occupationType, expYears, desire,
                                course, designCompetition, speech, visit, conference, simulator, incubator, leaderOrNo, soft_skills,
                                knowledge, financial, social, startupExperience, virtualExp, leadership, 
								contribution, startupStage, caution,motto, chances, smallgoal, risks, optimism, expect,
								count, overall, personalDevelopment, devotion, prioritize, achieve, deliver, time, handle,
								planning, importance, to_do);
                return randUser;
        }

        public User(String name, String surname, String email, int age, String uni, String level, String field,
                        String occupationType, String expYears, String desire, String course, int designCompetition,
                        int speech, int visit, int conference, int simulator, int incubator,
                        int biznamanOrNo, int soft_skills, int knowledge, int financial, int social,
                        int startupExperience, int virtualExp, int leadership, int contribution,
                        int startupStage, int caution, int motto, int chances, int smallgoal,
                        int risks, int optimism, int expect, int count, int overall,
                        int personalDevelopment, int devotion, int prioritize, int achieve, int deliver,
                        int time, int handle, int planning, int importance, int to_do) {
				this.id = 0;
                this.name = name;
                this.surname = surname;
                this.email = email;
                this.age = age;
                this.uni = uni;
                this.level = level;
                this.field = field;
                this.occupationType = occupationType;
                this.expYears = expYears;
                this.desire = desire;
                this.course = course;
                this.designCompetition = designCompetition;
                this.speech = speech;
                this.visit = visit;
                this.conference = conference;
                this.simulator = simulator;
                this.incubator = incubator;
                this.biznamanOrNo = biznamanOrNo;
                /**this.communication = communication;
                this.critical = critical;
                this.teamwork = teamwork;
                this.leadershipskills = leadershipskills;
                this.positivity = positivity;**/
				this.soft_skills = soft_skills;
                this.knowledge = knowledge;
                this.financial = financial;
                this.social = social;
                this.startupExperience = startupExperience;
                this.virtualExp = virtualExp;
                this.leadership = leadership;
                this.contribution = contribution;
                this.startupStage = startupStage;
                this.caution = caution;
                this.motto = motto;
                this.chances = chances;
                this.smallgoal = smallgoal;
                this.risks = risks;
                this.optimism = optimism;
                this.expect = expect;
                this.count = count;
                this.overall = overall;
                this.personalDevelopment = personalDevelopment;
                this.devotion = devotion;
                this.prioritize = prioritize;
                this.achieve = achieve;
                this.deliver = deliver;
                this.time = time;
                this.handle = handle;
                this.planning = planning;
                this.importance = importance;
				this.to_do = to_do;
				this.isAssigned = false;
        }

        @Override
        public String toString() {
			double user_risk_tolerance = getRiskTolerance();
			double user_optimism = getOverallOptimism();
			double user_involvement[] = getInvolvementMatrix();
			double inv = 0;
			int i;
			for (i = 0; i < user_involvement.length; i++){
				inv += user_involvement[i];
			}
			inv /= i;
			double user_time_management = getTimeManagementOverall();
                return "User has: surname=" + surname+", name=" + name  +", age = " + age +  ", expYears=" + expYears
                                + ", field=" + field + ", risk tolerance=" + user_risk_tolerance+ ", optimism"+ user_optimism 
                                + ", leadership=" + leadership + ", level=" + level + ", time management=" + user_time_management
                                + ", occupationType=" + occupationType + ", involvement" + inv 
                                + ", startupExperience=" + startupExperience + ", startupStage=" + startupStage
                                +  ", time=" + time + ", uni=" + uni;
        }

        public int getIdFromDB(Connection con) throws Exception{
                DAO d = new DAO();
                int id = d.getUserId(this.email,con);
                return id;
        }
		
		public int getId(){
			return id;
		}
		
		public void setId(int id) {
			this.id = id;
		}
		
		public boolean getIsAssigned(){
			return isAssigned;
		}
		
		public String getIsAssignedPretty(){
			String isAssigned = "";
			if (this.isAssigned){
				isAssigned = "Assigned";
			}else{
				isAssigned = "Unassigned";
			}
			return isAssigned;
		}
		
		public void setIsAssigned(boolean isAssigned){
			this.isAssigned = isAssigned;
		}

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getSurname() {
                return surname;
        }

        public void setSurname(String surname) {
                this.surname = surname;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public int getAge() {
                return age;
        }

        public void setAge(int age) {
                this.age = age;
        }

        public String getUni() {
                return uni;
        }

        public void setUni(String uni) {
                this.uni = uni;
        }

        public String getLevel() {
                return level;
        }
		
		public String getLevelPretty() {
            String level = "";
			if (this.level.equals("bachelor")){
				level = "Bachelor Degree";
			}else if (this.level.equals("master")){
				level = "Master Degree";
			}else if (this.level.equals("phd_cand")){
				level = "PHD Candidate";
			}else{
				level = "PHD Holder";
			}
			return level;
        }

        public void setLevel(String level) {
                this.level = level;
        }

        public String getField() {
                return field;
        }
		
		public String getFieldPretty(){
			String field = "";
			if (this.field.equals("business")){
				field = "Business Administration";
			}else if (this.field.equals("marketing")){
				field = "Marketing";
			}else if (this.field.equals("engineering")){
				field = "Engineering";
			}else if (this.field.equals("cs")){
				field = "Informatics";
			}else if (this.field.equals("economics")){
				field = "Economic Studies";
			}else if (this.field.equals("law")){
				field = "Studies of Law";
			}else if (this.field.equals("health")){
				field = "Health Studies";
			}else if (this.field.equals("teaching")){
				field = "Pedagogical Studies";
			}else if (this.field.equals("other")){
				field = "Other";
			}
			return field;

        }

        public void setField(String field) {
                this.field = field;
        }

        public String getOccupationType() {
                return occupationType;
        }

        public void setOccupationType(String occupationType) {
                this.occupationType = occupationType;
        }

        public String getExpYears() {
                return expYears;
        }

        public void setExpYears(String expYears) {
                this.expYears = expYears;
        }

        public String getDesire() {
                return desire;
        }

        public void setDesire(String desire) {
                this.desire = desire;
        }

        public String getCourse() {
                return course;
        }

        public void setCourse(String course) {
                this.course = course;
        }

        public int getDesignCompetition() {
                return designCompetition;
        }

        public void setDesignCompetition(int designCompetition) {
                this.designCompetition = designCompetition;
        }

        public int getSpeech() {
                return speech;
        }

        public void setSpeech(int speech) {
                this.speech = speech;
        }

        public int getVisit() {
                return visit;
        }

        public void setVisit(int visit) {
                this.visit = visit;
        }

        public int getConference() {
                return conference;
        }

        public void setConference(int conference) {
                this.conference = conference;
        }

        public int getSimulator() {
                return simulator;
        }

        public void setSimulator(int simulator) {
                this.simulator = simulator;
        }

        public int getIncubator() {
                return incubator;
        }

		public double[] getInvolvementMatrix(){
			double[] involvement = new double[6];
			involvement[0] = designCompetition;
			involvement[1] = speech;
			involvement[2] = visit;
			involvement[3] = conference;
			involvement[4] = simulator;
			involvement[5] = incubator;
			return involvement;
		
		}	

        public void setIncubator(int incubator) {
                this.incubator = incubator;
        }

        public int getBiznamanOrNo() {
                return biznamanOrNo;
        }

        public void setBiznamanOrNo(int biznamanOrNo) {
                this.biznamanOrNo = biznamanOrNo;
        }
		
		public int getSoftSkills(){
			return soft_skills;
		}

        public int getKnowledge() {
                return knowledge;
        }

        public void setKnowledge(int knowledge) {
                this.knowledge = knowledge;
        }

        public int getFinancial() {
                return financial;
        }

        public void setFinancial(int financial) {
                this.financial = financial;
        }

        public int getSocial() {
                return social;
        }

        public void setSocial(int social) {
                this.social = social;
        }

        public int getStartupExperience() {
                return startupExperience;
        }

        public void setStartupExperience(int startupExperience) {
                this.startupExperience = startupExperience;
        }

        public int getVirtualExp() {
                return virtualExp;
        }

        public void setVirtualExp(int virtualExp) {
                this.virtualExp = virtualExp;
        }

        public int getLeadership() {
                return leadership;
        }

        public void setLeadership(int leadership) {
                this.leadership = leadership;
        }

        public int getContribution() {
                return contribution;
        }

        public void setContribution(int contribution) {
                this.contribution = contribution;
        }

        public int getStartupStage() {
                return startupStage;
        }

        public void setStartupStage(int startupStage) {
                this.startupStage = startupStage;
        }

        public int getCaution() {
                return caution;
        }

        public void setCaution(int caution) {
                this.caution = caution;
        }

        public int getMotto() {
                return motto;
        }

        public void setMotto(int motto) {
                this.motto = motto;
        }

        public int getChances() {
                return chances;
        }

        public void setChances(int chances) {
                this.chances = chances;
        }

        public int getSmallgoal() {
                return smallgoal;
        }

        public void setSmallgoal(int smallgoal) {
                this.smallgoal = smallgoal;
        }

        public int getRisks() {
                return risks;
        }

        public void setRisks(int risks) {
                this.risks = risks;
        }
		
		public double getRiskTolerance(){
			double rt = 0 ;
			rt = caution + smallgoal + motto + chances + risks;
			rt /= 5;
			return rt;
		}
		
        public int getOptimism() {
                return optimism;
        }

        public void setOptimism(int optimism) {
                this.optimism = optimism;
        }

        public int getExpect() {
                return expect;
        }

        public void setExpect(int expect) {
                this.expect = expect;
        }

        public int getCount() {
                return count;
        }

        public void setCount(int count) {
                this.count = count;
        }

        public int getOverall() {
                return overall;
        }

        public void setOverall(int overall) {
                this.overall = overall;
        }
		
		public double getOverallOptimism(){
			double oo = 0;
			oo = overall + count + optimism + expect;
			oo /= 4;
			return oo;
		}
		
        public int getPersonalDevelopment() {
                return personalDevelopment;
        }

        public void setPersonalDevelopment(int personalDevelopment) {
                this.personalDevelopment = personalDevelopment;
        }

        public int getDevotion() {
                return devotion;
        }

        public void setDevotion(int devotion) {
                this.devotion = devotion;
        }

        public int getPrioritize() {
                return prioritize;
        }

        public void setPrioritize(int prioritize) {
                this.prioritize = prioritize;
        }

        public int getAchieve() {
                return achieve;
        }

        public void setAchieve(int achieve) {
                this.achieve = achieve;
        }

        public int getDeliver() {
                return deliver;
        }

        public void setDeliver(int deliver) {
                this.deliver = deliver;
        }

        public int getTime() {
                return time;
        }

        public void setTime(int time) {
                this.time = time;
        }

        public int getHandle() {
                return handle;
        }

        public void setHandle(int handle) {
                this.handle = handle;
        }

        public int getPlanning() {
                return planning;
        }

        public void setPlanning(int planning) {
                this.planning = planning;
        }

        public int getImportance() {
                return importance;
        }

        public void setImportance(int importance) {
                this.importance = importance;
        }
		
		public int getToDo() {
                return to_do;
        }

        public void setToDo(int to_do) {
                this.to_do = to_do;
        }
		
		public double getTimeManagementOverall(){
			double tm = 0;
			tm = prioritize + achieve + deliver + time + handle + to_do + planning;
			tm /= 7;
			return tm;
		}
		
		public boolean userNotInThere(ArrayList<User> list){
			int id = getId();
			
			boolean there = true;
			try{
				for (User lus : list){
					if (lus.getId() == id){
						there = false;
						break;
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			return there;
		}
		
		public String getAgePretty(){
			String years = "";
			if (age == 1){
			  years = "18-24";
			}else if(age == 2){
			  years = "25-35";
			}else{
			  years = "+35";
			}
			return years;
		}
		
		
			
}
