let trackNameElement = document.getElementById("search-input");
let trackName = "";
trackNameElement.addEventListener("input", (event) => {
  trackName = event.target.value;
});

let artistNameElement = document.getElementById("search-input");
let artistName = "";
artistNameElement.addEventListener("input", (event) => {
  artistName = event.target.value;
});

let playlistNameElement = document.getElementById("search-input");
let playlistName = "";
playlistNameElement.addEventListener("input", (event) => {
  playlistName = event.target.value;
});

let genreNameElement = document.getElementById("search-input");
let genreName = "";
genreNameElement.addEventListener("input", (event) => {
  genreName = event.target.value;
});

let readAllTracksButton = document.getElementById("searchButton");
let readAllArtistsButton = document.getElementById("searchButton");
let readAllPlaylistsButton = document.getElementById("searchButton");
let readAllGenresButton = document.getElementById("searchButton");

readAllTracksButton.onclick = async () => {
  await readAllTracks();
};

readAllArtistsButton.onclick = async () => {
  await readAllArtists();
};

readAllPlaylistsButton.onclick = async () => {
  await readAllPlaylists();
};

readAllGenresButton.onclick = async () => {
  await readAllGenres();
};

async function readAllTracks() {
  let response = await fetch(`http://localhost:8082/tracks/search`, {
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
    let track = `Track ID: ${data_i.id}<br>Name: ${data_i.name}<br>`;
    tracks.push(track);
  }
  div.innerHTML = tracks.join("");
}

async function readAllArtists() {
  let response = await fetch(`http://localhost:8082/artists/search`, {
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
  let artists = [];

  for (let data_i of data) {
    let artist = `Artist ID: ${data_i.id}<br>Name: ${data_i.name}<br>`;
    artists.push(artist);
  }
  div.innerHTML = artists.join("");
}

async function readAllPlaylists() {
  let response = await fetch(`http://localhost:8082/playlists/search`, {
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
  let playlists = [];

  for (let data_i of data) {
    let playlist = `Playlist ID: ${data_i.id}<br>Name: ${data_i.name}<br>`;
    playlists.push(playlist);
  }
  div.innerHTML = playlists.join("");
}

async function readAllGenres() {
  let response = await fetch(`http://localhost:8082/genre/search`, {
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
  let genres = [];

  for (let data_i of data) {
    let genre = `Genre ID: ${data_i.id}<br>Name: ${data_i.name}<br>`;
    genres.push(genre);
  }
  div.innerHTML = genres.join("");
}
