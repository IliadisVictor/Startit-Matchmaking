<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Startit.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*"%>

<%
Database db = new Database();
Connection con = db.getConnection();
DAO dao = new DAO();
ArrayList<User> users = dao.getUnassignedUsers(con);
String teamid=request.getParameter("teamID");
int id=Integer.parseInt(teamid);   
CompleteTeam team = dao.getTeamById(id, con); 
%>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/add-members.css" />
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
      href="https://fonts.googleapis.com/css2?family=Montserrat:wght@700&display=swap"
      rel="stylesheet"
    />
    <script src="https://kit.fontawesome.com/aec989c09b.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> -->
    <script src="js/add-members.js" defer></script>
    <title>Add Members</title>
  </head>
  <body>
    <nav class="navbar">
      <div class="brand-title">StartIt</div>
      <div class="navbar-links">
        <ul>
          <li><img src="images/navbar-line.svg" alt="" style="padding: 15px 0 0 0;"></li>
          <li><a href="homepage.jsp">Home</a></li>
          <li><a href="applicants.jsp">Applicants</a></li>
          <li><a href="teams.jsp">Teams</a></li>
        </ul>
      </div>
    </nav>
    <main class="main">
      <div class="title-button">
        <h1 id="numberOfApplicants">Adding to team <%=id%></h1>
        <button id="filters-button" onclick="showDiv()">Filters &nbsp;&nbsp;&nbsp; <img id="filter-img" src="images/filters-icon.svg" alt=""></button>
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
          <option value="18-25">18-25</option>
          <option value="25-35">25-35</option>
          <option value="35+">35+</option>
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

      <table align="center">
        <thead>
          <tr>
            <th>Fullname</th>
            <th>Study Field</th>
            <th>Education Level</th>
            <th>Age</th>
            <th>Experience</th>
            <th>Diversity</th>
          </tr>
        </thead>

        <tbody id="tableData" align="center">
        <% for(User u : users){
          int i =  u.getIdFromDB(con);
         %>   
        
          <tr>
            <td class="Name"><%= u.getName() + " " + u.getSurname()%></td>
            <td class="Study" data-Study="<%=u.getFieldPretty()%>"><%= u.getFieldPretty() %></td>
            <td class="Education" data-Education="<%=u.getLevelPretty()%>"><%= u.getLevelPretty()%></td>
            <td class="Age" data-Age="<%=u.getAgePretty()%>"><%= u.getAgePretty() %></td>
            <td class="Experience" data-Experience="<%=u.getExpYears()%>"><%= u.getExpYears()%></td>
            <td class="Diversity" data-Diversity="<%= team.showChangeOnDiversityIf(u, "added")%>"><%= team.showChangeOnDiversityIf(u, "added")%> <img src="images/PositiveDiversity.svg" alt=""></td>
            <form method="post" action="<%=request.getContextPath() %>/StartIt/teamProfileController4.jsp">
              <input type="hidden" name="userID" value="<%= i%>">
              <input type="hidden" name="teamID" value="<%= teamid%>">
              <input type="hidden" name="url" value="team-profile.jsp">
              
              <td><button><img src="images/add-member.svg" alt="" style="cursor: pointer;"></button></td>
              
            </form>
          </tr>
         <%}%>
        </tbody>
      </table>
    </main>
  </body>
</html>
