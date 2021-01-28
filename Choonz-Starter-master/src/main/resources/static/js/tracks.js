let trackNameElement = document.getElementById("track-input");
let trackName = "";
trackNameElement.addEventListener("input", (event) => {
  trackName = event.target.value;
});

let searchAllTracksButton = document.getElementById("searchTrackButton");

searchAllTracksButton.onclick = async () => {
  await searchTrack(trackName);
};

async function searchTrack(trackName) {
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

  window.location.href = "view_track.html?=" + data[0].id;
}

//Read All Function Fetch Request

function readAllTrack() {
  fetch("http://localhost:8082/track/read")
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

        data.forEach((track) => {
          console.log(track.title);
          createCard(track);
        });
      });
    })
    .catch(function (err) {
      console.log("Fetch Error :-S", err);
    });
}

readAllTrack();

function createCard(data) {
  let trackDiv = document.querySelector("#trackDisplay");

  let tempString =
    '<div class="card text-white bg-dark mb-3 inlineCard" style="max-width: 18rem;">';
  tempString += '<div class="card-header">Tracks</div>';
  tempString += '<div class="card-body">';
  tempString += '<h5 class="card-title">' + data.title + "</h5>";
  tempString +=
    '<button type="button" onclick="window.location.href=\'view_track.html?=' +
    data.id +
    '\';" class="btn btn-success">View</button>';
  tempString +=
    '<button type="button" onclick="window.location.href=\'update_track.html?=' +
    data.id +
    '\';" class="btn btn-warning">Update</button>';
  tempString +=
    '<button type="button" onclick="deleteTrack(' +
    data.id +
    ')" class="btn btn-danger">Delete</button>';
  tempString += " </div>";
  tempString += "</div>";

  trackDiv.innerHTML += tempString;
}

function deleteTrack(id) {
  fetch("http://localhost:8082/track/delete/" + id, {
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
