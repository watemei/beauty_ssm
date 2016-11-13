package com.iigeo.ssm.web;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iigeo.ssm.dto.BaseBean;
import com.iigeo.ssm.dto.ResultUtil;
import com.iigeo.ssm.entity.Goods;
import com.iigeo.ssm.enums.ResultEnum;
import com.iigeo.ssm.exception.BizException;
import com.iigeo.ssm.service.GoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(Integer offset, Integer limit) {
        LOG.info("invoke----------/goods/list");
        ModelAndView model =new ModelAndView();
        offset = offset == null ? 0 : offset;//默认便宜0
        limit = limit == null ? 50 : limit;//默认展示50条
        List<Goods> list = goodsService.getGoodsList(offset, limit);
        model.addObject("goodslist", list);
        model.setViewName("goodslist");
        return model;
    }

    @RequestMapping(value = "/{goodsId}/buy", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public BaseBean buy(@CookieValue(value = "userPhone", required = false) Long userPhone,
        /*@PathVariable("goodsId") Long goodsId*/ @Valid Goods goods, BindingResult result) {
        LOG.info("invoke----------/" + goods.getGoodsId() + "/buy userPhone:" + userPhone);
        if (userPhone == null) {
            return ResultUtil.baseBean(false, ResultEnum.INVALID_USER);
        }
        //Valid 参数验证(这里注释掉，采用AOP的方式验证,见BindingResultAop.java)
        //if (result.hasErrors()) {
        //    String errorInfo = "[" + result.getFieldError().getField() + "]" + result.getFieldError().getDefaultMessage();
        //    return new BaseResult<Object>(false, errorInfo);
        //}
        try {
            goodsService.buyGoods(userPhone, goods.getGoodsId(), false);
        } catch (BizException e) {
            return new BaseBean(false, e.getMessage());
        } catch (Exception e) {
            return ResultUtil.baseBean(false, ResultEnum.INNER_ERROR);
        }
        return ResultUtil.baseBean(true, null);
    }
}
