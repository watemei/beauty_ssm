package com.iigeo.ssm.wx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iigeo.ssm.dao.WxAccountDao;
import com.iigeo.ssm.wx.entity.WxAccount;
import com.iigeo.ssm.wx.service.WxAccountService;

@Component
public class WxAccountServiceImpl implements WxAccountService {

	@Autowired
	private WxAccountDao wxAccountDao;
	
	@Override
	public int insert(WxAccount wxAccount) {
		return wxAccountDao.insert(wxAccount);
	}

	@Override
	public int update(WxAccount wxAccount) {
		return wxAccountDao.update(wxAccount);
	}


}
