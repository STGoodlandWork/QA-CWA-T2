let searchNameElement = document.getElementById("search-input");
let searchName = "";
searchNameElement.addEventListener("input", (event) => {
  searchName = event.target.value;
});
let searchButton = document.getElementById("searchButton");

searchButton.onclick = async () => {
  await searchAllTables(searchName);
};

console.log("Before async");
async function searchAllTables(searchName) {
  console.log("After async");

  let response = await fetch(`http://localhost:8082/search/${searchName}`, {
    method: "GET",
    headers: {
      "Content-type": "application/json ",
    },
  });

  if (!response.ok) {
    console.error(
      `Looks like there was a problem. Status Code: ${response.status}`
    );
    return;
  }

  let data = await response.json();
  console.log(data);

  let div = document.getElementById("myDiv");
  let results = [];

  for (let data_i of data) {
    let result = `${data_i}<br>`;
    results.push(result);
  }
}
