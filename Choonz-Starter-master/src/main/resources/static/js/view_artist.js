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
             trackdata = data.tracks
             albumdata = data.albums

             let trackTable = document.querySelector("table");
             let albumTable = document.getElementById("album")
             
             TrackCreateTableHead(trackTable);
             createTableBody(trackTable,trackdata);
             
             AlbumCreateTableHead(albumTable)
             createTableBody(albumTable,albumdata)




            
             
    
          });
        }
      )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });
    }


    function AlbumCreateTableHead(table){
        let tableHead= table.createTHead();
          let row = tableHead.insertRow();
         
            
             let th = document.createElement("th");
             let text = document.createTextNode("Album Name");
             th.appendChild(text);
             row.appendChild(th)
           
         }

    function TrackCreateTableHead(table){
        let tableHead= table.createTHead();
          let row = tableHead.insertRow();
         
            
             let th = document.createElement("th");
             let text = document.createTextNode("Title");
             th.appendChild(text);
             row.appendChild(th)
 
             let th2 = document.createElement("th");
             let text2 = document.createTextNode("Duration");
             th2.appendChild(text2);
             row.appendChild(th2)
           
         }

    function createTableBody(table,commentData){
        for(let commentRecord of commentData){ 
          let row = table.insertRow();
            for(let values in commentRecord){
              
                if(values == "title" || values == "duration" || values == "name" ){
              let cell = row.insertCell();
                let text = document.createTextNode(commentRecord[values]);
                cell.appendChild(text);
              
            }
            }
              let newCell = row.insertCell();
              let myViewButton = document.createElement("a");
              let myButtonValue = document.createTextNode("View")
              myViewButton.className ="btn btn-warning pull-right";
              if (commentRecord.hasOwnProperty('title')){
                myViewButton.href="view_track.html?id="+commentRecord.id;
                myViewButton.appendChild(myButtonValue);
              newCell.appendChild(myViewButton)
              }
              else {
              myViewButton.href="view_album.html?id="+commentRecord.id;
              myViewButton.appendChild(myButtonValue);
              newCell.appendChild(myViewButton)
              }
         }      


    }