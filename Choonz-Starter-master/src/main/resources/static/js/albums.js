let albumNameElement = document.getElementById("album-input");
let albumName = "";
albumNameElement.addEventListener("input", (event) => {
  albumName = event.target.value;
});

let readAlbumButton = document.getElementById("searchAlbumButton");

readAlbumButton.onclick = async () => {
  await readAlbum(albumName);
};

async function readAlbum(albumName) {
  let response = await fetch(
    `http://localhost:8082/album/search/${albumName}`,
    {
      method: "GET",
      headers: {
        "Content-type": "application/json ",
      },
    }
  );

  if (!response.ok) {
    console.error(
      `Looks like there was a problem. Status Code: ${response.status}`
    );
    return;
  }

  let data = await response.json();
  console.log(data);
  window.location.href = "view_album.html?=" + data[0].id;
}

// ReadAll Function for Albums

function readAllAlbum() {
  fetch("http://localhost:8082/album/read")
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

        data.forEach((album) => {
          console.log(album.name);
          createCard(album);
        });
      });
    })
    .catch(function (err) {
      console.log("Fetch Error :-S", err);
    });
}

readAllAlbum();

function createCard(data) {
  let trackDiv = document.querySelector("#albumDisplay");

  let tempString =
    '<div class="card text-white bg-dark mb-3 inlineCard" style="max-width: 20rem;">';
  tempString += '<div class="card-header">Album</div>';
  tempString += '<div class="card-body">';
  tempString += '<h5 class="card-title">' + data.name + "</h5>";
  tempString +=
    '<button type="button" onclick="window.location.href=\'view_album.html?=' +
    data.id +
    '\';" class="btn btn-success">View</button>';
  tempString +=
    '<button type="button" onclick="window.location.href=\'update_album.html?=' +
    data.id +
    '\';" class="btn btn-warning">Update</button>';
  tempString +=
    '<button type="button" onclick="deleteAlbum(' +
    data.id +
    ')" class="btn btn-danger">Delete</button>';
  tempString += " </div>";
  tempString += "</div>";

  trackDiv.innerHTML += tempString;
}

function deleteAlbum(id) {
  fetch("http://localhost:8082/album/delete/" + id, {
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
