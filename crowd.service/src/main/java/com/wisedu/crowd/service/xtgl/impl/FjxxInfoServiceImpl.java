package com.wisedu.crowd.service.xtgl.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisedu.crowd.common.exception.ServiceException;
import com.wisedu.crowd.common.util.DateUtil;
import com.wisedu.crowd.common.util.StringUtil;
import com.wisedu.crowd.dao.xtgl.FjxxInfoMapper;
import com.wisedu.crowd.service.dto.DataResult;
import com.wisedu.crowd.entity.log.CustomOperateLog;
import com.wisedu.crowd.entity.xtgl.FjxxInfo;
import com.wisedu.crowd.service.xtgl.FjxxInfoService;

@Service("fjxxInfoService")
@Transactional
public class FjxxInfoServiceImpl implements FjxxInfoService{

	@Autowired
	private  FjxxInfoMapper fjxxInfoMapper;

	@Override
	public DataResult<FjxxInfo> selectByPrimaryKey(String wid,CustomOperateLog log) throws ServiceException {
		return  DataResult.success(fjxxInfoMapper.selectByPrimaryKey(wid));
	}

	@Override
	public DataResult<FjxxInfo> insertSelective(FjxxInfo record,CustomOperateLog log) throws ServiceException {
		fjxxInfoMapper.insertSelective(record);
		return  DataResult.success(record);
	}

	@Override
	public DataResult<Integer> deleteByPrimaryKey(String wid,CustomOperateLog log) throws ServiceException {
		return DataResult.success(fjxxInfoMapper.deleteByPrimaryKey(wid));
	}
	
	

	
    
}