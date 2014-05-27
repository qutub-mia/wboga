<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="layout" content="wboga"/>
    <title>Inbox - Ace Admin</title>
</head>

<body>

    <div class="row">
        <div class="col-md-12">
            <g:render template="compose"/>
        </div>



    <div class="message-list">
        <div class="col-md-12">
            <table id="messenger-inbox-tbl" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>Sender Name</th>
                    <th>Subject</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody id="introducerTableId">
                <g:each in="${retailAccount?.retailIntroducer}" var="introducer" status="i">
                <g:if test="${introducer.status == true}">
                    <tr>
                        <td id="attCaption">${introducer.client.name}</td>
                        <td id="attName">${introducer.client.accountNo}</td>

                        <td id="attDescription">${introducer.relation}</td>
                        <td id="attSize">${introducer.client.address}</td>
                        <td id="attType">${introducer.client.contactNo}</td>


                        <td class="actions ">
                            <div class="btn-group">
                                <a class="btn btn-sm delete btn-danger introducer-delete-link" onclick="return confirm('Are you sure delete Introducer Information?')"
                                   href="" id="${introducer.id}" retailAccount = "${retailAccount?.id}"
                                   title="Delete"><i class="glyphicon glyphicon-remove "></i></a>
                            </div>
                        </td>
                    </tr>
                 </g:if>
                </g:each>
                </tbody>
            </table>
        </div>
    </div>

    </div>

</body>
</html>
