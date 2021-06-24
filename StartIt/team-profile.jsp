<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Startit.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*"%>

<%
Database db = new Database();
Connection conn = db.getConnection();
DAO dao = new DAO();
String teamid=request.getParameter("teamID");
int id=Integer.parseInt(teamid);  
CompleteTeam team = dao.getTeamById(id,conn);
ArrayList<User> members = team.getMembers();
int status = team.getStatus();
%>
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/team-profile.css" />
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
    <script src="js/team-profile.js" defer></script>
    <title>Team Profile</title>
  </head>
  <body>
    <nav class="navbar blur">
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
          <li><a href="homepage.jsp">Home</a></li>
          <li><a href="applicants.jsp">Applicants</a></li>
          <li><a href="teams.jsp">Teams</a></li>
        </ul>
      </div>
    </nav>
    <main class="main">
      <div class="pop-up">
        <h2>Are you sure you want to delete the Team ?</h2>
        <div id="popUpLine">&nbsp</div>
        <h4>If you delete the team you wont be able to recover it.</h4>
        <div class="popUpButtons">
          <button id="keep" onclick="popUp()">Keep team</button>
	  <form  method="post" action="<%=request.getContextPath() %>/StartIt/teamprofcontr2.jsp">
	  	<input type="hidden" name="teamID" value="<%= id %>">
		<button id="delete" onclick="popUp()" type="submit" >Delete Team</button>
	  </form
        </div>
      </div>
</div>

      <!-- User profile pop-up -->
      <div id="user-profile">
        <div class="column">
          <div class="row">
            <div class="memberImage" id="user-profile-memberImage"></div>
            <div class="cancel-circle" onclick="userprofile()">
              <img src="images/cancelx.svg" alt="" />
            </div>
          </div>
          <h1 class="header" id="user-profile-name">Iliadis Victor</h1>
          <div class="row-selects">
            <!-- Changing link status with id->selected -->
            <a id="selected" href="">Candidate Info</a>
            <a href="">Questionnaire</a>
            <a href="">Candidate Team</a>
          </div>
          <!-- This is the part that will change onclick alongside the selected id  -->
          <div class="content">
            <div class="info-column">
              <div class="info-row">
                <h1 id="grey">Age</h1>
                <h1>18-24</h1>
              </div>
              <div class="info-row">
                <h1 id="grey">Study Field</h1>
                <h1>Engineering</h1>
              </div>
              <div class="info-row">
                <h1 id="grey">Education Level</h1>
                <h1>Bachelor</h1>
              </div>
            </div>
            <div class="info-column">
              <div class="info-row">
                <h1 id="grey">Time Devotion</h1>
                <h1>18-24</h1>
              </div>
              <div class="info-row">
                <h1 id="grey">Experience</h1>
                <h1>10+</h1>
              </div>
              <div class="info-row">
                <h1 id="grey">Startup Experience</h1>
                <h1>4-6</h1>
              </div>
            </div>
          </div>
        </div>
      </div>


      <div class="name-status blur">
        <h2 id="name">Team <%= id %>
           </h2>
        <div class="status-bin">
          <form method="post" action="<%=request.getContextPath() %>/StartIt/teamProfileController3.jsp">
            <select name="Status" onchange="this.form.submit()" id="StatusId">
              <% if(status==0){ %>
                <option value="0" >Pending</option>
                <option  value="1">Approved</option>
              <%}else{%>
                <option  value="1" >Approved</option>
                <option value="0" >Pending</option>
              <%}%>
              <input type="hidden" name="teamID" value="<%= id %>">
        
            </select><i
                style="margin-left: -25px; color: #fafafa; pointer-events: none"
                class="fas fa-chevron-down"
              ></i>


          </form>
                                         
          
          <div id="bin" onclick="popUp()">
            <img src="images/Iconly-Bulk-Delete.svg" alt="" />
          </div>
        </div>
      </div>
      <div class="main-content blur">
        <div class="members">
          <ul>
            <i
              class="fas fa-edit fa-lg"
              style="
                position: absolute;
                margin-left: 70px;
                /* padding: 0px -10px 10px 0px; */
                cursor: pointer;
                color: #bfbfc2;
                width: fit-content;
                height: fit-content;
              "
              onclick="edit()"
            ></i>
          
            <div class="image-name">
              
              
              <form method="post" action="<%=request.getContextPath() %>/StartIt/teamProfileController.jsp">
                <input type="hidden" name="teamID" value="<%= id %>">
                <input type="hidden" name="urel" value="add-members.jsp">
                <button  class="team-button">
                <img
                  id="add-member"
                  src="images/AddMember.svg"
                  alt=""
                  style="width: 70px; margin: 0px 0 0px 0px; cursor: pointer"
                />
              </button>
              </form>
	      <li id="add-member-text">Add Member</li>
            </div>
            <% for(User u : members){
              %>              
            <div class="image-name">
              <form method="post" action="<%=request.getContextPath() %>/StartIt/teamProfileController.jsp">
                <input type="hidden" name="teamID" value="<%= id %>">
                <input type="hidden" name="userID" value="<%= u.getId() %>">
                <input type="hidden" name="urel" value="team-profile.jsp">
                <div class="image-delete" type = "hidden">
			
		  
                  <input type="submit" class="delete" value="x" />		
            
                  <div class="memberImage" onclick="userprofile()"></div>
                </div>

              </form>

              <li id="member-name"><%= u.getName() +" "+ u.getSurname()%></li>
            </div>
            <%}%>
            
          </ul>
        </div>

        <div class="diversity-info">
         <div class="info-content">
            <div class="info">
              <h2>Age</h2>
              <% for (String[] cat : team.getAgeProfile()){ %>
              <div class="row">
                <h3><%= cat[0]%></h3>
                <div class="circle" ><%= cat[1] %></div> 
              </div> 
              <% } %>  
            </div>
            <div class="info">
              <h2>Education</h2>
              <% for (String[] cat : team.getLevelProfile()){ %>
                <div class="row">
                  <h3><%= cat[0]%></h3>
                  <div class="circle" ><%= cat[1] %></div> 
                </div> 
              <% } %>   
            </div>
            <div class="info">
              <h2>Experience</h2>
              <% for (String[] cat : team.getExpYearsProfile()){ %>
                <div class="row">
                  <h3><%= cat[0]%></h3>
                  <div class="circle" ><%= cat[1] %></div> 
                </div> 
              <% } %>  
            </div>
            <div class="info">
              <h2 >
                Startup Exp 
              </h2>
              <% for (String[] cat : team.getStartupExpProfile()){ %>
                <div class="row">
                  <h3><%= cat[0]%></h3>
                  <div class="circle" ><%= cat[1] %></div> 
                </div> 
              <% } %>   
            </div>
            <div class="info">
              <h2 >
                Time Devotion&nbsp
              </h2>
              <% for (String[] cat : team.getDevotionProfile()){ %>
                <div class="row">
                  <h3><%= cat[0]%></h3>
                  <div class="circle" ><%= cat[1] %></div> 
                </div> 
              <% } %>  
            </div>
          </div>          <div class="diversity-content">
            <h2>Diversity</h2>
            <div class="diversity">
              <div class="column-one">
                <h4>Parameters</h4>
                <div class="diversity-percentages">
                  <div class="text">
                    <h5>Risk Tolerance</h5>
                    <h5 style="color: #707070; margin: 30px -10px -10px 0px">
                      <%=team.getRiskTolerance()%>%
                    </h5>
                  </div>
                  <div class="percentage-bar-back">
                    <div class="percentage-bar-front" style="width: <%=team.getRiskTolerance()%>%">
                      &nbsp;
                    </div>
                  </div>
                </div>
                <div class="diversity-percentages">
                  <div class="text">
                    <h5>Soft Skills</h5>
                    <h5 style="color: #707070; margin: 30px -10px -10px 0px">
                      <%= team.getSoftSkills()%>%
                    </h5>
                  </div>
                  <div class="percentage-bar-back">
                    <div class="percentage-bar-front" style="width: <%= team.getSoftSkills()%>%">
                      &nbsp;
                    </div>
                  </div>
                </div>
                <div class="diversity-percentages">
                  <div class="text">
                    <h5>Contribution</h5>
                    <h5 style="color: #707070; margin: 30px -10px -10px 0px">
                      <%= team.getContribution()%>%
                    </h5>
                  </div>
                  <div class="percentage-bar-back">
                    <div class="percentage-bar-front" style="width: <%= team.getContribution()%>%">
                      &nbsp;
                    </div>
                  </div>
                </div>
                <div class="diversity-percentages">
                  <div class="text">
                    <h5>Optimism</h5>
                    <h5 style="color: #707070; margin: 30px -10px -10px 0px">
                      <%= team.getOptimism()%>%
                    </h5>
                  </div>
                  <div class="percentage-bar-back">
                    <div class="percentage-bar-front" style="width: <%= team.getOptimism()%>%">
                      &nbsp;
                    </div>
                  </div>
                </div>
              </div>

              <div class="column-two">
                <div class="diversity-percentages">
                  <div class="text">
                    <h5>Leadership</h5>
                    <h5 style="color: #707070; margin: 30px -10px -10px 0px">
                      <%= team.getLeadership()%>%
                    </h5>
                  </div>
                  <div class="percentage-bar-back">
                    <div class="percentage-bar-front" style="width: <%= team.getLeadership()%>%">
                      &nbsp;
                    </div>
                  </div>
                </div>
                <div class="diversity-percentages">
                  <div class="text">
                    <h5>Time Management</h5>
                    <h5 style="color: #707070; margin: 30px -10px -10px 0px">
                      <%= team.getTimeManagement()%>%
                    </h5>
                  </div>
                  <div class="percentage-bar-back">
                    <div class="percentage-bar-front" style="width: <%= team.getTimeManagement()%>%">
                      &nbsp;
                    </div>
                  </div>
                </div>
                <div class="diversity-percentages">
                  <div class="text">
                    <h5>Involvement</h5>
                    <h5 style="color: #707070; margin: 30px -10px -10px 0px">
                      <%= team.getInvolvement()%>%
                    </h5>
                  </div>
                  <div class="percentage-bar-back">
                    <div class="percentage-bar-front" style="width: <%= team.getInvolvement()%>%">
                      &nbsp;
                    </div>
                  </div>
                </div>
              </div>
              <div class="progress">
                <div class="barOverflow">
                  <div class="bar"></div>
                </div>
                <span><%= team.getDiversity()%></span>%
              </div>

              <!-- <i class="far fa-circle fa-9x"></i> -->
            </div>
          </div>
        </div>
      </div>
    </main>
  </body>
</html>
