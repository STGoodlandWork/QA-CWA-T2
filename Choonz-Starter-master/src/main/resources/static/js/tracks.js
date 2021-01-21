let trackNameElement = document.getElementById("track-input");
let trackName = "";
trackNameElement.addEventListener("input", (event) => {
  trackName = event.target.value;
});

let createTrackButton = document.getElementById("createTrackButton");
let searchAllTracksButton = document.getElementById("searchTrackButton");

createTrackButton.onclick = async () => {
  await createTrack(trackName);
};

searchAllTracksButton.onclick = async () => {
  await searchTrack(trackName);
};

async function createTrack(trackName) {
  let response = await fetch(`http://localhost:8082/track/create`, {
    method: "POST",
    headers: {
      "Content-type": "application/json ",
    },
    body: JSON.stringify({
      title: trackName,
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

  /*
  let div = document.getElementById("myDiv");
  let tracks = [];

  for (let data_i of data) {
    let track = `Track ID: ${data_i.id}<br>Name: ${data_i.name}<br>`;
    tracks.push(track);
  }
  //console.log(tracks);*/
}
