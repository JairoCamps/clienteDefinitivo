 $(document).ready(function () {
            $('#txtSearchText').keypress(function (e) {
                if (e.keyCode == 13)
                    $('#btnSearch').click();
            });
        });

        function WikipediaAPISearch() {
            var txt = $("#txtSearchText").val();

            $.ajax({
                type: "GET",
                url: "http://en.wikipedia.org/w/api.php?action=opensearch&search=" + txt + "&callback=?",
                contentType: "application/json; charset=utf-8",
                async: false,
                dataType: "json",
                success: function (data, textStatus, jqXHR) {
                    $.each(data, function (i, item) {
                        if (i == 1) {
                            var searchData = item[0];
                            WikipediaAPIGetContent(searchData);
                        }
                    });
                },
                error: function (errorMessage) {
                    alert(errorMessage);
                }
            });
        }

        function WikipediaAPIGetContent(search) {
            $.ajax({
                type: "GET",
                url: "http://en.wikipedia.org/w/api.php?action=parse&format=json&prop=text&section=0&page=" + search + "&callback=?",
                contentType: "application/json; charset=utf-8",
                async: false,
                dataType: "json",
                success: function (data, textStatus, jqXHR) {

                    var markup = data.parse.text["*"];
                    var blurb = $('<div></div>').html(markup);

                    // se quitan los enlaces
                    blurb.find('a').each(function () { $(this).replaceWith($(this).html()); });

                    // se quitan referencias
                    blurb.find('sup').remove();

                    // se quitan citas
                    blurb.find('.mw-ext-cite-error').remove();
                    $('#results').html($(blurb).find('p'));
                    $('#results').html(blurb);

                },
                error: function (errorMessage) {
                    alert(errorMessage);
                }
            });
        }