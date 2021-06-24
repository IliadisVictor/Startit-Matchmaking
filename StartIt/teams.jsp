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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js" type="text/javascript"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet" />
    <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> -->

    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>    
    <script src="js/teams.js" ></script>
    <title>Teams</title>
  </head>
  <body>
    <% DAO dao = new DAO();
  ArrayList<CompleteTeam> teams = new ArrayList<CompleteTeam>();
  Database dbobj = new Database();
  Connection con = dbobj.getConnection();
  teams = dao.getTeams(con);
  CompleteTeam ct = new CompleteTeam();
  teams = ct.sortByDiversity(teams);
  ArrayList<User> unassigned = dao.getUnassignedUsers(con);
  %>
    <nav class="navbar">
      <div class="brand-title">StartIt</div>
      <div class="navbar-links">
        <ul align="left">
          <li><img src="images/navbar-line.svg" alt="" style="padding: 15px 0 0 0;"></li>
          <li><a href="homepage.jsp">Home</a></li>
          <li><a href="applicants.jsp">Applicants</a></li>
          <li><a href="teams.jsp" id="active">Teams</a></li>
        </ul>
      </div>
    </nav>
    <main class="main">
      <div class="title-button">
        	<h1>Showing <%= teams.size()%> teams</h1>
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
          <% if (unassigned.size() >= 10){ %>
          <form method="post" action="<%=request.getContextPath() %>/StartIt/runMatchmaking.jsp">
            <input type="hidden" name="url" value="team-profile.jsp">
            <button id="matchmaking">Matchmaking</button>
          </form>
          <% } %>
      </div>



      <div id="filters">
        <select id="filter-Education" class="js-example-basic-multiple js-example-placeholder-multiple-Education filter" name="Education[]" multiple="multiple">
          <option value="Bachelor">Bachelor Degree</option>
          <option value="Master">Master Degree</option>
          <option value="Phd">Phd</option>
        </select>
        <select id="filter-Age" class="js-example-basic-multiple js-example-placeholder-multiple-Age filter" name="Age[]" multiple="multiple">
          <option value="18-24">18-24</option>
          <option value="25-35">25-35</option>
          <option value="35+">35+</option>
        </select>
        
        <select id="filter-Experience" class="js-example-basic-multiple js-example-placeholder-multiple-Experience filter" name="Experience[]" multiple="multiple">
          <option value="0 years exp.">0 years</option>
          <option value="1-2 years exp.">1-2 years</option>
          <option value="3-4 years exp.">3-4 years</option>
          <option value="5-10 years exp.">5-10 years</option>
          <option value="10+ years exp.">More than 10 years</option>
        </select>
        <select id="filter-Status" class="js-example-basic-multiple  js-example-placeholder-multiple-Status  filter" name="Status" multiple="multiple">
          <option value="Approved">Approved</option>
          <option value="Pending">Pending</option>
        </select>
      </div>

      <div class="grid">
      <% for (CompleteTeam team: teams){ 
        int i = team.getId();%>
        <div class="card">
          <div class="title-status">
            <h2>Team: <%= i %> </h2>
            <h4 class="status" data-status="<%= team.getStatusPreety() %>">
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
              <h5 class="age" data-age="<%= team.ageTag()%>"><%= team.ageTag()%></h5>
              <h5 class="education" data-education="<%= team.eduLevelTag()%>"><%= team.eduLevelTag()%></h5>
              <h5 id="members"><%= team.getMembers().size()%> Members</h5>
              <h5 class="experience" data-experience="<%= team.expYearsTag()%> years exp."><%= team.expYearsTag()%> years exp.</h5>
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
