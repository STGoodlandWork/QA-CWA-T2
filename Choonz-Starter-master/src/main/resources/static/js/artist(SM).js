
  
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
          createCard(artist);
        })
      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });


  function createCard(data){

    let myDiv = document.querySelector("#responseDisplay");
    
    let tempString = '<div class="card text-white bg-dark mb-3 inlineCard" style="max-width: 18rem;">';
        tempString +='<div class="card-header">Artist</div>';
        tempString += '<div class="card-body">';
        tempString +=  '<h5 class="card-title">' + data.name + '</h5>';
        tempString += '<button type="button" class="btn btn-warning">Warning</button>';
        tempString += '<button type="button" class="btn btn-danger">Danger</button>'
        tempString +=' </div>';
        tempString += '</div>';

    myDiv.innerHTML += tempString;

  }
  





