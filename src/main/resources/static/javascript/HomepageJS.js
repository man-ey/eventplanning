//start monitoring searchbar on keypress
//window.onkeyup = keyup;


//Text in searchbar
let searchBarInput = "";


function keyup(e) {
  //updating the string
    let searchBarInput = "";
  searchBarInput = document.getElementById('searchbar').value;
  console.log(searchBarInput);


  //when pressing enter
  if (searchBarInput.charAt(searchBarInput.length-1)=== "\n") {
      let destination = "?q=";
      destination = destination.concat(searchBarInput.substring(0, searchBarInput.length - 1));
      console.log(destination);
      reDirect(destination);
  }
}


function reDirect(target) {
    var domain = "http://localhost:8080/search"
    var destination = domain.concat(target);
    console.log(destination);
    //location = window.Location;
    //window.location.href=destination;
}


//Necessary javascriptline for the dropdownmenu
$('.dropdown-toggle').dropdown();

