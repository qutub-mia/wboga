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
        <i class="icon-bell green"> <b>${flash.message}</b></i> <a class="close" data-dismiss="alert">×</a>
    </div>
</g:if>
<div class="page-header">
    <h1>Trash Message</h1>
</div>

<div class="row">
    <div class="col-md-12">
        <g:render template="compose"/>

        <div class="message-list">
            <table id="messenger-trash-tbl" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>Serial</th>
                    <th>Name</th>
                    <th>Subject</th>
                    <th>Date</th>
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
        var inboxTable = $('#messenger-trash-tbl').dataTable({
            "sDom": "<'row'<'col-md-4'><'col-md-4'><'col-md-4'f>r>t<'row'<'col-md-4'l><'col-md-4'i><'col-md-4'p>>",
            "bProcessing": false,
            "bAutoWidth": true,
            "bServerSide": true,
            "sAjaxSource": "${g.createLink(controller: 'messenger',action: 'trashList')}",
            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                $('td:eq(4)', nRow).html(getActionButtons(nRow, aData));
                return nRow;
            },
            "aoColumns": [
                null,
                null,
                null,
                null,
                { "bSortable": false }
            ]
        });

        // Delete
        $('#messenger-trash-tbl').on('click', 'a.delete-user', function(e) {
            var control = this;
            var userId = $(control).attr('userId');
            jQuery.ajax({
                type: 'POST',
                url: "${g.createLink(controller: 'messenger',action: 'trashDelete')}?id="+userId,
                success: function (data, textStatus) {
                    $('body').html(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                //$('#'+updateDiv).html(data);
                }
            });
            e.preventDefault();
        });

        // View
        $('#messenger-trash-tbl').on('click', 'a.view-user', function(e) {
            var control = this;
            var userId = $(control).attr('userId');
            jQuery.ajax({
                type: 'POST',
                url: "${g.createLink(controller: 'messenger',action: 'view')}?id="+userId,
                success: function (data, textStatus) {
                    $('body').html(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
//                    $('#'+updateDiv).html(data);
                }
            });
            e.preventDefault();
        });

        // View
        $('#messenger-trash-tbl').on('click', 'a.undo-user', function(e) {
            var control = this;
            var userId = $(control).attr('userId');
            jQuery.ajax({
                type: 'POST',
                url: "${g.createLink(controller: 'messenger',action: 'delete')}?id="+userId+"&undo=undo&view=trash",
                success: function (data, textStatus) {
                    $('body').html(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
//                    $('#'+updateDiv).html(data);
                }
            });
            e.preventDefault();
        });

        // Undo
        $('#messenger-trash-tbl').on('click', 'a.undo-user', function(e) {
            var control = this;
            var userId = $(control).attr('userId');
            jQuery.ajax({
                type: 'POST',
                url: "${g.createLink(controller: 'messenger',action: 'undo')}?id="+userId,
                success: function (data, textStatus) {
                    $('body').html(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
//                    $('#'+updateDiv).html(data);
                }
            });
            e.preventDefault();
        });
    })

    function getActionButtons(nRow, aData) {
        var actionButtons = "";
        actionButtons += '<span class="col-xs-3"><a href="" userId="'+aData.DT_RowId+ '" class="view-user" title="View">';
actionButtons += '<span class="blue glyphicon glyphicon-envelope"></span>';
actionButtons += '</a></span>';
        actionButtons += '<span class="col-xs-3"><a href="" userId="'+aData.DT_RowId+ '" class="undo-user" title="Reply">';
actionButtons += '<span class="green glyphicon glyphicon-repeat"></span>';
actionButtons += '</a></span>';
        actionButtons += '<span class="col-xs-3"><a href="" userId="'+aData.DT_RowId+ '" class="delete-user" title="Delete">';
actionButtons += '<span class="red glyphicon glyphicon-trash"></span>';
actionButtons += '</a></span>';

        return actionButtons;
    }
</r:script>
</body>
</html>
