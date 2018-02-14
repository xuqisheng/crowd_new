package com.wisedu.crowd.service.cwgl.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wisedu.crowd.common.code.JsztEnum;
import com.wisedu.crowd.common.code.JyztEnum;
import com.wisedu.crowd.common.code.SzlbEnum;
import com.wisedu.crowd.common.code.XtcsEnum;
import com.wisedu.crowd.common.code.YesNoEnum;
import com.wisedu.crowd.common.exception.ServiceException;
import com.wisedu.crowd.common.util.CommonUtil;
import com.wisedu.crowd.common.util.ConditionUtil;
import com.wisedu.crowd.common.util.DateUtil;
import com.wisedu.crowd.common.util.DecimalUtil;
import com.wisedu.crowd.common.util.PageUtil;
import com.wisedu.crowd.common.util.PersonalTaxUtil;
import com.wisedu.crowd.common.util.StringUtil;
import com.wisedu.crowd.dao.cwgl.KfzzhmxInfoMapper;
import com.wisedu.crowd.dao.cwgl.extend.KfzzhmxInfoExtendMapper;
import com.wisedu.crowd.entity.cwgl.KfzzhmxInfo;
import com.wisedu.crowd.entity.cwgl.extend.GrsdsInfoExtend;
import com.wisedu.crowd.entity.cwgl.extend.KfzzhmxInfoExtend;
import com.wisedu.crowd.entity.dto.QueryCondition;
import com.wisedu.crowd.entity.log.CustomOperateLog;
import com.wisedu.crowd.entity.rwgl.extend.RwjbxxInfoExtend;
import com.wisedu.crowd.entity.xtgl.extend.XtcsbInfoExtend;
import com.wisedu.crowd.entity.yhgl.KfzxxInfo;
import com.wisedu.crowd.service.cwgl.GrsdsInfoService;
import com.wisedu.crowd.service.cwgl.KfzzhmxInfoService;
import com.wisedu.crowd.service.dto.DataResult;
import com.wisedu.crowd.service.xtgl.XtcsbInfoService;
import com.wisedu.crowd.service.yhgl.KfzxxInfoService;

@Service("kfzzhmxInfoService")
public class KfzzhmxInfoServiceImpl implements KfzzhmxInfoService{

	@Autowired
	private KfzzhmxInfoMapper kfzzhmxInfoMapper;
	@Autowired
	private KfzzhmxInfoExtendMapper kfzzhmxInfoExtendMapper;
	@Autowired
	private KfzxxInfoService kfzxxInfoService;
	@Autowired
	private XtcsbInfoService xtcsbInfoService; 
	@Autowired
	private GrsdsInfoService grsdsInfoService;
	@Override
	public DataResult<Integer> deleteByPrimaryKey(String wid, CustomOperateLog log) throws ServiceException {
		return DataResult.success(kfzzhmxInfoMapper.deleteByPrimaryKey(wid));
	}

	@Override
	public DataResult<Integer> insertSelective(KfzzhmxInfo record, CustomOperateLog log) throws ServiceException {
		return DataResult.success(kfzzhmxInfoMapper.insertSelective(record));
	}

	@Override
	public DataResult<KfzzhmxInfo> selectByPrimaryKey(String wid, CustomOperateLog log) throws ServiceException {
		return DataResult.success(kfzzhmxInfoMapper.selectByPrimaryKey(wid));
		}

	@Override
	public DataResult<Integer> updateByPrimaryKeySelective(KfzzhmxInfo record, CustomOperateLog log)
			throws ServiceException {
		return DataResult.success(kfzzhmxInfoMapper.updateByPrimaryKeySelective(record));
	}

	@Override
	public DataResult<Double> selectTotalMoney(QueryCondition<KfzzhmxInfoExtend> condition, CustomOperateLog log)
			throws ServiceException {
		return DataResult.success(kfzzhmxInfoExtendMapper.selectTotalMoney(condition));
	}

	@Override
	public DataResult<List<Map<String, Object>>> selectTotalMoneyByMonth(QueryCondition<KfzzhmxInfoExtend> condition,
			CustomOperateLog log) throws ServiceException {
		if (condition.getPageInfo() != null) {
			Page<RwjbxxInfoExtend> page = PageHelper.startPage(condition.getPageInfo().getPageNum(),
					condition.getPageInfo().getPageSize());
			List<Map<String,Object>> datas = kfzzhmxInfoExtendMapper.selectTotalMoneyByMonth(condition);

			DataResult<List<Map<String,Object>>> dataResult = DataResult.success(datas);
			dataResult.setPageInfo(PageUtil.changePageInfo(page));
			return dataResult;
		} else {
			return DataResult.success(kfzzhmxInfoExtendMapper.selectTotalMoneyByMonth(condition));

		}
	}

	@Override
	public DataResult<List<KfzzhmxInfoExtend>> selectDisplayByCondition(QueryCondition<KfzzhmxInfoExtend> condition,
			CustomOperateLog log) throws ServiceException {
		if (condition.getPageInfo() != null) {
			Page<RwjbxxInfoExtend> page = PageHelper.startPage(condition.getPageInfo().getPageNum(),
					condition.getPageInfo().getPageSize());
			List<KfzzhmxInfoExtend> datas = kfzzhmxInfoExtendMapper.selectDisplayByCondition(condition);

			DataResult<List<KfzzhmxInfoExtend>> dataResult = DataResult.success(datas);
			dataResult.setPageInfo(PageUtil.changePageInfo(page));
			return dataResult;
		} else {
			return DataResult.success(kfzzhmxInfoExtendMapper.selectDisplayByCondition(condition));

		}
	}

	@Override
	public DataResult<BigDecimal> calcuYkgs(BigDecimal money, CustomOperateLog log) throws ServiceException {
		KfzxxInfo kfzxxInfo=kfzxxInfoService.selectByPrimaryKey(log.getKfzId(), log).getDatas();
		return DataResult.success(caluGs(kfzxxInfo,money,log));
	}

	private BigDecimal caluGs(KfzxxInfo kfzxxInfo,BigDecimal money, CustomOperateLog log){
		KfzzhmxInfoExtend queryKfzzhmxInfoExtend=new KfzzhmxInfoExtend();
		queryKfzzhmxInfoExtend.setMinMonth(DateUtil.getCurrentMonthStr());
		queryKfzzhmxInfoExtend.setMaxMonth(DateUtil.getCurrentMonthStr());
		queryKfzzhmxInfoExtend.setSzlbid(SzlbEnum.TX.getCode());
		queryKfzzhmxInfoExtend.setKfzid(log.getKfzId());
		List<Map<String, Object>> txxx=this.selectTotalMoneyByMonth(ConditionUtil.createCondition(queryKfzzhmxInfoExtend), log).getDatas();
		Map<String,Object> map=new HashMap<String,Object>();
		if(CommonUtil.isNotEmptyList(txxx)){
			for(Map<String,Object> tx:txxx){
				map.putAll(tx);
			}
		}
		//获取本月的已提现但还未结算的金额
		queryKfzzhmxInfoExtend.setJszt(StringUtil.toShort(YesNoEnum.YES.getCode()));
		List<Map<String, Object>> yjsTxxx=this.selectTotalMoneyByMonth(ConditionUtil.createCondition(queryKfzzhmxInfoExtend), log).getDatas();
		if(CommonUtil.isNotEmptyList(yjsTxxx)){
			map.put("wjstxje",DecimalUtil.sub(DecimalUtil.toDecimal(map.get("je")), DecimalUtil.toDecimal(yjsTxxx.get(0).get("je"))));
		}
		
		BigDecimal ktxje=DecimalUtil.sub(DecimalUtil.changeNull(kfzxxInfo.getZhye()), DecimalUtil.toDecimal(map.get("wjstxje")));
		if(money.compareTo(ktxje)==1){
			throw new ServiceException("您的提现金额已超过您的可提现范围，请重新确认！");
		}
		//本月累积提现金额
		BigDecimal monthTxJe=DecimalUtil.add(money, DecimalUtil.toDecimal(map.get("je")));
		
		//获取扣税起征点
		XtcsbInfoExtend queryXtcsbInfoExtend=new XtcsbInfoExtend();
		queryXtcsbInfoExtend.setCsmc(XtcsEnum.GRSDS_QZZ.getCode());
		
		Integer qzd=StringUtil.toInt(xtcsbInfoService.selectByCondition(queryXtcsbInfoExtend).getDatas().get(0).getCsz());
		GrsdsInfoExtend  grsdxInfo=grsdsInfoService.selectByMoney(DecimalUtil.sub(monthTxJe, DecimalUtil.toDecimal(qzd)), log).getDatas().get(0);
		
		BigDecimal ksje=PersonalTaxUtil.calculation(monthTxJe, qzd, grsdxInfo.getSl(), grsdxInfo.getSskcf());
		//本次扣税金额
		BigDecimal bcksje=DecimalUtil.sub(ksje, DecimalUtil.toDecimal(map.get("ykgs")));
		return bcksje;
	}
	@Override
	public DataResult<Integer> applyTx(BigDecimal txje, String yzm, String zfbzh, CustomOperateLog log)
			throws ServiceException {
		KfzxxInfo kfzxxInfo=kfzxxInfoService.selectByPrimaryKey(log.getKfzId(), log).getDatas();
		BigDecimal bcksje=caluGs(kfzxxInfo,txje,log);
		KfzzhmxInfo saveKfzzhmxInfo=new KfzzhmxInfo();
		saveKfzzhmxInfo.setWid(StringUtil.getUuId());
		saveKfzzhmxInfo.setFysm("提现申请");
		saveKfzzhmxInfo.setJe(txje);
		saveKfzzhmxInfo.setJszt(StringUtil.toShort(JsztEnum.DZF.getCode()));
		saveKfzzhmxInfo.setJyzt(StringUtil.toShort(JyztEnum.YZB.getCode()));
		saveKfzzhmxInfo.setKfzid(log.getKfzId());
		saveKfzzhmxInfo.setSzlbid(SzlbEnum.TX.getCode());
		saveKfzzhmxInfo.setSzsj(DateUtil.getCurrentDate());
		saveKfzzhmxInfo.setYkgs(bcksje);
		saveKfzzhmxInfo.setZdip(log.getCustomIp());
		saveKfzzhmxInfo.setZfbzh(zfbzh);
		saveKfzzhmxInfo.setZhye(kfzxxInfo.getZhye());
		saveKfzzhmxInfo.setZzczsj(DateUtil.getCurrentDate());
		saveKfzzhmxInfo.setDjzje(kfzxxInfo.getDjye());
		this.insertSelective(saveKfzzhmxInfo, log);
		return DataResult.success(1);
	}

	@Override
	public DataResult<List<Map<String, Object>>> selectDisplayTotalMoney(QueryCondition<KfzzhmxInfoExtend> condition,
			CustomOperateLog log) throws ServiceException {
		if (condition.getPageInfo() != null) {
			Page<RwjbxxInfoExtend> page = PageHelper.startPage(condition.getPageInfo().getPageNum(),
					condition.getPageInfo().getPageSize());
			List<Map<String,Object>> datas = kfzzhmxInfoExtendMapper.selectDisplayTotalMoney(condition);

			DataResult<List<Map<String,Object>>> dataResult = DataResult.success(datas);
			dataResult.setPageInfo(PageUtil.changePageInfo(page));
			return dataResult;
		} else {
			return DataResult.success(kfzzhmxInfoExtendMapper.selectDisplayTotalMoney(condition));

		}
	}

	@Override
	public DataResult<List<Map<String, Object>>> selectCustomByCondition(QueryCondition<KfzzhmxInfoExtend> condition,
			CustomOperateLog log) throws ServiceException {
		if (condition.getPageInfo() != null) {
			Page<Map<String, Object>> page = PageHelper.startPage(condition.getPageInfo().getPageNum(),
					condition.getPageInfo().getPageSize());
			List<Map<String, Object>> datas = kfzzhmxInfoExtendMapper.selectCustomByCondition(condition);

			DataResult<List<Map<String, Object>>> dataResult = DataResult.success(datas);
			dataResult.setPageInfo(PageUtil.changePageInfo(page));
			return dataResult;
		} else {
			return DataResult.success(kfzzhmxInfoExtendMapper.selectCustomByCondition(condition));

		}
	}

}