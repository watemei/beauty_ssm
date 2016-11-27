package com.iigeo.ssm.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iigeo.ssm.wx.config.WxConfig;
import com.iigeo.ssm.wx.config.WxGzh2Config;
import com.iigeo.ssm.wx.handler.AbstractHandler;
import com.iigeo.ssm.wx.handler.MenuHandler;
import com.iigeo.ssm.wx.handler.MsgHandler;
import com.iigeo.ssm.wx.handler.SubscribeHandler;
import com.iigeo.ssm.wx.handler.UnsubscribeHandler;

/**
 * 
 * @author Binary Wang
 *
 */
@Service
public class Gzh2WxService extends BaseWxService {

  @Autowired
  private WxGzh2Config wxConfig;

  @Override
  protected WxConfig getServerConfig() {
    return this.wxConfig;
  }

  @Override
  protected MenuHandler getMenuHandler() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected SubscribeHandler getSubscribeHandler() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected UnsubscribeHandler getUnsubscribeHandler() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected AbstractHandler getLocationHandler() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected MsgHandler getMsgHandler() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected AbstractHandler getScanHandler() {
    // TODO Auto-generated method stub
    return null;
  }

}
