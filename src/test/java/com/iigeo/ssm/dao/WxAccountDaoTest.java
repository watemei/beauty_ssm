package com.iigeo.ssm.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.iigeo.ssm.wx.entity.WxAccount;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 
 * @author laowa
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class WxAccountDaoTest {

    @Autowired
    private WxAccountDao wxAccountDao;
	
	@Test
	public void insert() {
		WxMpUser wxMpUser =new WxMpUser();
		wxMpUser.setCity("北京市");
		wxMpUser.setCountry("丰台区石榴庄30号2单元102");
		wxMpUser.setGroupId(1);
		wxMpUser.setHeadImgUrl("http://img05.tooopen.com/images/20140604/sy_62331342149.jpg");
		wxMpUser.setLanguage("");
		wxMpUser.setNickname("莫逐");
		wxMpUser.setOpenId("openId");
		wxMpUser.setProvince("北京市");
		wxMpUser.setSex("0");
		wxMpUser.setSexId(0);
		WxAccount wxAccount =JSON.parseObject(JSON.toJSONString(wxMpUser), WxAccount.class);
		int i = wxAccountDao.insert(wxAccount);
		System.out.println(wxAccount.toString());
		System.out.println("-----------"+i+"---------------");
	}

}
