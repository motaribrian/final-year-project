$(window).on('load', function () {

    $('.preloader-wrapper').fadeOut();
    $('body').removeClass('preloader-site');
});
$(document).ready(
    function () {
        var Body = $('body');
        Body.addClass('preloader-site');

        $("#downloadButton").click(function (){downloadTimetable();});
        $("#solveButton").click(function (){
            solve();
        });
    }
);

$(document).ajaxStart(function () {
    $('.preloader-wrapper').fadeIn();
    $('body').addClass('preloader-site');
});

$(document).ajaxStop(function () {
    $('.preloader-wrapper').fadeOut();
    $('body').removeClass('preloader-site');
});


function downloadTimetable(){

    // SHOW LOADER

    $.ajax({
        url: "/timeTable/download",
        method: "GET",
        xhrFields: {
            responseType: "blob"
        },

        success: function (data) {

            const blob = new Blob([data], { type: "application/pdf" });
            const url = window.URL.createObjectURL(blob);

            $("#pdfViewer").attr("src", url);
            $("#pdfModal").modal("show");
        },

        error: function () {
            alert("Failed to generate timetable.");
        },

        complete: function () {
            // $("#pdfLoader").hide();
        }
    });
}

function solve() {

    var csrfToken = $('meta[name="_csrf"]').attr('content');
    var csrfHeader = $('meta[name="_csrf_header"]').attr('content');

    $.ajax({
        url: "/timeTable/solve",
        type: "POST",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function () {
            console.log("success");
        },
        error: function (xhr) {
            showError("Start solving failed.", xhr);
        }
    });
}

