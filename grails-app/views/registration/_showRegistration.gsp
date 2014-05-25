<%@ page import="wboga.core.Country; wboga.core.MemberType" %>
<!DOCTYPE html>
<html>
<head>

    <script type="text/javascript">
        $(document).ready(function(){
            $('#ageConditionDiv').hide();
            // Date format
            $("#openingDate").datepicker({
                format: 'dd/mm/yyyy',
                gotoCurrent: true,
                autoclose: true
            });

            $('#registrationForm').validate({

                errorElement: 'small',
                errorClass: 'help-block',
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
                        required: true
                    },
                    username: {
                        required: true
                    },
                    password: {
                        required: true
                    },
                    cPassword: {
                        required: true
                    },
                    answer: {
                        required: true
                    }
                } ,
                messages: {
                    name: {
                        required: " "
                    },
                    memberType: {
                        required: " "
                    },
                    dob: {
                        required: " "
                    },
                    country: {
                        required: " "
                    },
                    email: {
                        required: " "
                    },
                    username: {
                        required: " "
                    },
                    password: {
                        required: " "
                    },
                    cPassword: {
                        required: " "
                    },
                    answer: {
                        required: " "
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
                },
                submitHandler: function (form) {

                    // Date checking
                    var dateString = $('#dob').val();
                    var dateSplit = dateString.split("/");
                    var dob = dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0];
                    var dateDob = calcAge(dateString);
                    if(dateDob < 18){
                        $('#ageConditionDiv').show();
                        var condition = $('#ageCondition').prop('checked');
                        if(condition == false){
                            return;
                        }
                    }

                    // Confirm password checking
                    var password = $('#password').val();
                    var cPassword = $('#cPassword').val();
                    if (password != cPassword){
                        $('#cPassword').addClass('red');
                        alert("Confirm password not match, Try again!");
                        return;
                    }
                    else{
                        $('#cPassword').removeClass('red');
                    }


                    jQuery.ajax({
                        url:"${createLink(controller: 'registration', action: 'save')}",
                        type:'post',
                        data: $('#registrationForm').serialize(),
                        success:function(data){
                            //$('#page-content').html(data);
                            $('body').html(data);
                        },
                        failure:function(data){
                        }
                    })
                }
            });

            function calcAge(dateString) {
                var birthday = +new Date(dateString);
                return~~ ((Date.now() - birthday) / (31557600000));
            }
        });

    </script>
</head>

<body>

<g:if test="${flash.message}">

    <div class="alert alert-success">
        <i class="icon-bell green"> <b> ${flash.message} </b> </i> <a class="close" data-dismiss="alert">×</a>
    </div>
</g:if>

<div class="widget-box" style="margin: 5px 150px;">
    <div class="widget-header">
        <h4 class="widget-title">Member Registration</h4>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <g:form class="form-horizontal" method="post" name="registrationForm" id="registrationForm" role="form" url="[action: 'saveRegistration', controller: 'deposit']" onsubmit="return false;">
                <fieldset>
                    <div class="row">
                        <div class="col-md-8">

                            <div class="form-group">
                                <label for="memberType" class="control-label col-md-4">Package</label>
                                <div class="col-md-6">
                                    <g:select id="memberType" name='memberType' class="form-control" noSelection="${['': '- Select Package -']}"
                                              from='${MemberType.list(sort: 'name')}' value=""
                                              optionKey="id" optionValue="name">
                                    </g:select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="control-label col-md-4">Name <sup class="red">*</sup></label>
                                <div class="col-md-6">
                                    <input type="text" id="name" class="form-control" name="name" value="" placeholder="Enter the Name" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-4 no-padding-right" for="openingDate">Date of birth <span class="red">*</span></label>
                                <div class="col-md-6">
                                    <div class="clearfix">
                                        <div class="input-append date input-group" id="openingDate">
                                            <input type="date" id="dob" name="dob" value="" class="form-control datepicker" data-date-format="dd-mm-yyyy"/>
                                            <span class="input-group-addon add-on"><i class="icon-calendar"></i></span>
                                        </div>
                                    </div>
                                    <div id="ageConditionDiv" class="red">
                                        <input type="checkbox" class="ace" id="ageCondition"><span class="lbl"></span>
                                        <small>Under <b>18 years</b> old must have parents or guardian permission</small>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="country" class="control-label col-md-4">Country <sup class="red">*</sup></label>
                                <div class="col-md-6">
                                    <g:select id="country" name='country' class="form-control" noSelection="${['': '- Select Country -']}"
                                              from='${Country.list(sort: 'name')}' value=""
                                              optionKey="id" optionValue="name">
                                    </g:select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="email" class="control-label col-md-4">Email <sup class="red">*</sup></label>
                                <div class="col-md-6">
                                    <input type="text" id="email" class="form-control" name="email" value="" placeholder="Enter the email" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="username" class="control-label col-md-4">Username <sup class="red">*</sup></label>
                                <div class="col-md-6">
                                    <input type="text" id="username" class="form-control" name="username" value="" placeholder="Enter the username" />
                                </div>
                            </div>


                            <div class="form-group">
                                <label for="password" class="control-label col-md-4">Password <sup class="red">*</sup></label>
                                <div class="col-md-6">
                                    <input type="text" id="password" class="form-control" name="password" value="" placeholder="Enter the password" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="password" class="control-label col-md-4">Confirm Password <sup class="red">*</sup></label>
                                <div class="col-md-6">
                                    <input type="text" id="cPassword" class="form-control" name="cPassword" value="" placeholder="Enter the password" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-4">Hint</label>
                                <input type="hidden" name="hint" value="${hint?.id}">
                                <label class="col-md-8 green">${hint?.question}</label>
                            </div>

                            <div class="form-group">
                                <label for="answer" class="control-label col-md-4">Answer <sup class="red">*</sup></label>
                                <div class="col-md-6">
                                    <input type="text" id="answer" class="form-control" name="answer" value="" placeholder="Enter the answer" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="captcha" class="control-label col-md-4">Type the letters <img src="${createLink(controller: 'simpleCaptcha', action: 'captcha')}"/></label>
                                <div class="col-md-6">
                                    <input type="text" id="captcha" class="form-control" name="captcha" value="" placeholder="Enter the captcha" />
                                </div>
                            </div>

                        </div>
                    </div>

                    <div class="clearfix form-actions">
                        <div class="align-center">
                            <button type="reset" class="btn">
                                <i class="icon-undo bigger-110"></i>
                                Reset
                            </button>
                            <button type="submit" class="btn btn-info">
                                <i class="icon-ok bigger-110"></i>
                                Create
                            </button>
                        </div>
                    </div>


                </fieldset>
            </g:form>
        </div>
    </div>
</div>

<r:script type="text/javascript">
    $(document).ready(function(){
        $('#ageConditionDiv').hide();
        // Date format
        $("#openingDate").datepicker({
            format: 'dd/mm/yyyy',
            gotoCurrent: true,
            autoclose: true
        });

        $('#registrationForm').validate({

            errorElement: 'small',
            errorClass: 'help-block',
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
                    required: true
                },
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                cPassword: {
                    required: true
                },
                answer: {
                    required: true
                }
            } ,
            messages: {
                name: {
                    required: " "
                },
                memberType: {
                    required: " "
                },
                dob: {
                    required: " "
                },
                country: {
                    required: " "
                },
                email: {
                    required: " "
                },
                username: {
                    required: " "
                },
                password: {
                    required: " "
                },
                cPassword: {
                    required: " "
                },
                answer: {
                    required: " "
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
            },
            submitHandler: function (form) {

               // Date checking
               var dateString = $('#dob').val();
               var dateSplit = dateString.split("/");
               var dob = dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0];
               var dateDob = calcAge(dateString);
               if(dateDob < 18){
                   $('#ageConditionDiv').show();
                   var condition = $('#ageCondition').prop('checked');
                   if(condition == false){
                        return;
                   }
               }

               // Confirm password checking
               var password = $('#password').val();
               var cPassword = $('#cPassword').val();
               if (password != cPassword){
                   $('#cPassword').addClass('red');
                   alert("Confirm password not match, Try again!");
                   return;
               }
               else{
                   $('#cPassword').removeClass('red');
               }


                jQuery.ajax({
                    url:"${createLink(controller: 'registration', action: 'save')}",
                    type:'post',
                    data: $('#registrationForm').serialize(),
                    success:function(data){
                        //$('#page-content').html(data);
                        $('body').html(data);
                    },
                    failure:function(data){
                    }
                })
            }
        });

        function calcAge(dateString) {
            var birthday = +new Date(dateString);
            return~~ ((Date.now() - birthday) / (31557600000));
        }
     });

</r:script>



</body>
</html>