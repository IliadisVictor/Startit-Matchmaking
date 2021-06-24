<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Startit.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*"%>


<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/applicants.css" />
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

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js" type="text/javascript"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css" rel="stylesheet" />

    <script src="https://kit.fontawesome.com/aec989c09b.js" crossorigin="anonymous"></script>
    <script src="js/applicants.js" defer></script>
    <title>Applicants</title>
  </head>
  <body>
    <%
    DAO updater = new DAO();
    Database db = new Database();
    Connection conn = db.getConnection();
    ArrayList<User> users = updater.getUsers(conn);
    %>
        <nav class="navbar">
      <div class="brand-title">StartIt</div>
      <div class="navbar-links">
        <ul>
          <li><img src="images/navbar-line.svg" alt="" style="padding: 15px 0 0 0;"></li>
          <li><a href="homepage.jsp">Home</a></li>
          <li><a href="applicants.jsp" id="active">Applicants</a></li>
          <li><a href="teams.jsp">Teams</a></li>
        </ul>
      </div>
    </nav>
    <main class="main">
      <div class="title-button">
        <h1 id="numberOfApplicants">Showing <%= users.size()  %> applicants</h1>
        <button id="filters-button" onclick="showDiv()">Filters &nbsp;&nbsp;&nbsp; <img id="filter-img" src="images/filters-icon.svg" alt=""></button>
      </div>    
      
      

      <div id="filters">
        <select name="Study Field" id="filter-Study" class="filter" >
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
        <select name="Education Level" id="filter-Education"  class="filter">
          <option value="0" selected>Education Level</option>
          <option value="Bachelor Degree">Bachelor Degree</option>
          <option value="Master Degree">Master Degree</option>
          <option value="PHD Candidate">PhD Candidate</option>
          <option value="PHD Holder">PhD Holder</option>
        </select>
        <select name="Age" id="filter-Age" class="filter">
          <option value="0" selected>Age</option>
          <option value="18-24">18-24</option>
          <option value="25-35">25-35</option>
          <option value="+35">35+</option>
        </select>
        <select name="Experience" id="filter-Experience" class="filter">
          <option value="-1" selected>Experience</option>
          <option value="0">0 years</option>
          <option value="1-2">1-2 years</option>
          <option value="3-4">3-4 years</option>
          <option value="5-10">5-10 years</option>
          <option value="10+">More than 10 years</option>
        </select>
        <select name="Status" id="filter-Status"  class="filter">
          <option value="0" selected>Status</option>
          <option value="Assigned">Assigned</option>
          <option value="Unassigned">Unassigned</option>
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
            <th>Status</th>
          </tr>
        </thead>

        <tbody id="tableData" align="center">
        <% for (User u: users){ %>
            <tr>
                <td class="Name"><%= u.getName() + " " + u.getSurname()%></td>
                <td class="Study" data-Study="<%=u.getFieldPretty()%>"><%= u.getFieldPretty() %></td>
                <td class="Education" data-Education="<%=u.getLevelPretty()%>"><%= u.getLevelPretty()%></td>
                <td class="Age" data-Age="<%=u.getAgePretty()%>"><%= u.getAgePretty() %></td>
                <td class="Experience" data-Experience="<%=u.getExpYears()%>"><%= u.getExpYears()%></td>
                <td class="Status" data-Status="<%=u.getIsAssignedPretty() %>"><% if (u.getIsAssigned()){ %><i class="fas fa-circle fa-xs" style="color: #24A148;"></i><%} else 
                  {%><i class="fas fa-circle fa-xs" style="color: #DA1E28;"></i><%}%> <%= u.getIsAssignedPretty() %></td>
            </tr>
          <% } %>
        </tbody>
      </table>
    </main>
  </body>
</html>
