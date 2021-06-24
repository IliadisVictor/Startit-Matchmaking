<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Startit.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*"%>


<% try{
    Database db = new Database();
    Connection conn = db.getConnection();
    String url = request.getParameter("urel");
    String teamid=request.getParameter("teamID");
    int tid=Integer.parseInt(teamid);
    if (url.equals("team-profile.jsp")){
        String userid=request.getParameter("userID");
        int uid=Integer.parseInt(userid); 
        DAO dao = new DAO();
        dao.deleteMemberFromTeam(uid, tid, conn);  
    }
    url+= "?" + "teamID="+ teamid;
    
    response.setStatus(response.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", url); 
}finally{
}%>