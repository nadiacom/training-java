/**
 * Created by ebiz on 23/03/17.
 */
//paginator add prev and next buttons
(function ($) {
    var pageItem = $(".pagination li").not(".prev,.next");
    var prev = $(".pagination li.prev");
    var next = $(".pagination li.next");
    var limitOnglet = 10;
    var currentOngletList = 1;

    $.refreshList = function () {
        var index = 1;
        pageItem.each(function () {
            $(this).css("display", "none");
            if (index >= (limitOnglet * (currentOngletList - 1) + 1)
                && index <= (limitOnglet * currentOngletList)) {
                $(this).css("display", "");
            }
            index += 1;
        });
    };

    $.fn.initialization = function (page) {
        currentOngletList = Math.ceil(page / limitOnglet);
        $.refreshList();
    };

    $.fn.paginatePrev = function () {
        currentOngletList -= 1;
        $.refreshList();
    };

    $.fn.paginateNext = function () {
        currentOngletList += 1;
        $.refreshList();
    };
}(jQuery));