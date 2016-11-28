package com.iigeo.ssm.wx.service;

import com.iigeo.ssm.wx.entity.WxAccount;

public interface WxAccountService {
	
    public int insert(WxAccount wxAccount);
    
    public int update(WxAccount wxAccount);
}
