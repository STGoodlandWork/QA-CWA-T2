let playlistNameElement = document.getElementById("playlist-input");
let playlistName = "";
playlistNameElement.addEventListener("input", (event) => {
  playlistName = event.target.value;
});

let readAllPlaylistsButton = document.getElementById("searchPlaylistButton");

readAllPlaylistsButton.onclick = async () => {
  await readPlaylist(playlistName);
};

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

  window.location.href = "view_playlist.html?=" + data[0].id;
}

// ReadAll for Tracks

function readAllPlaylist() {
  fetch("http://localhost:8082/playlist/read")
    .then(function (response) {
      if (response.status !== 200) {
        console.log(
          "Looks like there was a problem. Status Code: " + response.status
        );
        return;
      }

      // Examine the text in the response
      response.json().then(function (data) {
        console.log(data);

        data.forEach((playlist) => {
          console.log(playlist.name);
          createCard(playlist);
        });
      });
    })
    .catch(function (err) {
      console.log("Fetch Error :-S", err);
    });
}

readAllPlaylist();

function createCard(data) {
  let myDiv = document.querySelector("#allPlaylist");

  let tempString =
  '<div class="card text-white bg-dark mb-3 inlineCard" style="max-width: 20rem;">';
tempString += '<div class="card-header">Artist</div>';
tempString += '<div class="card-body">';
tempString += '<h5 class="card-title">' + data.name + "</h5>";
tempString +=
  '<button type="button" onclick="window.location.href=\'view_playlist.html?=' +
  data.id +
  '\';" class="btn btn-success">View</button>';
tempString +=
  '<button type="button" onclick="window.location.href=\'update_playlist.html?=' +
  data.id +
  '\';" class="btn btn-warning">Update</button>';
tempString +=
  '<button type="button" onclick="deletePlaylist(' +
  data.id +
  ')" class="btn btn-danger">Delete</button>';
tempString += " </div>";
tempString += "</div>";

myDiv.innerHTML += tempString;
}

function deletePlaylist(id) {
  fetch("http://localhost:8082/playlist/delete/" + id, {
    method: "delete",
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  })
    .then(function (data) {
      console.log("Request succeeded with JSON response", data);
      window.location.reload();
    })
    .catch(function (error) {
      console.log("Request failed", error);
    });
}
