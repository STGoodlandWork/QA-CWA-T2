const params = new URLSearchParams(window.location.search);

for(let param of params ){
    
    let id = param[1];
    console.log(id);
    getData(id)
}


function getData(id){
    fetch('http://localhost:8082/artist/read/'+id)
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
             
           
             var name = document.getElementById("ArtistDisplay");
             name.querySelector("p").innerHTML = data.name;
            
             
    
          });
        }
      )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });
    }
