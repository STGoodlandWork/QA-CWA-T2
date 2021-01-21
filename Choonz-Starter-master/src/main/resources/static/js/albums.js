let albumNameElement = document.getElementById("album-input");
let albumName = "";
albumNameElement.addEventListener("input", (event) => {
  albumName = event.target.value;
});

let readAlbumButton = document.getElementById("searchAlbumButton");

readAlbumButton.onclick = async () => {
  await readAlbum(albumName);
};

async function readAlbum(albumName) {
  let response = await fetch(
    `http://localhost:8082/album/search/${albumName}`,
    {
      method: "GET",
      headers: {
        "Content-type": "application/json ",
      },
    }
  );

  if (!response.ok) {
    console.error(
      `Looks like there was a problem. Status Code: ${response.status}`
    );
    return;
  }

  let data = await response.json();
  console.log(data);

  let div = document.getElementById("myDiv");
  let albumResult = `Album: ${data.name} <br>`;
  let albums = [];

  for (let data_i of data) {
    let album = `Album: ${data_i}<br>`;
    albums.push(album);
  }
  console.log(albums);
}
