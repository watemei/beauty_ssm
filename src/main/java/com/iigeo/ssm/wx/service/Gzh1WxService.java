package com.iigeo.ssm.wx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iigeo.ssm.wx.config.WxConfig;
import com.iigeo.ssm.wx.config.WxGzh1Config;
import com.iigeo.ssm.wx.handler.AbstractHandler;
import com.iigeo.ssm.wx.handler.Gzh1LocationHandler;
import com.iigeo.ssm.wx.handler.Gzh1MenuHandler;
import com.iigeo.ssm.wx.handler.Gzh1MsgHandler;
import com.iigeo.ssm.wx.handler.Gzh1SubscribeHandler;
import com.iigeo.ssm.wx.handler.Gzh1UnSubscribeHandler;
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
public class Gzh1WxService extends BaseWxService {
  @Autowired
  private WxGzh1Config wxConfig;

  @Autowired
  private Gzh1LocationHandler locationHandler;
  
  @Autowired
  private Gzh1MenuHandler menuHandler;
  
  @Autowired
  private Gzh1MsgHandler msgHandler;
  
  @Autowired
  private Gzh1UnSubscribeHandler unSubscribeHandler;
  
  @Autowired
  private Gzh1SubscribeHandler subscribeHandler;

  @Override
  protected WxConfig getServerConfig() {
    return this.wxConfig;
  }

  @Override
  protected MenuHandler getMenuHandler() {
    return this.menuHandler;
  }

  @Override
  protected SubscribeHandler getSubscribeHandler() {
    return this.subscribeHandler;
  }

  @Override
  protected UnsubscribeHandler getUnsubscribeHandler() {
    return this.unSubscribeHandler;
  }

  @Override
  protected AbstractHandler getLocationHandler() {
    return this.locationHandler;
  }

  @Override
  protected MsgHandler getMsgHandler() {
    return this.msgHandler;
  }

  @Override
  protected AbstractHandler getScanHandler() {
    return null;
  }

}
