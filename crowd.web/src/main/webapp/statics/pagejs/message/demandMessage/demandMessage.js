define(function(require, exports, module) {
	var $$ = jQuery$ = require('jquery');
	require('plugins/vendor/bootstrap/validator/entrance');
	
	refreshXqxxTable(1, true);
});


//刷新表格
function refreshXqxxTable(pageNum, needPage){
   $("tbody tr").remove();
   var url=basePath+"/demandMessage/queryXqxxInfo";
   var params="pageNum="+pageNum+"&pageSize=20";
   doPostBack(url,params,function(data){
	   	if(data.success){
	   		if(needPage){
				$('.mypaging2').pagination({
					pageCount:data.pageInfo.totalPage,
				    showData:data.pageInfo.pageSize,
				    coping:true,
				    callback:function(api){
				    	var _cPage=api.getCurrent();
				    	refreshXqxxTable(_cPage,false);
				    }
				});
			}
			var html="";
			for(var i=0;i<data.datas.length;i++){
				var result=data.datas[i];
				if(result.sfyd=="1"){
					html+='<tr class="text-xs-center">';
					html+='<td><input name="msg" type="checkbox" value="'+result.wid+'"></td>';
					html+='<td><a href="#"><i class="fa fa-envelope-open-o" aria-hidden="true" ';
					html+=' title="未读" ></i></a></td>';	
				}else{
					html+='<tr class="text-xs-center message-unread">';
					html+='<td><input name="msg" type="checkbox" value="'+result.wid+'"></td>';
					html+='<td><a href="#"><i class="fa fa-envelope-o" aria-hidden="true" ';
					html+=' title="未读" onclick="markXqxxReadedOne(\''+result.wid+'\')"></i></a></td>';					
				}
				html+='<td class="text-xs-left w-50">'+result.rwbh+'</td>';
				html+='<td class="text-xs-left w-100">'+result.rq+'</td>';
				html+='<td class="text-xs-left">'+result.xxnr+'</td>';
				html+='<td>';
/*				html+='<a href="#"><i class="icon wb-edit" aria-hidden="true" ';
				html+=' onclick="editRbxx(\''+result.wid+'\')"></i></a>';*/
				html+='<a href="#"><i class="icon wb-trash glyphicon glyphicon-trash" aria-hidden="true" ';
				html+='onclick="deleteXqxx(\''+result.wid+'\')"></i></a></td></tr>';
			}
			if(html == ""){
				html+='<tr><td>您暂时还没有消息！</td></tr>';
			}else{
				$('.mypaging2').show();
			}
			$("tbody").append(html);
	   	}
     });
}

function deleteXqxx(wid){
	confirm("确认删除该条数据吗",function(data){
		var url=basePath+"/demandMessage/delXqxxInfo";
	    var basicData={wid:wid};
	    doPostBack(url,basicData,function(data){
	    	refreshXqxxTable(1, true);
	    });
	});
}

function checkAllXqxx(){
	if($("#checkAll").is(":checked")){
		$("[name=msg]:checkbox").prop("checked", true);
	}else{
		$("[name=msg]:checkbox").prop("checked", false);
	}
}

function markXqxxReadedOne(wid){
	if(wid == "" || wid == null || wid == "null"){
		return ;
	}
	var url=basePath+"/demandMessage/markReaded";
    var basicData={wids:wid};
    doPostBack(url,basicData,function(data){
    	refreshXqxxTable(1, true);
    });
}

function markXqxxReaded(){
	var msgIds = "";
	$('input[name="msg"]:checked').each(function(){ 
		msgIds += $(this).val() + ","; 
	});
	msgIds = msgIds.substr(0, msgIds.length-1);
	if(msgIds == "" || msgIds == null){
		return ;
	}
	var url=basePath+"/demandMessage/markReaded";
    var basicData={wids:msgIds};
    doPostBack(url,basicData,function(data){
    	refreshXqxxTable(1, true);
    	$("#checkAll").prop("checked",  false);
    });
}

function markXqxxUnreadOne(wid){
	if(wid == "" || wid == null || wid == "null"){
		return ;
	}
	var url=basePath+"/demandMessage/markUnread";
    var basicData={wids:wid};
    doPostBack(url,basicData,function(data){
    	refreshXqxxTable(1, true);
    });
}


function markXqxxUnread(){
	var msgIds = "";
	$('input[name="msg"]:checked').each(function(){ 
		msgIds += $(this).val() + ","; 
	});
	msgIds = msgIds.substr(0, msgIds.length-1);
	if(msgIds == "" || msgIds == null){
		return ;
	}
	var url=basePath+"/demandMessage/markUnread";
    var basicData={wids:msgIds};
    doPostBack(url,basicData,function(data){
    	refreshXqxxTable(1, true);
    	$("#checkAll").prop("checked",  false);
    });
}

function batchDeleteXqxx(){
	var msgIds = "";
	$('input[name="msg"]:checked').each(function(){ 
		msgIds += $(this).val() + ","; 
	});
	msgIds = msgIds.substr(0, msgIds.length-1);
	if(msgIds == "" || msgIds == null){
		return ;
	}
	var url=basePath+"/demandMessage/batchDelete";
    var basicData={wids:msgIds};
    doPostBack(url,basicData,function(data){
    	refreshXqxxTable(1, true);
    	$("#checkAll").prop("checked",  false);
    });
}

function xqxxSet(){
	var url=basePath+"/demandMessage/queryGrxxszInfo";
	var params={wid:""};
    doSynchrPostBack(url,params,function(data){
    	if(data.success&&isNotEmpty(data.datas)){
    		
        }
    });
	$("#xqxx-set-modal").modal("show");
}