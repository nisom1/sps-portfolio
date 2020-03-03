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

import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/jsondata")
public class JSONServlet extends HttpServlet {

  // step 1
  private ArrayList<String> words;

  @Override
  public void init() {
    words = new ArrayList<>();
    words.add("Message1");
    words.add("Message2");
    words.add("Message3");
  }

   /**
   * Converts an Arry of words instance into a JSON string using the Gson library. Note: We first added
   * the Gson library dependency to pom.xml and the library above
   */
  private String convertToJsonUsingGson(ArrayList words) {
    Gson gson = new Gson();
    Type listOfWords = new TypeToken<List<String>>(){}.getType();
    String wordsJson = gson.toJson(words, listOfWords);
    
    return wordsJson;
  }

  @Override // what we print out on the other page. we can get these words printed our on our main, index page - using onload or onclick
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // step 2
    String words2 = convertToJsonUsingGson(words);
    response.setContentType("text/html;");
    response.getWriter().println(words2);
  }
}
// we're print to a "sepearte/additional" server page. 
// and then our main code is drawing / retreiving infomation from these extrnal pages (servlets)
// these servlets can get their data from the user