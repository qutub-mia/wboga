<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="layout" content="wboga"/>
    <title>Inbox - Ace Admin</title>

</head>

<body>
<div class="page-content">
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <div class="row">
                <div class="col-xs-12">
                    <div class="tabbable">
                        <ul id="inbox-tabs" class="inbox-tabs nav nav-tabs padding-16 tab-size-bigger tab-space-1">
                            <li class="li-new-mail pull-right">
                                <a data-toggle="tab" href="#write" data-target="write" class="btn-new-mail">
                                    <span class="btn bt1n-small btn-purple no-border">
                                        <i class=" icon-envelope bigger-130"></i>
                                        <span class="bigger-110">Write Mail</span>
                                    </span>
                                </a>
                            </li><!-- ./li-new-mail -->
                        </ul>

                        <div class="tab-content no-border no-padding">
                            <div class="tab-pane in active">
                                <div class="message-container">
                                    <div id="id-message-new-navbar" class="hide message-navbar align-center clearfix">
                                        <div class="message-bar">
                                            <div class="message-toolbar">
                                                <a href="#" class="btn btn-xs btn-message">
                                                    <i class="icon-save bigger-125"></i>
                                                    <span class="bigger-110">Save Draft</span>
                                                </a>

                                                <a href="#" class="btn btn-xs btn-message">
                                                    <i class="icon-remove bigger-125"></i>
                                                    <span class="bigger-110">Discard</span>
                                                </a>
                                            </div>
                                        </div>

                                        <div class="message-item-bar">
                                            <div class="messagebar-item-left">
                                                <a href="#" class="btn-back-message-list no-hover-underline">
                                                    <i class="icon-arrow-left blue bigger-110 middle"></i>
                                                    <b class="middle bigger-110">Back</b>
                                                </a>
                                            </div>

                                            <div class="messagebar-item-right">
                                                <span class="inline btn-send-message">
                                                    <button type="button" class="btn btn-sm btn-primary no-border">
                                                        <span class="bigger-110">Send</span>

                                                        <i class="icon-arrow-right icon-on-right"></i>
                                                    </button>
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                </div><!-- /.message-container -->
                            </div><!-- /.tab-pane -->
                        </div><!-- /.tab-content -->
                    </div><!-- /.tabbable -->
                </div><!-- /.col -->
            </div><!-- /.row -->

            <form id="id-message-form" class="hide form-horizontal message-form  col-xs-12">
                <div class="">
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-recipient">Recipient:</label>

                        <div class="col-sm-9">
                            <span class="input-icon">
                                <input type="email" name="recipient" id="form-field-recipient" data-value="alex@doe.com" value="alex@doe.com" placeholder="Recipient(s)" />
                                <i class="icon-user"></i>
                            </span>
                        </div>
                    </div>

                    <div class="hr hr-18 dotted"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="form-field-subject">Subject:</label>

                        <div class="col-sm-6 col-xs-12">
                            <div class="input-icon block col-xs-12 no-padding">
                                <input maxlength="100" type="text" class="col-xs-12" name="subject" id="form-field-subject" placeholder="Subject" />
                                <i class="icon-comment-alt"></i>
                            </div>
                        </div>
                    </div>

                    <div class="hr hr-18 dotted"></div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right">
                            <span class="inline space-24 hidden-480"></span>
                            Message:
                        </label>

                        <div class="col-sm-9">
                            <div class="wysiwyg-editor"></div>
                        </div>
                    </div>

                    <div class="hr hr-18 dotted"></div>

                    <div class="form-group no-margin-bottom">
                        <label class="col-sm-3 control-label no-padding-right">Attachments:</label>

                        <div class="col-sm-9">
                            <div id="form-attachments">
                                <input type="file" name="attachment[]" />
                            </div>
                        </div>
                    </div>

                    <div class="align-right">
                        <button id="id-add-attachment" type="button" class="btn btn-sm btn-danger">
                            <i class="icon-paper-clip bigger-140"></i>
                            Add Attachment
                        </button>
                    </div>

                    <div class="space"></div>
                </div>
            </form>

            <!-- PAGE CONTENT ENDS -->
        </div><!-- /.col -->
    </div><!-- /.row -->
</div><!-- /.page-content -->


<script src="${resource(dir: 'js', file: 'jquery.hotkeys.min.js')}"></script>
<script src="${resource(dir: 'js', file: 'bootstrap-wysiwyg.min.js')}"></script>


<script type="text/javascript">
    jQuery(function($){
        //handling tabs and loading/displaying relevant messages and forms
        //not needed if using the alternative view, as described in docs
        var prevTab = 'inbox'
        $('#inbox-tabs a[data-toggle="tab"]').on('show.bs.tab', function (e) {
            var currentTab = $(e.target).data('target');
            if(currentTab == 'write') {
                Inbox.show_form();
            }
            else {
                if(prevTab == 'write')
                    Inbox.show_list();

                //load and display the relevant messages
            }
            prevTab = currentTab;
        })


        //display first message in a new area
        $('.message-list .message-item:eq(0) .text').on('click', function() {
            //show the loading icon
            $('.message-container').append('<div class="message-loading-overlay"><i class="icon-spin icon-spinner orange2 bigger-160"></i></div>');

            $('.message-inline-open').removeClass('message-inline-open').find('.message-content').remove();

            var message_list = $(this).closest('.message-list');

            //some waiting
            setTimeout(function() {

                //hide everything that is after .message-list (which is either .message-content or .message-form)
                message_list.next().addClass('hide');
                $('.message-container').find('.message-loading-overlay').remove();

                //close and remove the inline opened message if any!

                //hide all navbars
                $('.message-navbar').addClass('hide');
                //now show the navbar for single message item
                $('#id-message-item-navbar').removeClass('hide');

                //hide all footers
                $('.message-footer').addClass('hide');
                //now show the alternative footer
                $('.message-footer-style2').removeClass('hide');


                //move .message-content next to .message-list and hide .message-list
                message_list.addClass('hide').after($('.message-content')).next().removeClass('hide');

                //add scrollbars to .message-body
                $('.message-content .message-body').slimScroll({
                    height: 200,
                    railVisible:true
                });

            }, 500 + parseInt(Math.random() * 500));
        });





        var Inbox = {
            //displays a toolbar according to the number of selected messages
            display_bar : function (count) {
                if(count == 0) {
                    $('#id-toggle-all').removeAttr('checked');
                    $('#id-message-list-navbar .message-toolbar').addClass('hide');
                    $('#id-message-list-navbar .message-infobar').removeClass('hide');
                }
                else {
                    $('#id-message-list-navbar .message-infobar').addClass('hide');
                    $('#id-message-list-navbar .message-toolbar').removeClass('hide');
                }
            }
            ,
            select_all : function() {
                var count = 0;
                $('.message-item input[type=checkbox]').each(function(){
                    this.checked = true;
                    $(this).closest('.message-item').addClass('selected');
                    count++;
                });

                $('#id-toggle-all').get(0).checked = true;

                Inbox.display_bar(count);
            }
            ,
            select_none : function() {
                $('.message-item input[type=checkbox]').removeAttr('checked').closest('.message-item').removeClass('selected');
                $('#id-toggle-all').get(0).checked = false;

                Inbox.display_bar(0);
            }
            ,
            select_read : function() {
                $('.message-unread input[type=checkbox]').removeAttr('checked').closest('.message-item').removeClass('selected');

                var count = 0;
                $('.message-item:not(.message-unread) input[type=checkbox]').each(function(){
                    this.checked = true;
                    $(this).closest('.message-item').addClass('selected');
                    count++;
                });
                Inbox.display_bar(count);
            }
            ,
            select_unread : function() {
                $('.message-item:not(.message-unread) input[type=checkbox]').removeAttr('checked').closest('.message-item').removeClass('selected');

                var count = 0;
                $('.message-unread input[type=checkbox]').each(function(){
                    this.checked = true;
                    $(this).closest('.message-item').addClass('selected');
                    count++;
                });

                Inbox.display_bar(count);
            }
        }


        //show write mail form
        Inbox.show_form = function() {
            if($('.message-form').is(':visible')) return;
            if(!form_initialized) {
                initialize_form();
            }


            var message = $('.message-list');
            $('.message-container').append('<div class="message-loading-overlay"><i class="icon-spin icon-spinner orange2 bigger-160"></i></div>');

            setTimeout(function() {
                message.next().addClass('hide');

                $('.message-container').find('.message-loading-overlay').remove();

                $('.message-list').addClass('hide');
                $('.message-footer').addClass('hide');
                $('.message-form').removeClass('hide').insertAfter('.message-list');

                $('.message-navbar').addClass('hide');
                $('#id-message-new-navbar').removeClass('hide');


                //reset form??
                $('.message-form .wysiwyg-editor').empty();

                $('.message-form .ace-file-input').closest('.file-input-container:not(:first-child)').remove();
                $('.message-form input[type=file]').ace_file_input('reset_input');

                $('.message-form').get(0).reset();

            }, 300 + parseInt(Math.random() * 300));
        }




        var form_initialized = false;
        function initialize_form() {
            if(form_initialized) return;
            form_initialized = true;

            //intialize wysiwyg editor
            $('.message-form .wysiwyg-editor').ace_wysiwyg({
                toolbar:
                        [
                            'bold',
                            'italic',
                            'strikethrough',
                            'underline',
                            null,
                            'justifyleft',
                            'justifycenter',
                            'justifyright',
                            null,
                            'createLink',
                            'unlink',
                            null,
                            'undo',
                            'redo'
                        ]
            }).prev().addClass('wysiwyg-style1');

            //file input
            $('.message-form input[type=file]').ace_file_input()
                //and the wrap it inside .span7 for better display, perhaps
                    .closest('.ace-file-input').addClass('width-90 inline').wrap('<div class="row file-input-container"><div class="col-sm-7"></div></div>');

            //the button to add a new file input
            $('#id-add-attachment').on('click', function(){
                var file = $('<input type="file" name="attachment[]" />').appendTo('#form-attachments');
                file.ace_file_input();
                file.closest('.ace-file-input').addClass('width-90 inline').wrap('<div class="row file-input-container"><div class="col-sm-7"></div></div>')
                        .parent(/*.span7*/).append('<div class="action-buttons pull-right col-xs-1">\
							<a href="#" data-action="delete" class="middle">\
								<i class="icon-trash red bigger-130 middle"></i>\
							</a>\
						</div>').find('a[data-action=delete]').on('click', function(e){
                            //the button that removes the newly inserted file input
                            e.preventDefault();
                            $(this).closest('.row').hide(300, function(){
                                $(this).remove();
                            });
                        });
            });
        }//initialize_form

    });
</script>
</body>
</html>
