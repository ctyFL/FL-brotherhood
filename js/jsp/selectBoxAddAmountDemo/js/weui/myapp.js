/**
 * Created by jf on 2015/9/11.
 */
var _alertCallback=nop;
window.alert=function(text,title){
	$alert_weui(text,title);
};
var $alert=function(text,title,_cbf){
	$alert_weui(text,title,_cbf);
};
var $alert_weui=function(text,title,_cbf){
	_alertCallback = _cbf ? _cbf : nop;
	var html='';
	html+='<div class="weui_dialog_alert" style="display: none;">';
	html+='    <div class="weui_mask"></div>';
	html+='    <div class="weui_dialog">';
	if(title){
		html+='        <div class="weui_dialog_hd"><strong class="weui_dialog_title">'+title+'</strong></div>';
	}else{
		html+='        <div class="weui_dialog_hd"><strong class="weui_dialog_title"></strong></div>';
	}
	html+='        <div class="weui_dialog_bd">'+text+'</div>';
	html+='        <div class="weui_dialog_ft">';
	html+='            <a href="javascript:;" class="weui_btn_dialog primary">确定</a>';
	html+='        </div>';
	html+='    </div>';
	html+='</div>';
	
	var $dialog = $(html);
	$(document.body).append($dialog);
    $dialog.show();
    $dialog.find('.weui_btn_dialog').one('click', function () {
        $dialog.remove();
        _alertCallback();
    });
	
};

var nop=function(){ };

var toast=function(text,type){
	var html='';
	if(typeof(type)!="undefined" && type==1){
		html+='<div style="position:absolute;z-index: 999;display: none;">';
		html+='    <div class="weui_mask_transparent"></div>';
		html+='    <div class="weui_toast weui_toast_ok">';
		html+='        <i class="weui_icon_toast weui_icon_toast_ok"></i>';
		html+='        <p class="weui_toast_content">'+text+'</p>';
		html+='    </div>';
		html+='</div>';
	}else{
		html+='<div style="position:absolute;z-index: 999;display: none;">';
		html+='    <div class="weui_mask_transparent"></div>';
		html+='    <div class="weui_toast">';
		html+='        <i class="weui_icon_toast"></i>';
		html+='        <p class="weui_toast_content">'+text+'</p>';
		html+='    </div>';
		html+='</div>';
	}

	var $toast = $(html);
	$(document.body).append($toast); 
	$toast.show();
	setTimeout(function () {
	    $toast.remove();
	}, 2000);
};
