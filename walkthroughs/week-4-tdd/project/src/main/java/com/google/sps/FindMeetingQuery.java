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

package com.google.sps;

import java.util.*;
import java.util.ArrayList;
import java.util.Collection;

public final class FindMeetingQuery {
  private ArrayList<TimeRange> busyTimes;
  private ArrayList<TimeRange> freeTimes;
  private ArrayList<String> meetingAttendees;
  private ArrayList<String> eventAttendees;


  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
// only add the event to busyTimes if an attendee on the Meeting Request is on the event
    busyTimes = new ArrayList<TimeRange>();
    meetingAttendees = new ArrayList<String>();
    eventAttendees = new ArrayList<String>();

    for (int i = 0; i<request.getAttendees().size(); i++){
        (request.getAttendees()).forEach(person -> meetingAttendees.add(person));
    }
    // for-each loop
    for (Event currEvent : events) { // for each event
        for (int i = 0; i<currEvent.getAttendees().size(); i++){
            (currEvent.getAttendees()).forEach(person -> eventAttendees.add(person));
        }
        for (String eventPerson : eventAttendees){
            if (meetingAttendees.contains(eventPerson)){ // if event person is in meeting request
                busyTimes.add(currEvent.getWhen());
                break; // move to next event
                }
            // if it gets here, check the next person in the event
        }
    // break goes here. on to next event
    }
                
     // 1. Sort the inputted ranges by start time. Smallest Start time @ the front.
        Collections.sort(busyTimes, TimeRange.ORDER_BY_START);

     // 2a. Compare the start time of the element at the front to each element 
            // 2b. Combine ranges that overlap
            // 2c. Remove the old range element
     
    // Convert the busyTimes --> freeTimes
    freeTimes = new ArrayList<TimeRange>();

    if (busyTimes.size()==0){
        freeTimes.add(TimeRange.fromStartEnd(0,1440,true));
    }

    int i = 0;
    int j = 1;
    if (busyTimes.size() == 1){ // create a time range from 0 to busyTimes[i].end)
            freeTimes.add(TimeRange.fromStartEnd(0,busyTimes.get(i).start(),false));
            freeTimes.add(TimeRange.fromStartEnd(busyTimes.get(i).end(),1440,false));
        }

    if (busyTimes.size() >= 2){
        if (i==0){ // on the first time range. (0 to ...)
            freeTimes.add(TimeRange.fromStartEnd(0,busyTimes.get(i).start(),true));
            i++;
        }
        while(j != busyTimes.size()){
            freeTimes.add(TimeRange.fromStartEnd(busyTimes.get(j).end(),busyTimes.get(i).start(),true));
            i++;
            j++;
        }
        if (j==busyTimes.size()){ // on the last time range. (... to 1440)
            freeTimes.add(TimeRange.fromStartEnd(busyTimes.get(j).end(),1439,false));
        }
    }
    
     return freeTimes;
    // throw new UnsupportedOperationException("TODO: Implement this method.");
  }
}
