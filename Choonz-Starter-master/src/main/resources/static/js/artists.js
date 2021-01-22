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
  data.forEach((artist) => {
          
    console.log(artist.name);
    createArtistCard(artist);
  })

}

//Read All Function Fetch Request

function readAllArtist() {
fetch('http://localhost:8082/artist/read')
  .then(
    function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' +
          response.status);
        return;
      }

      // Examine the text in the response
      response.json().then(function(data) {
        console.log(data);
        
        
        data.forEach((artist) => {
          
          console.log(artist.name);
          createArtistCard(artist);
        })
      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });
}

readAllArtist();

function createArtistCard(data){

  let myDiv = document.querySelector("#ArtistDisplay");
  
  let tempString = '<div class="card text-white bg-dark mb-3 inlineCard" style="max-width: 18rem;">';
      tempString +='<div class="card-header">Artist</div>';
      tempString += '<div class="card-body">';
      tempString +=  '<h5 class="card-title">' + data.name + '</h5>';
      tempString += '<button type="button" class="btn btn-warning">Update</button>';
      tempString += '<button type="button" class="btn btn-danger">Delete</button>'
      tempString +=' </div>';
      tempString += '</div>';

  myDiv.innerHTML += tempString;

}

function deleteArtist(id){
  fetch("http://localhost:8082/artist/delete/" + id, {
      method: 'delete',
      headers: {
        "Content-type": "application/json; charset=UTF-8"
      }
  
    })
    .then(function (data) {
      console.log('Request succeeded with JSON response', data);
      window.location.reload();
    })
    .catch(function (error) {
      console.log('Request failed', error);
    });
    
}
