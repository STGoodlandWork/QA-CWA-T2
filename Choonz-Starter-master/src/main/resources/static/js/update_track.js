const params = new URLSearchParams(window.location.search);

for(let param of params ){
    
    let id = param[1];
    console.log(id);
    getData(id)
}
getData(id);


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

             document.querySelector("input#title").value = data.title
             document.querySelector("input#artist").value = data.artist
             document.querySelector("input#album").value = data.album
             document.querySelector("input#genre").value = data.genre
             document.querySelector("input#playtime").value = data.playtime
             document.querySelector("input#lyrics").value = data.lyrics
             
             trackid = data.id
             
             
             
            
             
    
          });
        }
      )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });
    }

    
    document.querySelector("form.viewRecord").addEventListener("submit", function (stop) {
    stop.preventDefault();
    let formElements = document.querySelector("form.viewRecord").elements;
    console.log(formElements)
    
    
    let title=formElements["title"].value;
    let artist =formElements["artist"].value;
    let album =formElements["album"].value;
    let genre =formElements["genre"].value;
    let playtime =formElements["playtime"].value;
    let lyrics =formElements["lyrics"].value;
   
    

    let data = {
        "title": title,
        "lyrics": lyrics,
        "duration": duration,
        "artist": {
          "id": artist,
        },
        "genre": {
          "id": genre,
        },
        "album": {
          "id": album,
        },
        "playlist": {
          "id": playlist,
        },
    }
      
      
    
    console.log("Data to post",data)
   

    sendData(data,trackid)
   
  });


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
      })
      .catch(function (error) {
        console.log('Request failed', error);
      });
    }

   