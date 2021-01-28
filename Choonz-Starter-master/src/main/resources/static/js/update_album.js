const params = new URLSearchParams(window.location.search);

for (let param of params) {
  let id = param[1];
  console.log(id);
  getData(id);
}

function getData(id) {
  fetch("http://localhost:8082/album/read/" + id)
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

        document.querySelector("input#name").value = data.name;
        document.querySelector("input#artist").value = data.artist.id;
        document.querySelector("input#genre").value = data.genre.id;
        document.querySelector("input#img").value = data.cover;
        albumid = data.id;

        getTrackData(data);
      });
    })
    .catch(function (err) {
      console.log("Fetch Error :-S", err);
    });
}

document
  .querySelector("form.viewRecord")
  .addEventListener("submit", function (stop) {
    stop.preventDefault();
    let formElements = document.querySelector("form.viewRecord").elements;
    console.log(formElements);

    let name = formElements["name"].value;
    let artist = formElements["artist"].value;
    let genre = formElements["genre"].value;
    let img = formElements["img"].value;

    let data = {
      name: name,
      artist: {
        id: artist,
      },
      genre: {
        id: genre,
      },
      cover: img,
    };

    id = albumid;
    console.log("Data to post", data);
    console.log(albumid);

    sendData(data, albumid);
  });

function sendData(data, id) {
  fetch("http://localhost:8082/album/update/" + id, {
    method: "put",
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

function getTrackData(jsondata) {
  data = jsondata.tracks;

  console.log("2", data);

  let table = document.querySelector("table");

  createTableHead(table);
  createTableBody(table, data);

  function createTableHead(table) {
    let tableHead = table.createTHead();
    let row = tableHead.insertRow();

    let th = document.createElement("th");
    let text = document.createTextNode("Title");
    th.appendChild(text);
    row.appendChild(th);

    let th2 = document.createElement("th");
    let text2 = document.createTextNode("Duration");
    th2.appendChild(text2);
    row.appendChild(th2);
  }

  function createTableBody(table, commentData) {
    for (let commentRecord of commentData) {
      let row = table.insertRow();
      for (let values in commentRecord) {
        if (values == "title" || values == "duration") {
          let cell = row.insertCell();
          let text = document.createTextNode(commentRecord[values]);
          cell.appendChild(text);
        }
      }
      let newCell = row.insertCell();
      let myViewButton = document.createElement("a");
      let myButtonValue = document.createTextNode("View");
      myViewButton.className = "btn btn-warning pull-right";
      myViewButton.href = "view_track.html?id=" + commentRecord.id;
      myViewButton.appendChild(myButtonValue);
      newCell.appendChild(myViewButton);
      let newCellDelete = row.insertCell();
      let myDelButton = document.createElement("button");
      let myButtonValue1 = document.createTextNode("Delete");
      myDelButton.className = "btn btn-danger pull-right";
      myDelButton.onclick = function () {
        fetch("http://localhost:8082/track/delete/" + commentRecord.id, {
          method: "delete",
          headers: {
            "Content-type": "application/json; charset=UTF-8",
          },
        });
        window.location.reload();
      };

      myDelButton.appendChild(myButtonValue1);
      newCellDelete.appendChild(myDelButton);
    }
  }
}
function addSong() {
   
  let songid = document.getElementById('song').value;
  updateTrackList(songid, albumid);
}

function updateTrackList(songid, flag) {
   
    fetch('http://localhost:8082/track/read/'+songid)
      .then(
        function(response) {
          if (response.status !== 200) {
            console.log('Looks like there was a problem. Status Code: ' +
              response.status);
            return;
          }
          // Examine the text in the response
          response.json().then(function(data) {
             
           
            console.log("MY DATA OBJ",data)

             title = data.title
             lyrics = data.lyrics
             duration = data.duration
             artist = data.artist.id
             genre = data.genre.id
             album = flag
             console.log("flag" ,flag)
             playlist = data.playlist.id

             let jsondata = {
              "title": title,
              "lyrics": lyrics,
              "duration": duration,
              "artist": {
                "id": artist
              },
              "genre": {
                "id": genre
              },
              "album": {
                "id": album
              },
              "playlist": {
                "id": playlist
              }
            }
            sendData(jsondata,songid);

             
             
             function sendData(data,id){
              fetch("http://localhost:8082/track/update/"+id, {
                  method: 'put',
                  headers: {
                    "Content-type": "application/json; charset=UTF-8"
                  },
                  body:JSON.stringify(data)
                })
                .then(function (data) {
                  console.log('Request succeeded with JSON response', data);
                  window.location.reload();
                })
                .catch(function (error) {
                  console.log('Request failed', error);
                });
              }
            
             
             
             
             
            
            
             
    
          });
        }
      )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });
    }
    
    
