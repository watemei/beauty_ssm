package com.iigeo.ssm.wx.handler;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.iigeo.ssm.wx.builder.TextBuilder;
import com.iigeo.ssm.wx.service.BaseWxService;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * @author Binary Wang
 *
 */
public abstract class MsgHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
            Map<String, Object> context, WxMpService wxMpService,
            WxSessionManager sessionManager)    {

        BaseWxService weixinService = (BaseWxService) wxMpService;

        if (!wxMessage.getMsgType().equals(WxConsts.XML_MSG_EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
            && weixinService.hasKefuOnline()) {
            return WxMpXmlOutMessage
                .TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser()).build();
        }

        //TODO 组装回复消息
        String content = "金色小晶体[http://182.254.156.219/page/index.html]";
//        return new ImageBuilder().build(content, wxMessage, weixinService);
        return new TextBuilder().build(content, wxMessage, weixinService);

    }

}
