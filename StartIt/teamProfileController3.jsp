<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Startit.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*"%>

<% try{
    Database db = new Database();
    Connection conn = db.getConnection();
    String teamid=request.getParameter("teamID");
    String status = request.getParameter("Status");
    DAO dao = new DAO();
    int tid=Integer.parseInt(teamid);
    if(status.equals("0")){
        dao.changeTeamStatus(0, tid, conn);

    }else if(status.equals("1")){
        dao.changeTeamStatus(1, tid, conn);

    }
    String url="team-profile.jsp";
    url+= "?" + "teamID="+ teamid;
    response.setStatus(response.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", url); 
}finally{
}%>