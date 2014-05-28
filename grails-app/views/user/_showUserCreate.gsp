<%@ page import="com.gsl.wboga.uma.security.Role; com.gsl.uma.security.Role" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <style type="text/css">
    .help-block {
        margin-bottom: 3px;
        margin-top: 3px;
    }
    .form-group{
        margin-bottom:5px;
    }
    </style>
</head>

<body>

<div class="row">
    <div class="col-xs-12">
        <g:if test='${flash.message}'>
            <div class='alert alert-success '>
                <i class="icon-bell green"><b>${flash.message}</b></i>
            </div>
        </g:if>
        <g:hasErrors bean="${user}">
            <div class='alert alert-success '>
                <ul>
                    <g:eachError var="err" bean="${user}">
                        <li><g:message error="${err}"/></li>
                    </g:eachError>
                </ul>
            </div>
        </g:hasErrors>
        <g:form class="form-horizontal" id="userCreateForm" method="post" role="form" url="[action: 'save', controller: 'user']" onsubmit="return false;">
            <g:hiddenField name="id" value="${user?.id}"/>
            <div class="form-group ${hasErrors(bean: user, field: 'username', 'has-error')}">
                <label class="col-sm-3 control-label no-padding-right" for="username">User Name</label>

                <div class="col-xs-12 col-sm-9">
                    <div class="clearfix">
                        <g:textField id="username" name="username" placeholder="User name" class="col-xs-10 col-sm-5"
                                     value="${fieldValue(bean: user, field: 'username')}"/>
                    </div>
                    %{--<span class="help-block" for="username">Please provide your user name s</span>--}%
                </div>
            </div>


            <div class="form-group ${hasErrors(bean: user, field: 'email', 'has-error')}">
                <label class="col-sm-3 control-label no-padding-right" for="email">Email</label>

                <div class='col-xs-12 col-sm-9'>
                    <div class="clearfix">
                        <g:textField id="email" name="email" placeholder="Email address" class="col-xs-10 col-sm-5"
                                     value="${fieldValue(bean: user, field: 'email')}"/>
                    </div>
                </div>
            </div>

            <div class="form-group ${hasErrors(bean: user, field: 'password', 'has-error')}">
                <label class="col-sm-3 control-label no-padding-right" for="password">Password</label>

                <div class='col-xs-12 col-sm-9'>
                    <div class="clearfix">
                        <g:passwordField id="password" name="password" placeholder="Password" class="col-xs-10 col-sm-5"/>
                    </div>
                </div>
            </div>

            <div class="form-group ${hasErrors(bean: user, field: 'confirm', 'has-error')}">
                <label class="col-sm-3 control-label no-padding-right" for="confirm">Confirm Password</label>

                <div class='col-xs-12 col-sm-9'>
                    <div class="clearfix">
                        <g:passwordField id="confirm" name="confirm" placeholder="Confirm Password"
                                         class="col-xs-10 col-sm-5"/>
                    </div>
                </div>
            </div>
            <div class="form-group ${hasErrors(bean: user, field: 'role', 'has-error')}">
                <label class="col-sm-3 control-label no-padding-right" for="confirm">Role</label>

                <div class="col-xs-12 col-sm-9">
                    <div class="clearfix">
                        <g:select class="width-40 chosen-select" id="role" name='role'
                                  noSelection="${['': 'Select Role...']}"
                                  from='${com.gsl.wboga.uma.security.Role.findAllByAuthorityNotIlike('ROLE_SUPER_ADMIN')}'
                                  optionKey="id" optionValue="authority"></g:select>
                    </div>
                </div>
            </div>
            <div class="form-group ${hasErrors(bean: user, field: 'enabled', 'has-error')}">
                <label class="col-sm-3 control-label no-padding-right" for="confirm">Status</label>

                <div class='col-xs-12 col-sm-9'>
                    <g:radioGroup name="enabled" labels="['Enable', 'Disable']" values="['true', 'false']"
                                  value="${user?.enabled}">
                        <label class="radio-inline">${it.label} ${it.radio}</label>
                    </g:radioGroup>
                </div>
            </div>
            <div class="clearfix form-actions">
                <div class="col-md-offset-3 col-md-9">
                    <g:submitButton name="resetBtn" type='reset' value='Reset' id="resetBtn" class="btn btn-info"/>
                    &nbsp; &nbsp; &nbsp;
                    <g:if test="${user?.id}">
                        <g:submitButton name="Update" id="saveBtn" class="btn btn-info"/>
                    </g:if>
                    <g:else>
                        <g:submitButton name="Save" id="saveBtn" class="btn btn-info"/>
                    </g:else>
                </div>
            </div>
        </g:form>
    </div><!-- /.col -->
</div><!-- /.row -->

<script type="text/javascript">
    $(".chosen-select").chosen();
    jQuery(function ($) {
        $(".chosen-select").css('width','200px').select2({allowClear:true})
                .on('change', function(){
                    $(this).closest('form').validate().element($(this));
                });
        $('#userCreateForm').validate({
            errorElement: 'span',
            errorClass: 'help-block',
            focusInvalid: false,
            rules: {
                username: {
                    required: true
//                        email:true
                },
                email: {
                    required: true,
                    email:true
                },
                password: {
                    required: true,
                    minlength: 5
                },
                confirm: {
                    required: true,
                    equalTo: "#password"
                },
                role: {
                    required: true
                },
                enabled: {
                    required: true
                }
            } ,
            messages: {
                username: {
                    required: "User name is required"
//                        email: "User name not valid."
                }
            },
            highlight: function (e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },

            success: function (e) {
                $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
                $(e).remove();
            },
            errorPlacement: function (error, element) {
                if(element.is(':checkbox') || element.is(':radio')) {
                    var controls = element.closest('div[class*="col-"]');
                    if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
                    else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                }
                else if(element.is('.select2')) {
                    error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                }
                else if(element.is('.chosen-select')) {
                    error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                }
                else error.insertAfter(element.parent());
//                return true;
            },
            submitHandler: function (form) {
                $.ajax({
                    url:"${createLink(controller: 'user', action: 'save')}",
                    type:'post',
                    data: $("#userCreateForm").serialize(),
                    success:function(data){
                        $('#page-content').html(data);
                    },
                    failure:function(data){
                    }
                })

            }
        });
    });

</script>
</body>
</html>