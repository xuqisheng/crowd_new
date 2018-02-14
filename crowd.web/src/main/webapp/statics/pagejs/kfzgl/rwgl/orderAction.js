/**
 * 
 */

define(function(require, exports, module) {
	var $$ = jQuery$ = require('jquery');
	require('plugins/vendor/bootstrap/validator/entrance');
	$$('#formSqys').bootstrapValidator({
		 fields:{ 
			 pj:{
				 validators:{
					 callback:{
						 message:"此项不能为空，请打分",
						 callback:function(value, validator,$field){
							 var _selectValue=6;
							 if($("#formSqys [data-option='pj'] a.clibg").css("z-index")){
								 _selectValue=$("#formSqys [data-option='pj'] a.clibg").css("z-index");
							 }
							 var _value= (6-_selectValue);
								if(_value<=0){
									return false;
								}
								return true;
							 }
						 }
					 }
						
				 }
			 }
	});
	$$('#formPjxx').bootstrapValidator({
		 fields:{ 
			 pj:{
				 validators:{
					 callback:{
						 message:"此项不能为空，请打分",
						 callback:function(value, validator,$field){
							 var _selectValue=6;
							 if($("#formPjxx [data-option='pj'] a.clibg").css("z-index")){
								 _selectValue=$("#formPjxx [data-option='pj'] a.clibg").css("z-index");
							 }
							 var _value= (6-_selectValue);
								if(_value<=0){
									return false;
								}
								return true;
								
								
							 }
						 }
					 }
						
				 }
			 }
	});
	
	$("#modalPj [data-option='pj']").on("click","a",function(){
		$$('formPjxx').data('bootstrapValidator').updateStatus('pj', 'NOT_VALIDATED',null).validateField('pj')
	});
	
	$("#modal_sqys [data-option='pj']").on("click","a",function(){
		$$('#formSqys').data('bootstrapValidator').updateStatus('pj', 'NOT_VALIDATED',null).validateField('pj')
	});
	
	$("")
	
	scoreFun($("#modal_sqys [data-option='pj']"),{
		 fen_d:32,//每一个a的宽度
        ScoreGrade:5,//a的个数5
        defaultValue:(score?score:0)
	});
	scoreFun($("#modalPj [data-option='pj']"),{
		 fen_d:32,//每一个a的宽度
       ScoreGrade:5,//a的个数5
       defaultValue:(score?score:0)
	});
	//申请验收
	$("a[data-option='sqys']").click(function(){
		   $("#modal_sqys").modal("show");
		
	});
	$("a[data-option='pj']").click(function(){
	   $("#modalPj").modal("show");
	
	});
	
	$("a[data-option='sh']").click(function(){
		confirm("您确认申请售后结束吗？",function(){
			doPost(basePath+"/kfzOrderAction/applyEndSale","rwid="+rwid,function(data){
				if(data && data.datas){
					window.location.reload();
				}
			})
		});
	});
	$("#btnSavePjxx").click(function(){
		var bootstrapValidator = $$('#formPjxx').data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var param="rwid="+rwid;
			var _selectValue=6;
			 if($("#formPjxx [data-option='pj'] a.clibg").css("z-index")){
				 _selectValue=$("#formPjxx [data-option='pj'] a.clibg").css("z-index");
			 }
			 _selectValue=6-parseInt(_selectValue);
			 param+="&score="+_selectValue;
			 param+="&content="+$("#formPjxx [name='tgpjnr']").val();
			doPost(basePath+"/kfzOrderAction/pj",param,function(data){
				window.location.reload();
			});
		}
	});
	
	$("#btnSaveSqys").click(function(){
		var bootstrapValidator = $$('#formSqys').data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var param="rwid="+rwid;
			var _selectValue=6;
			 if($("#formSqys [data-option='pj'] a.clibg").css("z-index")){
				 _selectValue=$("#formSqys [data-option='pj'] a.clibg").css("z-index");
			 }
			 _selectValue=6-parseInt(_selectValue);
			 param+="&score="+_selectValue;
			 param+="&content="+$("#formSqys [name='tgpjnr']").val();
			doPost(basePath+"/kfzOrderAction/applyAccept",param,function(data){
				window.location.reload();
			});
		}
	});
});