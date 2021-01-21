let genreNameElement = document.getElementById("genre-input");
let genreName = "";
playlistNameElement.addEventListener("input", (event) => {
  genreName = event.target.value;
});

let readAllGeneresButton = document.getElementById("searchGenreButton");

readAllGenresButton.onclick = async () => {
  await readGenre(genreName);
};

async function readGenre(genreName) {
  let response = await fetch(
    `http://localhost:8082/genre/search/${genreName}`,
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
  let genreResult = `Genre: ${data.name} <br>`;
  let genres = [];

  for (let data_i of data) {
    let genre = `Genre: ${data_i}<br>`;
    genres.push(genre);
  }
  console.log(genres);
}
