var header = new Headroom(document.getElementById("header"), {
    tolerance: 0,
    offset: 70,
    classes: {
        initial: "animated",
        pinned: "slideDown",
        unpinned: "slideUp"
    }
});
header.init();
var navbarMobileSwith = document.getElementById("navbar-mobile-swith");
navbarMobileSwith.onclick = function() {
    navbarMobileSwith.classList.toggle("navbar-mobile-menu-on");
};
if ('addEventListener' in document) {
    document.addEventListener('DOMContentLoaded', function () {
        FastClick.attach(document.body);
    }, false);
}
function current(event) {
    console.log(event.toElement);
    event.toElement.className = 'current'
    let link = event.toElement.attributes.getNamedItem('link').value;
}
