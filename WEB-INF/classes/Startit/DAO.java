package Startit;

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

public class DAO {

    public void createEmptyTeam(Connection con) throws Exception{
        int status = 0;
        double diversity=-1;
        String sqlinsert = "INSERT INTO teams (diversity,status_) values (?,?) ";
        try {

            PreparedStatement statement = con.prepareStatement(sqlinsert);
            statement.setDouble(1, diversity);
            statement.setInt(2,status);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }
    public void createUser(User user, Connection con ) throws Exception { 
        String sqlinsert = "INSERT INTO users (firstname,lastname,email,age,uni,lvl,field,occupationType,expYears,desire,course,designComp,speech,visit,conference,simulator,incubator,biznamanOrNo,softskills,knowledge,financial,social,startupexp,virtualexp,leadership,contribution,startupStage,caution,motto,chances,smallgoal,risks,optimism,expect,count,overall,personalDev,devotion,prioritize,achieve,deliver,time_,handle,todo,planning,importance,status_) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = con.prepareStatement(sqlinsert);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getAge());
            statement.setString(5, user.getUni());
            statement.setString(6, user.getLevel());
            statement.setString(7, user.getField());
            statement.setString(8, user.getOccupationType());
            statement.setString(9, user.getExpYears());
            statement.setString(10, user.getDesire());
            statement.setString(11, user.getCourse());
            statement.setInt(12, user.getDesignCompetition());
            statement.setInt(13, user.getSpeech());
            statement.setInt(14, user.getVisit());
            statement.setInt(15, user.getConference());
            statement.setInt(16, user.getSimulator());
            statement.setInt(17, user.getIncubator());
            statement.setInt(18, user.getBiznamanOrNo());
            statement.setInt(19, user.getSoftSkills());
            statement.setInt(20, user.getKnowledge());
            statement.setInt(21, user.getFinancial());
            statement.setInt(22, user.getSocial());
            statement.setInt(23, user.getStartupExperience());
            statement.setInt(24, user.getVirtualExp());
            statement.setInt(25, user.getLeadership());
            statement.setInt(26, user.getContribution());
            statement.setInt(27, user.getStartupStage());
            statement.setInt(28, user.getCaution());
            statement.setInt(29, user.getMotto());
            statement.setInt(30, user.getChances());
            statement.setInt(31, user.getSmallgoal());
            statement.setInt(32, user.getRisks());
            statement.setInt(33, user.getOptimism());
            statement.setInt(34, user.getExpect());
            statement.setInt(35, user.getCount());
            statement.setInt(36, user.getOverall());
            statement.setInt(37, user.getPersonalDevelopment());
            statement.setInt(38, user.getDevotion());
            statement.setInt(39, user.getPrioritize());
            statement.setInt(40, user.getAchieve());
            statement.setInt(41, user.getDeliver());
            statement.setInt(42, user.getTime());
            statement.setInt(43, user.getHandle());
            statement.setInt(44, user.getToDo());
            statement.setInt(45, user.getPlanning());
            statement.setInt(46, user.getImportance());
            statement.setBoolean(47, user.getIsAssigned());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void createTeam(Team team,Connection con) throws Exception {
        //int status = team.getStatus();
        int status = 0;
        String sqlinsert = "INSERT INTO teams (diversity,status_) values (?,?) ";
        double div = team.getDiversity();
        ArrayList<User> users = team.getMembers();
        try {

            PreparedStatement statement = con.prepareStatement(sqlinsert);
            statement.setDouble(1, div);
            statement.setInt(2,status);
            statement.executeUpdate();
            int teamid = getlastteamid(con);

            matchTeamUsers(team, teamid,con);

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

    public void matchTeamUsers(Team team, int teamid,Connection con) throws Exception {

        try {
            ArrayList<User> users = team.getMembers();

            for (User u: users) {
				changeUserStatus(u.getIdFromDB(con), true,con);
                String sql = "insert into usersinteam (teamID,userID) value (?,?) ";
                PreparedStatement statement2 = con.prepareStatement(sql);
                statement2.setInt(1, teamid);
				statement2.setInt(2, u.getIdFromDB(con));
                statement2.executeUpdate();
				statement2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
        }
    }

    public void insertUserToTeam(int userid, int teamid,Connection con ) throws Exception {
        CompleteTeam ct = getTeamById(teamid,con);
		User us = getUserById(userid,con);
		ct.addMember(us);
        try {
            String sql = "insert into usersinteam (teamID,userID) values (?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, teamid);
            statement.setInt(2, userid);
            statement.executeUpdate();
            statement.close();
            String sql2= "update teams set diversity = ? where teamID= ? ";
            PreparedStatement statement2 = con.prepareStatement(sql2);
            statement2.setDouble(1,ct.getDiversity());
            statement2.setInt(2,teamid);
            statement2.executeUpdate();
            statement2.close();
            String sql3 = "update users set status_=? where userid=?";
            PreparedStatement statement3 = con.prepareStatement(sql3);
            statement3.setBoolean(1, true);
            statement3.setInt(2, userid);
            statement3.executeUpdate();
            statement3.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public int getlastteamid(Connection con) throws Exception {
        int id = -1;
        String sql = "SELECT teamID FROM teams ORDER BY teamID DESC LIMIT 1";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            id = rs.getInt("teamID");
        }
		statement.close();
		return id;
 
    }

    public int findIdByEmail(String email,Connection con) throws Exception {
        String sqlinsert = "select userID from users where email= ? ";
        try {
            int id = -1;
            PreparedStatement statement = con.prepareStatement(sqlinsert);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                id = rs.getInt("userID");
            }

            statement.close();
            return id;
        } finally {
        }
    }

    public void deleteTeam(int teamid,Connection con) throws Exception {
        String sqldelete = "delete from teams where teamID= ? ";
        try {
            CompleteTeam t = getTeamById(teamid, con);
            PreparedStatement statement = con.prepareStatement(sqldelete);
            statement.setInt(1, teamid);
            statement.executeUpdate();
            deleterelations(teamid,con);
            for(User user : t.getMembers()){
                changeUserStatus( user.getIdFromDB(con),  false, con);
            }
			statement.close();
           
        } finally {
        }
    }

    public void deleterelations(int teamid,Connection con) throws Exception {
        String sqldelete = "delete from usersinteam where teamID= ? ";
        try {
            PreparedStatement statement = con.prepareStatement(sqldelete);
            statement.setInt(1, teamid);
            statement.executeUpdate();
			statement.close();
        } finally {
        }
    }

    public int getUserId(String email,Connection con) throws Exception {
        return findIdByEmail(email,con);
    }
	public ArrayList<User> getUnassignedUsers(Connection con){

   ArrayList<User> users = new ArrayList<User>();
        try{
            String sql = "SELECT * FROM users WHERE status_=?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setBoolean(1, false);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
				int id = rs.getInt("userID");
                String name = rs.getString("firstname");
                String lname=rs.getString("lastname");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                String uni = rs.getString("uni");
                String lvl = rs.getString("lvl");
                String field = rs.getString("field");
                String occType = rs.getString("occupationType");
                String expYears = rs.getString("expYears");
                String desire = rs.getString("desire");
                String course = rs.getString("course");
                int designComp = rs.getInt("designComp");
                int speech = rs.getInt("speech");
                int visit = rs.getInt("visit");
                int conference=rs.getInt("conference");
                int simulator=rs.getInt("simulator");
                int incubator=rs.getInt("incubator");
                int biznamanOrNo=rs.getInt("biznamanOrNo");
                int softskills=rs.getInt("softskills");
                int knowledge=rs.getInt("knowledge");
                int financial=rs.getInt("financial");
                int social = rs.getInt("social");
                int startupexp=rs.getInt("startupexp");
                int virtualexp=rs.getInt("virtualexp");
                int leadership=rs.getInt("leadership");
                int contribution=rs.getInt("contribution");
                int startupStage=rs.getInt("startupStage");
                int caution = rs.getInt("caution");
                int motto = rs.getInt("motto");
                int chances = rs.getInt("chances");
                int smallgoal=rs.getInt("smallgoal");
                int risks=rs.getInt("risks");
                int optimism=rs.getInt("optimism");
                int expect= rs.getInt("expect");
                int count = rs.getInt("count");
                int overall=rs.getInt("overall");
                int personalDev=rs.getInt("personalDev");
                int devotion=rs.getInt("devotion");
                int prioritize=rs.getInt("prioritize");
                int achieve = rs.getInt("achieve");
                int deliver=rs.getInt("deliver");
                int time_=rs.getInt("time_");
                int handle = rs.getInt("handle");
                int todo = rs.getInt("todo");
                int planning=rs.getInt("planning");
                int importance=rs.getInt("importance");
                User u = new User(name,lname,email,age,uni,lvl,field,occType,expYears,desire,course,designComp,speech,visit,conference,simulator,incubator,biznamanOrNo,softskills,knowledge,financial,social,startupexp,virtualexp,leadership,contribution,startupStage,caution,motto,chances,smallgoal,risks,optimism,expect,count,overall,personalDev,devotion,prioritize,achieve,deliver,time_,handle,todo,planning,importance);
            
				u.setId(id);
				users.add(u);
            }
            statement.close();
        }finally{
			return users;
        }
    }
    public void deleteMemberFromTeam(int userid, int teamid,Connection con) throws Exception{
		CompleteTeam ct = getTeamById(teamid,con);
		User us = getUserById(userid,con);
		ct.removeMember(us);
        
        String sqldelete = "delete from usersinteam where userID= ? ";
        String sqldelete2= "update teams set diversity = ? where teamID=?";
        String sqldelete3="update users set status_=? where userID=? ";
        try {
            PreparedStatement statement = con.prepareStatement(sqldelete);
            statement.setInt(1, userid);
            statement.executeUpdate();
			statement.close();
            PreparedStatement statement2=con.prepareStatement(sqldelete2);
            statement2.setDouble(1, ct.getDiversity());
            statement2.setInt(2, teamid);
            statement2.executeUpdate();
            statement2.close();
            PreparedStatement statement3=con.prepareStatement(sqldelete3);
            statement3.setBoolean(1, false);
            statement3.setInt(2, userid);
            statement3.executeUpdate();
            statement3.close();
        } finally {
        }
    }

    public void changeTeamStatus(int status, int teamid,Connection con) throws Exception{
        String sqldelete = "update teams set status_=? where teamID= ? ";
        try {
            PreparedStatement statement = con.prepareStatement(sqldelete);
            statement.setInt(1, status);
            statement.setInt(2, teamid);
            statement.executeUpdate();	
			statement.close();
        } finally {
		
        }
    }

    public ArrayList<User> getUsers(Connection con) throws Exception{
        ArrayList<User> users = new ArrayList<User>();
        try{
            String sql = "SELECT * FROM users ";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("firstname");
                String lname=rs.getString("lastname");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                String uni = rs.getString("uni");
                String lvl = rs.getString("lvl");
                String field = rs.getString("field");
                String occType = rs.getString("occupationType");
                String expYears = rs.getString("expYears");
                String desire = rs.getString("desire");
                String course = rs.getString("course");
                int designComp = rs.getInt("designComp");
                int speech = rs.getInt("speech");
                int visit = rs.getInt("visit");
                int conference=rs.getInt("conference");
                int simulator=rs.getInt("simulator");
                int incubator=rs.getInt("incubator");
                int biznamanOrNo=rs.getInt("biznamanOrNo");
                int softskills=rs.getInt("softskills");
                int knowledge=rs.getInt("knowledge");
                int financial=rs.getInt("financial");
                int social = rs.getInt("social");
                int startupexp=rs.getInt("startupexp");
                int virtualexp=rs.getInt("virtualexp");
                int leadership=rs.getInt("leadership");
                int contribution=rs.getInt("contribution");
                int startupStage=rs.getInt("startupStage");
                int caution = rs.getInt("caution");
                int motto = rs.getInt("motto");
                int chances = rs.getInt("chances");
                int smallgoal=rs.getInt("smallgoal");
                int risks=rs.getInt("risks");
                int optimism=rs.getInt("optimism");
                int expect= rs.getInt("expect");
                int count = rs.getInt("count");
                int overall=rs.getInt("overall");
                int personalDev=rs.getInt("personalDev");
                int devotion=rs.getInt("devotion");
                int prioritize=rs.getInt("prioritize");
                int achieve = rs.getInt("achieve");
                int deliver=rs.getInt("deliver");
                int time_=rs.getInt("time_");
                int handle = rs.getInt("handle");
                int todo = rs.getInt("todo");
                int planning=rs.getInt("planning");
                int importance=rs.getInt("importance");
				boolean isAssigned = rs.getBoolean("status_");
                User u = new User(name,lname,email,age,uni,lvl,field,occType,expYears,desire,course,designComp,speech,visit,conference,simulator,incubator,biznamanOrNo,softskills,knowledge,financial,social,startupexp,virtualexp,leadership,contribution,startupStage,caution,motto,chances,smallgoal,risks,optimism,expect,count,overall,personalDev,devotion,prioritize,achieve,deliver,time_,handle,todo,planning,importance);
				u.setIsAssigned(isAssigned);
				int id = this.findIdByEmail(email,con);
				u.setId(id);
				users.add(u);
            }
            statement.close();
        }finally{
			return users;
        }
    }

    public ArrayList<CompleteTeam> getTeams(Connection con) throws Exception{
        ArrayList<CompleteTeam> teams = new ArrayList<CompleteTeam>();
        try{
            String sql = "SELECT * FROM teams ";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            int id = -1;
            double diversity=-1;
            int status =-1;
            while (rs.next()) {
                id = rs.getInt("teamID");
                diversity= rs.getDouble("diversity");
                status=rs.getInt("status_");
                String sql2="SELECT * FROM usersinteam WHERE teamid=?";
                PreparedStatement statement2=con.prepareStatement(sql2);
                statement2.setInt(1, id);
                ResultSet rs2 = statement2.executeQuery();
                ArrayList<User> users = new ArrayList<User>();
                while(rs2.next()){
                    int userid=rs2.getInt("userID");
                    User u = getUserById(userid, con);
                    users.add(u);
                }
                CompleteTeam t = new CompleteTeam(users);
                t.setId(id);
                t.setStatus(status);
                teams.add(t);
            }
        	statement.close();

            return teams;
        }finally{
			return teams;
        }
    }
    /*public ArrayList<CompleteTeam> getTeams(Connection con) throws Exception{
        ArrayList<CompleteTeam> teams = new ArrayList<CompleteTeam>();
        ArrayList<Integer> ids = new ArrayList<Integer>();

        try{
            String sql = "SELECT * FROM teams ";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("teamID");
                ids.add(id);
            }
            for(int i : ids){
                CompleteTeam t = this.getTeamById(i,con);
                teams.add(t);
            }
        	statement.close();
        }finally{
			return teams;
        }
    }*/
	
    public CompleteTeam getTeamById(int teamid,Connection con) throws Exception{
        //int status = team.getStatus();
        int status = 0;
        ArrayList<User> members = new ArrayList<User>();
		double diversity = 0;
        String sqlselect = "SELECT * FROM teams WHERE teamID = ?";
        try {
            PreparedStatement statement = con.prepareStatement(sqlselect);
            statement.setInt(1, teamid);
            ResultSet rs = statement.executeQuery();
			while (rs.next()){
				teamid = rs.getInt("teamID");
				diversity = rs.getDouble("diversity");
				status = rs.getInt("status_");
			}
			sqlselect = "SELECT * FROM usersinteam WHERE teamID = ?";
			PreparedStatement statement2 = con.prepareStatement(sqlselect);
			statement2.setInt(1, teamid);
            ResultSet rs2 = statement2.executeQuery();
			while (rs2.next()){
				int uid = rs2.getInt("userID");
				User u = getUserById(uid,con);
				members.add(u);
			}
            statement.close();
			statement2.close();
        } finally {
			CompleteTeam ct = new CompleteTeam(members);
			ct.setId(teamid);
			ct.setStatus(status);
			return ct;
        }
	}
	public User getUserById(int id,Connection con) throws Exception{
		User u = new User();
        try{
            String sql = "SELECT * FROM users WHERE userID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("firstname");
                String lname=rs.getString("lastname");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                String uni = rs.getString("uni");
                String lvl = rs.getString("lvl");
                String field = rs.getString("field");
                String occType = rs.getString("occupationType");
                String expYears = rs.getString("expYears");
                String desire = rs.getString("desire");
                String course = rs.getString("course");
                int designComp = rs.getInt("designComp");
                int speech = rs.getInt("speech");
                int visit = rs.getInt("visit");
                int conference=rs.getInt("conference");
                int simulator=rs.getInt("simulator");
                int incubator=rs.getInt("incubator");
                int biznamanOrNo=rs.getInt("biznamanOrNo");
                int softskills=rs.getInt("softskills");
                int knowledge=rs.getInt("knowledge");
                int financial=rs.getInt("financial");
                int social = rs.getInt("social");
                int startupexp=rs.getInt("startupexp");
                int virtualexp=rs.getInt("virtualexp");
                int leadership=rs.getInt("leadership");
                int contribution=rs.getInt("contribution");
                int startupStage=rs.getInt("startupStage");
                int caution = rs.getInt("caution");
                int motto = rs.getInt("motto");
                int chances = rs.getInt("chances");
                int smallgoal=rs.getInt("smallgoal");
                int risks=rs.getInt("risks");
                int optimism=rs.getInt("optimism");
                int expect= rs.getInt("expect");
                int count = rs.getInt("count");
                int overall=rs.getInt("overall");
                int personalDev=rs.getInt("personalDev");
                int devotion=rs.getInt("devotion");
                int prioritize=rs.getInt("prioritize");
                int achieve = rs.getInt("achieve");
                int deliver=rs.getInt("deliver");
                int time_=rs.getInt("time_");
                int handle = rs.getInt("handle");
                int todo = rs.getInt("todo");
                int planning=rs.getInt("planning");
                int importance=rs.getInt("importance");
				boolean isAssigned = rs.getBoolean("status_");
                u = new User(name,lname,email,age,uni,lvl,field,occType,expYears,desire,course,designComp,speech,visit,conference,simulator,incubator,biznamanOrNo,softskills,knowledge,financial,social,startupexp,virtualexp,leadership,contribution,startupStage,caution,motto,chances,smallgoal,risks,optimism,expect,count,overall,personalDev,devotion,prioritize,achieve,deliver,time_,handle,todo,planning,importance);
				u.setIsAssigned(isAssigned);
				u.setId(id);
            }
            statement.close();
        }finally{
			return u;
        }
    }

	
		/***

    public User getUserById(int userid) throws Exception{
        User u = new User();
        Database dataObj = new Database();
        
        try{
			Connection con = dataObj.getConnection();
            String sql = "SELECT * FROM users where userID=? ";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, userid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("firstname");
                String lname=rs.getString("lastname");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                String uni = rs.getString("uni");
                String lvl = rs.getString("lvl");
                String field= rs.getString("field");
                String occType = rs.getString("occupationType");
                String expYears = rs.getString("expYears");
                String desire = rs.getString("desire");
                String course = rs.getString("course");
                int designComp = rs.getInt("designComp");
                int speech = rs.getInt("speech");
                int visit = rs.getInt("visit");
                int conference=rs.getInt("conference");
                int simulator=rs.getInt("simulator");
                int incubator=rs.getInt("incubatobiznamanOrNo");
                int softskills=rs.getInt("softskills");
                int knowledge=rs.getInt("knowledge");
                int financial=rs.getInt("financial");
                int social = rs.getInt("social");
                int startupexp=rs.getInt("startupexp");
                int virtualexp=rs.getInt("virtualexp");
                int leadership=rs.getInt("leadership");
                int contribution=rs.getInt("contribution");
                int startupStage=rs.getInt("startupStage");
                int caution = rs.getInt("caution");
                int motto = rs.getInt("mottor");
                int biznamanOrNo=rs.getInt("");
                int chances = rs.getInt("chances");
                int smallgoal=rs.getInt("smallgoal");
                int risks=rs.getInt("risks");
                int optimism=rs.getInt("optimism");
                int expect= rs.getInt("expect");
                int count = rs.getInt("count");
                int overall=rs.getInt("overall");
                int personalDev=rs.getInt("personalDev");
                int devotion=rs.getInt("devotion");
                int prioritize=rs.getInt("prioritize");
                int achieve = rs.getInt("achieve");
                int deliver=rs.getInt("deliver");
                int time_=rs.getInt("time_");
                int handle = rs.getInt("handle");
                int todo = rs.getInt("todo");
                int planning=rs.getInt("planning");
                int importance=rs.getInt("importance");
                u = new User(name,lname,email,age,uni,lvl,field,occType,expYears,desire,course,designComp,speech,visit,conference,simulator,incubator,biznamanOrNo,softskills,knowledge,financial,social,startupexp,virtualexp,leadership,contribution,startupStage,caution,motto,chances,smallgoal,risks,optimism,expect,count,overall,personalDev,devotion,prioritize,achieve,deliver,time_,handle,todo,planning,importance);
				int id = this.findIdByEmail(email);
				u.setId(id);
				dataObj.close();
				con.close();
				
			}
        }catch (Exception e){
			e.printStackTrace();		
		}finally{
			return u;
        }
    }
	***/
	
    public void changeUserStatus(int userid, boolean status, Connection con) throws Exception{
        String sqldelete = "update users set status_=? where userID= ? ";
        try {
            PreparedStatement statement = con.prepareStatement(sqldelete);
            statement.setBoolean(1, status);
            statement.setInt(2, userid);
            statement.executeUpdate();
			statement.close();
        } finally {
        }
    }

    public ArrayList<Integer> getFieldAbsolute(Connection con) throws Exception{
        String sql = "SELECT field FROM users";
        int business_count=0;
        int marketing_count=0;
        int cs_count=0;
        int engineering_count=0;
        int economics_count=0;
        int law_count=0;
        int health_count=0;
        int teaching_count=0;
        int other_count=0;
        ArrayList<Integer> x = new ArrayList<Integer>();
        ArrayList<String> s = new ArrayList<String>();
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String field = rs.getString("field");
            s.add(field);
            if(field.equals("business")){
                business_count+=1;
            }else if(field.equals("marketing")){
                marketing_count+=1;
            }else if(field.equals("cs")){
                cs_count+=1;
            }else if(field.equals("engineering")){
                engineering_count+=1;
            }else if(field.equals("economics")){
                economics_count+=1;
            }else if(field.equals("law")){
                law_count+=1;
            }else if(field.equals("health")){
                health_count+=1;
            }else if(field.equals("teaching")){
                teaching_count+=1;
            }else{
                other_count+=1;
            }
        }
        x.add(business_count);
        x.add(marketing_count);
        x.add(cs_count);
        x.add(engineering_count);
        x.add(economics_count);
        x.add(law_count);
        x.add(health_count);
        x.add(teaching_count);
        x.add(other_count);
        return x;
    }
    public ArrayList<Double> getFieldPercentages(Connection con) throws Exception{
        
        ArrayList<Integer> ints = getFieldAbsolute(con);
        int total = getUsers(con).size();
        double dtotal=Double.valueOf(total);
        ArrayList<Double> percentages = new ArrayList<Double>();
        for(int i : ints ) {
            double d = Double.valueOf(i);
            double res = d/dtotal*100;
            percentages.add(roundDouble(res));
        }
        return percentages;
    }

    public int getNumOfUnassignedUsers(Connection con) throws Exception{
        String sql = "select * from users where status_=0";
        PreparedStatement statement=con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        int count= 0; 
        while(rs.next()){
            count+=1;
        }
        return count;
    }
    public int getNumOfPendingTeams(Connection con) throws Exception{
        String sql = "select count(*) from teams WHERE status_=0";
        PreparedStatement statement=con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        int count=-1; 
        while(rs.next()){
            count=rs.getInt("count(*)");
        }
        return count;
    }
    public int getNumOfTeams(Connection con) throws Exception{
        String sql = "select count(*) from teams ";
        PreparedStatement statement=con.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        int count=-1; 
        while(rs.next()){
            count=rs.getInt("count(*)");
        }
        return count;
    }
	
	public static double roundDouble(double d){
        double roundedDouble = Math.round(d * 10.0) / 10.0;
		return roundedDouble;
	}
}