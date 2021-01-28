document
  .querySelector("form.viewRecord")
  .addEventListener("submit", function (stop) {
    stop.preventDefault();
    let formElements = document.querySelector("form.viewRecord").elements;
    console.log(formElements);

    let title = formElements["title"].value;
    let lyrics = formElements["lyrics"].value;
    let duration = formElements["duration"].value;
    let artist = formElements["artist"].value;
    let genre = formElements["genre"].value;
    let album = formElements["album"].value;
    let playlist = formElements["playlist"].value;

    let data = {
      title: title,
      lyrics: lyrics,
      duration: duration,
      artist: {
        id: artist,
      },
      genre: {
        id: genre,
      },
      album: {
        id: album,
      },
      playlist: {
        id: playlist,
      },
    };
    sessionStorage.setItem("title", title);
    console.log("Data to post", data);
    sendData(data);
  });

function sendData(data) {
  fetch("http://localhost:8082/track/create", {
    method: "post",
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
    body: JSON.stringify(data),
  })
    .then(function (data) {
      console.log("Request succeeded with JSON response", data);
    })
    .catch(function (error) {
      console.log("Request failed", error);
    });
}
