	package com.xdshop.service;

import java.util.HashMap;
import java.util.List;

import com.xdshop.api.BaseParam;
import com.xdshop.dal.domain.SysAccount;

public interface ISysAccountService {
	public SysAccount getSysAccountByAccountId(String accountId);
	
	public List<SysAccount> getSysAccountList(BaseParam baseParam);
}
