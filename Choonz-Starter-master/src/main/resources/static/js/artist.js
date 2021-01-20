
function createArtistElement(data) {

  for (const i of data) {

    const myDiv = document.querySelector('#responseDisplay');
    const cardDiv = document.createElement('div');
    const table = document.createElement('table');
    const tableRow = document.createElement('tr');
    const tableData = document.createElement('td');
    const cardHeaderDiv = document.createElement('div');
    const cardBodyDiv = document.createElement('div');
    const cardBodyH = document.createElement('h5');
    const updateButton = document.createElement('button');

    // for styling
    
    cardDiv.className = "card text-white bg-dark mb-3";
    cardHeaderDiv.className = "card-header";
    cardHeaderDiv.innerHTML = "Artist";
    cardBodyDiv.className = "card-body";
    cardBodyH.className = "card-title";
    cardBodyH.innerHTML = {i.name};
    updateButton.className = "btn btn-outline-warning";
    updateButton.innerHTML = "Update";

    cardDiv.append(cardHeaderDiv, cardBodyDiv, cardBodyH, updateButton);
    cardDiv.append(div);
  
  }

}

async function readAllArtists() {
  let response = await fetch(`http://localhost:8082/artists/read`, {
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

  createArtistElement(data);
  }


  function createTracksElement(data) {

    const myDiv = document.querySelector('#responseDisplay');
    const cardDiv = document.createElement('div');
    const table = document.createElement('table');
    const tableRow = document.createElement('tr');
    const tableData = document.createElement('td');
    const cardHeaderDiv = document.createElement('div');
    const cardBodyDiv = document.createElement('div');
    const cardBodyH = document.createElement('h5');
    const updateButton = document.createElement('button');

  }
