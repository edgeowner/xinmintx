package com.xinmintx.factory.api.service.impl;

import cfca.sm2rsa.common.PKIException;
import cfca.util.SignatureUtil2;

import com.xinmintx.factory.api.common.ResultCode;
import com.xinmintx.factory.api.service.WithdrawService;
import com.xinmintx.factory.api.util.ConfigInfo;
import com.xinmintx.factory.api.util.StringUtil;
import com.xinmintx.factory.api.util.XMLConvertor;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @ClassName: com.xinmintx.factory.service.impl.WithdrawServiceImpl
 * @Author:Pikachu
 * @Date: 2019/12/23 16:50
 * @Version: v1.0
 */
@Service
public class WithdrawServiceImpl implements WithdrawService {
    @Override
    public ResultCode withdraw(Map<String, String> paramMap) {
        // 初始化Map
        initMap(paramMap);
        // 生成post方法
        return pay(paramMap);
    }
    // 维护map初始信息
    private void initMap(Map<String, String> paramMap) {
        paramMap.put("platformId", "00000000035514");// 支付平台统一分配的商户编号
        paramMap.put("version", "2.0");// 版本号
        paramMap.put("signType", "CFCA");// 加密类型
        paramMap.put("md5Key", "309M0O4C9LFKXN3G6KX0THLK");// MD5加密串
        paramMap.put("postUrl", "https://www.56zhifu.com/user/warrantrCash.do");
    }
    // 调用支付接口
    private ResultCode pay(Map<String, String> paramMap) {
        ResultCode rc = new ResultCode();
        try {
            HttpClient httpclient = new HttpClient();
            // post赋值
            PostMethod post = initPostValue(paramMap);
            // 请求
            int httpcode = httpclient.executeMethod(post);
            System.out.println("httpcode---->" + httpcode);
            String info = new String(post.getResponseBody(), StandardCharsets.UTF_8);
            System.out.println("返回信息---->" + info);
            // 处理返回结果
            XMLConvertor.dealResult(info, rc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // post对象赋值
    private PostMethod initPostValue(Map<String, String> paramMap) {
        String postUrl = paramMap.get("postUrl");
        PostMethod post = new PostMethod(postUrl);//请求的测试url
        String version = paramMap.get("version"); // 版本号
        String platformId = paramMap.get("platformId");// 支付平台统一分配的商户编号
        String signType = paramMap.get("signType");// 加签方式MD5
        String userId = paramMap.get("userId");//用户编号
        String orderId = paramMap.get("orderId");//订单编号
        String pwd = paramMap.get("pwd");//支付密码
        String messPwdFlg = paramMap.get("messPwdFlg");//是否验证短信接口
        String orderAmt = paramMap.get("orderAmt");//提现金额
        String mession = paramMap.get("mession");//短信编号	用此字段验证是否为当前验证短信
        String smsCode = paramMap.get("smsCode");//短信验证码		短信验证码

        //支付密码RSA加密
        pwd = StringUtil.encryptWarrantr(pwd);
        paramMap.put("pwd",pwd);

        // 获取签名
        String signature = getSignature(paramMap);
        // post赋值
        post.getParams().setParameter(
                HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        post.addParameter("platformId", platformId);
        post.addParameter("version", version);
        post.addParameter("userId",userId);
        post.addParameter("orderId",orderId);
        post.addParameter("messPwdFlg",messPwdFlg);
        post.addParameter("orderAmt",orderAmt);
        post.addParameter("signType", signType);
        post.addParameter("signature", signature);
        post.addParameter("mession",mession);
        post.addParameter("smsCode",smsCode);
        post.addParameter("pwd",pwd);
        return post;
    }

    // 获取签名方法
    private String getSignature(Map<String, String> paramMap) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("platformId", paramMap.get("platformId"));
        map.put("version", paramMap.get("version"));
        map.put("userId",paramMap.get("userId"));
        map.put("orderId",paramMap.get("orderId"));
        map.put("messPwdFlg",paramMap.get("messPwdFlg"));
        map.put("orderAmt",paramMap.get("orderAmt"));
        map.put("signType", paramMap.get("signType"));
        map.put("mession",paramMap.get("mession"));
        map.put("smsCode",paramMap.get("smsCode"));
        map.put("pwd",paramMap.get("pwd"));
        Map<String, String> params = StringUtil.paraFilter(map);
        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
        StringUtil.buildPayParams(buf, params, false);
        String payStr = buf.toString();

        String signature;
        System.out.println("payStr:" + payStr);
        String md5Key = paramMap.get("md5Key");
        System.out.println("source+key:==>" + payStr + md5Key);
        try {
            SignatureUtil2 signUtil = new SignatureUtil2();
            byte[] CFCAsignature = signUtil.p7SignMessageDetach("SHA1withRSAEncryption", payStr.getBytes("UTF-8"),
                    ConfigInfo.getPrivateKey(), ConfigInfo.getX509Cert(), ConfigInfo.getSession());
            signature = new String(CFCAsignature, "UTF-8");
            System.out.println("signature:" + signature);
            String str = payStr + "&signature=" + signature;
            System.out.println("发送--加密前参数==" + str);
            return signature;
        } catch (UnsupportedEncodingException | PKIException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 处理返回结果
    private void dealResult(String info) {
        if (info != null && !"".equals(info)) {
            try {
                Document doc = DocumentHelper.parseText(info);
                Element root = doc.getRootElement();
                if (root.attributeValue("retCode").equals("0001")) {
                    System.out.println("success");
                    System.out.println(root.attributeValue("payInfo"));
                } else {
                    System.out.println("fail");
                }
                System.out.println(root.attributeValue("retMsg"));
            } catch (DocumentException e1) {
                e1.printStackTrace();
            }
        }
    }


}
