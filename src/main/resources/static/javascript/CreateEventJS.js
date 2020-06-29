//start monitoring searchbar on keypress
window.onkeyup = keyup;


//Text in searchbar
var searchBarInput;


function keyup(e) {
    //updating the string
    searchBarInput = e.target.value;


    //if loop starting when pressing enter
    if (e.keyCode == 13) {
        var destination = "?q=";
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


//Only enabling to submit if everything is filled out
function checkInputs() {
    var isValid = true;
    $('input').filter('[required]').each(function() {
        if ($(this).val() === '') {
            $('#confirm').prop('disabled', true)
            isValid = false;
            return false;
        }
    });
    if(isValid) {$('#confirm').prop('disabled', false)}
    return isValid;
}
//Enable or disable button based on if inputs are filled or not
$('input').filter('[required]').on('keyup',function() {
    checkInputs()
})
checkInputs()