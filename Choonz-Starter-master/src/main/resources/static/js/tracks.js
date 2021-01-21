let trackNameElement = document.getElementById("track-input");
let trackName = "";
trackNameElement.addEventListener("input", (event) => {
  trackName = event.target.value;
});

let createTrackButton = document.getElementById("createTrackButton");
let searchAllTracksButton = document.getElementById("searchTrackButton");

createTrackButton.onclick = async () => {
  await createTrack(playlistId, trackName);
};

searchAllTracksButton.onclick = async () => {
  await searchTrack();
};

async function createTrack(playlistId, trackName) {
  let playlistIdInt = parseInt(playlistId);
  let response = await fetch(`http://localhost:8082/track/create`, {
    method: "POST",
    headers: {
      "Content-type": "application/json ",
    },
    body: JSON.stringify({
      title: trackName,
      playlist: {
        id: playlistIdInt,
      },
    }),
  });

  if (!response.ok) {
    console.log(
      `Looks like there was a problem. Status Code: ${response.status}`
    );
    return;
  }
  let div = document.getElementById("myDiv");
  div.innerText = `New task has been added!`;
}

async function searchTrack() {
  let response = await fetch(
    `http://localhost:8082/track/search/${trackName}`,
    {
      method: "GET",
      headers: {
        "Content-type": "application/json ",
      },
    }
  );

  if (!response.ok) {
    console.log(
      `Looks like there was a problem. Status Code: ${response.status}`
    );
    return;
  }

  let data = await response.json();
  console.log(data);
  data.forEach((track) => {
        
    console.log(track.name);
    createCard(track);
  });

  
  }
  



// ReadAll for Tracks

fetch('http://localhost:8082/track/read')
.then(
  function(response) {
    if (response.status !== 200) {
      console.log('Looks like there was a problem. Status Code: ' +
        response.status);
      return;
    }

    // Examine the text in the response
    response.json().then(function(data) {
      console.log(data);
      

      data.forEach((track) => {
        
        console.log(track.name);
        createCard(track);
      })
    });
  }
)
.catch(function(err) {
  console.log('Fetch Error :-S', err);
});


function createCard(data){

  let myDiv = document.querySelector("#tracksDisplay");
  
  let tempString = '<div class="card text-white bg-dark mb-3 inlineCard" style="max-width: 18rem;">';
      tempString +='<div class="card-header">Tracks</div>';
      tempString += '<div class="card-body">';
      tempString +=  '<h5 class="card-title">' + data.title + '</h5>';
      tempString += '<button type="button" class="btn btn-warning">Warning</button>';
      tempString += '<button type="button" class="btn btn-danger">Danger</button>'
      tempString +=' </div>';
      tempString += '</div>';

  myDiv.innerHTML += tempString;

}