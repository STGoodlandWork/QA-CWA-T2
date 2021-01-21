let artistNameElement = document.getElementById("artist-input");
let artistName = "";
artistNameElement.addEventListener("input", (event) => {
  artistName = event.target.value;
});

let readAllArtistsButton = document.getElementById("searchArtistButton");

readAllArtistsButton.onclick = async () => {
  await readArtist(artistName);
};

console.log("Before async");
async function readArtist(artistName) {
  console.log("After async");
  let response = await fetch(
    `http://localhost:8082/artist/search/${artistName}`,
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

  let div = document.getElementById("myDiv");
  let artistResult = `Artist: ${data.name} <br>`;
  let artists = [];

  for (let data_i of data) {
    let artist = `${data_i}<br>`;
    artists.push(artist);
  }
  console.log(artists);
}
