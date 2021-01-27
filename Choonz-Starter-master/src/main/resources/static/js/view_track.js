const params = new URLSearchParams(window.location.search);

for(let param of params ){
    
    let id = param[1];
    console.log(id);
    getData(id)
}


function getData(id){
    fetch('http://localhost:8082/track/read/'+id)
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
             
           
             var name = document.getElementById("title");
             name.querySelector("p").innerHTML = data.title;
             
             var name = document.getElementById("artist");
             name.querySelector("p").innerHTML = data.artist.name;

             var name = document.getElementById("genre");
             name.querySelector("p").innerHTML = data.genre.name;

             
             duration = data.duration;
             
            
             result = duration/60
             result = Math.floor(result)
             var name = document.getElementById("playtime");
             name.querySelector("p").innerHTML = result + " Minutes";

             var name = document.getElementById("lyrics");
             name.querySelector("p").innerHTML = data.lyrics;


            



            
             
    
          });
        }
      )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });
    }

