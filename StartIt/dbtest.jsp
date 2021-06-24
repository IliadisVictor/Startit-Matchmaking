<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Startit.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*"%>

<% 
DAO updater = new DAO(); 
Database db = new Database();
Connection conn = db.getConnection();
updater.deleteMemberFromTeam(1222,251,conn);
%>
<h3>deleted 1222 from 251</h3>