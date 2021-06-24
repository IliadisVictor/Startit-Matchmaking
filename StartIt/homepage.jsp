<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Startit.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*"%>

<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/homepage.css" />
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script
      src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"
      type="text/javascript"
    ></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/js/select2.min.js"></script>
    <link
      href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.3/css/select2.min.css"
      rel="stylesheet"
    />
    <script
      src="https://kit.fontawesome.com/aec989c09b.js"
      crossorigin="anonymous"
    ></script>
    <script src="js/homepage.js" defer></script>
    <title>Home</title>
  </head>
  <body>
    <nav class="navbar">
      <div class="brand-title">StartIt</div>
      <div class="navbar-links">
        <ul>
          <li>
            <img
              src="images/navbar-line.svg"
              alt=""
              style="padding: 15px 0 0 0"
            />
          </li>
          <li><a href="homepage.jsp" id="active">Home</a></li>
          <li><a href="applicants.jsp">Applicants</a></li>
          <li><a href="teams.jsp">Teams</a></li>
        </ul>
      </div>
    </nav>
    <main class="main">
      <div class="main-top">
        <div class="title">
          <h1>Welcome Home</h1>
          <h3>Ennovation Competion Dashboard</h3>
        </div>
        <div><img src="images/email-icon.svg" alt="" /></div>
      </div>

      <div class="main-content">
        <div class="content-left">
          <div class="title-filter">
            <h2>Candidates Metrics</h2>
            <div class="filter">
              <select name="" id="filter-Study">
                <option value="0">Field of Study</option>
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
                <option value="Other">Other</option></select
              ><i
                style="margin-left: -25px; color: #fafafa; pointer-events: none"
                class="fas fa-chevron-down"
              ></i>
            </div>
          </div>
          <div class="table">
            <table align="left" class="scroll">
              <thead align="center">
                <tr>
                  <th align="left">Field</th>
                  <th>Candidates</th>
                  <th>Percentage</th>
                </tr>
              </thead>
              <tbody id="tableData" align="center">
                <tr>
                  <% Database db = new Database();
                    DAO dao = new DAO();
                    Connection conn = db.getConnection();
                    int numOfApplicants = dao.getUsers(conn).size();
                    ArrayList<Integer> absolutes= dao.getFieldAbsolute(conn); 
                    ArrayList<Double> percentages =dao.getFieldPercentages(conn);
                    ArrayList<User> users = dao.getUsers(conn);
                      %>
                  <td
                  style =" color: #ffffff;" 
                    align="left"
                    class="Study"
                    data-study="Business Administration"
                  >
                    Business Administration
                  </td>
                  <td style =" color: #ffffff;" ><%=absolutes.get(0)%></td>
                  <td style =" color:#ffffff" ><%=percentages.get(0)%>%</td>
                </tr>
                <tr>
                  <td align="left" class="Study" data-study="Marketing">
                    Marketing
                  </td>
                  <td><%=absolutes.get(1)%></td>
                  <td><%=percentages.get(1)%>%</td>
                </tr>
                <tr>
                  <td align="left" class="Study" data-study="Informatics">
                    Informatics
                  </td>
                  <td><%=absolutes.get(2)%></td>
                  <td><%=percentages.get(2)%>%</td>
                </tr>
                <tr>
                  <td align="left" class="Study" data-study="Engineering">
                    Engineering
                  </td>
                  <td><%=absolutes.get(3)%></td>
                  <td><%=percentages.get(3)%>%</td>
                </tr>
                <tr>
                  <td align="left" class="Study" data-study="Economic Studies">
                    Economic Studies
                  </td>
                  <td><%=absolutes.get(4)%></td>
                  <td><%=percentages.get(4)%>%</td>
                </tr>
                <tr>
                  <td align="left" class="Study" data-study="Studies of Law">
                    Studies of Law
                  </td>
                  <td><%=absolutes.get(5)%></td>
                  <td><%=percentages.get(5)%>%</td>
                </tr>
                <tr>
                  <td align="left" class="Study" data-study="Health Studies">
                    Health Studies
                  </td>
                  <td><%=absolutes.get(6)%></td>
                  <td><%=percentages.get(6)%>%</td>
                </tr>
                <tr>
                  <td
                    align="left"
                    class="Study"
                    data-study="Pedagogical Studies"
                  >
                    Pedagogical Studies
                  </td>
                  <td><%=absolutes.get(7)%></td>
                  <td><%=percentages.get(7)%>%</td>
                </tr>
                <tr>
                  <td align="left" class="Study" data-study="Other">Other</td>
                  <td><%=absolutes.get(8)%></td>
                  <td><%=percentages.get(8)%>%</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="content-right">
          <div class="card">
            <h3>Unassigned Candidates</h3>
            <div class="line-number">
              <div class="card-left-line" style="margin-top: -28px">&nbsp;</div>
              <h1 style="margin-top: 12px"><%= dao.getNumOfUnassignedUsers(conn)%></h1>
            </div>
          </div>
          <div class="card">
            <h3>Pending Teams</h3>
            <div class="line-number">
              <div class="card-left-line">&nbsp;</div>
              <h1><%= dao.getNumOfPendingTeams(conn)%></h1>
            </div>
          </div>
          <div class="card">
            <h3>Total Teams</h3>
            <div class="line-number">
              <div class="card-left-line">&nbsp;</div>
              <h1><%= dao.getNumOfTeams(conn)%></h1>
            </div>
          </div>
          <div class="card">
            <h3>Total Applicants</h3>
            <div class="line-number">
              <div class="card-left-line">&nbsp;</div>
              <h1 style="margin-right: -10%"><%= users.size() %></h1>
            </div>
          </div>
        </div>
      </div>
    </main>
  </body>
</html>

