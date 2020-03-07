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
import com.google.sps.data.CollegeTips;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for listing tasks. */
@WebServlet("/load-comments")
public class LoadCommentServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("CollegeTips").addSort("timestamp", SortDirection.DESCENDING);
    // create a Query that holds all "instances" (tips) "of the kind"/ "in the class" CollegeTips

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); // Open datastore
    PreparedQuery results = datastore.prepare(query); // Pass query into the datastore. Query only wants instances in the class "CollegeTips" 
    // results holds all of the entities/instance in Datastore with that kind (CollegeTip label)

    // We will now loop through those entities (userTips) in the larger Bucket
    List<CollegeTips> listOfTips = new ArrayList<>(); // Empty list of college tips
    for (Entity entity : results.asIterable()) { // Got through the CollegeEntity bucket, converting each userTip (entity) to an element.
                                                // These converted entities become elements that we can place in an array list and iterate through
      long id = entity.getKey().getId();
      String tipText = (String) entity.getProperty("tipText");
      long timestamp = (long) entity.getProperty("timestamp");
      String email = (String) entity.getProperty("email");

      CollegeTips userTip = new CollegeTips(id, tipText,timestamp,email);
      listOfTips.add(userTip); // add this tip to our array list
    }

    Gson gson = new Gson();
    response.setContentType("application/json;"); // is this the right slash??
    response.getWriter().println(gson.toJson(listOfTips));
  }
}
