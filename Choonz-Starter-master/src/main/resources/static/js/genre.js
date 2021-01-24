let genreNameElement = document.getElementById("genre-input");
let genreName = "";
genreNameElement.addEventListener("input", (event) => {
  genreName = event.target.value;
});

let createGenreButton = document.getElementById("createGenreButton");
let readAllGenresButton = document.getElementById("searchGenreButton");

createGenreButton.onclick = async () => {
  await createGenre(genreName);
};

readAllGenresButton.onclick = async () => {
  await readGenre(genreName);
};

async function createGenre(genreName) {
  let response = await fetch(`http://localhost:8082/genre/create`, {
    method: "POST",
    headers: {
      "Content-type": "application/json ",
    },
    body: JSON.stringify({
      title: genreName,
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
  let myDiv = document.querySelector("#allGenres");

  let tempString =
    '<div class="card text-white bg-dark mb-3 inlineCard" style="max-width: 18rem;">';
  tempString += '<div class="card-header">Tracks</div>';
  tempString += '<div class="card-body">';
  tempString += '<h5 class="card-title">' + data.name + "</h5>";
  tempString +=
    '<button type="button" class="btn btn-warning">Warning</button>';
  tempString +=
    "<button type = 'button' class='btn btn-danger data-id='" +
    data.id +
    "' class='delete' onclick='deletePlaylist(" +
    data.id +
    ")'> Delete</button>";
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
