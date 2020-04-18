<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<script>
    function onBridgeReady(){
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId":"${payRequestDto.appId}",     //公众号名称，由商户传入
                "timeStamp":"${payRequestDto.timeStamp}",         //时间戳，自1970年以来的秒数
                "nonceStr":"${payRequestDto.nonceStr}", //随机串
                "package":"prepay_id=${payRequestDto.prepayId}",
                "signType":"${payRequestDto.signType}",         //微信签名方式：
                "paySign":"${payRequestDto.paySign}" //微信签名
            },
            function(res){
                if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                    location.href = '${redirectUri}';
                }

                if(res.err_msg == "get_brand_wcpay_request:cancel" ) {
                    alert("支付已取消");
                }

                if(res.err_msg == "get_brand_wcpay_request:fail" ) {
                    alert("支付失败");
                }
            }
        );
    }
    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
       onBridgeReady();
    }
</script>
