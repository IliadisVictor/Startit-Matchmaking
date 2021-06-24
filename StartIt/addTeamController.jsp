<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Startit.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*"%>


<% try{
    Database db = new Database();
    Connection conn = db.getConnection();
    DAO dao = new DAO();
    String teamid=request.getParameter("teamID");
    int lasteamid=dao.getlastteamid(conn);
    lasteamid+=1;
    ArrayList<User> users = new ArrayList<User>();
    Team t = new Team(users);
    dao.createEmptyTeam(conn);
    String url="team-profile.jsp";
    url+= "?" + "teamID="+ lasteamid;
    
    response.setStatus(response.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", url); 
}finally{
}%>