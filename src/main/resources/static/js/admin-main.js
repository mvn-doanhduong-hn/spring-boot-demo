$(document).ready(function () {
    $("#sidebar-overlay").on('click', function () {
        $(".sidebar-mini").removeClass("sidebar-open");
        $(".sidebar-mini").addClass("sidebar-closed sidebar-collapse");
    });
});