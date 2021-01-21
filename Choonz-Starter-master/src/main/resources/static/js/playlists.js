let playlistNameElement = document.getElementById("playlist-input");
let playlistName = "";
playlistNameElement.addEventListener("input", (event) => {
  trackName = event.target.value;
});

let createPlaylistButton = document.getElementById("createPlaylistButton");
let readAllPlaylistsButton = document.getElementById("searchPlaylistButton");

createPlaylistButton.onclick = async () => {
  await createPlaylist();
};

readAllPlaylistsButton.onclick = async () => {
  await readAllPlaylists();
};

async function createPlaylist(playlistName, trackName) {
  let response = await fetch(`http://localhost:9092/playlist/create`, {
    method: "POST",
    headers: {
      "Content-type": "application/json ",
    },
    body: JSON.stringify({ name: playlistName, tracks: [trackName] }),
  });

  if (!response.ok) {
    console.log(
      `Looks like there was a problem. Status Code: ${response.status}`
    );
    return;
  }

  let div = document.getElementById("myDiv");
  div.innerText = `New name has been added!`;
}

async function readAllPlaylists() {
  let response = await fetch(`http://localhost:8082/playlist/search`, {
    method: "GET",
    headers: {
      "Content-type": "application/json ",
    },
  });

  if (!response.ok) {
    console.log(
      `Looks like there was a problem. Status Code: ${response.status}`
    );
    return;
  }

  let data = await response.json();
  console.log(data);

  let div = document.getElementById("myDiv");
  let tracks = [];

  for (let data_i of data) {
    let track = `Playlist ID: ${data_i.id}<br>Name: ${data_i.name}<br>`;
    tracks.push(track);
  }
  div.innerHTML = tracks.join("");
}
