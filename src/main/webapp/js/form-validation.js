/**
 * Created by ebiz on 30/03/17.
 */

$(function () {
    // Initialize form validation on the registration form.
    // It has the name attribute "computer_edit_add"
    $("form[id='computer_form']").validate({
        // Specify validation rules
        rules: {
            computerName: "required",
            introduced: {
                datePattern: true,
                required: false
            },
            discontinued: {
                datePattern: true,
                greaterThan: $('#introduced'),
                required: false,
            }
        },
        messages: {
            name: "Please enter a computer name"
        }
    });

    $.validator.addMethod(
        "datePattern",
        function (value, element) {
            return this.optional(element) || value.match(/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/);
        },
        "Please enter a date in the format YYYY-MM-dd."
    );

    $.validator.addMethod("greaterThan",
        function (value, element, params) {
            if ((element.value.length === 0)) {
                return true;
            }
            if (!/Invalid|NaN/.test(new Date(value))) {
                return Date(value) > new Date($(params).val());
            }
            return (Number(value) > Number($(params).val()));
        }, 'Please enter a discontinued date greater than introduced date. !!');
});

