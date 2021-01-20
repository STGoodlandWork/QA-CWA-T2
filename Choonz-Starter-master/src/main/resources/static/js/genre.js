let genreNameElement = document.getElementById("genre-input");
let genreName = "";
playlistNameElement.addEventListener("input", (event) => {
  trackName = event.target.value;
});

let readAllGeneresButton = document.getElementById("searchGenreButton");

readAllGenresButton.onclick = async () => {
  await readAllGenres();
};

async function readAllGenres() {
  let response = await fetch(`http://localhost:8082/genre/search`, {
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
    let track = `Genre ID: ${data_i.id}<br>Name: ${data_i.name}<br>`;
    tracks.push(track);
  }
  div.innerHTML = tracks.join("");
}
