package com.wisedu.crowd.controller.xqfxx.first;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisedu.crowd.common.code.DictionaryEnum;
import com.wisedu.crowd.common.code.YhxxShztEnum;
import com.wisedu.crowd.common.util.ConstantsUtil;
import com.wisedu.crowd.common.util.PageUtil;
import com.wisedu.crowd.common.util.StringUtil;
import com.wisedu.crowd.controller.BaseController;
import com.wisedu.crowd.entity.dictionary.DictionaryInfo;
import com.wisedu.crowd.entity.dictionary.XzqhInfo;
import com.wisedu.crowd.entity.dto.PageInfo;
import com.wisedu.crowd.entity.dto.QueryCondition;
import com.wisedu.crowd.entity.yhgl.KfzxxInfo;
import com.wisedu.crowd.entity.yhgl.XqfxxInfo;
import com.wisedu.crowd.entity.yhgl.extend.KfzxxInfoExtend;
import com.wisedu.crowd.entity.yhgl.extend.XqfxxInfoExtend;
import com.wisedu.crowd.interceptor.AuthLoginAnnotation;
import com.wisedu.crowd.service.dictionary.DictionaryService;
import com.wisedu.crowd.service.dictionary.XzqhInfoService;
import com.wisedu.crowd.service.dto.DataResult;
import com.wisedu.crowd.service.yhgl.XqfxxInfoService;

@Controller
@RequestMapping("xqfSupply")
public class XqfSupplyController extends BaseController {

	
	
	@Autowired
	private XzqhInfoService   xzqhInfoService;
	
	@Autowired
	private DictionaryService  dictionaryService;
	
	
	@Autowired
	private XqfxxInfoService xqfxxInfoService;
	
	@RequestMapping("index")
	@AuthLoginAnnotation
	public String index(Model model){
	
		if(!StringUtil.isEmpty(this.getXqfxx())&&!StringUtil.isEmpty(this.getXqfxx().getXm())&&!StringUtil.isEmpty(this.getXqfxx().getNc())){
			return "redirect:../xqfConfrim/index?flag=xqfconfirm";
		}
		if(StringUtil.isEmpty(this.getYhId())){
			return "redirect:../xqfxx/index";
		}
		List<DictionaryInfo> rangeList=dictionaryService.selectAllByTable(DictionaryEnum.T_CROWD_SJZD_USER_RANGE);
		model.addAttribute("rangeList", rangeList);
		
		XzqhInfo  xzqhInfo=new XzqhInfo();
		xzqhInfo.setCc("1");
		List<XzqhInfo>  provinceList=xzqhInfoService.selectByCondition(xzqhInfo);
		model.addAttribute("provinceList", provinceList);
		xzqhInfo.setCc("2");
		List<XzqhInfo>  cityList=xzqhInfoService.selectByCondition(xzqhInfo);
		model.addAttribute("cityList", cityList);
		xzqhInfo.setCc("3");
		List<XzqhInfo>  areaList=xzqhInfoService.selectByCondition(xzqhInfo);
		model.addAttribute("areaList", areaList);
		
		return "xqfgl/xqfxx/first/xqfSupplyBasic";
	}
	
	@ResponseBody
	@RequestMapping("queryXqfxxInfo")
	@AuthLoginAnnotation
	public DataResult<List<XqfxxInfoExtend>> queryKfzxxInfo(XqfxxInfoExtend xqfxxInfoExtend) throws Exception{
		if(StringUtil.isEmpty(this.getYhId())){
			return null;
		}
		xqfxxInfoExtend.setYhid(this.getYhId());
		QueryCondition<XqfxxInfoExtend> condition=new QueryCondition<>();
		PageInfo page=PageUtil.cratePageInfo(null, null);
		condition.setPageInfo(page);
		condition.setQuery(xqfxxInfoExtend);
		return  xqfxxInfoService.selectByCondition(condition,createCustomOperateLog());
	}
	
	
	@ResponseBody
	@RequestMapping("insertFirstXqfxxInfo")
	@AuthLoginAnnotation
	public DataResult<Integer> insertFirstXqfxxInfo(XqfxxInfoExtend xqfxxInfo) throws Exception{
		xqfxxInfo.setYhid(this.getYhId());
		if(!StringUtil.isEmpty(this.getXqfxx())&&!StringUtil.isEmpty(this.getXqfxx().getWid())){
			xqfxxInfo.setWid(this.getXqfxx().getWid());
		}
		DataResult<XqfxxInfoExtend> result=xqfxxInfoService.insertFirstXqfxxInfo(xqfxxInfo, this.createCustomOperateLog());
	    request.getSession().setAttribute(ConstantsUtil.SESSION_XQFXX, result.getDatas());	
	    return  DataResult.success(1);
	}
	
	
	
}