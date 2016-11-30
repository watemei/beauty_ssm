package com.iigeo.ssm.wx.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.iigeo.ssm.mapper.OrikaBeanMapper;
import com.iigeo.ssm.wx.builder.TextBuilder;
import com.iigeo.ssm.wx.entity.WxAccount;
import com.iigeo.ssm.wx.service.BaseWxService;
import com.iigeo.ssm.wx.service.WxAccountService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 
 * @author Binary Wang
 *
 */
public abstract class SubscribeHandler extends AbstractHandler {

	@Autowired
	private WxAccountService wxAccountService;
	@Autowired
	private OrikaBeanMapper orikaBeanMapper;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) throws WxErrorException {

		this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

		BaseWxService weixinService = (BaseWxService) wxMpService;

		// 获取微信用户基本信息
		WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);

		if (userWxInfo != null) {
			// TODO 可以添加关注用户到本地
			WxAccount wxAccount = orikaBeanMapper.map(userWxInfo, WxAccount.class);
			wxAccount.setSubscribe(userWxInfo.getSubscribe() ? 1 : 0);
			this.logger.info(wxAccount.toString());
			
			wxAccountService.insert(wxAccount);
		}

		WxMpXmlOutMessage responseResult = null;
		try {
			responseResult = handleSpecial(wxMessage);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		if (responseResult != null) {
			return responseResult;
		}

		try {
			return new TextBuilder().build("感谢关注", wxMessage, weixinService);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
	 */
	protected abstract WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception;

}
