function showSpinner(control) {
    var opts = {
        lines: 13, // The number of lines to draw
        length: 11, // The length of each line
        width: 5, // The line thickness
        radius: 17, // The radius of the inner circle
        corners: 1, // Corner roundness (0..1)
        rotate: 0, // The rotation offset
        color: '#FFF', // #rgb or #rrggbb
        speed: 1, // Rounds per second
        trail: 60, // Afterglow percentage
        shadow: false, // Whether to render a shadow
        hwaccel: false, // Whether to use hardware acceleration
        className: 'spinner', // The CSS class to assign to the spinner
        zIndex: 2e9, // The z-index (defaults to 2000000000)
        top: 'auto', // Top position relative to parent in px
        left: 'auto' // Left position relative to parent in px
    };

    var target = document.createElement("div");
    var spinner = new Spinner(opts).spin(target);
    var overlay = '';
    if (control == true) {
        overlay = iosOverlay({
            text: "Loading...",
            spinner: spinner
        });
    } else {
        $("div.ui-ios-overlay").removeClass("ios-overlay-show");
        $("div.ui-ios-overlay").addClass("ios-overlay-hide");
    }
}
function clearForm(form) {

    $(':input', form).each(function () {
        var type = this.type;
        var tag = this.tagName.toLowerCase(); // normalize case

        // password inputs, and textareas
        if (type == 'text' || type == 'password' || type == 'hidden' || tag == 'textarea' || type == 'email' || type == 'tel') {
            this.value = "";
        }

        // checkboxes and radios need to have their checked state cleared
        else if (type == 'checkbox' || type == 'radio')
            this.checked = false;

        // select elements need to have their 'selectedIndex' property set to -1
        else if (tag == 'select') {
            this.value = '';
        }
    });
}
