const params = new URLSearchParams(window.location.search);

for(let param of params ){
    
    let id = param[1];
    console.log(id);
    getData(id)
}


function getData(id){
    fetch('http://localhost:8082/playlist/read/'+id)
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
             
           
             var name = document.getElementById("img");
             name.querySelector("p").innerHTML = data.artwork;
             
             var name = document.getElementById("name");
             name.querySelector("p").innerHTML = data.name;

             var name = document.getElementById("description");
             name.querySelector("p").innerHTML = data.description;

             

             
            
             

             trackduration = data.tracks;
             let result = 0;

             trackduration.forEach(element => {

                result +=  element.duration 
              
                 
             });
             result = result/60
             result = Math.floor(result)
             var name = document.getElementById("playtime");
             name.querySelector("p").innerHTML = result + " Minutes";


             
             trackdata = data.tracks
             

             let trackTable = document.querySelector("table");
            
             
             TrackCreateTableHead(trackTable);
             createTableBody(trackTable,trackdata);
            



             
    
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
             let text = document.createTextNode("Title");
             th.appendChild(text);
             row.appendChild(th)
 
             let th2 = document.createElement("th");
             let text2 = document.createTextNode("Artist");
             th2.appendChild(text2);
             row.appendChild(th2)

             let th3 = document.createElement("th");
             let text3 = document.createTextNode("Duration (minutes)");
             th3.appendChild(text3);
             row.appendChild(th3)
           
         }

    function createTableBody(table,commentData){
        for(let commentRecord of commentData){ 
          let row = table.insertRow();
            for(let values in commentRecord){
              
                if(values == "title" || values == "duration" || values == "artist" ){
              let cell = row.insertCell();
              if(values == "duration") {
                 
                let text = document.createTextNode((commentRecord[values]/60) );
                cell.appendChild(text);

              }
                let text = document.createTextNode(commentRecord[values] );
                cell.appendChild(text);
              
            }
            }
              let newCell = row.insertCell();
              let myViewButton = document.createElement("a");
              let myButtonValue = document.createTextNode("View")
              myViewButton.className ="btn btn-warning pull-right";
              myViewButton.href="readOne.html?id="+commentRecord.id;
              myViewButton.appendChild(myButtonValue);
              newCell.appendChild(myViewButton)
             
         }      


    }


            

