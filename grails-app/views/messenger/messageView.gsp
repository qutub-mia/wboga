<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="layout" content="wboga"/>
    <title>Inbox - Ace Admin</title>
</head>

<body>
<g:if test="${flash.message}">
    <div class="alert alert-success">
        <i class="icon-bell green"><b>${flash.message}</b></i> <a class="close" data-dismiss="alert">×</a>
    </div>
</g:if>
<div class="page-header">
    <h1>View Message</h1>
</div>


<div class="row">
    <div class="col-md-12">
        <div id="id-message-content" class="message-content">
            <g:each in="${messengerList}" var="messenger">
                <div class="message-header clearfix">
                    <div class="pull-left">
                        <span class="blue bigger-125">Subject:</span> <span class="bigger-125"> ${messenger.subject} </span>
                        <div class="space-4"></div>
                        <i class="icon-star orange2 mark-star"></i>
                        <a class="sender" href="#">
                            ${messenger.sender.user.username}
                        </a>
                        <i class="icon-time bigger-110 orange middle"></i>
                        <span class="time">${messenger.dateOfMessage.format('dd/MM/yyyy, hh:mm a')}%{--Today, 7:15 pm--}%</span>
                    </div>

                    %{--<div class="action-buttons pull-right">
                        <a href="#" id="replyBox" title="Reply"><i class="icon-reply green icon-only bigger-130"></i></a>
                        <a href="#"><i class="icon-mail-forward blue icon-only bigger-130"></i></a>
                        <a href="#"><i class="icon-trash red icon-only bigger-130"></i></a>
                    </div>--}%
                </div>

                <div class="hr hr-double"></div>

                <div class="slimScrollDiv">
                    <div class="message-body"><p>${messenger.messageBody}</p></div>
                    <div class="slimScrollBar ui-draggable"></div>
                    <div class="slimScrollRail"></div>
                </div>
                <div class="hr"></div>

                %{-- reply --}%
                <g:each in="${messengerList.messengers.sort{it.id}}" var="replyMessage">
                    <div class="message-header clearfix">
                        <div class="pull-left">
                            <i class="icon-star orange2 mark-star"></i>
                            <a class="sender" href="#">${replyMessage.sender.user.username}</a>
                            <i class="icon-time bigger-110 orange middle"></i>
                            <span class="time">${replyMessage.dateOfMessage.format('dd/MM/yyyy, hh:mm a')}%{--Today, 7:15 pm--}%</span>
                        </div>

                        %{--<div class="action-buttons pull-right">
                            <a href="#" id="replyBox" title="Reply"><i class="icon-reply green icon-only bigger-130"></i></a>
                            <a href="#"><i class="icon-mail-forward blue icon-only bigger-130"></i></a>
                            <a href="#"><i class="icon-trash red icon-only bigger-130"></i></a>
                        </div>--}%
                    </div>
                    <div class="hr hr-double"></div>

                    <div class="slimScrollDiv">
                        <div class="message-body"><p>${replyMessage.messageBody}</p></div>
                        <div class="slimScrollBar ui-draggable"></div>
                        <div class="slimScrollRail"></div>
                    </div>
                    <div class="hr"></div>
                </g:each>

                %{-- reply end --}%

                <div class="message-header clearfix">
                    <div class="action-buttons pull-right">
                        <a href="#" id="replyBox" title="Reply"><i class="icon-reply green icon-only bigger-130"></i></a>
                        %{--<a href="#"><i class="icon-mail-forward blue icon-only bigger-130"></i></a>
                        <a href="#"><i class="icon-trash red icon-only bigger-130"></i></a>--}%
                    </div>
                </div>

            </g:each>

        </div>
    </div>

</div>


<div class="replyDiv">
    <div class="widget-box">
        <div class="widget-header">
            <h5 class="smaller">Reply: <small>${messengerList?.subject}</small></h5>
        </div>

        <div class="widget-body">
            <div class="widget-main">
                <form id="id-message-replyForm" class="form-horizontal">
                    <input type="hidden" name="parentId" value="${messengerList.id}" />
                    <textarea name="messageBody" class="col-md-12"></textarea>

                    <button type="button" id="replyButton" class="btn btn-sm btn-primary no-border">
                        <span class="bigger-110">Send</span>
                        <i class="icon-arrow-right icon-on-right"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<r:script>
    jQuery(function ($) {

        $('.replyDiv').hide();

        $('#replyBox').click(function(){
            $('.replyDiv').show();
        });

        $('#replyButton').click(function(){
            $.ajax({
                url:"${createLink(controller: 'messenger', action: 'reply')}",
                type:'post',
                data: $("#id-message-replyForm").serialize(),
                success:function(data){
                    $('body').html(data);
                },
                failure:function(data){
                }
            })
        });

    })
</r:script>
</body>
</html>
