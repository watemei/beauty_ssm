package com.iigeo.ssm.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iigeo.ssm.dto.BaseBean;
import com.iigeo.ssm.dto.ResultUtil;
import com.iigeo.ssm.dto.UserDto;
import com.iigeo.ssm.service.UserService;
import com.iigeo.ssm.util.SystemUtil;

@RestController
@RequestMapping("/user")
public class UserApiController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
	private UserService userService;
    
    @Autowired
    private SystemUtil systemUtil;
    
    @RequestMapping(value = "/detail/{v}", method = RequestMethod.GET)
    public BaseBean list(Integer offset, Integer limit) {
        LOG.info("invoke----------/user/detail/list");
        UserDto userDto = userService.getUserDetail();
//        demoService.pulish(ApplicationContextHolder.getApplicationContext());
        System.out.println(systemUtil.getValueByKey("dd"));
        System.out.println(systemUtil.getValueByKey("redis.pool.maxTotal"));
        return ResultUtil.baseBean(true,"查询成功", userDto);
    }

}
