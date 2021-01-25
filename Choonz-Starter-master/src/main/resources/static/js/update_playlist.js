

var albumName = sessionStorage.getItem("name");
var albumid
var jsonData

getData(albumName)


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
             
            jsonData = data
            console.log("MY DATA OBJ",data)

             document.querySelector("input#name").value = data[0].name
             document.querySelector("input#description").value = data[0].descrition  
             document.querySelector("input#img").value = data[0].artwork
             albumid = data[0].id
             
            
             
    
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
    let descrition =formElements["descrition"].value;
    
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
      
      
    id = albumid
    console.log("Data to post",data)
    console.log(albumid)

    sendData(data,albumid)
   
  });


  function sendData(data,id){
    fetch("http://localhost:8082/album/update/"+id, {
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

    getTrackData()
function getTrackData(){
  
  
       
  
          console.log("2",jsonData)

    //        let table = document.querySelector("tracks");
    //        let data = Object.keys(jsonData[0]); // first record in the array pos 0
          
    //        createTableHead(table,data);
    //       createTableBody(table,commentData);
          
        
      
    
    
  
  
    // function createTableHead(table,data){
    //     let tableHead= table.createTHead();
    //     let row = tableHead.insertRow();
    //     for(let keys of data){
    //         // console.log("data",data)
    //         let th = document.createElement("th");
    //         let text = document.createTextNode(keys);
    //         th.appendChild(text);
    //         row.appendChild(th)
    //       }
    //     }
    //     // let th2 = document.createElement("th")
    //     // let text2 = document.createTextNode("View");
    //     // th2.appendChild(text2);
    //     // row.appendChild(th2);
    //     // let th3 = document.createElement("th")
    //     // let text3 = document.createTextNode("Delete");
    //     // th3.appendChild(text3);
    //     // row.appendChild(th3);
  
    // function createTableBody(table,commentData){
    //     for(let commentRecord of commentData){
    //         let row = table.insertRow();
    //         for(let values in commentRecord){
    //             let cell = row.insertCell();
    //             let text = document.createTextNode(commentRecord[values]);
    //             cell.appendChild(text);
    //           }
    //           let newCell = row.insertCell();
    //           let myViewButton = document.createElement("a");
    //           let myButtonValue = document.createTextNode("Edit")
    //           myViewButton.className ="btn btn-warning pull-right";
    //           myViewButton.href="readOne.html?id="+commentRecord.id;
    //           myViewButton.appendChild(myButtonValue);
    //           newCell.appendChild(myViewButton)
    //           let newCellDelete = row.insertCell();
    //           let myDelButton = document.createElement("button");
    //           let myButtonValue1 = document.createTextNode("Delete")
    //            myDelButton.className ="btn btn-danger pull-right";
    //            myDelButton.onclick = function(){
               
    //                 fetch("http://localhost:9092/task/delete/"+commentRecord.id, {
    //                     method: 'delete',
    //                     headers: {
    //                       "Content-type": "application/json; charset=UTF-8"
    //                     },
    //                   })
    //                   window.location.reload();
                      
                      
    //                 }
               
    //            myDelButton.appendChild(myButtonValue1);
    //            newCellDelete.appendChild(myDelButton)
    //      }      


    }


  
        
        
  