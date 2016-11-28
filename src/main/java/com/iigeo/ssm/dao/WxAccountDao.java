package com.iigeo.ssm.dao;

import org.springframework.stereotype.Service;

import com.iigeo.ssm.wx.entity.WxAccount;

@Service
public interface WxAccountDao {

   int insert(WxAccount wxAccount);	
   
   int update(WxAccount wxAccount);
   
   int delete(long wxAccId);
}
