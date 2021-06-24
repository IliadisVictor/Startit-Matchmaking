<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Startit.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*"%>



<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/teams.css" />

    <link
      href="https://fonts.googleapis.com/css?family=Poppins"
      rel="stylesheet"
    />
    <link
      href="https://fonts.googleapis.com/css?family=Roboto:500,900,100,300,700,400"
      rel="stylesheet"
      type="text/css"
    />
    <link
      href="https://fonts.googleapis.com/css?family=Montserrat"
      rel="stylesheet"
    /> 
    <script
      src="https://kit.fontawesome.com/aec989c09b.js"
      crossorigin="anonymous"
    ></script>
    <script
      src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"
      integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    ></script>
    <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> -->
   
    <script src="js/teams.js" ></script>
    <title>Teams</title>
  </head>
  <body>
    <% DAO dao = new DAO();
  ArrayList<CompleteTeam> teams = new ArrayList<CompleteTeam>();
  Database dbobj = new Database();
  Connection con = dbobj.getConnection();
  teams = dao.getTeams(con);
  %>
    <nav class="navbar">
      <div class="brand-title">StartIt</div>
      <div class="navbar-links">
        <ul align="left">
          <li><img src="/images/Component 6 – 6.svg" alt="" style="padding: 15px 0 0 0;"></li>
          <li><a href="homepage.jsp">Home</a></li>
          <li><a href="applicants.jsp">Applicants</a></li>
          <li><a href="teams.jsp" id="active">Teams</a></li>
        </ul>
      </div>
    </nav>
    <main class="main">
      <div class="title-button">
        	<h1>Showing <%= teams.size()%> applicants</h1>
        	<div class="matchmaking">
          		<button onclick="matchmaking()">
            			<img id="matchmaking-img" src="images/matchmaking.svg" alt=""></a>
          		</button>
          		<button id="filters-button" onclick="showDiv()">Filters &nbsp;&nbsp;&nbsp; <img id="filter-img" src="images/filters-icon.svg" alt=""></button>
        	</div>
      </div>
      <div id="matchmaking-popup" >
          <form method="post" action="<%=request.getContextPath() %>/StartIt/addTeamController.jsp">
            <input type="hidden" name="url" value="team-profile.jsp">
            <button id="add-team" type="submit">Add Team</button>
          </form>
        <button id="matchmaking">Matchmaking</button>
      </div>



      <div id="filters">
        <select name="Study Field" id="filter-Study" class="filter">
          <option value="0">Study Field</option>
          <option value="Business Administration">
            Business Administration
          </option>
          <option value="Marketing">Marketing</option>
          <option value="Informatics">Informatics</option>
          <option value="Engineering">Engineering</option>
          <option value="Economic Studies">Economic Studies</option>
          <option value="Studies of Law">Studies of Law</option>
          <option value="Health Studies">Health Studies</option>
          <option value="Pedagogical Studies">Pedagogical Studies</option>
          <option value="Other">Other</option>
        </select>
        <select name="Education Level" id="filter-Education" class="filter">
          <option value="0" selected>Education Level</option>
          <option value="Bachelor Degree">Bachelor Degree</option>
          <option value="Master Degree">Master Degree</option>
          <option value="PhD CandidatePhD Candidate</option>
          <option value="PhD Holder">PhD Holder</option>
        </select>
        <select name="Age" id="filter-Age" class="filter">
          <option value="0" selected>Age</option>
          <option value="18-24">18-25</option>
          <option value="18-35">25-35</option>
          <option value="18-35+">35+</option>
        </select>
        <select name="Experience" id="filter-Experience" class="filter">
          <option value="-1" selected>Experience</option>
          <option value="0">0 years</option>
          <option value="1-2">1-2 years</option>
          <option value="2-5">2-5 years</option>
          <option value="5-10">5-10 years</option>
          <option value="10+">More than 10 years</option>
        </select>
        <select name="Status" id="filter-Status" class="filter">
          <option value="0" selected>Status</option>
          <option value="Assigned">Assigned</option>
          <option value="Pending">Pending</option>
        </select>
      </div>

      <div class="grid">
      <% for (CompleteTeam team: teams){ 
        int i = team.getId();%>
        <div class="card">
          <div class="title-status">
            <h2>Team: <%= i %> </h2>
            <h4 id="status" data-status="Approved">
              <% if (team.getStatus()==1){ %>
              <i class="fas fa-circle fa-xs" style="color: #24a148"></i>
              <%} else{ %>
                <i class="fas fa-circle fa-xs" style="color:#ecd701"></i>
              <% } %>
              <%= team.getStatusPreety() %>
            </h4>
          </div>
          <div class="content">
            <div class="top" >
              <h5><%= team.ageTag()%></h5>
              <h5><%= team.eduLevelTag()%></h4\5>
              <h5><%= team.getMembers().size()%> Members</h5>
              <h5><%= team.expYearsTag()%> years exp.</h5>
            </div>
            <div class="middle">
              <div class="text">
                <h5>Diversity</h5>
                <h5 style="color: #707070;"> <%= team.getDiversity()%></h5>
              </div>
              <div style="border-radius: 8px; height: 5px; background-color: #60E1CC47;">
                <div style= "border-radius: 8px; height: 5px; width:<%= team.getDiversity()%>%; background-color: #24a148;">&nbsp;</div>
              </div>
            </div>
            <div class="bottom" >
              <form method="post" action="<%=request.getContextPath() %>/StartIt/teamsController.jsp">
                <input type="hidden" name="teamID" value="<%= i %>">
                <input type="hidden" name="url" value="team-profile.jsp">
                <button  class="team-button">Team Profile</button>
              </form>
              
            </div>
          </div>
        </div>
        
      
        <%}%>
      </div>
        
        
        
      
    </main>
  </body>
</html>
