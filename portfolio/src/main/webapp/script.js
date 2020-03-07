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

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}


// fetch using a URL extension
async function getRandomFortuneUsingAsyncAwait() {
  const response = await fetch('/data'); //fetching info. This URL requests data from a 'site' and fetch requests data from 'servelet'
  const fortune = await response.text(); //converts the response to text
  document.getElementById('fortune-container').innerText = fortune;
}

/** Creates an <li> element containing text. Creates an element (of any type) from the text*/
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

// fetch from the jsondata servlet (get jsondata's response)
// step 3: fetching the JSON string (the array of words that was converted to a string) from the server. 
function getWords() {
    var i;
    fetch('/jsondata')  // sends a request to /jsondata, because we want the response from this server, bc our data is hre, not on foturnes-server
    .then( response => response.json() ) // parses the response as JSON
    .then( (myObject) => // now we can reference the fields in myObject!
    { 
        for(i=0; i< myObject.length;i++)
        {
            console.log(myObject[i]); // my object is currently text on a screen
        }
        // step 4: adds the hard-coded comments to the page (what we'll do with real comments!)
        const wordsList = document.getElementById('words-container');
        wordsList.innerHTML = ' ';
        for(i=0; i< myObject.length;i++)
        {
            wordsList.appendChild(createListElement(myObject[i]))
        }
    } );
}

function loadCollegeTips() { 
  console.log("Hi, loaded"); 
  fetch('/load-comments').then(response => response.json()).then((listOfTips) => { // listOfTips was collegeTips before
    const taskListElement = document.getElementById('user-tips');
    listOfTips.forEach((userTip) => {
      taskListElement.appendChild(createTaskElement(userTip));
    })
  });
}


/** Creates an element that represents a task, including its delete button. */
function createTaskElement(task) { // task is the CollegeTip
  const taskElement = document.createElement('li');
  taskElement.className = 'task';

  const nicknameElement = document.createElement('span');
  nicknameElement.innerText = task.nickname;

  const spaceElement = document.createElement('span');
  spaceElement.innerText = ": ";

  const tipTextElement = document.createElement('span');
  tipTextElement.innerText = task.tipText;

  const deleteButtonElement = document.createElement('button');
  deleteButtonElement.innerText = 'Delete';
  deleteButtonElement.addEventListener('click', () => {
    deleteTask(task);

    // Remove the task from the DOM.
    taskElement.remove();
  });
  taskElement.appendChild(nicknameElement);
  taskElement.appendChild(spaceElement);
  taskElement.appendChild(tipTextElement);
  taskElement.appendChild(deleteButtonElement);
  return taskElement;
}

/** Tells the server to delete the task. */
function deleteTask(task) {
  const params = new URLSearchParams();
  params.append('id', task.id);
  fetch('/delete-task', {method: 'POST', body: params});
}

async function getStatus() {
  console.log("got status"); 
  const response = await fetch('/status'); // runs the status servlet
  const status = await response.text(); //converts the response to text
  document.getElementById('possible-form').innerHTML = status;
}