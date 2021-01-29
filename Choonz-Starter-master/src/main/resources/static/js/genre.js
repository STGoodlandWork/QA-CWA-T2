let genreNameElement = document.getElementById("genre-input");
let genreName = "";
genreNameElement.addEventListener("input", (event) => {
  genreName = event.target.value;
});

let readAllGenresButton = document.getElementById("searchGenreButton");

readAllGenresButton.onclick = async () => {
  await readGenre(genreName);
};

console.log("Before async");
async function readGenre(genreName) {
  console.log("After async");

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

  window.location.href = "view_genre.html?=" + data[0].id;
}

// ReadAll for Tracks

function readAllGenres() {
  fetch("http://localhost:8082/genre/read")
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

        data.forEach((genre) => {
          console.log(genre.name);
          createCard(genre);
        });
      });
    })
    .catch(function (err) {
      console.log("Fetch Error :-S", err);
    });
}

readAllGenres();

function createCard(data) {
  let myDiv = document.querySelector("#GenreDisplay");

  let tempString =
    '<div class="card text-white bg-dark mb-3 inlineCard" style="max-width: 20rem;">';
  tempString += '<div class="card-header">Genre</div>';
  tempString += '<div class="card-body">';
  tempString += '<h5 class="card-title">' + data.name + "</h5>";
  tempString +=
    '<button type="button" onclick="window.location.href=\'view_genre.html?=' +
    data.id +
    '\';" class="btn btn-success">View</button>';
  tempString +=
    '<button type="button" onclick="window.location.href=\'update_artist.html?=' +
    data.id +
    '\';" class="btn btn-warning">Update</button>';
  tempString +=
    '<button type="button" onclick="deleteGenre(' +
    data.id +
    ')" class="btn btn-danger">Delete</button>';
  tempString += " </div>";
  tempString += "</div>";

  myDiv.innerHTML += tempString;
}

function deleteGenre(id) {
  fetch("http://localhost:8082/genre/delete/" + id, {
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
