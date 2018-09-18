package com.xdshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xdshop.api.BaseParam;
import com.xdshop.dal.dao.SysAccountMapper;
import com.xdshop.dal.domain.SysAccount;
import com.xdshop.service.ISysAccountService;

@Service
public class SysAccountServiceImpl implements ISysAccountService{

	@Autowired
	private SysAccountMapper sysAccountMapper;
	@Override
	public SysAccount getSysAccountByAccountId(String accountId) {
		return sysAccountMapper.selectByAccountId(accountId);
	}
	@Override
	public List<SysAccount> getSysAccountList(BaseParam queryParam) {
		PageHelper.startPage(Integer.parseInt(queryParam.getCurPage()), Integer.parseInt(queryParam.getPageSize()),true);
		return sysAccountMapper.selectByCreateTime(queryParam);
	}

}
