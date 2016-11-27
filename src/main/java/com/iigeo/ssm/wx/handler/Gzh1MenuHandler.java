package com.iigeo.ssm.wx.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iigeo.ssm.wx.config.WxConfig;
import com.iigeo.ssm.wx.config.WxGzh1Config;

@Component
public class Gzh1MenuHandler extends MenuHandler {
  @Autowired
  private  WxGzh1Config wxConfig;

  @Override
  protected WxConfig getWxConfig() {
    return this.wxConfig;
  }

}
