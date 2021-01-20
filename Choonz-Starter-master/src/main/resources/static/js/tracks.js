let trackNameElement = document.getElementById("track-input");
let trackName = "";
trackNameElement.addEventListener("input", (event) => {
  trackName = event.target.value;
});

let searchAllFieldsButton = document.getElementById("searchButton");

searchAllFieldsButton.onclick = async () => {
  await searchAll();
};

async function searchAll() {
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
  console.log(track);
  div.innerHTML = tracks.join("");
}
