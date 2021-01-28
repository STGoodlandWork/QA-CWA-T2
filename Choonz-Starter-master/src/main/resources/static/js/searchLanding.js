const params = new URLSearchParams(window.location.search);

for(let param of params ){
    
    let id = param[1];
    console.log(id);
    getData(id)
}


function getData(id){
    fetch('http://localhost:8082/search/'+id)
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
             
            

             if(Object.keys(data).length == 0 ) {
              var name = document.getElementById("search");
              name.querySelector("h3").innerHTML = "No search results found for: " + id;
             }
             else{
             
             var name = document.getElementById("search");
             name.querySelector("h3").innerHTML = "Search results for: " + id;

             let table = document.querySelector("table");
            
             TrackCreateTableHead(table);
             createTableBody(table,data);
             }



             
    
          });
        }
      )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });
    }


   
    function TrackCreateTableHead(table){
        let tableHead= table.createTHead();
          let row = tableHead.insertRow();
         
            
             let th = document.createElement("th");
             let text = document.createTextNode("Type");
             th.appendChild(text);
             row.appendChild(th)
 
           

            
           
         }

    function createTableBody(table,commentData){
        for(let commentRecord of commentData){ 
          let row = table.insertRow();
           
              
              if (commentRecord.hasOwnProperty('title')){
                type = "Track"
                insertType(type)
                source = "view_track.html?id="
                createButton(source)

                
              }
            
              else if(commentRecord.hasOwnProperty('cover')) {
                 
                
                type = "Album"
                insertType(type)
                source = "view_album.html?id="
                createButton(source)

              }
              else if(commentRecord.hasOwnProperty('artwork')) {
                 
                
                type = "Playlist"
                insertType(type)
                source = "view_playlist.html?id="
                createButton(source)

              }
              else if(commentRecord.hasOwnProperty('description')) {
                 
                
                type = "Genre"
                insertType(type)
                source = "view_genre.html?id="
                createButton(source)
              }
              else{
                 
                
                type = "Artist"
                insertType(type)
                source = "view_artist.html?id="
                createButton(source)

              }

              function insertType(type) {
                let cell = row.insertCell();
                let text = document.createTextNode(type);
                cell.appendChild(text);
              }

              function createButton(source) {

                let newCell = row.insertCell();
              let myViewButton = document.createElement("a");
              let myButtonValue = document.createTextNode("View")
              myViewButton.className ="btn btn-warning pull-right";
              myViewButton.href=source+commentRecord.id;
              myViewButton.appendChild(myButtonValue);
              newCell.appendChild(myViewButton)
              }
               
              
            
            
              
            }
             
         }      


    


            

