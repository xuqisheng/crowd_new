package com.wisedu.crowd.service.rwgl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisedu.crowd.common.exception.ServiceException;
import com.wisedu.crowd.dao.rwgl.extend.CpmlInfoExtendMapper;
import com.wisedu.crowd.service.dto.DataResult;
import com.wisedu.crowd.entity.rwgl.extend.CpmlInfoExtend;
import com.wisedu.crowd.service.rwgl.CpmlInfoService;

@Service("cpmlInfoService")
public class CpmlInfoServiceImpl implements CpmlInfoService{

	@Autowired
	private CpmlInfoExtendMapper cpmlInfoExtendMapper;
	@Override
	public DataResult<List<CpmlInfoExtend>> selectByCondition(CpmlInfoExtend record) throws ServiceException {
		return DataResult.success(cpmlInfoExtendMapper.selectByCondition(record));
	}

	
}