//start monitoring searchbar on keypress
window.onkeyup = keyup;



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
checkInputs();
