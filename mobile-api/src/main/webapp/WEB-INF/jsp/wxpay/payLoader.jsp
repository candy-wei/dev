<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>支付完成中</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <script src="../../../js/jquery.min-2.0.0.js"></script>
</head>
<body>
<div id="modal-overlay">
    <div class="modal-data">
        <p id="massage">请稍候,支付完成中......</p>
    </div>
</div>
</body>
</html>

<style>
    #modal-overlay {
        visibility: visible;
        position: absolute; /* 使用绝对定位或固定定位  */
        left: 0px;
        top: 0px;
        width: 100%;
        height: 100%;
        text-align: center;
        z-index: 1000;
        background-color: #333;
        opacity: 0.5; /* 背景半透明 */
    }

    /* 模态框样式 */
    .modal-data {
        width: 300px;
        margin: 200px auto;
        background-color: #fff;
        border: 1px solid #000;
        padding: 15px;
        text-align: center;
    }
</style>

<script>
    var timeSecond = 5;
    rediretPay();

    function rediretPay() {
        if (timeSecond > 0) {
            $('#massage').text('支付完成中' + timeSecond + '秒')
            timeSecond--;
            setTimeout('rediretPay()', 1000, 'JScript');
            return;
        }
        location.href = '${redirectUri}';
    }
</script>
