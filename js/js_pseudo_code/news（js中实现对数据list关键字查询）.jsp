<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.skyland.servicecenter.*" %>
<%@ page import="com.skyland.community.*,com.skyland.pageflow.attachcenter.*" %>
<%@ page import="com.skyland.community.service.repair.*" %>
<%@page import="com.skyland.community.service.complaint.ServCustomerComplaintService"%>
<%@ page import="com.skyland.portals.service.*"%>
<%@ page import="java.util.*,java.text.*,com.skyland.portals.*"%>
<%@ page import="system.Global,system.SysUserAccount,system.auth.*"%>
<%@ page import="com.skyland.pageflow.util.StringUtil"%>
<%
String noImgUrl = "../img/noImg.png";
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
PortalsIndexContentHelper heplerServiceIndex=new PortalsIndexContentHelper();
List<PortalsModel> newModels = heplerServiceIndex.getPortalsModelListByContentType(1);
StringUtil su=new StringUtil();
List<Portals_Information> list=new ArrayList<Portals_Information>();

if(newModels!=null && newModels.size()>0){
	String ids = "";
	for(PortalsModel newModel : newModels){
		if(ids.equals("")){
			ids += newModel.getId(); 
		}else{
			ids += "," + newModel.getId(); 
		}		
	}
	list=heplerServiceIndex.getIndexInfomationList(ids,2);
}
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta name="viewport" content="width=device-width; initial-scale=1; maximum-scale=1" />
<meta name="author" content="FamousThemes" />
<meta name="description" content="My Mobile Page Version 3 Template" />
<meta name="keywords" content="mobile templates, mobile wordpress themes, mobile themes, my mobile page, premium css templates, premium wordpress themes" />
<title>社区公告</title>

<!-- Main CSS file -->
<link rel="stylesheet" href="style.css"   type="text/css" media="screen" />
<link rel="stylesheet" href="../../framework/weui/style/weui.css"/>
<link rel="stylesheet" href="../../framework/weui/style/weuiV2.css"/>
<link rel="stylesheet" href="../../framework/weui/style/weuix.css"/>
<link rel="stylesheet" href="../../framework/weui/style/myapp.css"/>
</head>

<body>
   	  <div class="content">  
   	  	
		 <div class="weui-search-bar weui-search-bar_focusing" id="searchBar">
		    <form class="weui-search-bar__form" action="#">
		        <div class="weui-search-bar__box">
		            <i class="weui-icon-search"></i>
		            <input class="weui-search-bar__input" id="searchInput" oninput="onInput(event)" onpropertychange="onPropChanged(event)" placeholder="公告栏标题查询" required="" type="search">
		            <a href="javascript:" class="weui-icon-clear" id="searchClear" onclick="clearSearchValue()"></a>
		        </div>
		    </form>
		    <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel" style="color:#789AE2" onclick="onSearch()">搜索</a>
		</div>
		
	  	<div id="infoList" style="display:">
		<!-- 除置顶以外发布的信息,topFalse表示未置顶 -->
		<%
		if(list!=null && !list.isEmpty()){
			for(int i=0; i<list.size(); i++){
				Portals_Information bean = list.get(i);
				long id = bean.getId();
				
				String title = bean.getTopic()!=null ? bean.getTopic() :"";
				title = title.length()>14 ? title.substring(0,14) : title;
				String content = bean.getContent()!=null ? bean.getContent() : "";
				content = StringUtil.getInstance().trimHtmlTag(content);
				content = content.length()>30 ? content.substring(0,30) : content;
				
				AttachmentCenterService acs=new AttachmentCenterService();
				String imageurl=bean.getImage()!=null ? acs.toImageUrl(request, bean.getImage()) : "" ;		
		 %>
			<div class="post" id="infoDetail<%=i %>" style="display:" onclick="location.href='detail.jsp?id=<%=id %>';"> 
          		<div  class="post_content" style="width:65%;margin-left: 10px;">
          		    <div class="post_title" style="float: left;color: #000; font-size:17px;">
          				<a id="infoDetailTitle<%=i %>" data-ajax="false" style="color: black" ><%=title %></a>
          			</div>
          			<div style="clear: both;height: 5px;"></div>
          			<div class="post_describe" style="font-size: 15px;height: 40px;color: #aaa;line-height: 20px">
           				<%=content %>
           			</div> 
           			<div style="clear: both;height: 10px;"></div>
           			<div class="post_date" style="float: left;font-size:12px;color: #000;padding-right: 5px;">
          				<%=bean.getPublicDate()!=null?sdf.format(bean.getPublicDate()):"" %>
          			</div>
           		</div>
           		<div class="post_thumb" >
					<div align="center"><img src="<%=bean.getImage()!=null? imageurl : noImgUrl %>" border="1" class="post_images_rounded" style="max-height: 75px;" /></div>           		
           			
           		</div> 
            </div>
       	  <%}} %>	 
			
		<%if(list.size()==0){ %>
    	<div class="weui_msg">
        	<div class="weui_icon_area"><i class="weui_icon_info weui_icon_msg"></i></div>
        	<div class="weui_text_area">
         		<h2 class="weui_msg_desc">暂时没有发布新信息</h2>
       		</div>
   		</div>
		<%}%>
		</div>
		
		<div class="weui_msg" id="noResult" style="display:none">
        	<div class="weui_icon_area"><i class="weui_icon_info weui_icon_msg"></i></div>
        	<div class="weui_text_area">
         		<h2 class="weui_msg_desc">没有查询到结果</h2>
       		</div>
   		</div>
		
	</div>

</body>
<script type="text/javascript">

/**
 * 实时监控input中value值的变化事件
 */
function onPropChanged(event){
	onSearch();
	if(document.getElementById("searchInput").value == ""){
		hideNoResult();
		showAllInfo();
	}
}

/**
 * 实时监控input中输入value事件
 */
function onInput(event){
	onSearch();
}

function clearSearchValue(){
	document.getElementById("searchInput").value = "";
	hideNoResult();
	showAllInfo();
}

function onSearch(){
	hideNoResult();
	var key = document.getElementById("searchInput").value;
	var infoListSize = "<%=list.size()%>";
	if(infoListSize <= 0){
		return;
	}
	if(key == null || key == ""){
		showAllInfo();
		return;
	}
	var count = 0;
	for(var i=0; i<infoListSize; i++){
		var title = document.getElementById("infoDetailTitle" + i).innerHTML;
		if(title.indexOf(key) != -1){
			document.getElementById("infoDetail" + i).style = "display:";
		}else{
			count++;
			document.getElementById("infoDetail" + i).style = "display:none";
		}
	}
	if(count == infoListSize){
		document.getElementById("noResult").style = "display:";
	}
}

function showAllInfo(){
	var infoListSize = "<%=list.size()%>";
	for(var i=0; i<infoListSize; i++){
		document.getElementById("infoDetail" + i).style = "display:";
	}
}

function hideNoResult(){
	document.getElementById("noResult").style = "display:none";
}

</script>
</html>
