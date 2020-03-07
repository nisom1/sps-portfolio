// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;


import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.sps.data.CollegeTips;


@WebServlet("/status")
public class LoginServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter(); 
    out.println("<h1>College Tips</h1>");
    UserService userService = UserServiceFactory.getUserService(); // gets all info
    
    
    // If user is not logged in, show a login form (could also redirect to a login page    
    if (!userService.isUserLoggedIn()) {
      String urlToRedirectToAfterUserLogsIn = "/index.html"; // when the user click the login link below, it will redirect to here... the NICKNAME page
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn); // this is the URL 'wired' into the login button
      out.println("<b>Check out college tips from other students on the bullet pointed list below!</b><br/>Want to post some of your own advice?");
      out.println("<br/>");
      out.println("Login with your gmail <a href=\"" + loginUrl + "\">here</a> to see more.</p>");
      out.println("<hr/>");
    return;
    }

    // If user has not set a nickname, redirect to nickname page
    String nickname = getUserNickname(userService.getCurrentUser().getUserId());
    if (nickname == null) {
      response.sendRedirect("/nickname");
      return;
    }

    // User is logged in and has a nickname, so the request can proceed
      String userEmail = userService.getCurrentUser().getEmail();
      nickname = getUserNickname(userService.getCurrentUser().getUserId());
      String urlToRedirectToAfterUserLogsOut = "/index.html"; // redirect to that 'dead' homepage that greets strangers (there is a logout button on the homepage).. aka the LoginServlet - strangerpage
      String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
      String nicknameURL = "/nickname";
    // Print form... not sure where to put that logout link above
      out.println("<p>Hello " + nickname + "!");
      out.println("(Logout <a href=\"" + logoutUrl + "\">here</a>"); // not to the servlet page
      out.println(" or Change nickname <a href=\"" + nicknameURL + "\">here</a>)</p>"); // not to the servlet page

      out.println("<form action=\"/new-comment\" method=\"POST\">");
      out.println("<p>Post a piece of college advice below:</p>");
      out.println("<textarea name=\"text-input\"> ~ type here! ~ </textarea>");
      out.println("<br/>");
      out.println("<input type=\"submit\"/>");
      out.println("</form> <hr/> ");
       
    }
 
  /** Returns the nickname of the user with id, or null if the user has not set a nickname. */
    private String getUserNickname(String id) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query =
        new Query("UserInfo")
            .setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
    PreparedQuery results = datastore.prepare(query);
    Entity entity = results.asSingleEntity();
    if (entity == null) {
      return null;
    }
    String nickname = (String) entity.getProperty("nickname");
    return nickname;
  }

}   




// Print the form with the text box, OR print a login link
    // Either way, show the college comments/tips below (bullet pointed and all that)
    // Go to the datastore and print out what's in storage
    
    /* out.println("<ul>");
    Query query = new Query("CollegeTips").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); // Open datastore
    PreparedQuery results = datastore.prepare(query); // Pass query into the datastore. Query only wants instances in the class "CollegeTips". Grabs bucket of College Tips

    // We will now loop through those entities (userTips) in College Tips and print them out
    for (Entity entity : results.asIterable()) { // Got through the CollegeEntity bucket, converting each userTip (entity) to an element.
                                                // These converted entities become elements that we can place in an array list and iterate through
      String tipText = (String) entity.getProperty("tipText");
      String email = (String) entity.getProperty("email"); // then "nickname"
      out.println("<li>" + email + ": " + tipText + "</li>");
    }
    out.println("</ul>"); */