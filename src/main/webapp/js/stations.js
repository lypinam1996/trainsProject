$(function () {
    $("#lastStation").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "/restStations",
                dataType: "json",
                data: {
                    term: request.term
                },
                success: function (data) {
                    response(data);
                }
            });
        },
        minLength: 2,
        select: function (event, ui) {
        }
    });
});

$(function () {
    $("#firstStation").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "/restStations",
                dataType: "json",
                data: {
                    term: request.term
                },
                success: function (data) {
                    response(data);
                }
            });
        },
        minLength: 2,
        select: function (event, ui) {
        }
    });
});