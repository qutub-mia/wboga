<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>World BOGA - Login</title>

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'font-awesome.css')}" type="text/css"/>

    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace-rtl.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ace-skins.css')}" type="text/css"/>

    <script src="${resource(dir: 'js', file: 'ace-extra.js')}"></script>
    <g:layoutHead/>
    <r:layoutResources/>
</head>

<body class="login-layout">
        <g:layoutBody/>

        <script type="text/javascript">
         window.jQuery || document.write("<script src='${resource(dir: 'js', file: 'jquery-1.10.2.min.js')}'>"+"<"+"/script>");
        </script>
        <script src="${resource(dir: 'js', file: 'bootstrap.js')}"></script>
        <script src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>

        <g:javascript library="application"/>
        <r:layoutResources/>
</body>
</html>