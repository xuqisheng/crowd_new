package com.wisedu.crowd.service.rwgl.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wisedu.crowd.common.code.JyztEnum;
import com.wisedu.crowd.common.code.KfzxyjflxEnum;
import com.wisedu.crowd.common.code.NwbEnum;
import com.wisedu.crowd.common.code.PjClassifyEnum;
import com.wisedu.crowd.common.code.PjResultEnum;
import com.wisedu.crowd.common.code.RwcfxxZtEnum;
import com.wisedu.crowd.common.code.RwlbEnum;
import com.wisedu.crowd.common.code.RwtbztEnum;
import com.wisedu.crowd.common.code.RwxqlxEnum;
import com.wisedu.crowd.common.code.RwxsEnum;
import com.wisedu.crowd.common.code.RwztStateEnum;
import com.wisedu.crowd.common.code.SzlbEnum;
import com.wisedu.crowd.common.code.XqfxyjflxEnum;
import com.wisedu.crowd.common.code.XtcsEnum;
import com.wisedu.crowd.common.code.YesNoEnum;
import com.wisedu.crowd.common.exception.ServiceException;
import com.wisedu.crowd.common.util.CommonUtil;
import com.wisedu.crowd.common.util.ConditionUtil;
import com.wisedu.crowd.common.util.ConstantsUtil;
import com.wisedu.crowd.common.util.DateUtil;
import com.wisedu.crowd.common.util.DecimalUtil;
import com.wisedu.crowd.common.util.PageUtil;
import com.wisedu.crowd.common.util.StringUtil;
import com.wisedu.crowd.dao.rwgl.RwjbxxInfoMapper;
import com.wisedu.crowd.dao.rwgl.extend.BugZrrInfoExtendMapper;
import com.wisedu.crowd.dao.rwgl.extend.RwjbxxInfoExtendMapper;
import com.wisedu.crowd.dao.rwgl.extend.RwztbglsInfoExtendMapper;
import com.wisedu.crowd.dao.yhgl.KfzxyjfInfoMapper;
import com.wisedu.crowd.dao.yhgl.KfzxyjfxqInfoMapper;
import com.wisedu.crowd.dao.yhgl.XqfxyjfInfoMapper;
import com.wisedu.crowd.dao.yhgl.XqfxyjfxqInfoMapper;
import com.wisedu.crowd.dao.yhgl.extend.KfzxxInfoExtendMapper;
import com.wisedu.crowd.dao.yhgl.extend.KfzxyjfInfoExtendMapper;
import com.wisedu.crowd.dao.yhgl.extend.KfzxyjfxqInfoExtendMapper;
import com.wisedu.crowd.dao.yhgl.extend.XqfxyjfInfoExtendMapper;
import com.wisedu.crowd.dao.yhgl.extend.XqfxyjfxqInfoExtendMapper;
import com.wisedu.crowd.entity.cwgl.KfzzhmxInfo;
import com.wisedu.crowd.entity.cwgl.XqfzhmxInfo;
import com.wisedu.crowd.entity.cwgl.YyfzhszmxInfo;
import com.wisedu.crowd.entity.cwgl.YyfzhxxInfo;
import com.wisedu.crowd.entity.datacode.xtgl.XtcsbTypeEnum;
import com.wisedu.crowd.entity.dto.PageInfo;
import com.wisedu.crowd.entity.dto.QueryCondition;
import com.wisedu.crowd.entity.log.CustomOperateLog;
import com.wisedu.crowd.entity.rwgl.RwjbxxExtInfo;
import com.wisedu.crowd.entity.rwgl.RwjbxxInfo;
import com.wisedu.crowd.entity.rwgl.RwtbxxInfo;
import com.wisedu.crowd.entity.rwgl.RwztbglsInfo;
import com.wisedu.crowd.entity.rwgl.extend.BugZrrInfoExtend;
import com.wisedu.crowd.entity.rwgl.extend.RwcfxxInfoExtend;
import com.wisedu.crowd.entity.rwgl.extend.RwjbxxExtInfoExtend;
import com.wisedu.crowd.entity.rwgl.extend.RwjbxxInfoExtend;
import com.wisedu.crowd.entity.rwgl.extend.RwtbxxInfoExtend;
import com.wisedu.crowd.entity.rwgl.extend.RwztbglsInfoExtend;
import com.wisedu.crowd.entity.xtgl.extend.XtcsbInfoExtend;
import com.wisedu.crowd.entity.yhgl.KfzpjxxDetailInfo;
import com.wisedu.crowd.entity.yhgl.KfzpjxxInfo;
import com.wisedu.crowd.entity.yhgl.KfzxxInfo;
import com.wisedu.crowd.entity.yhgl.KfzxyjfInfo;
import com.wisedu.crowd.entity.yhgl.KfzxyjfxqInfo;
import com.wisedu.crowd.entity.yhgl.XqfbpjgInfo;
import com.wisedu.crowd.entity.yhgl.XqfxxInfo;
import com.wisedu.crowd.entity.yhgl.XqfxyjfInfo;
import com.wisedu.crowd.entity.yhgl.XqfxyjfxqInfo;
import com.wisedu.crowd.entity.yhgl.extend.KfzxxInfoExtend;
import com.wisedu.crowd.entity.yhgl.extend.KfzxyjfInfoExtend;
import com.wisedu.crowd.entity.yhgl.extend.KfzxyjfxqInfoExtend;
import com.wisedu.crowd.entity.yhgl.extend.XqfbpjgInfoExtend;
import com.wisedu.crowd.entity.yhgl.extend.XqfxyjfInfoExtend;
import com.wisedu.crowd.entity.yhgl.extend.XqfxyjfxqInfoExtend;
import com.wisedu.crowd.service.cwgl.KfzzhmxInfoService;
import com.wisedu.crowd.service.cwgl.XqfzhmxInfoService;
import com.wisedu.crowd.service.cwgl.YyfzhszmxInfoService;
import com.wisedu.crowd.service.cwgl.YyfzhxxInfoService;
import com.wisedu.crowd.service.dictionary.DictionaryService;
import com.wisedu.crowd.service.dto.DataResult;
import com.wisedu.crowd.service.mq.message.ApplyShjsMessages;
import com.wisedu.crowd.service.mq.message.ApplyYsMessages;
import com.wisedu.crowd.service.mq.message.PublishingRwjbxxMessages;
import com.wisedu.crowd.service.mq.message.SelectRwjbxxMessages;
import com.wisedu.crowd.service.mq.message.ShNotPassMessages;
import com.wisedu.crowd.service.mq.message.ShPassMessages;
import com.wisedu.crowd.service.mq.message.TbRwjbxxMessages;
import com.wisedu.crowd.service.mq.message.YsNotPassMessages;
import com.wisedu.crowd.service.mq.message.YsPassMessages;
import com.wisedu.crowd.service.rwgl.RwcfxxInfoService;
import com.wisedu.crowd.service.rwgl.RwjbxxExtInfoService;
import com.wisedu.crowd.service.rwgl.RwjbxxInfoService;
import com.wisedu.crowd.service.rwgl.RwtbxxInfoService;
import com.wisedu.crowd.service.rwgl.RwztbglsInfoService;
import com.wisedu.crowd.service.xtgl.SequenceInfoService;
import com.wisedu.crowd.service.xtgl.XtcsbInfoService;
import com.wisedu.crowd.service.yhgl.KfzpjxxDetailInfoService;
import com.wisedu.crowd.service.yhgl.KfzpjxxInfoService;
import com.wisedu.crowd.service.yhgl.KfzxxInfoService;
import com.wisedu.crowd.service.yhgl.XqfbpjgInfoService;
import com.wisedu.crowd.service.yhgl.XqfxxInfoService;

@Service("rwjbxxInfoService")
@Transactional
public class RwjbxxInfoServiceImpl implements RwjbxxInfoService {

	@Autowired
	private RwjbxxInfoMapper rwjbxxInfoMapper;

	@Autowired
	private RwjbxxInfoExtendMapper rwjbxxInfoExtendMapper;

	@Autowired
	private RwjbxxExtInfoService rwjbxxExtInfoService;

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private RwztbglsInfoService rwztbglsInfoService;

	@Autowired
	private RwtbxxInfoService rwtbxxInfoService;
	@Autowired
	private XtcsbInfoService xtcsbInfoService;

	@Autowired
	private KfzpjxxInfoService kfzpjxxInfoService;

	@Autowired
	private XqfbpjgInfoService xqfbpjgInfoService;
	@Autowired
	private KfzpjxxDetailInfoService kfzpjxxDetailInfoService;
	@Autowired
	private KfzzhmxInfoService kfzzhmxInfoService;

	@Autowired
	private XqfzhmxInfoService xqfzhmxInfoService;

	@Autowired
	private YyfzhxxInfoService yyfzhxxInfoService;
	@Autowired
	private YyfzhszmxInfoService yyfzhszmxInfoService;

	@Autowired
	private KfzxxInfoService kfzxxInfoService;

	@Autowired
	private XqfxxInfoService xqfxxInfoService;

	@Autowired
	private RwcfxxInfoService rwcfxxInfoService;
	
	@Autowired
	private SequenceInfoService sequenceInfoService;

	@Autowired
	private PublishingRwjbxxMessages publishingRwjbxxMessages;
	@Autowired
	private TbRwjbxxMessages tbRwjbxxMessages;
	@Autowired
	private SelectRwjbxxMessages selectRwjbxxMessages;
	@Autowired
	private ApplyYsMessages applyYsMessages;
	@Autowired
	private ApplyShjsMessages applyShjsMessages;
	@Autowired
	private YsPassMessages ysPassMessages;
	@Autowired
	private YsNotPassMessages ysNotPassMessages;
	@Autowired
	private ShPassMessages shPassMessages;
	@Autowired
	private KfzxyjfInfoMapper kfzxyjfInfoMapper;
	@Autowired
	private KfzxyjfInfoExtendMapper kfzxyjfInfoExtendMapper;
	@Autowired
	private KfzxyjfxqInfoMapper kfzxyjfxqInfoMapper;
	@Autowired
	private KfzxyjfxqInfoExtendMapper kfzxyjfxqInfoExtendMapper;
	@Autowired
	private RwztbglsInfoExtendMapper rwztbglsInfoExtendMapper;
	@Autowired
	private BugZrrInfoExtendMapper bugZrrInfoExtendMapper;
	@Autowired
	private KfzxxInfoExtendMapper kfzxxInfoExtendMapper;
	@Autowired
	private XqfxyjfInfoMapper xqfxyjfInfoMapper;
	@Autowired
	private XqfxyjfInfoExtendMapper xqfxyjfInfoExtendMapper;
	@Autowired
	private XqfxyjfxqInfoMapper xqfxyjfxqInfoMapper;
	@Autowired
	private XqfxyjfxqInfoExtendMapper xqfxyjfxqInfoExtendMapper;

	@Autowired
	private ShNotPassMessages shNotPassMessages;
	private final static Logger LOG = LoggerFactory.getLogger(RwjbxxInfoServiceImpl.class);

	@Override
	public DataResult<Integer> deleteByPrimaryKey(String wid) throws ServiceException {
		return DataResult.success(rwjbxxInfoMapper.deleteByPrimaryKey(wid));
	}

	@Override
	public DataResult<Integer> insertSelective(RwjbxxInfo record) throws ServiceException {
		return DataResult.success(rwjbxxInfoMapper.insertSelective(record));
	}

	@Override
	public DataResult<RwjbxxInfo> selectByPrimaryKey(String wid) throws ServiceException {
		return DataResult.success(rwjbxxInfoMapper.selectByPrimaryKey(wid));
	}

	@Override
	public DataResult<Integer> updateByPrimaryKeySelective(RwjbxxInfo record) throws ServiceException {
		return DataResult.success(rwjbxxInfoMapper.updateByPrimaryKeySelective(record));
	}
	@Override
	public DataResult<List<RwjbxxInfoExtend>> selectByCondition(QueryCondition<RwjbxxInfoExtend> record,
			CustomOperateLog log) throws ServiceException {
		if (record.getPageInfo() != null) {
			Page<RwjbxxInfoExtend> page = PageHelper.startPage(record.getPageInfo().getPageNum(),
					record.getPageInfo().getPageSize());
			List<RwjbxxInfoExtend> datas = rwjbxxInfoExtendMapper.selectByCondition(record);

			DataResult<List<RwjbxxInfoExtend>> dataResult = DataResult.success(datas);
			dataResult.setPageInfo(PageUtil.changePageInfo(page));
			return dataResult;
		} else {
			return DataResult.success(rwjbxxInfoExtendMapper.selectByCondition(record));

		}
	}

	/**
	 * 需求必填项检测
	 * 
	 * @param rwjbxxInfo
	 * @throws ServiceException
	 */
	private void checkRwjbxx(RwjbxxInfo rwjbxxInfo) throws ServiceException {
		if (rwjbxxInfo == null) {
			throw new ServiceException("需求不能为空，请重新确认！");
		}
		if (StringUtil.isEmpty(rwjbxxInfo.getRwmc())) {
			throw new ServiceException("需求名称不能为空，请重新确认！");
		}
		if (StringUtil.isEmpty(rwjbxxInfo.getRwxs())) {
			throw new ServiceException("需求分类不能为空，请重新确认！");
		}
		// 开发性质的任务
		if (rwjbxxInfo.getRwxs().equals(RwxsEnum.KF.getCode())) {
			if (StringUtil.isEmpty(rwjbxxInfo.getRwlx())) {
				throw new ServiceException("需求类型不能为空，请重新确认！");
			}
			if (StringUtil.isEmpty(rwjbxxInfo.getSfjj())) {
				throw new ServiceException("是否紧急不能为空，请重新确认！");
			}
			if (RwxqlxEnum.XQ.getCode().equals(rwjbxxInfo.getRwlx())) {
				if (StringUtil.toInt(rwjbxxInfo.getXmysje()) <= 0) {
					throw new ServiceException("需求预算金额不能小于0，请重新确认");
				}
			}
		} else {
			if (StringUtil.toInt(rwjbxxInfo.getXmysje()) <= 0) {
				throw new ServiceException("需求预算金额不能小于0，请重新确认");
			}
		}
		if (StringUtil.isEmpty(rwjbxxInfo.getXmgs())) {
			throw new ServiceException("需求描述不能为空，请重新确认！");
		}
		long zbzq = DateUtil.getDaysBetween(DateUtil.getCurrentDateStr(), DateUtil.formatDate(rwjbxxInfo.getZbjzrq()));
		if (zbzq < 0) {
			throw new ServiceException("招标截至日期不能小于当前日期，请重新确认！");
		}
		long jfzq = DateUtil.getDaysBetween(DateUtil.formatDate(rwjbxxInfo.getZbjzrq()), rwjbxxInfo.getJfrq());
		if (jfzq < 0) {
			throw new ServiceException("交付日期日期不能小于招标截至日期，请重新确认！");
		}

	}

	@Override
	/**
	 * 
	 * @param rwjbxxInfo
	 * @param rwjbxxExtInfo
	 * @param flag
	 *            1发布2 草稿
	 * @param log
	 * @return
	 * @throws ServiceException
	 */
	public DataResult<Integer> publishing(RwjbxxInfo rwjbxxInfo, RwjbxxExtInfo rwjbxxExtInfo, String flag,
			CustomOperateLog log) throws ServiceException {
		checkRwjbxx(rwjbxxInfo);
		RwjbxxInfo saveRwjbxxInfo = new RwjbxxInfo();
		String rwid = "", rwbh = "";
		// 新建
		if (StringUtil.isEmpty(rwjbxxInfo.getWid())) {
			rwid = StringUtil.getUuId();
			rwbh=sequenceInfoService.selectNewRwbh(DateUtil.format(DateUtil.getCurrentDate(), "yyyyMM"), log).getDatas();
			saveRwjbxxInfo.setCjsj(DateUtil.getCurrentDate());
			saveRwjbxxInfo.setCjzip(log.getCustomIp());
		} else {
			RwjbxxInfo oldRwjbxxInfo = rwjbxxInfoMapper.selectByPrimaryKey(rwjbxxInfo.getWid());
			if (!log.getXqfId().equals(oldRwjbxxInfo.getXqfid())) {
				throw new ServiceException("该需求不是当前用户的需求，请重新确认");
			}
			// 发布
			if ("1".equals(flag)) {
				if (StringUtil.toInt(oldRwjbxxInfo.getXmzt()) > RwztStateEnum.CG.getCode()) {
					throw new ServiceException("该需求状态不满足发布需求状态，请重新确认");
				}

			} else {
				if (StringUtil.toInt(oldRwjbxxInfo.getXmzt()) != RwztStateEnum.CG.getCode()) {
					throw new ServiceException("该需求状态不满足保存草稿需求状态，请重新确认");
				}
			}
			rwid = rwjbxxInfo.getWid();
			rwbh = oldRwjbxxInfo.getRwbh();
		}
		saveRwjbxxInfo.setWid(rwid);
		saveRwjbxxInfo.setRwbh(rwbh);
		saveRwjbxxInfo.setRwmc(rwjbxxInfo.getRwmc());
		saveRwjbxxInfo.setRwxs(rwjbxxInfo.getRwxs());
		saveRwjbxxInfo.setRwlx(rwjbxxInfo.getRwlx());
		saveRwjbxxInfo.setSfjj(rwjbxxInfo.getSfjj());
		saveRwjbxxInfo.setKfkj(rwjbxxInfo.getKfkj());
		saveRwjbxxInfo.setZbjzrq(rwjbxxInfo.getZbjzrq());
		saveRwjbxxInfo.setJfrq(rwjbxxInfo.getJfrq());
		saveRwjbxxInfo.setXmysje(rwjbxxInfo.getXmysje());
		saveRwjbxxInfo.setXmgs(rwjbxxInfo.getXmgs());
		saveRwjbxxInfo.setXqwdfjid(rwjbxxInfo.getXqwdfjid());

		saveRwjbxxInfo.setYwxlb(rwjbxxExtInfo.getYwxbm());
		saveRwjbxxInfo.setXqfid(log.getXqfId());
		saveRwjbxxInfo.setFb(rwjbxxExtInfo.getFb());
		saveRwjbxxInfo
				.setZbzq(DateUtil.getDaysBetween(DateUtil.formatDate(rwjbxxInfo.getZbjzrq()), rwjbxxInfo.getJfrq()));
		// 不是开发任务
		if (!saveRwjbxxInfo.getRwxs().equals(RwxsEnum.KF.getCode())) {
			saveRwjbxxInfo.setRwlx(StringUtil.toShort(RwxqlxEnum.XQ.getCode()));
			saveRwjbxxInfo.setKfkj(null);
			saveRwjbxxInfo.setSfjj(YesNoEnum.NO.getCode());
		}
		// BUG类型的需求项目预算金额默认为0
		if (saveRwjbxxInfo.getRwlx() == StringUtil.toShort(RwxqlxEnum.BUG.getCode())) {
			saveRwjbxxInfo.setXmysje(0L);
		}
		Map<String, Object> rwxsMap = dictionaryService.selectRwxsByBm(saveRwjbxxInfo.getRwxs());
		Integer xmzt = StringUtil.toInt(RwztStateEnum.CG.getCode());
		if ("1".equals(flag)) {
			if (YesNoEnum.YES.getCode().equals(StringUtil.toStr(rwxsMap.get("SFXQRWLX")))) {
				xmzt = StringUtil.toInt(RwztStateEnum.DSH.getCode());

			} else {
				xmzt = StringUtil.toInt(RwztStateEnum.ZBZ.getCode());
			}
		} else {
			xmzt = StringUtil.toInt(RwztStateEnum.CG.getCode());
		}
		saveRwjbxxInfo.setXmzt(xmzt);
		rwjbxxExtInfo.setRwid(rwid);
		if (StringUtil.isEmpty(rwjbxxInfo.getWid())) {
			rwjbxxExtInfo.setWid(StringUtil.getUuId());
			rwjbxxInfoMapper.insertSelective(saveRwjbxxInfo);
			rwjbxxExtInfoService.insertSelective(rwjbxxExtInfo, log);
		} else {
			rwjbxxInfoMapper.updateByPrimaryKeySelective(saveRwjbxxInfo);
			rwjbxxExtInfoService.updateByPrimaryKeySelective(rwjbxxExtInfo, log);
		}

		// 任务状态变更日志
		RwztbglsInfo rwztbglsInfo = new RwztbglsInfo();
		rwztbglsInfo.setWid(StringUtil.getUuId());
		rwztbglsInfo.setCzrip(log.getCustomIp());
		rwztbglsInfo.setCzrxm(log.getXm());
		rwztbglsInfo.setCzsj(DateUtil.getCurrentDate());
		rwztbglsInfo.setShyy("发布需求");
		rwztbglsInfo.setXmid(rwid);
		rwztbglsInfo.setXqfid(log.getXqfId());
		rwztbglsInfo.setZt(StringUtil.toShort(xmzt));
		rwztbglsInfoService.insertSelective(rwztbglsInfo, log);
		// 发送申请审核通知
		if (saveRwjbxxInfo.getXmzt() == RwztStateEnum.DSH.getCode()) {
			try {
				publishingRwjbxxMessages.sendMsg(saveRwjbxxInfo.getWid());
			} catch (Exception e) {
				LOG.error("" + e);
			}
		}
		return DataResult.success(1);
	}

	/**
	 * 大厅众包专用
	 * 
	 * @param record
	 * @return
	 */
	public DataResult<List<RwjbxxInfoExtend>> selectForCenter(QueryCondition<RwjbxxInfoExtend> record)
			throws ServiceException {
		if (record.getPageInfo() != null) {
			Page<RwjbxxInfoExtend> page = PageHelper.startPage(record.getPageInfo().getPageNum(),
					record.getPageInfo().getPageSize());
			List<RwjbxxInfoExtend> datas = rwjbxxInfoExtendMapper.selectForCenter(record);

			DataResult<List<RwjbxxInfoExtend>> dataResult = DataResult.success(datas);
			dataResult.setPageInfo(PageUtil.changePageInfo(page));
			return dataResult;
		} else {
			return DataResult.success(rwjbxxInfoExtendMapper.selectForCenter(record));

		}
	}

	@Override
	public DataResult<List<RwjbxxInfoExtend>> selectDisplayByCondition(QueryCondition<RwjbxxInfoExtend> record)
			throws ServiceException {
		if (record.getPageInfo() != null) {
			Page<RwjbxxInfoExtend> page = PageHelper.startPage(record.getPageInfo().getPageNum(),
					record.getPageInfo().getPageSize());
			List<RwjbxxInfoExtend> datas = rwjbxxInfoExtendMapper.selectDisplayByCondition(record);

			DataResult<List<RwjbxxInfoExtend>> dataResult = DataResult.success(datas);
			dataResult.setPageInfo(PageUtil.changePageInfo(page));
			return dataResult;
		} else {
			return DataResult.success(rwjbxxInfoExtendMapper.selectDisplayByCondition(record));

		}
	}

	@Override
	public DataResult<List<RwjbxxInfoExtend>> selectDisplayByLikeCondition(QueryCondition<RwjbxxInfoExtend> record)
			throws ServiceException {
		if (record.getPageInfo() != null) {
			Page<RwjbxxInfoExtend> page = PageHelper.startPage(record.getPageInfo().getPageNum(),
					record.getPageInfo().getPageSize());
			List<RwjbxxInfoExtend> datas = rwjbxxInfoExtendMapper.selectDisplayByLikeCondition(record);

			DataResult<List<RwjbxxInfoExtend>> dataResult = DataResult.success(datas);
			dataResult.setPageInfo(PageUtil.changePageInfo(page));
			return dataResult;
		} else {
			return DataResult.success(rwjbxxInfoExtendMapper.selectDisplayByLikeCondition(record));

		}
	}

	@Override
	public DataResult<Integer> selectCountByCondition(QueryCondition<RwjbxxInfoExtend> record, CustomOperateLog log)
			throws ServiceException {
		return DataResult.success(rwjbxxInfoExtendMapper.selectCountByCondition(record));
	}

	@Override
	public DataResult<Integer> reback(String rwid, String shly, CustomOperateLog log) throws ServiceException {
		DataResult<RwjbxxInfo> rwjbxxInfoData = this.selectByPrimaryKey(rwid);
		if (StringUtil.toStr(shly).equals("")) {
			throw new ServiceException("收回需求的理由不能为空，请重新确认");
		}
		if (rwjbxxInfoData.getDatas() == null) {
			throw new ServiceException("该需求不存在，请重新确认");
		}
		RwjbxxInfo rwjbxxInfoOld = rwjbxxInfoData.getDatas();
		if (StringUtil.toInt(rwjbxxInfoOld.getXmzt()) != RwztStateEnum.DSH.getCode()) {
			throw new ServiceException("该需求状态不是待审核装惕啊，无法收回该需求");
		}
		if (!log.getXqfId().equals(rwjbxxInfoOld.getXqfid())) {
			throw new ServiceException("该需求状态不是您发布的需求，无法收回该需求");
		}

		RwjbxxInfo rwjbxxInfoSave = new RwjbxxInfo();
		rwjbxxInfoSave.setWid(rwid);
		rwjbxxInfoSave.setCjsj(DateUtil.getCurrentDate());
		rwjbxxInfoSave.setCjzip(log.getCustomIp());
		rwjbxxInfoSave.setXmzt(RwztStateEnum.CG.getCode());
		this.updateByPrimaryKeySelective(rwjbxxInfoSave);
		// 任务状态变更日志
		RwztbglsInfo rwztbglsInfo = new RwztbglsInfo();
		rwztbglsInfo.setWid(StringUtil.getUuId());
		rwztbglsInfo.setCzrip(log.getCustomIp());
		rwztbglsInfo.setCzrxm(log.getXm());
		rwztbglsInfo.setCzsj(DateUtil.getCurrentDate());
		rwztbglsInfo.setShyy("收回需求：" + shly);
		rwztbglsInfo.setXmid(rwid);
		rwztbglsInfo.setXqfid(log.getXqfId());
		rwztbglsInfo.setZt(StringUtil.toShort(RwztStateEnum.CG.getCode()));
		rwztbglsInfoService.insertSelective(rwztbglsInfo, log);
		return DataResult.success(1);
	}

	@Override
	public DataResult<Integer> modifyDate(String rwid, String zbjzrq, String jfrq, CustomOperateLog log)
			throws ServiceException {
		DataResult<RwjbxxInfo> rwjbxxInfoData = this.selectByPrimaryKey(rwid);
		if (rwjbxxInfoData.getDatas() == null) {
			throw new ServiceException("该需求不存在，请重新确认");
		}

		RwjbxxInfo rwjbxxInfo = rwjbxxInfoData.getDatas();
		if (!log.getXqfId().equals(rwjbxxInfo.getXqfid())) {
			throw new ServiceException("该需求状态不是您发布的需求，无法收回该需求");
		}

		long zbzq = DateUtil.getDaysBetween(DateUtil.getCurrentDateStr(), DateUtil.formatDate(rwjbxxInfo.getZbjzrq()));
		if (zbzq < 0) {
			throw new ServiceException("招标截至日期不能小于当前日期，请重新确认！");
		}
		long jfzq = DateUtil.getDaysBetween(DateUtil.formatDate(rwjbxxInfo.getZbjzrq()), rwjbxxInfo.getJfrq());
		if (jfzq < 0) {
			throw new ServiceException("交付日期日期不能小于招标截至日期，请重新确认！");
		}

		RwjbxxInfo saveRwjbxxInfo = new RwjbxxInfo();
		saveRwjbxxInfo.setWid(rwid);
		saveRwjbxxInfo.setZbjzrq(DateUtil.parseDate(zbjzrq));
		saveRwjbxxInfo.setJfrq(jfrq);
		this.updateByPrimaryKeySelective(saveRwjbxxInfo);
		return DataResult.success(1);
	}

	@Override
	public DataResult<Integer> delete(String rwid, CustomOperateLog log) throws ServiceException {
		DataResult<RwjbxxInfo> rwjbxxInfoData = this.selectByPrimaryKey(rwid);
		if (rwjbxxInfoData.getDatas() == null) {
			throw new ServiceException("该需求不存在，请重新确认");
		}

		RwjbxxInfo rwjbxxInfo = rwjbxxInfoData.getDatas();
		if (!log.getXqfId().equals(rwjbxxInfo.getXqfid())) {
			throw new ServiceException("该需求状态不是您发布的需求，无法删除该需求");
		}

		if (RwztStateEnum.CG.getCode() != StringUtil.toInt(rwjbxxInfo.getXmzt())) {
			throw new ServiceException("该需求状态不是草稿状态，无法删除该需求");
		}
		return this.deleteByPrimaryKey(rwid);
	}

	@Override
	public DataResult<Integer> doTb(String rwid, CustomOperateLog log) throws ServiceException {
		if (StringUtil.isEmpty(rwid)) {
			throw new ServiceException("需求WID不能为空，请重新确认");
		}
		DataResult<RwjbxxInfo> rwjbxxInfoDatas = this.selectByPrimaryKey(rwid);
		if (rwjbxxInfoDatas.getDatas() == null) {
			throw new ServiceException("该需求已不存在，请重新确认！");
		}
		if (rwjbxxInfoDatas.getDatas().getXqfid().equals(log.getXqfId())) {
			throw new ServiceException("您不能投标自己发布的需求，请重新确认");
		}
		return DataResult.success(1);

	}

	@Override
	/**
	 * 投标
	 */
	public DataResult<Integer> toTb(RwtbxxInfo rwtbxxInfo, CustomOperateLog log) throws ServiceException {
		if (StringUtil.isEmpty(rwtbxxInfo.getRwid())) {
			throw new ServiceException("需求WID不能为空，请重新确认");
		}
		DataResult<RwjbxxInfo> rwjbxxInfoDatas = this.selectByPrimaryKey(rwtbxxInfo.getRwid());
		if (rwjbxxInfoDatas.getDatas() == null) {
			throw new ServiceException("该需求已不存在，请重新确认！");
		}
		if (rwjbxxInfoDatas.getDatas().getXqfid().equals(log.getXqfId())) {
			throw new ServiceException("您不能投标自己发布的需求，请重新确认");
		}
		if (RwztStateEnum.ZBZ.getCode() != rwjbxxInfoDatas.getDatas().getXmzt()) {
			throw new ServiceException("该需求不在招标中，请重新确认！");
		}
		if (log.getKfzxxInfo() == null) {
			throw new ServiceException("您不是平台开发者，请重新确认！");
		}
		// 内部员工
		if (NwbEnum.NB.getCode().equals(log.getKfzxxInfo().getKfzlb())) {
			if (RwlbEnum.WB.getCode().equals(rwjbxxInfoDatas.getDatas().getRwlb())) {
				throw new ServiceException("该需求仅针对非金智教育员工开放投标，请重新确认！");
			}
		} else {
			if (RwlbEnum.NB.getCode().equals(rwjbxxInfoDatas.getDatas().getRwlb())) {
				throw new ServiceException("该需求仅针对金智教育内部员工开放投标，请重新确认！");
			}
		}

		if (YesNoEnum.YES.getCode().equals(rwjbxxInfoDatas.getDatas().getSfxsrw())) {
			BigDecimal zbje = this.selectSumZbjeByKfzid(log.getKfzId(), log).getDatas();
			zbje = DecimalUtil.changeNull(zbje);
			if (zbje.compareTo(ConstantsUtil.xsrwje) > 0) {
				throw new ServiceException("该需求仅新手可投标，新手定义请参考平台名称解释！");
			}
		}

		XtcsbInfoExtend queryXtcsbInfoExtend = new XtcsbInfoExtend();
		queryXtcsbInfoExtend.setCsmc(XtcsbTypeEnum.XMGL_TBJGHDBL.getCode());
		DataResult<List<XtcsbInfoExtend>> xtcsbInfoDatas = xtcsbInfoService.selectByCondition(queryXtcsbInfoExtend);
		// 上下浮动金额
		long sxfdje = rwjbxxInfoDatas.getDatas().getXmysje()
				* StringUtil.toLong(xtcsbInfoDatas.getDatas().get(0).getCsz()) / 100;
		long maxTbje = rwjbxxInfoDatas.getDatas().getXmysje() + sxfdje;
		long minTbje = rwjbxxInfoDatas.getDatas().getXmysje() - sxfdje;
		if (rwtbxxInfo.getTbje().longValue() > maxTbje || rwtbxxInfo.getTbje().longValue() < minTbje) {
			throw new ServiceException("投标金额只能在[" + minTbje + "," + maxTbje + "]之间，请重新确认！");
		}
		RwtbxxInfoExtend queryRwtbxxInfoExtend = new RwtbxxInfoExtend();
		queryRwtbxxInfoExtend.setKfzid(log.getKfzId());
		queryRwtbxxInfoExtend.setRwid(rwtbxxInfo.getRwid());

		DataResult<Integer> countData = rwtbxxInfoService
				.selectCountByCondition(ConditionUtil.createCondition(queryRwtbxxInfoExtend));
		if (StringUtil.toInt(countData.getDatas()) > 0) {
			throw new ServiceException("您已投标，无法重复投标！");
		}
		RwtbxxInfo saveRwtbxxInfo = new RwtbxxInfo();
		saveRwtbxxInfo.setWid(StringUtil.getUuId());
		saveRwtbxxInfo.setRwid(rwtbxxInfo.getRwid());
		saveRwtbxxInfo.setSfjskfzxy(YesNoEnum.YES.getCode());
		saveRwtbxxInfo.setTbcs(rwtbxxInfo.getTbcs());
		saveRwtbxxInfo.setTbje(rwtbxxInfo.getTbje());
		saveRwtbxxInfo.setKfzid(log.getKfzId());
		saveRwtbxxInfo.setTbrip(log.getCustomIp());
		saveRwtbxxInfo.setTbsj(DateUtil.getCurrentDate());
		saveRwtbxxInfo.setZbzt(RwtbztEnum.DPB.getCode());
		rwtbxxInfoService.insertSelective(saveRwtbxxInfo);
		try {
			tbRwjbxxMessages.sendMsg(rwjbxxInfoDatas.getDatas().getWid(), saveRwtbxxInfo.getKfzid(),
					saveRwtbxxInfo.getWid());
		} catch (Exception e) {
			LOG.error("" + e);
		}
		return DataResult.success(1);
	}

	@Override
	public DataResult<Integer> selectTb(String tbid, CustomOperateLog log) throws ServiceException {
		DataResult<RwtbxxInfo> rwtbxxInfoDatas = rwtbxxInfoService.selectByPrimaryKey(tbid);

		if (rwtbxxInfoDatas.getDatas() == null) {
			throw new ServiceException("该投标信息已不存在，请重新确认！");
		}
		DataResult<RwjbxxInfo> rwjbxxInfoDatas = this.selectByPrimaryKey(rwtbxxInfoDatas.getDatas().getRwid());
		if (rwjbxxInfoDatas.getDatas() == null) {
			throw new ServiceException("该需求信息已不存在，请重新确认！");
		}
		if (!rwjbxxInfoDatas.getDatas().getXqfid().equals(log.getXqfId())) {
			throw new ServiceException("该需求信息不是您发布的需求，请重新确认！");
		}
		if(RwxqlxEnum.XQ.getCode().equals(StringUtil.toStr(rwjbxxInfoDatas.getDatas().getRwlx()))){
			if (RwztStateEnum.ZBZ.getCode() != StringUtil.toInt(rwjbxxInfoDatas.getDatas().getXmzt())) {
				throw new ServiceException("该需求信息不在招标中，请重新确认！");
			}
		}
		XqfxxInfo xqfxxInfo = xqfxxInfoService.selectByPrimaryKey(rwjbxxInfoDatas.getDatas().getXqfid(), log)
				.getDatas();
		BigDecimal zje = DecimalUtil.ZERO;
		if (RwxqlxEnum.XQ.getCode().equals(StringUtil.toStr(rwjbxxInfoDatas.getDatas().getRwlx()))) {
			zje = DecimalUtil.changeNull(DecimalUtil.add(rwtbxxInfoDatas.getDatas().getTbje(),
					DecimalUtil.toDecimal(StringUtil.toStr(rwjbxxInfoDatas.getDatas().getJjbzje()))));
		} else {
			zje = DecimalUtil
					.changeNull(DecimalUtil.toDecimal(StringUtil.toStr(rwjbxxInfoDatas.getDatas().getJjbzje())));
		}

		if (zje.compareTo(DecimalUtil.changeNull(xqfxxInfo.getZhye())) == 1) {
			throw new ServiceException("你的账户余额不足" + zje + "元，请先去充值！");
		}

		RwtbxxInfo saveRwtbxxInfo = new RwtbxxInfo();
		saveRwtbxxInfo.setRwid(rwjbxxInfoDatas.getDatas().getWid());
		saveRwtbxxInfo.setZbzt(RwtbztEnum.WZB.getCode());
		rwtbxxInfoService.updateByCondition(saveRwtbxxInfo, log);
		saveRwtbxxInfo.setWid(tbid);
		saveRwtbxxInfo.setRwid(null);

		saveRwtbxxInfo.setZbzt(RwtbztEnum.YZB.getCode());
		rwtbxxInfoService.updateByPrimaryKeySelective(saveRwtbxxInfo);

		RwjbxxInfo saveRwjbxxInfo = new RwjbxxInfo();
		saveRwjbxxInfo.setXmzt(RwztStateEnum.KFZ.getCode());
		saveRwjbxxInfo.setKfzid(rwtbxxInfoDatas.getDatas().getKfzid());
		saveRwjbxxInfo.setZbsj(DateUtil.getCurrentDate());
		saveRwjbxxInfo.setWid(rwtbxxInfoDatas.getDatas().getRwid());
		saveRwjbxxInfo.setZbje(DecimalUtil.changeNull(rwtbxxInfoDatas.getDatas().getTbje()).longValue());
		this.updateByPrimaryKeySelective(saveRwjbxxInfo);

		// 任务状态变更日志
		RwztbglsInfo rwztbglsInfo = new RwztbglsInfo();
		rwztbglsInfo.setWid(StringUtil.getUuId());
		rwztbglsInfo.setCzrip(log.getCustomIp());
		rwztbglsInfo.setCzrxm(log.getXm());
		rwztbglsInfo.setCzsj(DateUtil.getCurrentDate());
		rwztbglsInfo.setShyy("选择投标人");
		rwztbglsInfo.setXmid(rwjbxxInfoDatas.getDatas().getWid());
		rwztbglsInfo.setXqfid(log.getXqfId());
		rwztbglsInfo.setKfzid(rwtbxxInfoDatas.getDatas().getKfzid());
		rwztbglsInfo.setZt(StringUtil.toShort(RwztStateEnum.KFZ.getCode()));
		rwztbglsInfoService.insertSelective(rwztbglsInfo, log);

		XqfxxInfo saveXqfxxInfo = new XqfxxInfo();
		saveXqfxxInfo.setWid(xqfxxInfo.getWid());
		saveXqfxxInfo.setZhdjye(DecimalUtil.add(xqfxxInfo.getZhdjye(), zje));
		saveXqfxxInfo.setZhye(DecimalUtil.sub(xqfxxInfo.getZhye(), zje));
		saveXqfxxInfo.setVersion(xqfxxInfo.getVersion());

		// 更新需求方账户信息(防止出现脏数据)
		for (int i = 0; i < 4; i++) {
			Integer updateCount = xqfxxInfoService.updateByVersion(saveXqfxxInfo, log).getDatas();
			if (i == 3) {
				throw new ServiceException("选标失败，请联系系统管理员！");
			}
			if (updateCount == null || updateCount == 0) {
				xqfxxInfo = xqfxxInfoService.selectByPrimaryKey(rwjbxxInfoDatas.getDatas().getXqfid(), log).getDatas();
				saveXqfxxInfo.setZhdjye(DecimalUtil.add(xqfxxInfo.getZhdjye(), zje));
				saveXqfxxInfo.setZhye(DecimalUtil.sub(xqfxxInfo.getZhye(), zje));
				saveXqfxxInfo.setVersion(xqfxxInfo.getVersion());
				continue;
			} else {
				break;
			}
		}
		if(RwxqlxEnum.XQ.getCode().equals(StringUtil.toStr(rwjbxxInfoDatas.getDatas().getRwlx()))){
			// 更新需求方账户明细
			XqfzhmxInfo saveSqfzhmxInfo = new XqfzhmxInfo();
			saveSqfzhmxInfo.setDjzje(saveXqfxxInfo.getZhdjye());
			saveSqfzhmxInfo.setFysm("选中投标人，平台冻结需求费用");
			saveSqfzhmxInfo.setId(StringUtil.getUuId());
			saveSqfzhmxInfo.setJe(StringUtil.toFloat(zje));
			saveSqfzhmxInfo.setJyzt(StringUtil.toShort(JyztEnum.YZB.getCode()));
			saveSqfzhmxInfo.setLyxmid(rwtbxxInfoDatas.getDatas().getRwid());
			saveSqfzhmxInfo.setSzlbid(SzlbEnum.PTTG.getCode());
			saveSqfzhmxInfo.setSzsj(DateUtil.getCurrentDate());
			saveSqfzhmxInfo.setXqfid(rwjbxxInfoDatas.getDatas().getXqfid());
			saveSqfzhmxInfo.setZdid(log.getUserId());
			saveSqfzhmxInfo.setZdip(log.getCustomIp());
			saveSqfzhmxInfo.setZhye(StringUtil.toFloat(saveXqfxxInfo.getZhye()));
			saveSqfzhmxInfo.setZzczrid(log.getUserId());
			saveSqfzhmxInfo.setZzczrip(log.getCustomIp());
			xqfzhmxInfoService.insertSelective(saveSqfzhmxInfo, log);
		}
		try {
			selectRwjbxxMessages.sendMsg(saveRwjbxxInfo.getWid(), saveRwjbxxInfo.getKfzid());
		} catch (Exception e) {
			LOG.error("" + e);
		}
		return DataResult.success(1);
	}

	@Override
	/**
	 * 申请验收
	 */
	public DataResult<Integer> applyAccept(String rwid, Integer score, String content, CustomOperateLog log)
			throws ServiceException {
		DataResult<RwjbxxInfo> rwjbxxInfoDatas = this.selectByPrimaryKey(rwid);
		if (rwjbxxInfoDatas.getDatas() == null) {
			throw new ServiceException("该需求信息已不存在，请重新确认！");
		}
		if (!rwjbxxInfoDatas.getDatas().getKfzid().equals(log.getKfzId())) {
			throw new ServiceException("该需求信息不是您投标的需求，请重新确认！");
		}
		if (RwztStateEnum.KFZ.getCode() != StringUtil.toInt(rwjbxxInfoDatas.getDatas().getXmzt())
				&& RwztStateEnum.YSBTG.getCode() != StringUtil.toInt(rwjbxxInfoDatas.getDatas().getXmzt())) {
			throw new ServiceException("该需求信息不在开发中(验收不通过)，无法申请验收，请重新确认！");
		}

		// 判断所有的拆分任务是否已完成
		RwcfxxInfoExtend queryRwcfxxInfoExtend = new RwcfxxInfoExtend();
		queryRwcfxxInfoExtend.setRwid(rwid);

		List<RwcfxxInfoExtend> rwcfxxInfoDatas = rwcfxxInfoService
				.selectByCondition(ConditionUtil.createCondition(queryRwcfxxInfoExtend), log).getDatas();
		if (CommonUtil.isNotEmptyList(rwcfxxInfoDatas)) {
			for (RwcfxxInfoExtend rwcfxxInfoExtend : rwcfxxInfoDatas) {
				if (rwcfxxInfoExtend.getRwzt() != StringUtil.toShort(RwcfxxZtEnum.YJJ.getCode())
						&& rwcfxxInfoExtend.getRwzt() != StringUtil.toShort(RwcfxxZtEnum.YWC.getCode())) {
					throw new ServiceException("该需求中存在拆分的需求还未完成，请重新确认！");
				}
			}
		}
		RwjbxxInfoExtend queryRwjbxxInfoExtend = new RwjbxxInfoExtend();
		queryRwjbxxInfoExtend.setParentId(rwid);
		queryRwjbxxInfoExtend.setKfzid(log.getKfzId());
		List<RwjbxxInfoExtend> rwjbxxInfoChildDatas = this
				.selectByCondition(ConditionUtil.createCondition(queryRwjbxxInfoExtend), log).getDatas();
		if (CommonUtil.isNotEmptyList(rwjbxxInfoChildDatas)) {
			for (RwjbxxInfoExtend rwjbxxInfoExtend : rwjbxxInfoChildDatas) {
				if (rwjbxxInfoExtend.getXmzt() < StringUtil.toInt(RwztStateEnum.SHZ.getCode())) {
					throw new ServiceException("该需求中存在问题还没有完成，请重新确认！");
				}
			}
		}

		RwjbxxInfo saveRwjbxxInfo = new RwjbxxInfo();
		saveRwjbxxInfo.setXmzt(RwztStateEnum.YSZ.getCode());
		saveRwjbxxInfo.setWid(rwid);
		this.updateByPrimaryKeySelective(saveRwjbxxInfo);

		// 获取需求方对开发者的评价结果

		this.kfzDoPj(rwjbxxInfoDatas.getDatas(), score, content, log);
		// 任务状态变更日志
		RwztbglsInfo rwztbglsInfo = new RwztbglsInfo();
		rwztbglsInfo.setWid(StringUtil.getUuId());
		rwztbglsInfo.setCzrip(log.getCustomIp());
		rwztbglsInfo.setCzrxm(log.getXm());
		rwztbglsInfo.setCzsj(DateUtil.getCurrentDate());
		rwztbglsInfo.setShyy("申请验收");
		rwztbglsInfo.setXmid(rwjbxxInfoDatas.getDatas().getWid());
		rwztbglsInfo.setKfzid(rwjbxxInfoDatas.getDatas().getKfzid());
		rwztbglsInfo.setZt(StringUtil.toShort(RwztStateEnum.YSZ.getCode()));
		rwztbglsInfoService.insertSelective(rwztbglsInfo, log);
		try {
			applyYsMessages.sendMsg(saveRwjbxxInfo.getWid());
		} catch (Exception e) {
			LOG.error("" + e);
		}
		return DataResult.success(1);
	}
	/**
	 * 申请售后结束
	 */
	 public DataResult<Integer> applyEndSale(String rwid,CustomOperateLog log) throws ServiceException{
		 DataResult<RwjbxxInfo> rwjbxxInfoDatas = this.selectByPrimaryKey(rwid);
		if (rwjbxxInfoDatas.getDatas() == null) {
			throw new ServiceException("该需求信息已不存在，请重新确认！");
		}
		if (!rwjbxxInfoDatas.getDatas().getKfzid().equals(log.getKfzId())) {
			throw new ServiceException("该需求信息不是您投标的需求，请重新确认！");
		}
		if (RwztStateEnum.SHJSBTG.getCode() != StringUtil.toInt(rwjbxxInfoDatas.getDatas().getXmzt())
				&& RwztStateEnum.YSBTG.getCode() != StringUtil.toInt(rwjbxxInfoDatas.getDatas().getXmzt())) {
			throw new ServiceException("该需求信息不在售后不通过，无法申请售后结束，请重新确认！");
		}
		// 判断所有的拆分任务是否已完成
			RwcfxxInfoExtend queryRwcfxxInfoExtend = new RwcfxxInfoExtend();
			queryRwcfxxInfoExtend.setRwid(rwid);
			List<RwcfxxInfoExtend> rwcfxxInfoDatas = rwcfxxInfoService
					.selectByCondition(ConditionUtil.createCondition(queryRwcfxxInfoExtend), log).getDatas();
			if (CommonUtil.isNotEmptyList(rwcfxxInfoDatas)) {
				for (RwcfxxInfoExtend rwcfxxInfoExtend : rwcfxxInfoDatas) {
					if (rwcfxxInfoExtend.getRwzt() != StringUtil.toShort(RwcfxxZtEnum.YJJ.getCode())
							&& rwcfxxInfoExtend.getRwzt() != StringUtil.toShort(RwcfxxZtEnum.YWC.getCode())) {
						throw new ServiceException("该需求中存在拆分的需求还未完成，请重新确认！");
					}
				}
			}
			RwjbxxInfoExtend queryRwjbxxInfoExtend = new RwjbxxInfoExtend();
			queryRwjbxxInfoExtend.setParentId(rwid);
			queryRwjbxxInfoExtend.setKfzid(log.getKfzId());
			List<RwjbxxInfoExtend> rwjbxxInfoChildDatas = this
					.selectByCondition(ConditionUtil.createCondition(queryRwjbxxInfoExtend), log).getDatas();
			if (CommonUtil.isNotEmptyList(rwjbxxInfoChildDatas)) {
				for (RwjbxxInfoExtend rwjbxxInfoExtend : rwjbxxInfoChildDatas) {
					if (rwjbxxInfoExtend.getXmzt() < StringUtil.toInt(RwztStateEnum.SHZ.getCode())) {
						throw new ServiceException("该需求中存在问题还没有完成，请重新确认！");
					}
				}
			}
			RwjbxxInfo saveRwjbxxInfo = new RwjbxxInfo();
			saveRwjbxxInfo.setXmzt(RwztStateEnum.SHJSDQR.getCode());
			saveRwjbxxInfo.setWid(rwid);
			this.updateByPrimaryKeySelective(saveRwjbxxInfo);
			// 任务状态变更日志
			RwztbglsInfo rwztbglsInfo = new RwztbglsInfo();
			rwztbglsInfo.setWid(StringUtil.getUuId());
			rwztbglsInfo.setCzrip(log.getCustomIp());
			rwztbglsInfo.setCzrxm(log.getXm());
			rwztbglsInfo.setCzsj(DateUtil.getCurrentDate());
			rwztbglsInfo.setShyy("申请售后结束");
			rwztbglsInfo.setXmid(rwjbxxInfoDatas.getDatas().getWid());
			rwztbglsInfo.setKfzid(rwjbxxInfoDatas.getDatas().getKfzid());
			rwztbglsInfo.setZt(StringUtil.toShort(RwztStateEnum.SHJSDQR.getCode()));
			rwztbglsInfoService.insertSelective(rwztbglsInfo, log);
			try {
				applyShjsMessages.sendMsg(saveRwjbxxInfo.getWid());
			} catch (Exception e) {
				LOG.error("" + e);
			}
			return DataResult.success(1);

	 }

	@Override
	/**
	 * 验收确认
	 */
	public DataResult<Integer> accept(String rwid, String pjjg, String bz, Float kfzl, Float fwzl, Float xysd,
			CustomOperateLog log) throws ServiceException {

		if ("".equals(rwid)) {
			throw new ServiceException("rwid不能为空，请重新确认！");
		}
		DataResult<RwjbxxInfo> rwjbxxInfoDatas = this.selectByPrimaryKey(rwid);
		if (rwjbxxInfoDatas.getDatas() == null) {
			throw new ServiceException("该需求信息已不存在，请重新确认！");
		}
		if ((log.isAdmin() == null || log.isAdmin() == false)
				&& (!rwjbxxInfoDatas.getDatas().getXqfid().equals(log.getXqfId()))) {
			throw new ServiceException("该需求信息不是您发布的需求，请重新确认！");
		}
		if (rwjbxxInfoDatas.getDatas().getXmzt() != RwztStateEnum.YSZ.getCode()) {
			throw new ServiceException("该需求状态不在验收中，请重新确认！");
		}
		xqfAccpetOrSaleEn(rwjbxxInfoDatas.getDatas(), pjjg, bz, kfzl, fwzl, xysd, log, 1);
		try {
			if (YesNoEnum.YES.getCode().equals(pjjg)) {// 售后通过
				ysPassMessages.sendMsg(rwid);
			} else {
				ysNotPassMessages.sendMsg(rwid);
			}
		} catch (Exception e) {
			LOG.error("" + e);
		}
		return DataResult.success(1);
	}

	/**
	 * 售后确认
	 */
	public DataResult<Integer> saleEnd(String rwid, String pjjg, String bz, Float kfzl, Float fwzl, Float xysd,
			CustomOperateLog log) throws ServiceException {

		if ("".equals(rwid)) {
			throw new ServiceException("rwid不能为空，请重新确认！");
		}
		DataResult<RwjbxxInfo> rwjbxxInfoDatas = this.selectByPrimaryKey(rwid);
		if (rwjbxxInfoDatas.getDatas() == null) {
			throw new ServiceException("该需求信息已不存在，请重新确认！");
		}
		if (!rwjbxxInfoDatas.getDatas().getXqfid().equals(log.getXqfId())) {
			throw new ServiceException("该需求信息不是您发布的需求，请重新确认！");
		}
		if (rwjbxxInfoDatas.getDatas().getXmzt() != RwztStateEnum.SHJSDQR.getCode()) {
			throw new ServiceException("该需求状态不在申请售后中，请重新确认！");
		}
		xqfAccpetOrSaleEn(rwjbxxInfoDatas.getDatas(), pjjg, bz, kfzl, fwzl, xysd, log, 2);
		try {
			if (YesNoEnum.YES.getCode().equals(pjjg)) {// 售后通过
				shPassMessages.sendMsg(rwid);
			} else {
				shNotPassMessages.sendMsg(rwid);
			}
		} catch (Exception e) {
			LOG.error("" + e);
		}
		return DataResult.success(1);
	}

	/**
	 * 
	 * @param rwjbxxInfo
	 * @param pjjg
	 * @param bz
	 * @param kfzl
	 * @param fwzl
	 * @param xysd
	 * @param log
	 * @param flag
	 *            1验收 2售后
	 * @throws ServiceException
	 */
	private void xqfAccpetOrSaleEn(RwjbxxInfo rwjbxxInfo, String pjjg, String bz, Float kfzl, Float fwzl, Float xysd,
			CustomOperateLog log, int flag) throws ServiceException {
		String message = "验收";
		if (flag == 2) {
			message = "售后";
		}
		Integer xmzt = RwztStateEnum.SHJSDQR.getCode();

		RwjbxxInfo saveRwjbxxInfo = new RwjbxxInfo();
		RwztbglsInfo rwztbglsInfo = new RwztbglsInfo();
		rwztbglsInfo.setCzrip(log.getCustomIp());
		rwztbglsInfo.setCzrxm(log.getXm());
		rwztbglsInfo.setCzsj(DateUtil.getCurrentDate());
		rwztbglsInfo.setShyy(bz);
		rwztbglsInfo.setWid(StringUtil.getUuId());
		rwztbglsInfo.setXmid(rwjbxxInfo.getWid());
		rwztbglsInfo.setXqfid(rwjbxxInfo.getXqfid());
		saveRwjbxxInfo.setWid(rwjbxxInfo.getWid());
		if("系统自动验收".equals(bz)){
			saveXqfxyjfxx(rwjbxxInfo, "1", 0, true, log);
		}else{
			saveXqfxyjfxx(rwjbxxInfo, "1", 0, false, log);
		}
		if (YesNoEnum.YES.getCode().equals(pjjg)) {
			boolean needSh = checkNeedSh(rwjbxxInfo);
			if (flag == 1) {
				if (YesNoEnum.YES.getCode().equals(pjjg)) {
					if (needSh) {
						xmzt = RwztStateEnum.SHJSDQR.getCode();
						//保存开发者验收得分
						saveKfzxyjfxx(rwjbxxInfo, xmzt, "1", pjjg, log);
					} else {
						xmzt = RwztStateEnum.DPJ.getCode();
						//保存开发者验收得分
						saveKfzxyjfxx(rwjbxxInfo, xmzt, "1", pjjg, log);
					}

				} else {
					xmzt = RwztStateEnum.YSBTG.getCode();
					saveKfzxyjfxx(rwjbxxInfo, xmzt, "1", null, log);
				}
			} else {
				if (YesNoEnum.YES.getCode().equals(pjjg)) {
					xmzt = RwztStateEnum.DPJ.getCode();
					//保存开发者验收得分
					saveKfzxyjfxx(rwjbxxInfo, xmzt, "1", pjjg, log);
				} else {
					xmzt = RwztStateEnum.SHJSBTG.getCode();
					saveKfzxyjfxx(rwjbxxInfo, xmzt, "1", null, log);
				}
			}
			saveRwjbxxInfo.setXmzt(xmzt);
			rwztbglsInfo.setZt(StringUtil.toShort(xmzt));
			this.xqfDoPj(rwjbxxInfo, bz, kfzl, fwzl, xysd, log);

			DataResult<KfzxxInfo> kfzxxInfoDatas = kfzxxInfoService.selectByPrimaryKey(rwjbxxInfo.getKfzid(), log);
			DataResult<XqfxxInfo> xqfxxInfoDatas = xqfxxInfoService.selectByPrimaryKey(rwjbxxInfo.getXqfid(), log);

			XtcsbInfoExtend queryXtcsbInfoExtend1 = new XtcsbInfoExtend();
			XtcsbInfoExtend queryXtcsbInfoExtend2 = new XtcsbInfoExtend();
			if (NwbEnum.NB.getCode().equals(kfzxxInfoDatas.getDatas().getKfzlb())) {// 内部开发者
				queryXtcsbInfoExtend1.setCsmc(XtcsEnum.NBKFZ_XMGL_XMYSJDFKBL.getCode());
				queryXtcsbInfoExtend2.setCsmc(XtcsEnum.NBKFZ_XMGL_YYPTYJSQBL.getCode());
			} else {
				queryXtcsbInfoExtend1.setCsmc(XtcsEnum.WBKFZ_XMGL_XMYSJDFKBL.getCode());
				queryXtcsbInfoExtend2.setCsmc(XtcsEnum.WBKFZ_XMGL_YYPTYJSQBL.getCode());
			}
			DataResult<List<XtcsbInfoExtend>> xmyssqblDatas = xtcsbInfoService.selectByCondition(queryXtcsbInfoExtend1);
			DataResult<List<XtcsbInfoExtend>> yyptsqblDatas = xtcsbInfoService.selectByCondition(queryXtcsbInfoExtend2);

			// 需求方账户明细
			saveYsXqfzhmxInfo(needSh, rwjbxxInfo, xmyssqblDatas.getDatas().get(0), xqfxxInfoDatas.getDatas(), log, flag,
					message);
			// 开发者账户明细
			saveYsKfzzhmxInfo(needSh, rwjbxxInfo, xmyssqblDatas.getDatas().get(0), yyptsqblDatas.getDatas().get(0),
					kfzxxInfoDatas.getDatas(), log, flag, message);

		} else if (YesNoEnum.NO.getCode().equals(pjjg)) {
			if (StringUtil.toStr(bz).equals("")) {
				throw new ServiceException(message + "不通过理由不能为空，请重新确认！");
			}
			if (flag == 1) {
				xmzt = RwztStateEnum.YSBTG.getCode();
				saveKfzxyjfxx(rwjbxxInfo, xmzt, "1", null, log);
			} else {
				xmzt = RwztStateEnum.SHJSBTG.getCode();
				saveKfzxyjfxx(rwjbxxInfo, xmzt, "1", null, log);
			}
			saveRwjbxxInfo.setXmzt(xmzt);
			rwztbglsInfo.setZt(StringUtil.toShort(xmzt));
		}
		this.updateByPrimaryKeySelective(saveRwjbxxInfo);
		this.rwztbglsInfoService.insertSelective(rwztbglsInfo, log);
	}

	/**
	 * 判断是否需要售后
	 * 
	 * @param rwjbxxInfo
	 * @return
	 */
	@Override
	public boolean checkNeedSh(RwjbxxInfo rwjbxxInfo) {
		Map<String, Object> rwxsMap = dictionaryService.selectRwxsByBm(rwjbxxInfo.getRwxs());
		boolean needSh = false;
		if (StringUtil.toStr(rwxsMap.get("SFXQRWLX")).equals(YesNoEnum.YES.getCode())) {
			needSh = true;
		}
		if (needSh) {
			if (RwxqlxEnum.BUG.getCode().equals(StringUtil.toStr(rwjbxxInfo.getRwlx()))) {// BUG类不需要售后
				needSh = false;
			}
		}
		if (needSh) {
			XtcsbInfoExtend queryXtcsbInfoExtend = new XtcsbInfoExtend();
			queryXtcsbInfoExtend.setCsmc(XtcsEnum.NOT_NEED_SH.getCode());
			DataResult<List<XtcsbInfoExtend>> notNeedShDatas = xtcsbInfoService.selectByCondition(queryXtcsbInfoExtend);
			if (CommonUtil.isNotEmptyList(notNeedShDatas.getDatas())) {
				if (rwjbxxInfo.getXmysje() <= StringUtil.toLong(notNeedShDatas.getDatas().get(0).getCsz())) {
					needSh = false;
				}
			}
		}
		return needSh;
	}

	/**
	 * 保存需求方账户明细
	 * 
	 * @param needSh
	 * @param rwjbxxInfo
	 * @param xmyssqbl
	 * @param xqfxxInfo
	 * @param log
	 * @throws ServiceException
	 */
	private void saveYsXqfzhmxInfo(boolean needSh, RwjbxxInfo rwjbxxInfo, XtcsbInfoExtend xmyssqbl, XqfxxInfo xqfxxInfo,
			CustomOperateLog log, int flag, String message) throws ServiceException {
		XqfzhmxInfo xqfzhmxInfo = new XqfzhmxInfo();
		BigDecimal je = DecimalUtil.toDecimal(StringUtil.toStr(rwjbxxInfo.getZbje()));// 招标金额
		BigDecimal ptsqje = DecimalUtil.toDecimal(StringUtil.toStr(rwjbxxInfo.getJjbzje()));// 平台收取金额
		BigDecimal ysje = je;

		if (needSh) {
			if (flag == 1) {
				ysje = DecimalUtil.div(DecimalUtil.mul(ysje, DecimalUtil.toDecimal(xmyssqbl.getCsz())),
						DecimalUtil.toDecimal("100"));
				ptsqje = DecimalUtil.div(DecimalUtil.mul(ptsqje, DecimalUtil.toDecimal(xmyssqbl.getCsz())),
						DecimalUtil.toDecimal("100"));
			} else {
				ysje = DecimalUtil.sub(ysje, DecimalUtil.div(
						DecimalUtil.mul(ysje, DecimalUtil.toDecimal(xmyssqbl.getCsz())), DecimalUtil.toDecimal("100")));

				ptsqje = DecimalUtil.sub(ptsqje,
						DecimalUtil.div(DecimalUtil.mul(ptsqje, DecimalUtil.toDecimal(xmyssqbl.getCsz())),
								DecimalUtil.toDecimal("100")));
			}

		}
		BigDecimal bcdjje = DecimalUtil.add(ysje, ptsqje);
		YyfzhszmxInfo yyfzhszmxInfo = new YyfzhszmxInfo();
		DataResult<List<YyfzhxxInfo>> yyfzhxxInfoDatas = yyfzhxxInfoService.selectByCondition(null, log);
//	BUG类型的需求费用由平台处
		if(RwxqlxEnum.XQ.getCode().equals(StringUtil.toStr(rwjbxxInfo.getRwlx()))){
			XqfxxInfo saveXqfxxInfo = new XqfxxInfo();
			saveXqfxxInfo.setWid(xqfxxInfo.getWid());
			saveXqfxxInfo.setZhdjye(DecimalUtil.sub(xqfxxInfo.getZhdjye(), bcdjje));
			saveXqfxxInfo.setVersion(xqfxxInfo.getVersion());

			// 更新需求方账户信息(防止出现脏数据)
			for (int i = 0; i < 4; i++) {
				Integer updateCount = xqfxxInfoService.updateByVersion(saveXqfxxInfo, log).getDatas();
				if (i == 3) {
					throw new ServiceException(message + "失败，请联系系统管理员！");
				}
				if (updateCount == null || updateCount == 0) {
					xqfxxInfo = xqfxxInfoService.selectByPrimaryKey(rwjbxxInfo.getXqfid(), log).getDatas();
					saveXqfxxInfo.setZhdjye(DecimalUtil.sub(xqfxxInfo.getZhdjye(), bcdjje));
					saveXqfxxInfo.setVersion(xqfxxInfo.getVersion());
					continue;
				} else {
					break;
				}
			}
			xqfzhmxInfo.setFysm("项目" + message + "支付" + DecimalUtil.add(ptsqje, ysje) + "元");
			xqfzhmxInfo.setId(StringUtil.getUuId());
			xqfzhmxInfo.setJe(DecimalUtil.add(ptsqje, ysje).floatValue());
			xqfzhmxInfo.setDjzje(saveXqfxxInfo.getZhdjye());
			xqfzhmxInfo.setZhye(DecimalUtil.changeNull(xqfxxInfo.getZhye()).floatValue());
			xqfzhmxInfo.setJyzt(StringUtil.toShort(JyztEnum.YZB.getCode()));
			xqfzhmxInfo.setXqfid(rwjbxxInfo.getXqfid());
			xqfzhmxInfo.setLyxmid(rwjbxxInfo.getWid());
			xqfzhmxInfo.setSzlbid(SzlbEnum.XMZF.getCode());
			xqfzhmxInfo.setSzsj(DateUtil.getCurrentDate());
			xqfzhmxInfo.setZdid(log.getUserId());
			xqfzhmxInfo.setZdip(log.getCustomIp());
			xqfzhmxInfo.setZzczrid(log.getUserId());
			xqfzhmxInfo.setZzczrip(log.getCustomIp());
			xqfzhmxInfoService.insertSelective(xqfzhmxInfo, log);
		}else{
			if (CommonUtil.isEmptyList(yyfzhxxInfoDatas.getDatas())) {
				throw new ServiceException("运营方账户不存在，请联系系统管理员！");
			}
			yyfzhszmxInfo.setFysm("项目验收平台收取加急等额外费用" + ptsqje + "元");
			yyfzhszmxInfo.setJe(StringUtil.toFloat(ysje));
			yyfzhszmxInfo.setJyzt(StringUtil.toShort(JyztEnum.YZB.getCode()));
			yyfzhszmxInfo.setLyxmid(rwjbxxInfo.getWid());
			yyfzhszmxInfo.setSzlbid(SzlbEnum.XMZF.getCode());
			yyfzhszmxInfo.setSzsj(DateUtil.getCurrentDate());
			yyfzhszmxInfo.setWid(StringUtil.getUuId());
			yyfzhszmxInfo.setYyfzhid(yyfzhxxInfoDatas.getDatas().get(0).getWid());
			yyfzhszmxInfo.setZdid(log.getUserId());
			yyfzhszmxInfo.setZdip(log.getCustomIp());
			yyfzhszmxInfo.setZfbzh(yyfzhxxInfoDatas.getDatas().get(0).getZfbzh());
			yyfzhszmxInfo.setZzczrid(log.getUserId());
			yyfzhszmxInfo.setZzczrip(DateUtil.getCurrentDate());
			yyfzhszmxInfoService.insertSelective(yyfzhszmxInfo, log);
		}
		
		
		if (StringUtil.toLong(rwjbxxInfo.getJjbzje()) > 0) {
			
			if (CommonUtil.isEmptyList(yyfzhxxInfoDatas.getDatas())) {
				throw new ServiceException("运营方账户不存在，请联系系统管理员！");
			}
			yyfzhszmxInfo.setFysm("项目验收平台收取加急等额外费用" + ptsqje + "元");
			yyfzhszmxInfo.setJe(StringUtil.toFloat(ptsqje));
			yyfzhszmxInfo.setJyzt(StringUtil.toShort(JyztEnum.YZB.getCode()));
			yyfzhszmxInfo.setLyxmid(rwjbxxInfo.getWid());
			yyfzhszmxInfo.setSzlbid(SzlbEnum.XMSR.getCode());
			yyfzhszmxInfo.setSzsj(DateUtil.getCurrentDate());
			yyfzhszmxInfo.setWid(StringUtil.getUuId());
			yyfzhszmxInfo.setYyfzhid(yyfzhxxInfoDatas.getDatas().get(0).getWid());
			yyfzhszmxInfo.setZdid(log.getUserId());
			yyfzhszmxInfo.setZdip(log.getCustomIp());
			yyfzhszmxInfo.setZfbzh(yyfzhxxInfoDatas.getDatas().get(0).getZfbzh());
			yyfzhszmxInfo.setZzczrid(log.getUserId());
			yyfzhszmxInfo.setZzczrip(DateUtil.getCurrentDate());
			yyfzhszmxInfoService.insertSelective(yyfzhszmxInfo, log);
		}
		

	}

	/**
	 * 保存验收时候的开发者账户明细
	 */
	private void saveYsKfzzhmxInfo(boolean needSh, RwjbxxInfo rwjbxxInfo, XtcsbInfoExtend xmyssqbl,
			XtcsbInfoExtend ptsqbl, KfzxxInfo kfzxxInfo, CustomOperateLog log, int flag, String message)
					throws ServiceException {

		BigDecimal je = DecimalUtil.add(DecimalUtil.toDecimal(StringUtil.toStr(rwjbxxInfo.getZbje())),
				DecimalUtil.toDecimal(StringUtil.toStr(rwjbxxInfo.getPtbzje())));
		BigDecimal ptsqje = DecimalUtil.toDecimal("0");
		BigDecimal ysje = je;
		// 平台补助金额
		BigDecimal ptbzje = DecimalUtil.toDecimal(StringUtil.toStr(rwjbxxInfo.getPtbzje()));
		if (needSh) {
			// 验收金额
			if (flag == 1) {
				ysje = DecimalUtil.div(DecimalUtil.mul(je, DecimalUtil.toDecimal(xmyssqbl.getCsz())),
						DecimalUtil.toDecimal("100"));
				ptbzje = DecimalUtil.div(DecimalUtil.mul(ptbzje, DecimalUtil.toDecimal(xmyssqbl.getCsz())),
						DecimalUtil.toDecimal("100"));
			} else {
				ysje = DecimalUtil.sub(je, DecimalUtil.div(
						DecimalUtil.mul(je, DecimalUtil.toDecimal(xmyssqbl.getCsz())), DecimalUtil.toDecimal("100")));
				ptbzje = DecimalUtil.sub(ptbzje,
						DecimalUtil.div(DecimalUtil.mul(ptbzje, DecimalUtil.toDecimal(xmyssqbl.getCsz())),
								DecimalUtil.toDecimal("100")));
			}
		}
		DataResult<List<YyfzhxxInfo>> yyfzhxxInfoDatas = yyfzhxxInfoService.selectByCondition(null, log);
		if (CommonUtil.isEmptyList(yyfzhxxInfoDatas.getDatas())) {
			throw new ServiceException("运营方账户不存在，请联系系统管理员！");
		}
		if (rwjbxxInfo.getPtbzje() != null && rwjbxxInfo.getPtbzje() > 0) {
			YyfzhszmxInfo yyfzhszmxInfo = new YyfzhszmxInfo();
			yyfzhszmxInfo.setFysm("项目" + message + "平台支出补助费用" + ptbzje + "元");
			yyfzhszmxInfo.setJe(ptsqje.floatValue());
			yyfzhszmxInfo.setJyzt(StringUtil.toShort(JyztEnum.YZB.getCode()));
			yyfzhszmxInfo.setLyxmid(rwjbxxInfo.getWid());
			yyfzhszmxInfo.setSzlbid(SzlbEnum.XMZF.getCode());
			yyfzhszmxInfo.setSzsj(DateUtil.getCurrentDate());
			yyfzhszmxInfo.setWid(StringUtil.getUuId());
			yyfzhszmxInfo.setYyfzhid(yyfzhxxInfoDatas.getDatas().get(0).getWid());
			yyfzhszmxInfo.setZdid(log.getUserId());
			yyfzhszmxInfo.setZdip(log.getCustomIp());
			yyfzhszmxInfo.setZfbzh(yyfzhxxInfoDatas.getDatas().get(0).getZfbzh());
			yyfzhszmxInfo.setZzczrid(log.getUserId());
			yyfzhszmxInfo.setZzczrip(DateUtil.getCurrentDate());
			yyfzhszmxInfoService.insertSelective(yyfzhszmxInfo, log);
		}
		if (StringUtil.toInt(ptsqbl.getCsz()) > 0) {
			ptsqje = DecimalUtil.div(DecimalUtil.mul(ysje, DecimalUtil.toDecimal(ptsqbl.getCsz())),
					DecimalUtil.toDecimal("100"));

			// ysje=DecimalUtil.sub(ysje, ptsqje);

			// 运营账户收入
			YyfzhszmxInfo yyfzhszmxInfo = new YyfzhszmxInfo();

			yyfzhszmxInfo.setFysm("项目" + message + "平台收取费用" + ptsqje + "元");
			yyfzhszmxInfo.setJe(ptsqje.floatValue());
			yyfzhszmxInfo.setJyzt(StringUtil.toShort(JyztEnum.YZB.getCode()));
			yyfzhszmxInfo.setLyxmid(rwjbxxInfo.getWid());
			yyfzhszmxInfo.setSzlbid(SzlbEnum.XMSR.getCode());
			yyfzhszmxInfo.setSzsj(DateUtil.getCurrentDate());
			yyfzhszmxInfo.setWid(StringUtil.getUuId());
			yyfzhszmxInfo.setYyfzhid(yyfzhxxInfoDatas.getDatas().get(0).getWid());
			yyfzhszmxInfo.setZdid(log.getUserId());
			yyfzhszmxInfo.setZdip(log.getCustomIp());
			yyfzhszmxInfo.setZfbzh(yyfzhxxInfoDatas.getDatas().get(0).getZfbzh());
			yyfzhszmxInfo.setZzczrid(log.getUserId());
			yyfzhszmxInfo.setZzczrip(DateUtil.getCurrentDate());
			yyfzhszmxInfoService.insertSelective(yyfzhszmxInfo, log);

		}

		// 获取任务拆分信息
		RwcfxxInfoExtend queryRwcfxxInfoExtend = new RwcfxxInfoExtend();
		queryRwcfxxInfoExtend.setRwid(rwjbxxInfo.getWid());
		List<RwcfxxInfoExtend> rwcfxxInfoDatas = rwcfxxInfoService
				.selectByCondition(ConditionUtil.createCondition(queryRwcfxxInfoExtend), log).getDatas();

		if (CommonUtil.isNotEmptyList(rwcfxxInfoDatas)) {
			XtcsbInfoExtend xtcsbInfoExtend = new XtcsbInfoExtend();
			xtcsbInfoExtend.setCsmc(XtcsEnum.RWCS_SQBL.getCode());
			XtcsbInfoExtend rwcsSqblDatas = xtcsbInfoService.selectByCondition(xtcsbInfoExtend).getDatas().get(0);
			for (RwcfxxInfoExtend rwcfxxInfoExtend : rwcfxxInfoDatas) {
				if (!rwcfxxInfoExtend.getKfzid().equals(kfzxxInfo.getWid())
						&& !RwcfxxZtEnum.YJJ.getCode().equals(StringUtil.toStr(rwcfxxInfoExtend.getRwzt()))) {
					KfzzhmxInfo kfzzhmxInfo = new KfzzhmxInfo();

					if (YesNoEnum.YES.getCode().equals(rwcfxxInfoExtend.getSfsqfy())) {
						BigDecimal grje = DecimalUtil.sub(rwcfxxInfoExtend.getRwjg(),
								DecimalUtil.div(
										DecimalUtil.mul(rwcfxxInfoExtend.getRwjg(),
												DecimalUtil.toDecimal(rwcsSqblDatas.getCsz())),
								DecimalUtil.toDecimal("100")));
						if (needSh) {
							// 验收金额
							if (flag == 1) {
								grje = DecimalUtil.div(DecimalUtil.mul(grje, DecimalUtil.toDecimal(xmyssqbl.getCsz())),
										DecimalUtil.toDecimal("100"));
							} else {
								grje = DecimalUtil.sub(grje,
										DecimalUtil.div(DecimalUtil.mul(grje, DecimalUtil.toDecimal(xmyssqbl.getCsz())),
												DecimalUtil.toDecimal("100")));
							}

						}
						ysje = DecimalUtil.sub(ysje, grje);
						BigDecimal ptsq_grje = DecimalUtil.toDecimal("0");
						if (StringUtil.toInt(ptsqbl.getCsz()) > 0) {
							ptsq_grje = DecimalUtil.div(DecimalUtil.mul(grje, DecimalUtil.toDecimal(ptsqbl.getCsz())),
									DecimalUtil.toDecimal("100"));
							grje = DecimalUtil.sub(grje, ptsq_grje);
							kfzzhmxInfo.setFysm("项目" + message + "费用收入,其中平台收取费用" + ptsq_grje + "元");
						} else {
							kfzzhmxInfo.setFysm("项目" + message + "费用收入");
						}
						KfzxxInfo newKfzxxInfo = kfzxxInfoService.selectByPrimaryKey(rwcfxxInfoExtend.getKfzid(), log)
								.getDatas();

						KfzxxInfo saveKfzxxInfo = new KfzxxInfo();
						saveKfzxxInfo.setWid(newKfzxxInfo.getWid());
						saveKfzxxInfo.setZhye(DecimalUtil.add(newKfzxxInfo.getZhye(), grje));
						saveKfzxxInfo.setVersion(newKfzxxInfo.getVersion());
						// 更新需求方账户信息(防止出现脏数据)
						for (int i = 0; i < 4; i++) {
							Integer updateCount = kfzxxInfoService.updateByVersion(saveKfzxxInfo, log).getDatas();
							if (i == 3) {
								throw new ServiceException(message + "失败，请联系系统管理员！");
							}
							if (updateCount == null || updateCount == 0) {
								newKfzxxInfo = kfzxxInfoService.selectByPrimaryKey(rwcfxxInfoExtend.getKfzid(), log)
										.getDatas();
								saveKfzxxInfo.setZhye(DecimalUtil.add(newKfzxxInfo.getZhye(), grje));
								saveKfzxxInfo.setVersion(newKfzxxInfo.getVersion());
								continue;
							} else {
								break;
							}
						}
						kfzzhmxInfo.setWid(StringUtil.getUuId());
						kfzzhmxInfo.setJe(grje);
						kfzzhmxInfo.setZhye(saveKfzxxInfo.getZhye());
						kfzzhmxInfo.setDjzje(DecimalUtil.changeNull(saveKfzxxInfo.getDjye()));
						kfzzhmxInfo.setJyzt(StringUtil.toShort(JyztEnum.YZB.getCode()));
						kfzzhmxInfo.setKfzid(newKfzxxInfo.getWid());
						kfzzhmxInfo.setLyxmid(rwjbxxInfo.getWid());
						kfzzhmxInfo.setSzlbid(SzlbEnum.XMSR.getCode());
						kfzzhmxInfo.setSzsj(DateUtil.getCurrentDate());
						kfzzhmxInfo.setWid(StringUtil.getUuId());
						kfzzhmxInfo.setXmzt(StringUtil.toStr(RwztStateEnum.SHJSDQR.getCode()));
						kfzzhmxInfo.setZdid(log.getUserId());
						kfzzhmxInfo.setZdip(log.getCustomIp());
						kfzzhmxInfo.setZzczrid(log.getUserId());
						kfzzhmxInfo.setZzczsj(DateUtil.getCurrentDate());
						kfzzhmxInfo.setZfbzh(newKfzxxInfo.getZfbzh());
						kfzzhmxInfoService.insertSelective(kfzzhmxInfo, log);

					}
				}

			}
		}
		KfzzhmxInfo kfzzhmxInfo = new KfzzhmxInfo();
		if (StringUtil.toInt(ptsqbl.getCsz()) > 0) {
			ptsqje = DecimalUtil.div(DecimalUtil.mul(ysje, DecimalUtil.toDecimal(ptsqbl.getCsz())),
					DecimalUtil.toDecimal("100"));
			ysje = DecimalUtil.sub(ysje, ptsqje);
			kfzzhmxInfo.setFysm("项目" + message + "费用收入,其中平台收取费用" + ptsqje + "元");
		} else {
			kfzzhmxInfo.setFysm("项目" + message + "费用收入");
		}

		KfzxxInfo saveKfzxxInfo = new KfzxxInfo();
		saveKfzxxInfo.setWid(kfzxxInfo.getWid());
		saveKfzxxInfo.setZhye(DecimalUtil.add(kfzxxInfo.getZhye(), ysje));
		saveKfzxxInfo.setVersion(kfzxxInfo.getVersion());
		// 更新需求方账户信息(防止出现脏数据)
		for (int i = 0; i < 4; i++) {
			Integer updateCount = kfzxxInfoService.updateByVersion(saveKfzxxInfo, log).getDatas();
			if (i == 3) {
				throw new ServiceException(message + "失败，请联系系统管理员！");
			}
			if (updateCount == null || updateCount == 0) {
				kfzxxInfo = kfzxxInfoService.selectByPrimaryKey(rwjbxxInfo.getKfzid(), log).getDatas();
				saveKfzxxInfo.setZhye(DecimalUtil.add(kfzxxInfo.getZhye(), ysje));
				saveKfzxxInfo.setVersion(kfzxxInfo.getVersion());
				continue;
			} else {
				break;
			}
		}
		kfzzhmxInfo.setWid(StringUtil.getUuId());
		kfzzhmxInfo.setJe(ysje);
		kfzzhmxInfo.setZhye(saveKfzxxInfo.getZhye());
		kfzzhmxInfo.setDjzje(DecimalUtil.changeNull(saveKfzxxInfo.getDjye()));
		kfzzhmxInfo.setJyzt(StringUtil.toShort(JyztEnum.YZB.getCode()));
		kfzzhmxInfo.setKfzid(rwjbxxInfo.getKfzid());
		kfzzhmxInfo.setLyxmid(rwjbxxInfo.getWid());
		kfzzhmxInfo.setSzlbid(SzlbEnum.XMSR.getCode());
		kfzzhmxInfo.setSzsj(DateUtil.getCurrentDate());
		kfzzhmxInfo.setWid(StringUtil.getUuId());
		kfzzhmxInfo.setXmzt(StringUtil.toStr(RwztStateEnum.SHJSDQR.getCode()));
		kfzzhmxInfo.setZdid(log.getUserId());
		kfzzhmxInfo.setZdip(log.getCustomIp());
		kfzzhmxInfo.setZzczrid(log.getUserId());
		kfzzhmxInfo.setZzczsj(DateUtil.getCurrentDate());
		kfzzhmxInfo.setZfbzh(kfzxxInfo.getZfbzh());
		kfzzhmxInfoService.insertSelective(kfzzhmxInfo, log);

	}

	private void saveKfzpjxxDetailInfo(List<KfzpjxxDetailInfo> kfzpjxxDetailInfos, RwjbxxInfo rwjbxxInfo,
			String pjClassify, float df, CustomOperateLog log) {
		KfzpjxxDetailInfo saveKfzpjxxDetailInfo = new KfzpjxxDetailInfo();
		saveKfzpjxxDetailInfo.setCpsj(DateUtil.getCurrentDate());
		saveKfzpjxxDetailInfo.setCpxqfid(rwjbxxInfo.getXqfid());
		saveKfzpjxxDetailInfo.setCpzip(log.getCustomIp());
		saveKfzpjxxDetailInfo.setKfzid(rwjbxxInfo.getKfzid());
		saveKfzpjxxDetailInfo.setSsxmid(rwjbxxInfo.getWid());
		saveKfzpjxxDetailInfo.setDf(df);
		saveKfzpjxxDetailInfo.setZbid(pjClassify);
		if (CommonUtil.isNotEmptyList(kfzpjxxDetailInfos)) {
			boolean find = false;
			for (int i = 0; i < kfzpjxxDetailInfos.size(); i++) {
				if (kfzpjxxDetailInfos.get(i).getZbid().equals(pjClassify)) {
					saveKfzpjxxDetailInfo.setWid(kfzpjxxDetailInfos.get(i).getWid());
					find = true;
					kfzpjxxDetailInfoService.updateByPrimaryKeySelective(saveKfzpjxxDetailInfo, log);
				}
			}
			if (!find) {
				saveKfzpjxxDetailInfo.setWid(StringUtil.getUuId());
				kfzpjxxDetailInfoService.insertSelective(saveKfzpjxxDetailInfo, log);
			}

		} else {
			saveKfzpjxxDetailInfo.setWid(StringUtil.getUuId());
			kfzpjxxDetailInfoService.insertSelective(saveKfzpjxxDetailInfo, log);
		}
	}

	@Override
	public DataResult<Map<String,BigDecimal>> selectSumMoney(QueryCondition<RwjbxxInfoExtend> record,CustomOperateLog log) throws ServiceException {
		return DataResult.success(rwjbxxInfoExtendMapper.selectSumMoney(record));
	}

	@Override
	public DataResult<Integer> publishingQuestion(String rwid, String rwmc, String xmgs, String jfrq,
			CustomOperateLog log) throws ServiceException {
		if (StringUtil.isEmpty(rwid)) {
			throw new ServiceException("rwid不能为空");
		}
		if (StringUtil.isEmpty(rwmc)) {
			throw new ServiceException("需求名称不能为空，请重新确认！");
		}
		if (StringUtil.isEmpty(xmgs)) {
			throw new ServiceException("需求描述不能为空，请重新确认！");
		}
		if (StringUtil.isEmpty(jfrq)) {
			throw new ServiceException("交付日期不能为空，请重新确认！");
		}
		long jfzq = DateUtil.getDaysBetween(DateUtil.getCurrentDateStr(), jfrq);
		if (jfzq < 0) {
			throw new ServiceException("交付日期日期不能小于当前日期，请重新确认！");
		}
		RwjbxxInfo rwjbxxInfo = this.selectByPrimaryKey(rwid).getDatas();
		if (rwjbxxInfo.getXmzt() < RwztStateEnum.KFZ.getCode() || rwjbxxInfo.getXmzt() == RwztStateEnum.YZZ.getCode()
				|| rwjbxxInfo.getXmzt() == RwztStateEnum.YGB.getCode()
				|| rwjbxxInfo.getXmzt() == RwztStateEnum.YSC.getCode()) {
			throw new ServiceException("该需求状态不满足发布问题的条件，请重新确认！");
		}
		if (!rwjbxxInfo.getXqfid().equals(log.getXqfId())) {
			throw new ServiceException("该需求不是您发布的需求，请重新确认！");
		}
		RwjbxxExtInfoExtend queryRwjbxxExtInfoExtend = new RwjbxxExtInfoExtend();
		queryRwjbxxExtInfoExtend.setRwid(rwid);
		List<RwjbxxExtInfoExtend> rwjbxxExtInfoDatas = rwjbxxExtInfoService
				.selectByCondition(ConditionUtil.createCondition(queryRwjbxxExtInfoExtend), log).getDatas();
		RwjbxxInfo saveRwjbxxInfo = new RwjbxxInfo();
		String newRwid = StringUtil.getUuId();
		saveRwjbxxInfo.setWid(newRwid);
		saveRwjbxxInfo.setRwmc(rwmc);
		saveRwjbxxInfo.setXmgs(xmgs);
		saveRwjbxxInfo.setJfrq(jfrq);
		saveRwjbxxInfo.setZbjzrq(DateUtil.parseDate(jfrq));
		String rwbh=sequenceInfoService.selectNewRwbh(DateUtil.format(DateUtil.getCurrentDate(), "yyyyMM"), log).getDatas();
		//saveRwjbxxInfo.setRwbh("123123");
		saveRwjbxxInfo.setRwbh(rwbh);
		saveRwjbxxInfo.setCjsj(DateUtil.getCurrentDate());
		saveRwjbxxInfo.setCjzip(log.getCustomIp());
		saveRwjbxxInfo.setRwxs(RwxsEnum.KF.getCode());
		saveRwjbxxInfo.setRwlx(StringUtil.toShort(RwxqlxEnum.BUG.getCode()));
		saveRwjbxxInfo.setSfjj(YesNoEnum.NO.getCode());
		saveRwjbxxInfo.setKfkj(rwjbxxInfo.getKfkj());
		saveRwjbxxInfo.setXmysje(0L);
		saveRwjbxxInfo.setYwxlb(rwjbxxInfo.getYwxlb());
		saveRwjbxxInfo.setXqfid(log.getXqfId());
		saveRwjbxxInfo.setFb(saveRwjbxxInfo.getFb());
		saveRwjbxxInfo
				.setZbzq(DateUtil.getDaysBetween(DateUtil.formatDate(rwjbxxInfo.getZbjzrq()), rwjbxxInfo.getJfrq()));
		saveRwjbxxInfo.setParentId(rwid);
		Map<String, Object> rwxsMap = dictionaryService.selectRwxsByBm(saveRwjbxxInfo.getRwxs());
		Integer xmzt = StringUtil.toInt(RwztStateEnum.CG.getCode());

		if (YesNoEnum.YES.getCode().equals(StringUtil.toStr(rwxsMap.get("SFXQRWLX")))) {
			xmzt = StringUtil.toInt(RwztStateEnum.DSH.getCode());

		} else {
			xmzt = StringUtil.toInt(RwztStateEnum.ZBZ.getCode());
		}
		saveRwjbxxInfo.setXmzt(xmzt);
		if (CommonUtil.isNotEmptyList(rwjbxxExtInfoDatas)) {
			RwjbxxExtInfo rwjbxxExtInfo = new RwjbxxExtInfo();
			rwjbxxExtInfo.setWid(StringUtil.getUuId());
			rwjbxxExtInfo.setMkbh(rwjbxxExtInfoDatas.get(0).getMkbh());
			rwjbxxExtInfo.setRwid(newRwid);
			rwjbxxExtInfo.setXmbh(rwjbxxExtInfoDatas.get(0).getXmbh());
			rwjbxxExtInfo.setYwxbm(rwjbxxExtInfoDatas.get(0).getYwxbm());
			rwjbxxExtInfoService.insertSelective(rwjbxxExtInfo, log);
		}
		this.insertSelective(saveRwjbxxInfo);
		// 任务状态变更日志
		RwztbglsInfo rwztbglsInfo = new RwztbglsInfo();
		rwztbglsInfo.setWid(StringUtil.getUuId());
		rwztbglsInfo.setCzrip(log.getCustomIp());
		rwztbglsInfo.setCzrxm(log.getXm());
		rwztbglsInfo.setCzsj(DateUtil.getCurrentDate());
		rwztbglsInfo.setShyy("发布问题");
		rwztbglsInfo.setXmid(newRwid);
		rwztbglsInfo.setXqfid(log.getXqfId());
		rwztbglsInfo.setZt(StringUtil.toShort(xmzt));
		rwztbglsInfoService.insertSelective(rwztbglsInfo, log);

		return DataResult.success(1);
	}

	@Override
	public DataResult<Integer> selectCountHasTb(String xqfid, Integer xmzt, CustomOperateLog log)
			throws ServiceException {
		return DataResult.success(rwjbxxInfoExtendMapper.selectCountHasTb(xqfid, xmzt));
	}

	@Override
	public DataResult<List<Map<String, Object>>> selectNewZbxx(PageInfo pageInfo, CustomOperateLog log)
			throws ServiceException {
		if (pageInfo != null) {
			Page<RwjbxxInfoExtend> page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
			List<Map<String, Object>> datas = rwjbxxInfoExtendMapper.selectNewZbxx();

			DataResult<List<Map<String, Object>>> dataResult = DataResult.success(datas);
			dataResult.setPageInfo(PageUtil.changePageInfo(page));
			return dataResult;
		} else {
			return DataResult.success(rwjbxxInfoExtendMapper.selectNewZbxx());

		}

	}

	@Override
	public DataResult<BigDecimal> selectSumZbjeByKfzid(String kfzid, CustomOperateLog log) throws ServiceException {
		return DataResult.success(rwjbxxInfoExtendMapper.selectSumZbjeByKfzid(kfzid));
	}

	@Override
	public DataResult<Integer> xqfDoPj(String rwid, String bz, Float kfzl, Float fwzl, Float xysd, CustomOperateLog log)
			throws ServiceException {
		if ("".equals(rwid)) {
			throw new ServiceException("rwid不能为空，请重新确认！");
		}
		RwjbxxInfo rwjbxxInfo = this.selectByPrimaryKey(rwid).getDatas();
		if (rwjbxxInfo == null) {
			throw new ServiceException("该需求信息已不存在，请重新确认！");
		}
		if (!rwjbxxInfo.getXqfid().equals(log.getXqfId())) {
			throw new ServiceException("该需求信息不是您发布的需求，请重新确认！");
		}
		if (rwjbxxInfo.getXmzt() != RwztStateEnum.DPJ.getCode()) {
			throw new ServiceException("该需求状态不在待评价中，请重新确认！");
		}
		this.xqfDoPj(rwjbxxInfo, bz, kfzl, fwzl, xysd, log);
		return DataResult.success(1);
	}

	private void xqfDoPj(RwjbxxInfo rwjbxxInfo, String bz, Float kfzl, Float fwzl, Float xysd, CustomOperateLog log) {
		// 获取需求方对开发者的评价明细
		if (kfzl == null || kfzl == 0) {
			throw new ServiceException("开发质量不能为空，请重新确认！");
		}
		if (fwzl == null || fwzl == 0) {
			throw new ServiceException("服务质量不能为空，请重新确认！");
		}
		if (xysd == null || xysd == 0) {
			throw new ServiceException("响应速度不能为空，请重新确认！");
		}
		KfzpjxxDetailInfo queryKfzpjxxDetailInfo = new KfzpjxxDetailInfo();
		queryKfzpjxxDetailInfo.setSsxmid(rwjbxxInfo.getWid());
		DataResult<List<KfzpjxxDetailInfo>> kfzpjxxDetailInfoDatas = kfzpjxxDetailInfoService
				.selectByCondition(ConditionUtil.createCondition(queryKfzpjxxDetailInfo), log);
		saveKfzpjxxDetailInfo(kfzpjxxDetailInfoDatas.getDatas(), rwjbxxInfo, PjClassifyEnum.KFZL.getCode(), kfzl, log);
		saveKfzpjxxDetailInfo(kfzpjxxDetailInfoDatas.getDatas(), rwjbxxInfo, PjClassifyEnum.FWZL.getCode(), fwzl, log);
		saveKfzpjxxDetailInfo(kfzpjxxDetailInfoDatas.getDatas(), rwjbxxInfo, PjClassifyEnum.XYSU.getCode(), xysd, log);
		// 获取需求方对开发者的评价结果
		KfzpjxxInfo queryKfzpjxxInfo = new KfzpjxxInfo();
		queryKfzpjxxInfo.setSsxmid(rwjbxxInfo.getWid());
		DataResult<List<KfzpjxxInfo>> kfzpjxxInfoDatas = kfzpjxxInfoService
				.selectByCondition(ConditionUtil.createCondition(queryKfzpjxxInfo), log);
		KfzpjxxInfo saveKfzpjxxInfo = new KfzpjxxInfo();
		saveKfzpjxxInfo.setCpsj(DateUtil.getCurrentDate());
		saveKfzpjxxInfo.setCpzip(log.getCustomIp());
		saveKfzpjxxInfo.setKfzid(rwjbxxInfo.getKfzid());
		saveKfzpjxxInfo.setCpxqfid(rwjbxxInfo.getXqfid());
		String pjjg = null;
		if ((kfzl + fwzl + xysd) >= 15) {
			pjjg = PjResultEnum.YX.getCode();
		} else if ((kfzl + fwzl + xysd) <= 3) {
			pjjg = PjResultEnum.CHA.getCode();
		} else {
			pjjg = PjResultEnum.LH.getCode();
		}
		saveKfzpjxxInfo.setPjjg(pjjg);
		saveKfzpjxxInfo.setPjnr(bz);
		saveKfzpjxxInfo.setSsxmid(rwjbxxInfo.getWid());
		saveKfzpjxxInfo.setKfzid(rwjbxxInfo.getKfzid());
		if (CommonUtil.isNotEmptyList(kfzpjxxInfoDatas.getDatas())) {
			saveKfzpjxxInfo.setWid(kfzpjxxInfoDatas.getDatas().get(0).getWid());
			kfzpjxxInfoService.updateByPrimaryKeySelective(saveKfzpjxxInfo, log);
		} else {
			saveKfzpjxxInfo.setWid(StringUtil.getUuId());
			kfzpjxxInfoService.insertSelective(saveKfzpjxxInfo, log);
		}
//		saveKfzxyjfxx(rwjbxxInfo, 0, "4", pjjg, log);
	}

	@Override
	public DataResult<Integer> kfzDoPj(String rwid, Integer score, String content, CustomOperateLog log)
			throws ServiceException {
		RwjbxxInfo rwjbxxInfo = this.selectByPrimaryKey(rwid).getDatas();
		if (rwjbxxInfo == null) {
			throw new ServiceException("该需求信息已不存在，请重新确认！");
		}
		if (!rwjbxxInfo.getKfzid().equals(log.getKfzId())) {
			throw new ServiceException("该需求信息不是您投标的需求，请重新确认！");
		}
		if (RwztStateEnum.DPJ.getCode() != rwjbxxInfo.getXmzt()) {
			throw new ServiceException("该需求信息不在待评价中，请重新确认！");
		}
		this.kfzDoPj(rwjbxxInfo, score, content, log);
		return DataResult.success(1);
	}

	private void kfzDoPj(RwjbxxInfo rwjbxxInfo, Integer score, String content, CustomOperateLog log)
			throws ServiceException {
		if (StringUtil.isEmpty(content)) {
			throw new ServiceException("请输入评价内容！");
		}
		score = StringUtil.toInt(score, 0);
		if (score > ConstantsUtil.MAX_SCORE) {
			score = ConstantsUtil.MAX_SCORE;
		}
		// 获取需求方对开发者的评价结果

		XqfbpjgInfoExtend queryXqfbpjgInfoExtend = new XqfbpjgInfoExtend();
		queryXqfbpjgInfoExtend.setSsxmid(rwjbxxInfo.getWid());
		DataResult<List<XqfbpjgInfoExtend>> xqfbpjgInfoDatas = xqfbpjgInfoService
				.selectByCondition(ConditionUtil.createCondition(queryXqfbpjgInfoExtend), log);
		XqfbpjgInfo saveXqfbpjgInfo = new XqfbpjgInfo();
		saveXqfbpjgInfo.setCpsj(DateUtil.getCurrentDate());
		saveXqfbpjgInfo.setCpzip(log.getCustomIp());
		saveXqfbpjgInfo.setKfzid(rwjbxxInfo.getKfzid());
		saveXqfbpjgInfo.setXqfid(rwjbxxInfo.getXqfid());
		saveXqfbpjgInfo.setPjjg(StringUtil.toStr(score));
		saveXqfbpjgInfo.setPjnr(content);
		saveXqfbpjgInfo.setSsxmid(rwjbxxInfo.getWid());

		if (CommonUtil.isNotEmptyList(xqfbpjgInfoDatas.getDatas())) {
			saveXqfbpjgInfo.setWid(xqfbpjgInfoDatas.getDatas().get(0).getWid());
			xqfbpjgInfoService.updateByPrimaryKeySelective(saveXqfbpjgInfo, log);
		} else {
			saveXqfbpjgInfo.setWid(StringUtil.getUuId());
			xqfbpjgInfoService.insertSelective(saveXqfbpjgInfo, log);
		}
		//保存需求方评价得分信息
//		saveXqfxyjfxx(rwjbxxInfo, "2", score, false, log);
	}

	@Override
	public DataResult<Integer> outTb(String rwid, CustomOperateLog log) throws ServiceException {
		RwjbxxInfo rwjbxxInfo = this.selectByPrimaryKey(rwid).getDatas();
		if (rwjbxxInfo == null) {
			throw new ServiceException("该需求已不存在，请重新确认！");
		}
		if (RwztStateEnum.ZBZ.getCode() != StringUtil.toInt(rwjbxxInfo.getXmzt())) {
			throw new ServiceException("该需求已不在招标中，请重新确认！");
		}
		RwtbxxInfo deleteRwtbxxInfo = new RwtbxxInfo();
		deleteRwtbxxInfo.setKfzid(log.getKfzId());
		deleteRwtbxxInfo.setRwid(rwid);
		return rwtbxxInfoService.deleteByCondition(deleteRwtbxxInfo, log);

	}

	/**
	 * 检测是否可结束验收/售后
	 * 
	 * @param wid
	 * @return
	 */
	@Override
	public DataResult<Boolean> checkAccept(String wid, CustomOperateLog log) throws ServiceException {
		RwjbxxInfo rwjbxxInfo = this.selectByPrimaryKey(wid).getDatas();
		RwjbxxInfoExtend queryRwjbxxInfoExtend = new RwjbxxInfoExtend();
		queryRwjbxxInfoExtend.setParentId(wid);
		List<RwjbxxInfoExtend> datas = this.selectByCondition(ConditionUtil.createCondition(queryRwjbxxInfoExtend), log)
				.getDatas();
		;
		if (CommonUtil.isNotEmptyList(datas)) {
			for (RwjbxxInfoExtend rwjbxx : datas) {
				if (RwztStateEnum.DPJ.getCode() != rwjbxx.getXmzt()
						&& rwjbxxInfo.getKfzid().equals(rwjbxx.getKfzid())) {
					return DataResult.success(false);
				}
			}
		}
		return DataResult.success(true);
	}
	
	
	
	
	/**
	 * 保存开发者信誉积分信息
	 * 
	 * @author dell
	 * @param rwjbxxInfo 任务基本信息
	 * @param flag       标志 1--任务操作积分；2--延期得分；3--投诉得分;4--评价得分
	 * @param pjjg        评价结果
	 * @return
	 * @throws ServiceException
	 * @since JDK 1.6
	 */
	@Override
	public DataResult<Integer> saveKfzxyjfxx(RwjbxxInfo rwjbxxInfo, int xmzt, String flag, String pjjg, CustomOperateLog log) throws ServiceException {
		String rwid = rwjbxxInfo.getWid();
		String kfzid = rwjbxxInfo.getKfzid();
		String rwlx = StringUtil.toStr(rwjbxxInfo.getRwlx());
		Integer count = 0;
		Integer oldScore = 0;
		String xyjfWid = null;
		KfzxyjfInfoExtend queryKfzxyjfInfoExtend = new KfzxyjfInfoExtend();
		queryKfzxyjfInfoExtend.setKfzid(kfzid);
		List<KfzxyjfInfoExtend> kfzxyjfInfoLst = kfzxyjfInfoExtendMapper
				.selectByCondition(ConditionUtil.createCondition(queryKfzxyjfInfoExtend));
		if(CommonUtil.isNotEmptyList(kfzxyjfInfoLst)){
			oldScore = StringUtil.toInt(kfzxyjfInfoLst.get(0).getScore());
			xyjfWid = StringUtil.toStr(kfzxyjfInfoLst.get(0).getWid());
		}
		if("1".equals(flag)){//任务操作得分
			if(xmzt == 9){
				count = saveKfzYsxyjf(rwid, kfzid, false, rwlx, oldScore, xyjfWid);
			}else if(xmzt == 11){
				count = saveKfzYsxyjf(rwid, kfzid, true, rwlx, oldScore, xyjfWid);
			}else if(xmzt == 12){
				count = saveKfzShxyjf(rwid, kfzid, false, oldScore, xyjfWid);
			}else if(xmzt == 13){
				count = saveKfzShxyjf(rwid, kfzid, true, oldScore, xyjfWid);
			}
		}else if("2".equals(flag)){//延期得分
			count = saveKfzyqdf(rwid, kfzid, oldScore, xyjfWid);
		}else if("3".equals(flag)){//投诉得分
			count = saveKfztsdf(rwid, kfzid, oldScore, xyjfWid);
		}else if("4".equals(flag)){//评价得分
			count = saveKfzPjdf(rwid, kfzid, pjjg, oldScore, xyjfWid);
		}
		return DataResult.success(count);
	}
	
	/**
	 * 保存验收得分信息
	 * @author dell
	 * @param rwid  任务ID
	 * @param kfzid  开发者ID
	 * @param pass  是否通过
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveKfzYsxyjf(String rwid, String kfzid, boolean pass, String rwlx, Integer oldScore, String xyjfWid){
		//查询开发者的信誉积分信息
		Integer xyjf = 0; 
		Integer xyjfxq = 0;
		//获取该任务之前验收不通过记录
		RwztbglsInfoExtend queryRwztbglsInfoExtend = new RwztbglsInfoExtend();
		queryRwztbglsInfoExtend.setXmid(rwid);
		queryRwztbglsInfoExtend.setZt(StringUtil.toShort(RwztStateEnum.YSBTG.getCode()));
		List<RwztbglsInfoExtend> ztbglsLst = rwztbglsInfoExtendMapper
				.selectByCondition(ConditionUtil.createCondition(queryRwztbglsInfoExtend));
		if(CommonUtil.isNotEmptyList(ztbglsLst)){//之前有验收不通过记录
			if(!pass){//验收通过不计分；验收不通过-5分
				xyjfxq = saveKfzxyjfxq(kfzid, rwid, KfzxyjflxEnum.DCYSBTG.getCode(), KfzxyjflxEnum.DCYSBTG.getName(),
						KfzxyjflxEnum.DCYSBTG.getScore(), oldScore);
				xyjf = saveOrUpdateKfzxyjf(kfzid, KfzxyjflxEnum.DCYSBTG.getScore(), oldScore, xyjfWid);
			}
		}else{//之前未验收不通过
			if(pass){//验收通过+1分
				xyjfxq = saveKfzxyjfxq(kfzid, rwid, KfzxyjflxEnum.DYCYSTG.getCode(), KfzxyjflxEnum.DYCYSTG.getName(),
						KfzxyjflxEnum.DYCYSTG.getScore(), oldScore);
				xyjf = saveOrUpdateKfzxyjf(kfzid, KfzxyjflxEnum.DYCYSTG.getScore(), oldScore, xyjfWid);
				if(RwxqlxEnum.BUG.getCode().equals(rwlx)){//判断是否是BUG，如果是BUG则计算BUG得分
					xyjf = saveKfzBugdf(rwid);
				}
			}else{//验收不通过-2分
				xyjfxq = saveKfzxyjfxq(kfzid, rwid, KfzxyjflxEnum.DYCYSBTG.getCode(), KfzxyjflxEnum.DYCYSBTG.getName(),
						KfzxyjflxEnum.DYCYSBTG.getScore(), oldScore);
				xyjf = saveOrUpdateKfzxyjf(kfzid, KfzxyjflxEnum.DYCYSBTG.getScore(), oldScore, xyjfWid);
			}
		}
		return xyjf + xyjfxq;
	}
	
	/**
	 * 保存售后得分信息
	 * @author dell
	 * @param rwid  任务ID
	 * @param kfzid  开发者ID
	 * @param pass  是否通过
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveKfzShxyjf(String rwid, String kfzid, boolean pass, Integer oldScore, String xyjfWid){
		//查询开发者的信誉积分信息
		Integer xyjf = 0; 
		Integer xyjfxq = 0;
		//获取该任务之前售后不通过记录
		RwztbglsInfoExtend queryRwztbglsInfoExtend = new RwztbglsInfoExtend();
		queryRwztbglsInfoExtend.setXmid(rwid);
		queryRwztbglsInfoExtend.setZt(StringUtil.toShort(RwztStateEnum.SHJSBTG.getCode()));
		List<RwztbglsInfoExtend> ztbglsLst = rwztbglsInfoExtendMapper
				.selectByCondition(ConditionUtil.createCondition(queryRwztbglsInfoExtend));
		if(CommonUtil.isNotEmptyList(ztbglsLst)){//之前有售后不通过记录
			if(!pass){//售后通过不计分；售后不通过-5分
				xyjfxq = saveKfzxyjfxq(kfzid, rwid, KfzxyjflxEnum.DCSHBTG.getCode(), KfzxyjflxEnum.DCSHBTG.getName(),
						KfzxyjflxEnum.DCSHBTG.getScore(), oldScore);
				xyjf = saveOrUpdateKfzxyjf(kfzid, KfzxyjflxEnum.DCSHBTG.getScore(), oldScore, xyjfWid);
			}
		}else{//之前未售后不通过
			if(pass){//售后通过+1分
				xyjfxq = saveKfzxyjfxq(kfzid, rwid, KfzxyjflxEnum.DYCSHTG.getCode(), KfzxyjflxEnum.DYCSHTG.getName(),
						KfzxyjflxEnum.DYCSHTG.getScore(), oldScore);
				xyjf = saveOrUpdateKfzxyjf(kfzid, KfzxyjflxEnum.DYCSHTG.getScore(), oldScore, xyjfWid);
			}else{//售后不通过-2分
				xyjfxq = saveKfzxyjfxq(kfzid, rwid, KfzxyjflxEnum.DYCSHBTG.getCode(), KfzxyjflxEnum.DYCSHBTG.getName(),
						KfzxyjflxEnum.DYCSHBTG.getScore(), oldScore);
				xyjf = saveOrUpdateKfzxyjf(kfzid, KfzxyjflxEnum.DYCSHBTG.getScore(), oldScore, xyjfWid);
			}
		}
		return xyjf + xyjfxq;
	}
	
	/**
	 * 新建或更新开发者信誉积分信息
	 * @author dell
	 * @param kfzid   开发者ID
	 * @param score   得分
	 * @param oldScore 原来总分
	 * @param xyjfWid 信誉积分WID
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveOrUpdateKfzxyjf(String kfzid, int score, int oldScore, String xyjfWid){
		KfzxyjfInfo kfzxyjfInfo = new KfzxyjfInfo();
		kfzxyjfInfo.setCjsj(DateUtil.getCurrentDateTimeStr());
		kfzxyjfInfo.setKfzid(kfzid);
		kfzxyjfInfo.setVersion(new BigDecimal(0));
		int newScore = score + oldScore;
		if(newScore >= 100){
			newScore = 100;
		}
		kfzxyjfInfo.setScore(new BigDecimal(newScore));
		if(StringUtil.isEmpty(xyjfWid)){
			kfzxyjfInfo.setWid(StringUtil.getUuId());
			kfzxyjfInfo.setIntegral(new BigDecimal(0));
			return kfzxyjfInfoMapper.insertSelective(kfzxyjfInfo);
		}else{
			kfzxyjfInfo.setWid(xyjfWid);
			return kfzxyjfInfoMapper.updateByPrimaryKeySelective(kfzxyjfInfo);
		}
	}
	
	/**
	 * 保存开发者信誉积分详情
	 * @author dell
	 * @param kfzid  开发者ID
	 * @param rwid   任务ID
	 * @param classify  得分类型
	 * @param content   备注说明
	 * @param score     得分
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveKfzxyjfxq(String kfzid, String rwid, String classify, String content,
			int score, int oldScore) {
		// 获取信誉积分详情信息
		KfzxyjfxqInfo insertKfzxyjfxqInfo = new KfzxyjfxqInfo();
		insertKfzxyjfxqInfo.setWid(StringUtil.getUuId());
		insertKfzxyjfxqInfo.setKfzid(kfzid);
		insertKfzxyjfxqInfo.setRwid(rwid);
		insertKfzxyjfxqInfo.setScoreClassify(classify);
		insertKfzxyjfxqInfo.setScore(new BigDecimal(score));
		insertKfzxyjfxqInfo.setContent(content);
		insertKfzxyjfxqInfo.setTotalScore(new BigDecimal(score + oldScore));
		insertKfzxyjfxqInfo.setCjsj(DateUtil.getCurrentDateTimeStr());
		return kfzxyjfxqInfoMapper.insertSelective(insertKfzxyjfxqInfo);
	}
	
	/**
	 * 计算BUG得分
	 * @author dell
	 * @param rwid
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveKfzBugdf(String rwid){
		String kfzid = null;
		int score = 0;
		String xyjfWid = null;
		Integer xyjf = 0; 
		Integer xyjfxq = 0;
		BugZrrInfoExtend queryBugZrrInfoExtend = new BugZrrInfoExtend();
		queryBugZrrInfoExtend.setRwid(rwid);
		List<BugZrrInfoExtend> bugzrrLst = bugZrrInfoExtendMapper
				.selectByCondition(ConditionUtil.createCondition(queryBugZrrInfoExtend));
		if(CommonUtil.isNotEmptyList(bugzrrLst)){
			BugZrrInfoExtend bugZrrInfoExtend = bugzrrLst.get(0);
			kfzid = bugZrrInfoExtend.getKfzid();
			if("-999999999".equals(kfzid)){
				String bgzrrgh = bugZrrInfoExtend.getBgzrrgh();
				KfzxxInfoExtend queryKfzxxInfoExtend = new KfzxxInfoExtend();
				queryKfzxxInfoExtend.setGh(bgzrrgh);
				List<KfzxxInfoExtend> kfzLst = kfzxxInfoExtendMapper
						.selectByCondition(ConditionUtil.createCondition(queryKfzxxInfoExtend));
				if(CommonUtil.isNotEmptyList(kfzLst)){
					KfzxxInfoExtend kfzxxInfoExtend = kfzLst.get(0);
					kfzid = kfzxxInfoExtend.getWid();
				}
			}
			if(!StringUtil.isEmpty(kfzid)){
				KfzxyjfInfoExtend queryKfzxyjfInfoExtend = new KfzxyjfInfoExtend();
				queryKfzxyjfInfoExtend.setKfzid(kfzid);
				List<KfzxyjfInfoExtend> kfzxyjfInfoLst = kfzxyjfInfoExtendMapper
						.selectByCondition(ConditionUtil.createCondition(queryKfzxyjfInfoExtend));
				if(CommonUtil.isNotEmptyList(kfzxyjfInfoLst)){
					score = StringUtil.toInt(kfzxyjfInfoLst.get(0).getScore());
					xyjfWid = StringUtil.toStr(kfzxyjfInfoLst.get(0).getWid());
				}
				xyjfxq = saveKfzxyjfxq(kfzid, rwid, KfzxyjflxEnum.BUGDF.getCode(), KfzxyjflxEnum.BUGDF.getName(),
						KfzxyjflxEnum.BUGDF.getScore(), score);
				xyjf = saveOrUpdateKfzxyjf(kfzid, KfzxyjflxEnum.BUGDF.getScore(), score, xyjfWid);
			}
		}
		return xyjf + xyjfxq;
	}
	
	/**
	 * 保存开发者评价得分
	 * @author dell
	 * @param rwid   任务ID
	 * @param kfzid  开发者ID
	 * @param pjjg   评价结果
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveKfzPjdf(String rwid, String kfzid, String pjjg, Integer oldScore, String xyjfWid){
		Integer xyjf = 0; 
		Integer xyjfxq = 0;
		String classify = null;
		String content = null;
		int score = 0;
		if(PjResultEnum.YX.getCode().equals(pjjg)){
			classify = KfzxyjflxEnum.HPDF.getCode();
			content = KfzxyjflxEnum.HPDF.getName();
			score = KfzxyjflxEnum.HPDF.getScore();
		}else if(PjResultEnum.LH.getCode().equals(pjjg)){
			classify = KfzxyjflxEnum.ZPDF.getCode();
			content = KfzxyjflxEnum.ZPDF.getName();
			score = KfzxyjflxEnum.ZPDF.getScore();
		}else{
			classify = KfzxyjflxEnum.CPDF.getCode();
			content = KfzxyjflxEnum.CPDF.getName();
			score = KfzxyjflxEnum.CPDF.getScore();
		}
		KfzxyjfxqInfoExtend queryKfzxyjfxqInfoExtend = new KfzxyjfxqInfoExtend();
		queryKfzxyjfxqInfoExtend.setRwid(rwid);
		List<String> scoreClassifys = new ArrayList<String>();
		scoreClassifys.add(KfzxyjflxEnum.HPDF.getCode());
		scoreClassifys.add(KfzxyjflxEnum.ZPDF.getCode());
		scoreClassifys.add(KfzxyjflxEnum.CPDF.getCode());
		queryKfzxyjfxqInfoExtend.setScoreClassifys(scoreClassifys);
		List<KfzxyjfxqInfoExtend> xyjfxqLst = kfzxyjfxqInfoExtendMapper
				.selectByCondition(ConditionUtil.createCondition(queryKfzxyjfxqInfoExtend));
		if(CommonUtil.isNotEmptyList(xyjfxqLst)){//之前有做过评价--先减去之前的评价得分
			int lstPjdf = xyjfxqLst.get(0).getScore().intValue();
			xyjf = saveOrUpdateKfzxyjf(kfzid, score, oldScore-lstPjdf, xyjfWid);
			xyjfxq = saveKfzxyjfxq(kfzid, rwid, classify, content, score, oldScore-lstPjdf);
		}else{//之前未做评价
			xyjf = saveOrUpdateKfzxyjf(kfzid, score, oldScore, xyjfWid);
			xyjfxq = saveKfzxyjfxq(kfzid, rwid, classify, content, score, oldScore);
		}
		return xyjf + xyjfxq;
	}
	
	/**
	 * 计算开发者延期得分
	 * @author dell
	 * @param rwid       任务ID
	 * @param kfzid      开发者ID
	 * @param oldScore   原分数
	 * @param xyjfWid    原信誉积分WID
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveKfzyqdf(String rwid, String kfzid, Integer oldScore, String xyjfWid){
		Integer xyjf = 0;
		Integer xyjfxq = 0;
		xyjfxq = saveKfzxyjfxq(kfzid, rwid, KfzxyjflxEnum.YQDF.getCode(), KfzxyjflxEnum.YQDF.getName(), KfzxyjflxEnum.YQDF.getScore(), oldScore);
		xyjf = saveOrUpdateKfzxyjf(kfzid, KfzxyjflxEnum.YQDF.getScore(), oldScore, xyjfWid);
		return xyjf + xyjfxq;
	}
	
	/**
	 * 计算开发者投诉得分
	 * @author dell
	 * @param rwid       任务ID
	 * @param kfzid      开发者ID
	 * @param oldScore   原分数
	 * @param xyjfWid    原信誉积分WID
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveKfztsdf(String rwid, String kfzid, Integer oldScore, String xyjfWid){
		Integer xyjf = 0;
		Integer xyjfxq = 0;
		xyjfxq = saveKfzxyjfxq(kfzid, rwid, KfzxyjflxEnum.TSDF.getCode(), KfzxyjflxEnum.TSDF.getName(), KfzxyjflxEnum.TSDF.getScore(), oldScore);
		xyjf = saveOrUpdateKfzxyjf(kfzid, KfzxyjflxEnum.TSDF.getScore(), oldScore, xyjfWid);
		return xyjf + xyjfxq;
	}
	
	
	@Override
	public DataResult<Integer> saveXqfxyjfxx(RwjbxxInfo rwjbxxInfo, String flag, int pjScore, boolean autoYs, CustomOperateLog log)
			throws ServiceException {
		String rwid = rwjbxxInfo.getWid();
		String xqfid = rwjbxxInfo.getXqfid();
		Integer count = 0;
		Integer oldScore = 0;
		String xyjfWid = null;
		XqfxyjfInfoExtend queryXqfxyjfInfoExtend = new XqfxyjfInfoExtend();
		queryXqfxyjfInfoExtend.setXqfid(xqfid);
		List<XqfxyjfInfoExtend> xqfxyjfLst = xqfxyjfInfoExtendMapper.selectByCondition(ConditionUtil.createCondition(queryXqfxyjfInfoExtend));
		if(CommonUtil.isNotEmptyList(xqfxyjfLst)){
			oldScore = StringUtil.toInt(xqfxyjfLst.get(0).getScore(), 0);
			xyjfWid = StringUtil.toStr(xqfxyjfLst.get(0).getWid()); 
		}
		if("1".equals(flag)){//验收得分
			count = saveXqfysdf(rwid, xqfid, oldScore, xyjfWid, autoYs);
		}else if("2".equals(flag)){//评价得分
			count = saveXqfpjdf(rwid, xqfid, oldScore, xyjfWid, pjScore);
		}else if("3".equals(flag)){//投诉得分
			count = saveXqftsdf(rwid, xqfid, oldScore, xyjfWid);
		}
		return DataResult.success(count);
	}
	
	/**
	 * 保存验收得分
	 * @author dell
	 * @param rwid       任务ID
	 * @param xqfid      需求方ID
	 * @param oldScore   原分数
	 * @param xyjfWid    原信誉积分WID
	 * @param autoYs     是否自动验收
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveXqfysdf(String rwid, String xqfid, Integer oldScore, String xyjfWid, boolean autoYs){
		Integer xyjf = 0;
		Integer xyjfxq = 0;
		String classify = null;
		String content = null;
		int score = 0;
		if(autoYs){
			classify = XqfxyjflxEnum.ZDYSDF.getCode();
			content = XqfxyjflxEnum.ZDYSDF.getName();
			score = XqfxyjflxEnum.ZDYSDF.getScore();
		}else{
			classify = XqfxyjflxEnum.SDYSDF.getCode();
			content = XqfxyjflxEnum.SDYSDF.getName();
			score = XqfxyjflxEnum.SDYSDF.getScore();
		}
		//查询是否有验收得分信息
		List<String> classifys = new ArrayList<String>();
		classifys.add(XqfxyjflxEnum.SDYSDF.getCode());
		classifys.add(XqfxyjflxEnum.ZDYSDF.getCode());
		XqfxyjfxqInfoExtend queryXqfxyjfxqInfoExtend = new XqfxyjfxqInfoExtend();
		queryXqfxyjfxqInfoExtend.setRwid(rwid);
		queryXqfxyjfxqInfoExtend.setScoreClassifys(classifys);
		List<XqfxyjfxqInfoExtend> xyjfxqLst = xqfxyjfxqInfoExtendMapper
				.selectByCondition(ConditionUtil.createCondition(queryXqfxyjfxqInfoExtend));
		if(CommonUtil.isNotEmptyList(xyjfxqLst)){//如果之前有验收得分信息，则先减去原来的验收分，再加上新的验收分
			int lstPjdf = xyjfxqLst.get(0).getScore().intValue();
			xyjf = saveOrUpdateXqfxyjf(xqfid, score, oldScore-lstPjdf, xyjfWid);
			//保存信誉积分详情
			xyjfxq = saveXqfxyjfxq(xqfid, rwid, classify, content, score, oldScore-lstPjdf);
		}else{//若没有，则直接加新验收分
			xyjf = saveOrUpdateXqfxyjf(xqfid, score, oldScore, xyjfWid);
			//保存信誉积分详情
			xyjfxq = saveXqfxyjfxq(xqfid, rwid, classify, content, score, oldScore);
		}
		return xyjf + xyjfxq;
	}
	
	/**
	 * 保存需求方评价得分信息
	 * @author dell
	 * @param rwid      任务ID
	 * @param xqfid     需求方ID
	 * @param oldScore  原分数
	 * @param xyjfWid   原信誉积分WID
	 * @param pjjg      评价结果
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveXqfpjdf(String rwid, String xqfid, Integer oldScore, String xyjfWid, int pjScore){
		Integer xyjf = 0; 
		Integer xyjfxq = 0;
		String classify = null;
		String content = null;
		int score = 0;
		if(pjScore >= 5){
			classify =XqfxyjflxEnum.HPDF.getCode();
			content = XqfxyjflxEnum.HPDF.getName();
			score = XqfxyjflxEnum.HPDF.getScore();
		}else if(pjScore >= 3){
			classify = XqfxyjflxEnum.ZPDF.getCode();
			content = XqfxyjflxEnum.ZPDF.getName();
			score = XqfxyjflxEnum.ZPDF.getScore();
		}else{
			classify = XqfxyjflxEnum.CPDF.getCode();
			content = XqfxyjflxEnum.CPDF.getName();
			score = XqfxyjflxEnum.CPDF.getScore();
		}
		//查询该任务评价得分信息
		XqfxyjfxqInfoExtend queryXqfxyjfxqInfoExtend = new XqfxyjfxqInfoExtend();
		queryXqfxyjfxqInfoExtend.setRwid(rwid);
		List<String> scoreClassifys = new ArrayList<String>();
		scoreClassifys.add(XqfxyjflxEnum.HPDF.getCode());
		scoreClassifys.add(XqfxyjflxEnum.ZPDF.getCode());
		scoreClassifys.add(XqfxyjflxEnum.CPDF.getCode());
		queryXqfxyjfxqInfoExtend.setScoreClassifys(scoreClassifys);
		List<XqfxyjfxqInfoExtend> xyjfxqLst = xqfxyjfxqInfoExtendMapper
				.selectByCondition(ConditionUtil.createCondition(queryXqfxyjfxqInfoExtend));
		if(CommonUtil.isNotEmptyList(xyjfxqLst)){//之前有做过评价--先减去之前的评价得分
			int lstPjdf = xyjfxqLst.get(0).getScore().intValue();
			xyjf = saveOrUpdateXqfxyjf(xqfid, score, oldScore-lstPjdf, xyjfWid);
			//保存信誉积分详情
			xyjfxq = saveXqfxyjfxq(xqfid, rwid, classify, content, score, oldScore-lstPjdf);
		}else{//之前未做评价
			xyjf = saveOrUpdateXqfxyjf(xqfid, score, oldScore, xyjfWid);
			//保存信誉积分详情
			xyjfxq = saveXqfxyjfxq(xqfid, rwid, classify, content, score, oldScore);
		}
		return xyjf + xyjfxq;
	}
	
	
	/**
	 * 计算需求方投诉得分
	 * @author dell
	 * @param rwid       任务ID
	 * @param xqfid      需求方ID
	 * @param oldScore   原分数
	 * @param xyjfWid    原信誉积分WID
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveXqftsdf(String rwid, String xqfid, Integer oldScore, String xyjfWid){
		Integer xyjf = 0;
		Integer xyjfxq = 0;
		xyjfxq = saveXqfxyjfxq(xqfid, rwid, XqfxyjflxEnum.TSDF.getCode(), XqfxyjflxEnum.TSDF.getName(), XqfxyjflxEnum.TSDF.getScore(), oldScore);
		xyjf = saveOrUpdateXqfxyjf(xqfid, XqfxyjflxEnum.TSDF.getScore(), oldScore, xyjfWid);
		return xyjf + xyjfxq;
	}
	
	/**
	 * 新建或更新需求方信誉积分信息
	 * @author dell
	 * @param xqfid   需求方ID
	 * @param score   得分
	 * @param oldScore 原来总分
	 * @param xyjfWid 信誉积分WID
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveOrUpdateXqfxyjf(String xqfid, int score, int oldScore, String xyjfWid){
		XqfxyjfInfo xqfxyjfInfo = new XqfxyjfInfo();
		xqfxyjfInfo.setCjsj(DateUtil.getCurrentDateTimeStr());
		xqfxyjfInfo.setXqfid(xqfid);
		xqfxyjfInfo.setVersion(new BigDecimal(0));
		int newScore = score + oldScore;
		if(newScore >= 100){
			newScore = 100;
		}
		xqfxyjfInfo.setScore(new BigDecimal(newScore));
		if(StringUtil.isEmpty(xyjfWid)){
			xqfxyjfInfo.setWid(StringUtil.getUuId());
			xqfxyjfInfo.setIntegral(new BigDecimal(0));
			return xqfxyjfInfoMapper.insertSelective(xqfxyjfInfo);
		}else{
			xqfxyjfInfo.setWid(xyjfWid);
			return xqfxyjfInfoMapper.updateByPrimaryKeySelective(xqfxyjfInfo);
		}
	}
	
	/**
	 * 保存需求方信誉积分详情
	 * @author dell
	 * @param xqfid  需求方ID
	 * @param rwid   任务ID
	 * @param classify  得分类型
	 * @param content   备注说明
	 * @param score     得分
	 * @return
	 * @since JDK 1.6
	 */
	protected Integer saveXqfxyjfxq(String xqfid, String rwid, String classify, String content,
			int score, int oldScore) {
		// 获取信誉积分详情信息
		XqfxyjfxqInfo insertXqfxyjfxqInfo = new XqfxyjfxqInfo();
		insertXqfxyjfxqInfo.setWid(StringUtil.getUuId());
		insertXqfxyjfxqInfo.setXqfid(xqfid);
		insertXqfxyjfxqInfo.setRwid(rwid);
		insertXqfxyjfxqInfo.setScoreClassify(classify);
		insertXqfxyjfxqInfo.setScore(new BigDecimal(score));
		insertXqfxyjfxqInfo.setContent(content);
		insertXqfxyjfxqInfo.setTotalScore(new BigDecimal(score + oldScore));
		insertXqfxyjfxqInfo.setCjsj(DateUtil.getCurrentDateTimeStr());
		return xqfxyjfxqInfoMapper.insertSelective(insertXqfxyjfxqInfo);
	}
}