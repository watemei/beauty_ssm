package com.iigeo.ssm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iigeo.ssm.cache.RedisCache;
import com.iigeo.ssm.dao.GoodsDao;
import com.iigeo.ssm.dao.OrderDao;
import com.iigeo.ssm.dao.UserDao;
import com.iigeo.ssm.entity.Goods;
import com.iigeo.ssm.entity.User;
import com.iigeo.ssm.enums.ResultEnum;
import com.iigeo.ssm.exception.BizException;
import com.iigeo.ssm.service.GoodsService;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Resource(name="goodsDao")
	private GoodsDao goodsDao;
	@Resource(name="orderDao")
	private OrderDao orderDao;
	@Resource(name="userDao")
	private UserDao userDao;
	@Resource(name="cache")
	private RedisCache cache;

	@Override
	public List<Goods> getGoodsList(int offset, int limit) {
		String cache_key = RedisCache.CAHCENAME + "|getGoodsList|" + offset + "|" + limit;
		List<Goods> result_cache = cache.getListCache(cache_key, Goods.class);
		if (result_cache != null) {
			LOG.info("get cache with key:" + cache_key);
		} else {
			// 缓存中没有再去数据库取，并插入缓存（缓存时间为60秒）
			result_cache = goodsDao.queryAll(offset, limit);
			cache.putListCacheWithExpireTime(cache_key, result_cache, RedisCache.CAHCETIME);
			LOG.info("put cache with key:" + cache_key);
			return result_cache;
		}
		return result_cache;
	}

	@Transactional
	@Override
	public void buyGoods(long userPhone, long goodsId, boolean useProcedure) {
		// 用户校验
		User user = userDao.queryByPhone(userPhone);
		if (user == null) {
			throw new BizException(ResultEnum.INVALID_USER.getMsg());
		}
		if (useProcedure) {
			// 通过存储方式的方法进行操作
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getUserId());
			map.put("goodsId", goodsId);
			map.put("title", "抢购");
			map.put("result", null);
			goodsDao.bugWithProcedure(map);
			int result = MapUtils.getInteger(map, "result", -1);
			if (result <= 0) {
				// 买卖失败
				throw new BizException(ResultEnum.INNER_ERROR.getMsg());
			} else {
				// 买卖成功
				// 此时缓存中的数据不是最新的，需要对缓存进行清理（具体的缓存策略还是要根据具体需求制定）
				cache.deleteCacheWithPattern("getGoodsList*");
				LOG.info("delete cache with key: getGoodsList*");
				return;
			}
		} else {

			int inserCount = orderDao.insertOrder(user.getUserId(), goodsId, "普通买卖");
			if (inserCount <= 0) {
				// 买卖失败
				throw new BizException(ResultEnum.DB_UPDATE_RESULT_ERROR.getMsg());
			} else {
				// 减库存
				int updateCount = goodsDao.reduceNumber(goodsId);
				if (updateCount <= 0) {
					// 减库存失败
					throw new BizException(ResultEnum.DB_UPDATE_RESULT_ERROR.getMsg());
				} else {
					// 买卖成功
					// 此时缓存中的数据不再是最新的，需要对缓存进行清理（具体的缓存策略还是要根据具体需求制定）
					cache.deleteCacheWithPattern("getGoodsList*");
					LOG.info("delete cache with key: getGoodsList*");
					return;
				}
			}
		}
	}

}
