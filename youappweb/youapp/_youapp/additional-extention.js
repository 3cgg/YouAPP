/**
 * Created by J on 2017/10/27.
 */
(function(){

    $.validator.addMethod("valueNotEquals", function(value, element, arg){
        return arg != value;
    }, "Value must not equal arg .");

    $.fn.datetimepicker.defaults={
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        todayBtn: true
    };


    $(document).on('blur','input,textarea', function (event) {

        var $dom=$(event.target);
        if(!$dom.hasClass('noTrim')){
            $dom.val($.trim($dom.val()));
        }

    });

})(window);
