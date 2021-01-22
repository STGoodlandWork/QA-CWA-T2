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

async function createPlaylist(playlistName) {
  let response = await fetch(`http://localhost:8082/playlist/create`, {
    method: "POST",
    headers: {
      "Content-type": "application/json ",
    },
    body: JSON.stringify({
      title: playlistName,
    }),
  });

  if (!response.ok) {
    console.log(
      `Looks like there was a problem. Status Code: ${response.status}`
    );
    return;
  }
  let div = document.getElementById("myDiv");
  //div.innerText = `New task has been added!`;
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

  data.forEach((playlist) => {
    console.log(playlist.name);
    createCard(playlist);
  });
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
    '<div class="card text-white bg-dark mb-3 inlineCard" style="max-width: 18rem;">';
  tempString += '<div class="card-header">Tracks</div>';
  tempString += '<div class="card-body">';
  tempString += '<h5 class="card-title">' + data.name + "</h5>";
  tempString +=
    '<button type="button" class="btn btn-warning">Warning</button>';
  tempString +=
    "<button type = 'button' class='btn btn-danger data-id='" +
    data.id +
    "' class='delete' onclick='deletePlaylist(" +
    data.id +
    ")'> Delete</button>";
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
