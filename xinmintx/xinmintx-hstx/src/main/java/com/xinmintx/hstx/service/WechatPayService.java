package com.xinmintx.hstx.service;

import com.xinmintx.hstx.pojo.bo.WechatPayBo;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2019/12/27 0027
 * @time: 上午 9:48
 * @Description:
 */
public interface WechatPayService {

    Map<String,Object> unifiedorder(WechatPayBo wechatPayBo);
}
