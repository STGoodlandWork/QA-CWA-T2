

var album = sessionStorage.getItem("name");
getData(album)

function getData(id){
    fetch('http://localhost:8082/album/search/'+id)
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

             document.querySelector("input#name").value = data.name
             document.querySelector("input#artist").value = data.id
             document.querySelector("input#genre").value = data.genre
             document.querySelector("input#img").value = data.cover
             
             
    
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
    
    let name=formElements["name"].value;
    let artist =formElements["artist"].value;
    let genre =formElements["genre"].value;
    let img =formElements["img"].value;
    

  let data = {
    "name":name,
      "artist":{
          "id":artist
      },
      "genre":{
          "id":genre
      },
      "cover":img
    }
      
      
    
    console.log("Data to post",data)
    console.log(id)

    sendData(data,id)
    // postData(noteTitle,noteBody)
  });


  function sendData(data,id){
    fetch("http://localhost:8082/task/update/"+id, {
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


  
        
        
      