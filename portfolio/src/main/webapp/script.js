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
      ['"The greatest weapon against stress is the ability to choose one thought over another." - Wlliam James', '"We are all faced with great opportunities brilliantly disguised as impossible situations." - Swindoll', '"After climbing a great hill, one only finds that there are many more hills to climb." - Nelson Mandela', '"To the mind that is still, the universe surrenders" - Lao Tzu'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

/**
 * Adds a link to the page.
 */
function addRandomLink() {
  const links = [ <a href="bio.html"></a> ];
  // Pick a random link.
  const link = links[Math.floor(Math.random() * links.length)];

  // Add it to the page.
  const linkContainer = document.getElementById('link-container');
  linkContainer.innerText = link;
}

