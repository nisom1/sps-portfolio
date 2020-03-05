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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/commentdata")
public class CommentServlet extends HttpServlet {
  private ArrayList<String> collegeTips;

  @Override
  public void init() {
    collegeTips = new ArrayList<>();
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input (the college TIP) from the form.
    String tipText = getParameter(request, "text-input", ""); // Checks that the parameter isn't empty
    collegeTips.add(tipText); 

/* Step 1: Instead of storing the resquest/text/comment in an array (above),
     Store each text Tip as an Entity in Datastore */
    Entity userTipEntity = new Entity("CollegeTips"); // Instance of the College-Tips "class"
    userTipEntity.setProperty("tipText", tipText);
    // is taskEntity like "collegeTipEntity? Like an external page for my array collegeTips to grow infinitely?

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(userTipEntity); // putting my collegeTipsEntity (infinite storage array?) on a datastore
    // which has the infinite storage?

    response.sendRedirect("/index.html");
    /* Gets the user's comment (request, tip) and produces response
    response.setContentType("text/html;");
    response.getWriter().println(text); // this is what is printed on this random server page. And our main page and draw from this data but doing /text
    response.sendRedirect("/index.html"); // POST to the servlet page a string of words.. Now I need to GET the data(their comment) from this servlet
    */
  } 

  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

}



// we're print to a "sepearte/additional" server page. 
// and then our main code is drawing / retreiving infomation from these extrnal pages (servlets)
// these servlets can get their data from the user