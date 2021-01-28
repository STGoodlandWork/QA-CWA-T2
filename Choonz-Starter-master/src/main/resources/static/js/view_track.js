const params = new URLSearchParams(window.location.search);

for (let param of params) {
  let id = param[1];
  console.log(id);
  getData(id);
}

function getData(id) {
  fetch("http://localhost:8082/track/read/" + id)
    .then(function (response) {
      if (response.status !== 200) {
        console.log(
          "Looks like there was a problem. Status Code: " + response.status
        );
        return;
      }
      // Examine the text in the response
      response.json().then(function (data) {
        console.log("MY DATA OBJ", data);

        var name = document.getElementById("title");
        name.querySelector("p").innerHTML = data.title;

        var artist = document.getElementById("artist");
        artist.querySelector("p").innerHTML = data.artist.name;

        var album = document.getElementById("album");
        album.querySelector("p").innerHTML = data.album.name;

        var genre = document.getElementById("genre");
        genre.querySelector("p").innerHTML = data.genre.name;

        duration = data.duration;

        result = duration / 60;
        result = Math.floor(result);
        var playtime = document.getElementById("playtime");
        playtime.querySelector("p").innerHTML = result + " Minutes";

        var lyrics = document.getElementById("lyrics");
        lyrics.querySelector("p").innerHTML = data.lyrics;
      });
    })
    .catch(function (err) {
      console.log("Fetch Error :-S", err);
    });
}
