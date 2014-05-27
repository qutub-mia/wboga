<!DOCTYPE html>
<html lang="en">
<head>
    <style>
    div.ui-ios-overlay {
        background: none repeat scroll 0 0 rgb(136, 136, 136);
    }
    </style>
    <meta charset="utf-8"/>
    <title>Dashboard - World BOGA</title>

    <meta name="description" content="overview &amp; stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <!-- basic styles -->

%{--    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'font-awesome.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'datepicker.css')}"/>
--}%%{----}%%{--
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'chosen.css')}"/>
--}%%{----}%%{--
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace-fonts.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace-rtl.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace-skins.css')}"/>--}%



    <link href="${resource(dir: 'css', file: 'bootstrap.css')}" rel="stylesheet"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'font-awesome.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'select2.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css/jqGrid', file: 'ui.jqgrid.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui-1.10.3.full.min.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'datepicker.css')}" />
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.gritter.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'chosen.css')}"/>


    <!-- page specific plugin styles -->

    <!-- fonts -->

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace-fonts.css')}"/>

    <!-- ace styles -->

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace-rtl.css')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace-skins.css')}"/>

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->

    <script src="${resource(dir: 'js', file: 'ace-extra.js')}"></script>

    <g:layoutHead/>
    <r:layoutResources/>

</head>

<body>
<g:render template="/topHeadBar"/>

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

        <g:render template="/leftNavBar"/>

        <div class="main-content">

            <g:render template="/breadcrumbs"/>

            <div class="page-content" id="page-content">

                <g:layoutBody/>

            </div><!-- /.page-content -->
        </div><!-- /.main-content -->

    </div><!-- /.main-container-inner -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->

%{--<script type="text/javascript">
    window.jQuery || document.write("<script src='${resource(dir: 'js',file: 'jquery-2.0.3.min.js')}'>" + "<" + "/script>");
</script>

<script type="text/javascript">
 window.jQuery || document.write("<script src='${resource(dir: 'js', file: 'jquery-1.10.2.min.js')}'>"+"<"+"/script>");
</script>--}%

<script src="${resource(dir: 'js', file: 'jquery-2.0.3.min.js')}"></script>
<script src="${resource(dir: 'js', file: 'ace-extra.js')}"></script>
<script src="${resource(dir: 'js', file: 'jquery.dataTables.js')}"></script>
<script src="${resource(dir: 'js', file: 'jquery.dataTables.bootstrap.js')}"></script>

<script src="${resource(dir: 'js', file: 'bootstrap-datepicker.min.js')}"></script>


%{--<script src="${resource(dir: 'js', file: 'bootstrap.js')}"></script>--}%
%{--<script src="${resource(dir: 'js', file: 'jquery.hotkeys.min.js')}"></script>
<script src="${resource(dir: 'js', file: 'bootstrap-wysiwyg.min.js')}"></script>
<script src="${resource(dir: 'js', file: 'chosen.jquery.js')}"></script>
--}%%{--<script src="${resource(dir: 'js', file: 'fuelux.wizard.min.js')}"></script>--}%%{--
<script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
<script src="${resource(dir: 'js', file: 'additional-methods.min.js')}"></script>

<script src="${resource(dir: 'js', file: 'ace-elements.min.js')}"></script>
--}%%{--<script src="${resource(dir: 'js', file: 'iosOverlay.js')}"></script>--}%%{--
<script src="${resource(dir: 'js', file: 'spin.min.js')}"></script>
<script src="${resource(dir: 'js', file: 'ace-elements.js')}"></script>
<script src="${resource(dir: 'js', file: 'ace.js')}"></script>

<script src="${resource(dir: 'js', file: 'jquery.gritter.min.js')}"></script>--}%

<!-- inline scripts related to this page -->


%{--comon inlcude--}%

<script type="text/javascript">
    window.jQuery || document.write("<script src='${resource(dir: 'js',file: 'jquery-2.0.3.min.js')}'>" + "<" + "/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${resource(dir: 'js', file: 'jquery-1.10.2.min.js')}'>"+"<"+"/script>");
</script>
<![endif]-->



<script src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>
<script src="${resource(dir: 'js', file: 'jquery.dataTables.bootstrap.js')}"></script>

<script src="${resource(dir: 'js/date-time', file: 'bootstrap-datepicker.min.js')}"></script>


<script src="${resource(dir: 'js', file: 'bootstrap.js')}"></script>
<script src="${resource(dir: 'js', file: 'jquery.hotkeys.min.js')}"></script>
<script src="${resource(dir: 'js', file: 'bootstrap-wysiwyg.min.js')}"></script>
<script src="${resource(dir: 'js', file: 'typeahead-bs2.min.js')}"></script>

<!-- page specific plugin scripts -->

<!-- inline scripts related to this page -->

<!--[if lte IE 8]>
		  <script src="${resource(dir: 'js', file: 'excanvas.js')}"></script>
<![endif]-->

<script src="${resource(dir: 'js', file: 'chosen.jquery.js')}"></script>

%{--<script src="${resource(dir: 'js', file: 'jquery.slimscroll.js')}"></script>--}%

<script src="${resource(dir: 'js', file: 'jquery.sparkline.js')}"></script>


<!-- ace scripts -->
<script src="${resource(dir: 'js', file: 'fuelux.wizard.min.js')}"></script>
<script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
<script src="${resource(dir: 'js', file: 'spin.min.js')}"></script>
<script src="${resource(dir: 'js', file: 'ace-elements.min.js')}"></script>
<script src="${resource(dir: 'js', file: 'ace.js')}"></script>

<r:layoutResources/>

<script type="text/javascript">
    /*$.ajaxSetup({
        timeout:50000,
        statusCode: {
            404: function() {
                bootstrap_alert.error("Ooops.. requested page is not available...");
            },
            401: function() {
                window.location = "${createLink(controller: 'logout')}";
//                bootstrap_alert.error("Ooops.. your session has timeout");
            },
            500: function() {
                bootstrap_alert.error("Ooops.. server has got some problems ...");
            },
            0: function() {
                bootstrap_alert.warning("Ooops.. this is taking much time.. retry"); //alert( "aborted" );
            }*//**//*,
            200:function(){
                bootstrap_alert.success("Done"); //alert( "aborted" );
            }
        }
    });*/
    $(document)
            .ajaxStart(function () {
                showSpinner(true);
            })
            .ajaxStop(function () {
//                $loading.hide();
                showSpinner(false);
            });
/*    bootstrap_alert = function() {};
    bootstrap_alert.warning = function(message) {
        $('#notify').html('<div class="alert alert-warning"><a class="close" data-dismiss="alert">×</a><span>'+message+'</span></div>')
    };
    bootstrap_alert.error = function(message) {
        $('#notify').html('<div class="alert alert-error"><a class="close" data-dismiss="alert">×</a><span>'+message+'</span></div>')
    };
    bootstrap_alert.success = function(message) {
        $('#notify').html('<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>'+message+'</span></div>')
    };*/

    $(document).ready(function () {
        showSuccessMsg("Welcome to World BOGA");

    });

    function showSuccessMsg(message){
        $.gritter.add({
            // (string | mandatory) the heading of the notification
            title: 'Success',
            // (string | mandatory) the text inside the notification
            text: message,
            class_name: 'gritter-success gritter-light'
        });
        return false;
    }

    function showErrorMsg(message){
        $.gritter.add({
            title: 'Error',
            text: message,
            class_name: 'gritter-error gritter-light',
            sticky: false
        });
        return false;
    }

    function showSpinner(control) {
        if (control) {
            $('.icon-spinner').modal('show');
        } else {
            $('.icon-spinner').modal('hide');
        }
        return false;
    }
</script>
</body>
</html>