//Necessary javascriptline for the dropdownmenu
$('.dropdown-toggle').dropdown()



//cookie management
var currentEvent = "Musik";
function updateLike() {
    var likesCookie = getCookie("likesCookie");
    if(likesCookie == null) {
        createCookie(likesCookie, currentEvent, 1);
    } else {
        var oldC = getCookieValue(likesCookie);
        oldC += currentEvent;
        createCookie(likesCookie, oldC, 1);
    }
}
function updateDislike() {
    var dislikesCookie = getCookie("dislikesCookie");
    if(dislikesCookie === null) {
        createCookie(dislikesCookie, currentEvent, 1);
    } else {
        var oldC = getCookieValue(dislikesCookie);
        oldC += currentEvent;
        createCookie(dislikesCookie, oldC, 1);
    }
}
function getCookie(a) {
    var b = document.cookie.match('(^|;)\\s*' + a + '\\s*=\\s*([^;]+)');
    return b ? b.pop() : '';
}

while (true) {
    var cookL = getCookie("likesCookie");
    var cookD = getCookie("dislikesCookie");
    if (cookL.includes(currentEvent) || cookL === null) {
        document.getElementById("buttonLike").style.backgroundColor = 'green';
        document.getElementById("buttonLike").style.color = 'black';
    }
    if (cookD.includes(currentEvent)) {
        document.getElementById("buttonDislike").style.backgroundColor = 'red';
        document.getElementById("buttonLike").style.color = 'black';
    }
}