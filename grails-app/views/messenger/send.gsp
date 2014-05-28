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
<div class="row">
    <div class="col-md-12">
        <g:render template="compose"/>

        <div class="message-list">
            <table id="messenger-inbox-tbl" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>Serial</th>
                    <th>Sender</th>
                    <th>Subject</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody id="introducerTableId">
                <g:each in="${retailAccount?.retailIntroducer}" var="introducer" status="i">
                    <tr>
                        %{--<td id="attCaption">${introducer.client.name}</td>
                        <td id="attName">${introducer.client.accountNo}</td>

                        <td class="actions ">
                            <div class="btn-group">
                                <a class="btn btn-sm delete btn-danger introducer-delete-link" onclick="return confirm('Are you sure delete Introducer Information?')"
                                   href="" id="${introducer.id}" retailAccount = "${retailAccount?.id}"
                                   title="Delete"><i class="glyphicon glyphicon-remove "></i></a>
                            </div>
                        </td>--}%
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
    </div>
</div>

<r:script>
    jQuery(function ($) {
        // dataTable
        var inboxTable = $('#messenger-inbox-tbl').dataTable({
            "sDom": "<'row'<'col-md-4'><'col-md-4'><'col-md-4'f>r>t<'row'<'col-md-4'l><'col-md-4'i><'col-md-4'p>>",
            "bProcessing": false,
            "bAutoWidth": true,
            "bServerSide": true,
            "sAjaxSource": "${g.createLink(controller: 'messenger',action: 'sendList')}",
            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                $('td:eq(3)', nRow).html(getActionButtons(nRow, aData));
                return nRow;
            },
            "aoColumns": [
                null,
                null,
                null,
                { "bSortable": false }
            ]
        });
    })

    function getActionButtons(nRow, aData) {
        var actionButtons = "";
        actionButtons += '<span class="col-xs-6"><a href="" userId="'+aData.DT_RowId+ '" class="edit-user" title="Edit">';
actionButtons += '<span class="green glyphicon glyphicon-edit"></span>';
actionButtons += '</a></span>';
        actionButtons += '<span class="col-xs-6"><a href="" userId="'+aData.DT_RowId+ '" class="delete-user" title="Delete">';
actionButtons += '<span class="red glyphicon glyphicon-trash"></span>';
actionButtons += '</a></span>';
        return actionButtons;
    }
</r:script>
</body>
</html>
