<html xmlns="http://www.w3.org/1999/html">
<head>
	<meta name='layout' content='logintpl'/>
	<title>Welcome to World BOGA</title>
</head>

<body >

<div class="main-container container">
    <div class="main-content">
        <div class="row">
            <div class="span12">
                <div class="login-container">
                    %{--<div class="row">
                        <div class="center">
                            <h1>
                                <i class="icon-leaf green"></i>
                                <span class="red"></span>
                            </h1>
                        </div>
                    </div>--}%

                    <div class="space-6"></div>

                    <div class="row">
                        <div class="position-relative">
                            <div id="login-box" class="login-box visible widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header blue lighter bigger">
                                            <i class="icon-coffee green"></i>
                                           Welcome to World BOGA
                                        </h4>

                                       <h5 class="help-block"><g:if test='${flash.message}'>  <div class='login_message alert-danger '> <i class="blue"> <b> ${flash.message} </b> </i></div> </g:if> </h5>

                                        <div class="space-6"></div>

                                        <form action='${postUrl}' method='POST' id='loginForm' class='cssform ' autocomplete='off'>
                                            <fieldset>
                                            <div class="form-group">
                                                <label for='username' class=" control-label">Username</label>
                                             <div class=" ">
                                             <span class="block input-icon input-icon-right ">
                                                    <input type="text" class="text_ span12 form-control" name='j_username' id='username' placeholder="Username" value="admin"/>
                                                    <i class="icon-user"></i>

                                                </span>
                                                 </div>
                                             </div>
                                            <div class="form-group">

                                            <label for='password' class=" control-label">Password</label>
                                                <div class="">
                                                 <span class="block input-icon input-icon-right">
                                                    <input type="password" class="text_ span12 form-control required" placeholder="Password" name='j_password' id='password' value="password" />
                                                    <i class="icon-lock"></i>
                                                 </span>
                                                </div>

                                            </div>
                                                <div class="space"></div>

                                                <div class="clearfix">
                                                 %{--   <label class="inline">
                                                    <input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/
                                                        <span class="lbl"> <label for='remember_me'></label></span>
                                                    </label>--}%
                                                    <input type="submit" value="Login" name="submit" class="width-35 pull-right btn btn-small btn-primary" id="submit" />
                                                </div>
                                                <div class="space-4"></div>
                                            </fieldset>
                                        </form>
                                    </div><!--/widget-main-->

                                    <div class="toolbar clearfix">
                                        %{--<div>
                                            <a href="#"   class="forgot-password-link">
                                                <i class="icon-arrow-left"></i>
                                                I forgot my password
                                            </a>
                                        </div>--}%
                                        <div>
                                            <a href="${createLink(controller: 'registration', action: 'create')}"  class="user-signup-link">
                                                I want to register
                                                <i class="icon-arrow-right"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div><!--/widget-body-->
                            </div><!--/login-box-->
                        </div><!--/login-box-->
                    </div><!--/position-relative-->
                </div>
            </div>
        </div><!--/.span-->
    </div><!--/.row-->
</div>


<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->

</script>

<r:script>
    jQuery(function ($) {

        $('#loginForm').validate({

            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: false,
            rules: {
                j_username: {
                    required: true
//                        email:true
                },
                j_password: {
                    required: true,
                    minlength: 5
                }
            } ,
            messages: {
                j_username: {
                    required: "Please provide your user name"
//                        email: "User name not valid."
                },
                j_password: {
                    required: "Please specify a password.",
                    minlength: "Password not valid."
                }
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                $('.alert-danger', $('.loginForm')).show();
            },

            highlight: function (e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },

            success: function (e) {
                $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
                $(e).remove();
            }
        });
    });

</r:script>


</body>
</html>
