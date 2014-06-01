<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>Member Registration - World BOGA</title>
    <meta name="description" content="overview &amp; stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <!-- basic styles -->

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'font-awesome.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'datepicker.css')}"/>
    %{----}%
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'chosen.css')}"/>
    %{----}%
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace-fonts.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace-rtl.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace-skins.css')}"/>
    <script src="${resource(dir: 'js', file: 'ace-extra.js')}"></script>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.gritter.css')}"/>
    <script src="${resource(dir: 'js', file: 'jquery-1.10.2.min.js')}"></script>

    <script src="${resource(dir: 'js', file: 'bootstrap-datepicker.min.js')}"></script>


    <script src="${resource(dir: 'js', file: 'bootstrap.js')}"></script>
    <script src="${resource(dir: 'js', file: 'chosen.jquery.js')}"></script>
    %{--<script src="${resource(dir: 'js', file: 'fuelux.wizard.min.js')}"></script>--}%
    <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <script src="${resource(dir: 'js', file: 'additional-methods.min.js')}"></script>

    <script src="${resource(dir: 'js', file: 'ace-elements.min.js')}"></script>
    <script src="${resource(dir: 'js', file: 'spin.min.js')}"></script>
    <script src="${resource(dir: 'js', file: 'ace.js')}"></script>
    <script src="${resource(dir: 'js', file: 'ace-elements.js')}"></script>
    <script src="${resource(dir: 'js', file: 'jquery.gritter.min.js')}"></script>
    <g:layoutHead/>
    <r:layoutResources/>

</head>

<body>
<g:render template="/registration/topHeadBarRegistration"/>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>


        <div class="main-content" style="margin-left: 0;">
            <g:render template="/breadcrumbs"/>

            <div class="page-content" id="page-content">
                %{--<g:layoutBody/>--}%
            <g:render template="/registration/showRegistration"/>

            </div><!-- /.page-content -->
        </div><!-- /.main-content -->

    </div><!-- /.main-container-inner -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->
<script type="text/javascript">
    $(document).ready(function () {
        $('#ageConditionDiv').hide();
        // Date format
        $("#openingDate").datepicker({
            format: 'dd/mm/yyyy',
            gotoCurrent: true,
            autoclose: true
        });

        $('#registrationForm').validate({
            errorPlacement: function (error, element) {
            },
            focusInvalid: false,
            rules: {
                name: {
                    required: true
                },
                memberType: {
                    required: true
                },
                dob: {
                    required: true
                },
                country: {
                    required: true
                },
                email: {
                    required: true,
                    email: true
                },
                regUsername: {
                    required: true
                },
                regPassword: {
                    required: true,
                    minlength: 8
                },
                confirmPassword: {
                    equalTo: "#regPassword"
                },
                answer: {
                    required: true
                }
            },

            highlight: function (e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },

            success: function (e) {
                $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
                $(e).remove();
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                $('.alert-danger', $('#chartClassForm')).show();
            }
        });

        // Date checking
        $('#dob').change(function(){
            var dateString = $('#dob').val();
            var dateSplit = dateString.split("/");
            var dob = dateSplit[2] + "/" + dateSplit[1] + "/" + dateSplit[0];
            var dateDob = calcAge(dateString);
            if (dateDob < 18) {
                $('#ageConditionDiv').show();
                var condition = $('#ageCondition').prop('checked');
                if (condition == false) {
                    return;
                }
            }
            else{
                $('#ageConditionDiv').hide();
            }
        })

    });

    function calcAge(dateString) {
        var birthday = +new Date(dateString);
        return~~((Date.now() - birthday) / (31557600000));
    }

    function showSuccessMsg(message) {
        $.gritter.add({
            // (string | mandatory) the heading of the notification
            title: 'Success',
            // (string | mandatory) the text inside the notification
            text: message,
            class_name: 'gritter-success gritter-light'
        });
        return false;
    }

    function showErrorMsg(message) {
        $.gritter.add({
            title: 'Error',
            text: message,
            class_name: 'gritter-error gritter-light',
            sticky: false
        });
        return false;
    }

</script>
</body>
</html>