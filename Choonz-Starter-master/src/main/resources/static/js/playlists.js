let playlistNameElement = document.getElementById("playlist-input");
let playlistName = "";
playlistNameElement.addEventListener("input", (event) => {
  playlistName = event.target.value;
});

let createPlaylistButton = document.getElementById("createPlaylistButton");
let readAllPlaylistsButton = document.getElementById("searchPlaylistButton");

createPlaylistButton.onclick = async () => {
  await createPlaylist(playlistName);
};

readAllPlaylistsButton.onclick = async () => {
  await readPlaylist(playlistName);
};
console.log("Hello rato 1");
async function createPlaylist(playlistName) {
  console.log("Hello rato 2");

  let response = await fetch(`http://localhost:8082/playlist/create`, {
    method: "POST",
    headers: {
      "Content-type": "application/json ",
    },
    body: JSON.stringify({
      name: playlistName,
    }),
  });

  if (!response.ok) {
    console.log(
      `Looks like there was a problem. Status Code: ${response.status}`
    );
    return;
  }

  let div = document.getElementById("myDiv");
  //div.innerText = `New playlist has been added!`;
}

async function readPlaylist(playlistName) {
  let response = await fetch(
    `http://localhost:8082/playlist/search/${playlistName}`,
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

  /*
  let div = document.getElementById("myDiv");
  let playlistResult = `Playlist: ${data.name} <br>`;
  let tracks = [];

  for (let track of data.tracks) {
    tracks.push(`(${track.id}: ${track.name})`);
  }

  tracks = "Tracks: " + tracks.join(", ") + "<br><br>";

  let listItem = `${playlistResult}${tracks}`;
  div.innerHTML = listItem;
  */
}


// ReadAll for Tracks

fetch('http://localhost:8082/playlist/read')
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

      data.forEach((playlist) => {
        
        console.log(playlist.name);
        createCard(playlist);
      })
      
    });
  }
)
.catch(function(err) {
  console.log('Fetch Error :-S', err);
});


function createCard(data){

  let myDiv = document.querySelector("#allPlaylist");
  
  let tempString = '<div class="card text-white bg-dark mb-3 inlineCard" style="max-width: 18rem;">';
      tempString +='<div class="card-header">Tracks</div>';
      tempString += '<div class="card-body">';
      tempString +=  '<h5 class="card-title">' + data.name + '</h5>';
      tempString += '<button type="button" class="btn btn-warning">Warning</button>';
      tempString += '<button type="button" class="btn btn-danger">Danger</button>'
      tempString +=' </div>';
      tempString += '</div>';

      myDiv.innerHTML += tempString;

}