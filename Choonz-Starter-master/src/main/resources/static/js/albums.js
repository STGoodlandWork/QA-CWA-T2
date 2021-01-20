let albumNameElement = document.getElementById("album-input");
let albumName = "";
albumNameElement.addEventListener("input", (event) => {
  albumName = event.target.value;
});

let readAllAlbumsButton = document.getElementById("searchAlbumButton");

readAllAlbumsButton.onclick = async () => {
  await readAllAlbums();
};

async function readAllAlbums() {
  let response = await fetch(`http://localhost:8082/albums/search`, {
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
    let track = `Album ID: ${data_i.id}<br>Name: ${data_i.name}<br>`;
    tracks.push(track);
  }
  div.innerHTML = tracks.join("");
}
