//start monitoring searchbar on keypress
window.onkeyup = keyup;


//Text in searchbar
var searchBarInput;


function keyup(e) {
  //updating the string
  searchBarInput = e.target.value;


  //if loop starting when pressing enter
  if (e.keyCode == 13) {
      var destination = "?searchby=";
      destination = destination.concat(searchBarInput);
      reDirect(destination);
  }
}


function reDirect(target) {
    var domain = "http://localhost:8080/"
    var destination = domain.concat(target);
    location.replace(destination);
}


//Necessary javascriptline for the dropdownmenu
$('.dropdown-toggle').dropdown()

