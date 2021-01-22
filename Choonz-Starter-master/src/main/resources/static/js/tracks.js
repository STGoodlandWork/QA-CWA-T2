let trackInfo = document.getElementsByClassName("create-form-control");
console.log(trackInfo);
let trackArray = [];
for (let i = 0; i < trackInfo.length; i++) {
  let trackData = { element: trackInfo[i], input: "" };
  trackArray.push(trackData);
}

trackArray.forEach((trackInfo) => {
  trackInfo.element.addEventListener("input", (event) => {
    trackInfo.input = event.target.value;
  });
});

let createTrackButton = document.getElementById("createTrackButton");
let searchAllTracksButton = document.getElementById("searchTrackButton");

createTrackButton.onclick = async () => {
  await createTrack(trackInfo);
};

searchAllTracksButton.onclick = async () => {
  await searchTrack(trackInfo);
};

async function createTrack(trackInfo) {
  console.log(trackArray);
  let response = await fetch(`http://localhost:8082/track/create`, {
    method: "POST",
    headers: {
      "Content-type": "application/json ",
    },
    body: JSON.stringify({
      title: trackInfo[0].input,
      duration: trackInfo[1].input,
      lyrics: trackInfo[2].input,
      /* album: trackInfo[3].input,
      artist: trackInfo[4].input,
      genre: trackInfo[5].input,
      playlist: trackInfo[6].input,*/
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
