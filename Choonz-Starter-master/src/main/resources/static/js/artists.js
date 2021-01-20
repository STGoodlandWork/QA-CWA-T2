let artistNameElement = document.getElementById("artist-input");
let artistName = "";
artistNameElement.addEventListener("input", (event) => {
  artistName = event.target.value;
});

let readAllArtistsButton = document.getElementById("searchArtistButton");

readAllArtistsButton.onclick = async () => {
  await readAllArtists();
};

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
  let tracks = [];

  for (let data_i of data) {
    let track = `Artist ID: ${data_i.id}<br>Name: ${data_i.name}<br>`;
    tracks.push(track);
  }
  div.innerHTML = tracks.join("");
}
