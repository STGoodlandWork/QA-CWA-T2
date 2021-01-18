let nameElement = document.getElementById("track-input");
let trackName = "";
nameElement.addEventListener("input", (event) => {
  trackName = event.target.value;
});

let readAllTracksButton = document.getElementById("searchTrackButton");

readAllTracksButton.onclick = async () => {
  await readAllTracks();
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

$(function () {
  $("#ChangeToggle").click(function () {
    $("#navbar-hamburger").toggleClass("hidden");
    $("#navbar-close").toggleClass("hidden");
  });
});
