(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-22379ea8"],{"20fb":function(t,e,n){"use strict";var i=n("2638"),s=n.n(i),a=n("d282"),o=n("a142"),r=n("ea8e"),c=n("a8fa"),u=n("1325"),h=n("78eb"),l=n("b566"),d=Object(a["a"])("stepper"),f=d[0],m=d[1],p=600,g=200;function b(t,e){return String(t)===String(e)}function v(t,e){var n=Math.pow(10,10);return Math.round((t+e)*n)/n}e["a"]=f({mixins:[h["a"]],props:{value:null,integer:Boolean,disabled:Boolean,inputWidth:[Number,String],buttonSize:[Number,String],asyncChange:Boolean,disablePlus:Boolean,disableMinus:Boolean,disableInput:Boolean,decimalLength:[Number,String],name:{type:[Number,String],default:""},min:{type:[Number,String],default:1},max:{type:[Number,String],default:1/0},step:{type:[Number,String],default:1},defaultValue:{type:[Number,String],default:1},showPlus:{type:Boolean,default:!0},showMinus:{type:Boolean,default:!0},longPress:{type:Boolean,default:!0}},data:function(){var t=Object(o["b"])(this.value)?this.value:this.defaultValue,e=this.format(t);return b(e,this.value)||this.$emit("input",e),{currentValue:e}},computed:{minusDisabled:function(){return this.disabled||this.disableMinus||this.currentValue<=this.min},plusDisabled:function(){return this.disabled||this.disablePlus||this.currentValue>=this.max},inputStyle:function(){var t={};return this.inputWidth&&(t.width=Object(r["a"])(this.inputWidth)),this.buttonSize&&(t.height=Object(r["a"])(this.buttonSize)),t},buttonStyle:function(){if(this.buttonSize){var t=Object(r["a"])(this.buttonSize);return{width:t,height:t}}}},watch:{max:"check",min:"check",integer:"check",decimalLength:"check",value:function(t){b(t,this.currentValue)||(this.currentValue=this.format(t))},currentValue:function(t){this.$emit("input",t),this.$emit("change",t,{name:this.name})}},methods:{check:function(){var t=this.format(this.currentValue);b(t,this.currentValue)||(this.currentValue=t)},formatNumber:function(t){return Object(l["a"])(String(t),!this.integer)},format:function(t){return t=this.formatNumber(t),t=""===t?0:+t,t=Math.max(Math.min(this.max,t),this.min),Object(o["b"])(this.decimalLength)&&(t=t.toFixed(this.decimalLength)),t},onInput:function(t){var e=t.target.value,n=this.formatNumber(e);if(Object(o["b"])(this.decimalLength)&&-1!==n.indexOf(".")){var i=n.split(".");n=i[0]+"."+i[1].slice(0,this.decimalLength)}b(e,n)||(t.target.value=n),this.emitChange(n)},emitChange:function(t){this.asyncChange?(this.$emit("input",t),this.$emit("change",t,{name:this.name})):this.currentValue=t},onChange:function(){var t=this.type;if(this[t+"Disabled"])this.$emit("overlimit",t);else{var e="minus"===t?-this.step:+this.step,n=this.format(v(+this.currentValue,e));this.emitChange(n),this.$emit(t)}},onFocus:function(t){this.$emit("focus",t),this.disableInput&&this.$refs.input&&this.$refs.input.blur()},onBlur:function(t){var e=this.format(t.target.value);t.target.value=e,this.currentValue=e,this.$emit("blur",t),Object(c["a"])()},longPressStep:function(){var t=this;this.longPressTimer=setTimeout((function(){t.onChange(),t.longPressStep(t.type)}),g)},onTouchStart:function(){var t=this;this.longPress&&(clearTimeout(this.longPressTimer),this.isLongPress=!1,this.longPressTimer=setTimeout((function(){t.isLongPress=!0,t.onChange(),t.longPressStep()}),p))},onTouchEnd:function(t){this.longPress&&(clearTimeout(this.longPressTimer),this.isLongPress&&Object(u["c"])(t))}},render:function(){var t=this,e=arguments[0],n=function(e){return{on:{click:function(){t.type=e,t.onChange()},touchstart:function(){t.type=e,t.onTouchStart()},touchend:t.onTouchEnd,touchcancel:t.onTouchEnd}}};return e("div",{class:m()},[e("button",s()([{directives:[{name:"show",value:this.showMinus}],attrs:{type:"button"},style:this.buttonStyle,class:m("minus",{disabled:this.minusDisabled})},n("minus")])),e("input",{ref:"input",attrs:{type:this.integer?"tel":"text",role:"spinbutton",disabled:this.disabled,readonly:this.disableInput,inputmode:this.integer?"numeric":"decimal","aria-valuemax":this.max,"aria-valuemin":this.min,"aria-valuenow":this.currentValue},class:m("input"),domProps:{value:this.currentValue},style:this.inputStyle,on:{input:this.onInput,focus:this.onFocus,blur:this.onBlur}}),e("button",s()([{directives:[{name:"show",value:this.showPlus}],attrs:{type:"button"},style:this.buttonStyle,class:m("plus",{disabled:this.plusDisabled})},n("plus")]))])}})},2166:function(t,e,n){"use strict";var i=n("b775");e["a"]={count:function(){return Object(i["a"])({url:"/shop/cart/count",method:"get"})},queryByUser:function(){return Object(i["a"])({url:"/shop/cart/queryByUser",method:"get"})},add:function(t){return Object(i["a"])({url:"/shop/cart/add",method:"POST",data:t})},update:function(t,e){return Object(i["a"])({url:"/shop/cart/update/"+t+"/"+e,method:"POST"})},delete:function(t){return Object(i["a"])({url:"/shop/cart/delete?id="+t.id,method:"DELETE",data:t})}}},2221:function(t,e,n){},4056:function(t,e,n){"use strict";n("68ef"),n("9d70"),n("3743"),n("09fe")},"482d":function(t,e,n){"use strict";function i(t,e,n){return Math.min(Math.max(t,e),n)}n.d(e,"a",(function(){return i}))},"66b9":function(t,e,n){"use strict";n("68ef"),n("9d70"),n("3743"),n("e3b3"),n("bc1b")},"75f4":function(t,e,n){"use strict";var i=n("cc2c"),s=n.n(i);s.a},9884:function(t,e,n){"use strict";n.d(e,"a",(function(){return o})),n.d(e,"b",(function(){return r}));var i=n("2b0e");function s(t){var e=[];function n(t){t.forEach((function(t){e.push(t),t.componentInstance&&n(t.componentInstance.$children.map((function(t){return t.$vnode}))),t.children&&n(t.children)}))}return n(t),e}function a(t,e){var n=e.$vnode.componentOptions;if(n&&n.children){var i=s(n.children);t.sort((function(t,e){return i.indexOf(t.$vnode)-i.indexOf(e.$vnode)}))}}function o(t,e){var n,s;void 0===e&&(e={});var o=e.indexKey||"index";return i["a"].extend({inject:(n={},n[t]={default:null},n),computed:(s={parent:function(){return this.disableBindRelation?null:this[t]}},s[o]=function(){return this.bindRelation(),this.parent?this.parent.children.indexOf(this):null},s),mounted:function(){this.bindRelation()},beforeDestroy:function(){var t=this;this.parent&&(this.parent.children=this.parent.children.filter((function(e){return e!==t})))},methods:{bindRelation:function(){if(this.parent&&-1===this.parent.children.indexOf(this)){var t=[].concat(this.parent.children,[this]);a(t,this.parent),this.parent.children=t}}}})}function r(t){return{provide:function(){var e;return e={},e[t]=this,e},data:function(){return{children:[]}}}}},b775:function(t,e,n){"use strict";var i=n("bc3a"),s=n.n(i),a=(n("41cb"),n("4360")),o=n("5d2d"),r=s.a.create({baseURL:"/mobile",withCredentials:!0,timeout:15e3});r.interceptors.request.use((function(t){return o["a"].getToken()&&(t.headers["Authorization"]=o["a"].getToken()),o["a"].getOpenId()&&(t.headers["openId"]=o["a"].getOpenId()),t}),(function(t){return Promise.reject(t)})),r.interceptors.response.use((function(t){t.headers.token&&a["a"].dispatch("app/toggleToken",t.headers.token);var e=t.data;return e}),(function(t){if(t.response)switch(t.response.status){case 401:return a["a"].dispatch("app/toggleUser",{}),a["a"].dispatch("app/toggleToken",""),Promise.reject(t.response.data.message);case 500:return t.response.data.message&&t.response.data.message.indexOf("relogin")>-1?(console.log("need relogin"),a["a"].dispatch("app/toggleUser",{}),a["a"].dispatch("app/toggleToken",""),Promise.reject(t.response.data.message)):Promise.reject(t.response.data.message);default:return Promise.reject(t.response.data.message)}})),e["a"]=r},c3a6:function(t,e,n){"use strict";n("68ef"),n("9d70"),n("3743")},cc2c:function(t,e,n){},da2a:function(t,e,n){"use strict";n.r(e);var i,s=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"cart"},[t.isLogin&&t.cartList.length>0?n("van-checkbox-group",{ref:"checkboxGroup",staticClass:"card-goods",on:{change:t.onChangBox},model:{value:t.checkedGoods,callback:function(e){t.checkedGoods=e},expression:"checkedGoods"}},[t._l(t.cartList,(function(e){return n("div",{key:e.id,staticClass:"card-goods__item"},[n("van-swipe-cell",{scopedSlots:t._u([{key:"right",fn:function(){return[n("van-button",{staticStyle:{height:"100%"},attrs:{square:"",text:"删除",type:"danger"},on:{click:function(n){return t.remove(e.id)}}})]},proxy:!0}],null,!0)},[n("div",{},[n("van-checkbox",{attrs:{name:e.id}})],1),t._v(" "),n("van-card",{staticStyle:{"margin-left":"15px"},attrs:{title:e.title,desc:e.goods.descript,num:e.amount,price:t.formatPrice(e.price),thumb:e.thumb}},[n("div",{attrs:{slot:"footer"},slot:"footer"},[n("van-stepper",{attrs:{disableInput:""},on:{change:function(n){return t.stepperEvent(e,arguments)}},model:{value:e.amount,callback:function(n){t.$set(e,"amount",n)},expression:"item.amount"}})],1)])],1)],1)})),t._v(" "),n("br"),t._v(" "),n("br"),t._v(" "),n("br"),t._v(" "),n("br"),t._v(" "),n("br")],2):t._e(),t._v(" "),t.isLogin&&t.cartList.length>0?n("van-submit-bar",{attrs:{price:t.totalPrice,disabled:!t.checkedGoods.length,"button-text":t.submitBarText},on:{submit:t.submit},scopedSlots:t._u([{key:"tip",fn:function(){return[t._v("\n      说明：含运费￥9.9\n    ")]},proxy:!0}],null,!1,382416057)},[t._v(" "),n("van-checkbox",{staticStyle:{padding:"0 10px"},on:{click:t.checkAll},model:{value:t.checkedAll,callback:function(e){t.checkedAll=e},expression:"checkedAll"}},[t._v("全选")])],1):t._e(),t._v(" "),t.isLogin&&0===t.cartList.length?n("div",{staticClass:"no-data"},[n("p",{staticStyle:{"text-align":"center",color:"lightgray"}},[n("van-icon",{staticStyle:{"text-align":"center"},attrs:{name:"cart-o",size:"3rem"}}),t._v(" "),n("br"),t._v("购物车还是空的\n    ")],1),t._v(" "),n("van-button",{attrs:{type:"default",block:"",round:""},on:{click:t.toHome}},[t._v("去逛逛")])],1):t._e(),t._v(" "),t.isLogin?t._e():n("div",{staticClass:"no-data"},[n("p",{staticStyle:{"text-align":"center",color:"lightgray"}},[n("van-icon",{staticStyle:{"text-align":"center"},attrs:{name:"cart-o",size:"3rem"}}),t._v(" "),n("br"),t._v("还没有登录\n    ")],1),t._v(" "),n("van-button",{attrs:{type:"primary",block:"",round:""},on:{click:t.toLogin}},[t._v("立即登录")])],1),t._v(" "),n("van-tabbar",{model:{value:t.activeFooter,callback:function(e){t.activeFooter=e},expression:"activeFooter"}},[n("van-tabbar-item",{attrs:{icon:"home-o",replace:"",to:"/index"}},[t._v("首页")]),t._v(" "),n("van-tabbar-item",{attrs:{icon:"cart-o",replace:"",to:"/cart"}},[t._v("购物车")]),t._v(" "),n("van-tabbar-item",{attrs:{icon:"user-o",replace:"",to:"/user"}},[t._v("我的")])],1)],1)},a=[],o=n("bd86"),r=(n("e7e5"),n("d399")),c=(n("68ef"),n("2221"),n("d282")),u=n("482d"),h=n("1325"),l=n("3875"),d=n("2b0e"),f=function(t){return d["a"].extend({props:{closeOnClickOutside:{type:Boolean,default:!0}},data:function(){var e=this,n=function(n){e.closeOnClickOutside&&!e.$el.contains(n.target)&&e[t.method]()};return{clickOutsideHandler:n}},mounted:function(){Object(h["b"])(document,t.event,this.clickOutsideHandler)},beforeDestroy:function(){Object(h["a"])(document,t.event,this.clickOutsideHandler)}})},m=Object(c["a"])("swipe-cell"),p=m[0],g=m[1],b=.15,v=p({mixins:[l["a"],f({event:"touchstart",method:"onClick"})],props:{onClose:Function,disabled:Boolean,leftWidth:[Number,String],rightWidth:[Number,String],beforeClose:Function,stopPropagation:Boolean,name:{type:[Number,String],default:""}},data:function(){return{offset:0,dragging:!1}},computed:{computedLeftWidth:function(){return+this.leftWidth||this.getWidthByRef("left")},computedRightWidth:function(){return+this.rightWidth||this.getWidthByRef("right")}},mounted:function(){this.bindTouchEvent(this.$el)},methods:{getWidthByRef:function(t){if(this.$refs[t]){var e=this.$refs[t].getBoundingClientRect();return e.width}return 0},open:function(t){var e="left"===t?this.computedLeftWidth:-this.computedRightWidth;this.opened=!0,this.offset=e,this.$emit("open",{position:t,name:this.name,detail:this.name})},close:function(t){this.offset=0,this.opened&&(this.opened=!1,this.$emit("close",{position:t,name:this.name}))},onTouchStart:function(t){this.disabled||(this.startOffset=this.offset,this.touchStart(t))},onTouchMove:function(t){if(!this.disabled&&(this.touchMove(t),"horizontal"===this.direction)){this.dragging=!0,this.lockClick=!0;var e=!this.opened||this.deltaX*this.startOffset<0;e&&Object(h["c"])(t,this.stopPropagation),this.offset=Object(u["a"])(this.deltaX+this.startOffset,-this.computedRightWidth,this.computedLeftWidth)}},onTouchEnd:function(){var t=this;this.disabled||this.dragging&&(this.toggle(this.offset>0?"left":"right"),this.dragging=!1,setTimeout((function(){t.lockClick=!1}),0))},toggle:function(t){var e=Math.abs(this.offset),n=this.opened?1-b:b,i=this.computedLeftWidth,s=this.computedRightWidth;s&&"right"===t&&e>s*n?this.open("right"):i&&"left"===t&&e>i*n?this.open("left"):this.close()},onClick:function(t){void 0===t&&(t="outside"),this.$emit("click",t),this.opened&&!this.lockClick&&(this.beforeClose?this.beforeClose({position:t,name:this.name,instance:this}):this.onClose?this.onClose(t,this,{name:this.name}):this.close(t))},getClickHandler:function(t,e){var n=this;return function(i){e&&i.stopPropagation(),n.onClick(t)}},genLeftPart:function(){var t=this.$createElement,e=this.slots("left");if(e)return t("div",{ref:"left",class:g("left"),on:{click:this.getClickHandler("left",!0)}},[e])},genRightPart:function(){var t=this.$createElement,e=this.slots("right");if(e)return t("div",{ref:"right",class:g("right"),on:{click:this.getClickHandler("right",!0)}},[e])}},render:function(){var t=arguments[0],e={transform:"translate3d("+this.offset+"px, 0, 0)",transitionDuration:this.dragging?"0s":".6s"};return t("div",{class:g(),on:{click:this.getClickHandler("cell")}},[t("div",{class:g("wrapper"),style:e},[this.genLeftPart(),this.slots(),this.genRightPart()])])}}),k=(n("4056"),n("44bf")),y=(n("c3a6"),n("ad06")),O=(n("66b9"),n("b650")),x=(n("1146"),n("fb6c"),n("20fb")),S=(n("a52c"),n("2ed4")),j=(n("537a"),n("ac28")),C=(n("5246"),n("6b41")),_=(n("a909"),n("3acc")),P=(n("be39"),n("efa0")),L=(n("3c32"),n("417e")),$=(n("9cb7"),n("66fd")),T=n("2166"),B="/mobile",w={components:(i={},Object(o["a"])(i,$["a"].name,$["a"]),Object(o["a"])(i,L["a"].name,L["a"]),Object(o["a"])(i,P["a"].name,P["a"]),Object(o["a"])(i,_["a"].name,_["a"]),Object(o["a"])(i,C["a"].name,C["a"]),Object(o["a"])(i,j["a"].name,j["a"]),Object(o["a"])(i,S["a"].name,S["a"]),Object(o["a"])(i,x["a"].name,x["a"]),Object(o["a"])(i,O["a"].name,O["a"]),Object(o["a"])(i,y["a"].name,y["a"]),Object(o["a"])(i,k["a"].name,k["a"]),Object(o["a"])(i,v.name,v),Object(o["a"])(i,r["a"].name,r["a"]),i),data:function(){return{isLogin:!0,activeFooter:1,checkedGoods:[],checkeAllCarts:[],cartList:[],checkedAll:!0}},mounted:function(){this.init()},computed:{submitBarText:function(){var t=this.checkedGoods.length;return"结算"+(t?"(".concat(t,")"):"")},totalPrice:function(){var t=this;return this.cartList.reduce((function(e,n){return e+(-1!==t.checkedGoods.indexOf(n.id)?parseFloat(n.price)*n.amount:0)}),990)}},methods:{init:function(){var t=this;T["a"].queryByUser().then((function(e){var n=e.data;for(var i in n){var s=n[i];s.thumb=B+"/rest/file/getImgStream?idFile="+s.goods.pic,t.checkedGoods.push(n[i].id)}t.cartList=n})).catch((function(t){}))},submit:function(){this.$router.push("/checkout")},formatPrice:function(t){return(t/100).toFixed(2)},stepperEvent:function(t,e){var n=e[0];T["a"].update(t.id,n)},toHome:function(){this.$router.push("/index")},toLogin:function(){this.$router.push({path:"login",query:{redirect:"cart"}})},checkAll:function(){this.checkedGoods.length===this.cartList.length?(this.$refs.checkboxGroup.toggleAll(),this.checkedAll=!1):(this.$refs.checkboxGroup.toggleAll(!0),this.checkedAll=!0)},onChangBox:function(t){this.checkedGoods.length===this.cartList.length?this.checkedAll=!0:this.checkedAll=!1},remove:function(t){var e=this;console.log(t,"id"),T["a"].delete({id:t}).then((function(n){e.$toast("删除成功"),e.cartList=e.cartList.filter((function(e){return e.id!==t})),e.checkedGoods=e.checkedGoods.filter((function(e){return e.id!==t})),e.checkeAllCarts=e.checkedGoods})).catch((function(t){console.log(t,"err"),e.$toast("删除失败")}))}}},W=w,G=(n("75f4"),n("2877")),V=Object(G["a"])(W,s,a,!1,null,null,null);e["default"]=V.exports},fb6c:function(t,e,n){}}]);