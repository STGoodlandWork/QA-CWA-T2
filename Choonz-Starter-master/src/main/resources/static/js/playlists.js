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
