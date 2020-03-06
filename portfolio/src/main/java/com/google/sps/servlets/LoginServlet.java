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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/status")
public class LoginServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");

    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) { // if they're on that login page
      // create info containers, prep the logout link
      String userEmail = userService.getCurrentUser().getEmail();
      String urlToRedirectToAfterUserLogsOut = "/status"; // redirect to that 'dead' homepage that greets strangers (there is a logout button on the homepage).. aka the LoginServlet - strangerpage
      String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
    // fill containers / get info
      response.getWriter().println("<p>Hello " + userEmail + "!</p>");
      response.getWriter().println("<p>Logout <a href=\"" + logoutUrl + "\">here</a>.</p>");
    } else { // if they're not logged in, prep the login links, give them the stranger page
      String urlToRedirectToAfterUserLogsIn = "/index.html"; // should be my HTML homepage? Should I add their status at the top?
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn); // this is the URL 'wired' into the login button

      response.getWriter().println("<p>Hello stranger!</br> Login to see more:</p>");
      response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
    }
  }
}
