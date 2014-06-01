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
                        <a class="sender" href="#">${messenger.sender.user.username}</a>
                        <i class="icon-time bigger-110 orange middle"></i>
                        <span class="time">${messenger.dateOfMessage.format('dd/MM/yyyy, hh:mm a')}%{--Today, 7:15 pm--}%</span>
                    </div>

                    <div class="action-buttons pull-right">
                        <a href="#" id="replyBox" title="Reply"><i class="icon-reply green icon-only bigger-130"></i></a>
                        <a href="#"><i class="icon-mail-forward blue icon-only bigger-130"></i></a>
                        <a href="#"><i class="icon-trash red icon-only bigger-130"></i></a>
                    </div>
                </div>

                <div class="hr hr-double"></div>

                <div class="slimScrollDiv">
                    <div class="message-body"><p>${messenger.messageBody}</p></div>
                    <div class="slimScrollBar ui-draggable"></div>
                    <div class="slimScrollRail"></div>
                </div>
                <div class="hr"></div>

                %{-- reply --}%
                <g:each in="${messengerList.messengers}" var="replyMessage">
                    <div class="message-header clearfix">
                        <div class="pull-left">
                            <i class="icon-star orange2 mark-star"></i>
                            <a class="sender" href="#">${replyMessage.sender.user.username}</a>
                            <i class="icon-time bigger-110 orange middle"></i>
                            <span class="time">${replyMessage.dateOfMessage.format('dd/MM/yyyy, hh:mm a')}%{--Today, 7:15 pm--}%</span>
                        </div>

                        <div class="action-buttons pull-right">
                            <a href="#" id="replyBox" title="Reply"><i class="icon-reply green icon-only bigger-130"></i></a>
                            <a href="#"><i class="icon-mail-forward blue icon-only bigger-130"></i></a>
                            <a href="#"><i class="icon-trash red icon-only bigger-130"></i></a>
                        </div>
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
                <form action="${createLink(controller: 'messenger', action: 'reply')}" method="post" class="">
                    <input type="hidden" name="parentId" value="${messengerList.id}" />
                    <textarea name="messageBody" class="col-md-12" />

                    <input type="submit" class="btn btn-info btn-sm tooltip-info" value="Send" />
                </form>
            </div>
        </div>
    </div>
</div>

<r:script>
    jQuery(function ($) {
        // dataTable
        var inboxTable = $('#messenger-inbox-tbl').dataTable({
            "sDom": " ", // <'row'<'col-md-4'><'col-md-4'><'col-md-4'f>r>t<'row'<'col-md-4'l><'col-md-4'i><'col-md-4'p>>
            "bProcessing": false,
            "bAutoWidth": true
        });

        $('.replyDiv').hide();
        $('#replyBox').click(function(){
            //alert("Yes reply On!");
            $('.replyDiv').show();
        })

    })
</r:script>
</body>
</html>
