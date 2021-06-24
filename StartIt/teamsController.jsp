<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Startit.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*"%>


<% try{
    String url = request.getParameter("url");
    String teamid=request.getParameter("teamID");
    url+= "?" + "teamID="+ teamid;
    
    String site = new String("team-profile.jsp?#");
    response.setStatus(response.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", url); 
}finally{
}%>