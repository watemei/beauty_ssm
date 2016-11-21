package com.iigeo.ssm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iigeo.ssm.cache.RedisCache;
import com.iigeo.ssm.dao.UserDao;
import com.iigeo.ssm.dto.UserDto;
import com.iigeo.ssm.entity.Address;
import com.iigeo.ssm.entity.User;
import com.iigeo.ssm.mapper.OrikaBeanMapper;
import com.iigeo.ssm.service.UserService;
import com.iigeo.ssm.util.SystemUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "userDao")
	private UserDao userDao;
	@Resource(name = "cache")
	private RedisCache cache;
	@Autowired
	OrikaBeanMapper mapper;
	@Autowired
	SystemUtil systemUtil;

	@Override
	public List<User> getUserList(int offset, int limit) {
		String cache_key = RedisCache.CAHCENAME + "|getUserList|" + offset
				+ "|" + limit;
		// 先去缓存中取
		List<User> result_cache = cache.getListCache(cache_key, User.class);
		if (result_cache == null) {
			// 缓存中没有再去数据库取，并插入缓存（缓存时间为60秒）
			result_cache = userDao.queryAll(offset, limit);
			cache.putListCacheWithExpireTime(cache_key, result_cache,
					RedisCache.CAHCETIME);
			LOG.info("put cache with key:" + cache_key);
		} else {
			LOG.info("get cache with key:" + cache_key);
		}
		return result_cache;
	}

	@Override
	public UserDto getUserDetail() {
		User user = new User();
		user.setUserId(1);
		user.setUserName("Pep");
		user.setScore(100);
		Address address = new Address();
		address.setNumber(25);
		address.setStreet("Barcelona St.");
		
		LOG.info("get val from properties. val is {}", systemUtil.getValueByKey("ips.durid"));
		return mapper.map(user, UserDto.class);
	}

}
