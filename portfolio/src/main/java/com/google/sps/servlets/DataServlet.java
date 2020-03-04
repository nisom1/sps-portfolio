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
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private List<String> fortunes;


  @Override
  public void init() {
    fortunes = new ArrayList<>();

    fortunes.add(
        "Always remember to fall asleep with a dream, "
            + "and wake up with a purpose.");
    fortunes.add(
        "Great minds discuss ideas; Average minds discuss events; "
            + "Small minds discuss people.  - Eleanor Roosevelt");
    fortunes.add("You don't have to be faster than the bear, you just have to be faster than the slowest guy running from it.");
    fortunes.add("This fortune is never gonna give you up, never gonna let you down, never gonna run around and desert youuu");
    fortunes.add(
        "Your problem isn't the problem, "
                    + "your reaction is the problem.");
    fortunes.add("Worrying doesn't take away tomorrow's troubles, "
                    + "it takes away today's peace");
    fortunes.add(
        "Think of the negatives in your life. "
            + "Now remember, life is like photography - you need the negatives to develop.");
    fortunes.add("Don't trip over what's behind you");
  }


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String fortune = fortunes.get((int) (Math.random() * fortunes.size()));
    response.setContentType("text/html;");
    response.getWriter().println(fortune);
    // response.getWriter().println("<h1>Hello Nia!</h1>");

  }
}


