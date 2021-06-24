<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Startit.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*"%>

<% try{
    Database db = new Database();
    Connection conn = db.getConnection();
    String url = request.getParameter("url");
    String teamid=request.getParameter("teamID");
    int tid=Integer.parseInt(teamid);
    DAO dao = new DAO();
    dao.deleteTeam(tid, conn);
    url="teams.jsp";
    response.setStatus(response.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", url); 
}finally{
}%>