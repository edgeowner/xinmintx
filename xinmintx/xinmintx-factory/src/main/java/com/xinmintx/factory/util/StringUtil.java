package com.xinmintx.factory.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str) || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isEmail(String email) {
        if (isEmpty(email)) return false;
        boolean bool = true;
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            bool = false;
        }
        return bool;
    }

    public static boolean isNumeric(String number) {
        if (number.matches("//d*")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 过滤参数
     *
     * @param sArray
     * @return
     * @author
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("signature")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * @param payParams
     * @return
     * @author
     */
    public static String payParamsToString(StringBuilder sb, Map<String, String> payParams, boolean encoding) {
        buildPayParams(sb, payParams, encoding);
        return sb.toString();
    }

    /**
     * @param payParams
     * @return
     * @author
     */
    public static void buildPayParams(StringBuilder sb, Map<String, String> payParams, boolean encoding) {
        List<String> keys = new ArrayList<String>();
        Set set = payParams.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            keys.add((String) it.next());
        }
        if (keys.size() > 0) {
            Collections.sort(keys);
            for (String key : keys) {
                sb.append(key).append("=");
                if (encoding) {
                    sb.append(payParams.get(key));
                } else {
                    sb.append(payParams.get(key));
                }
                sb.append("&");
            }
            sb.setLength(sb.length() - 1);
        }

    }

    /**
     * 商户
     *
     * @param aesStr
     * @param pubFile
     * @return
     */
    public static String encryptWarrantr(String aesStr, String pubFile) {
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            FileInputStream inputStream = new FileInputStream(new File(pubFile));
            Certificate cert = certificateFactory.generateCertificate(inputStream);
            PublicKey publicKey = cert.getPublicKey();
            // 对数据加密
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] keyByte = cipher.doFinal(aesStr.getBytes());
            return Base64.encodeBase64String(keyByte);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptWarrantr(String aesStr) {
        String encryptWarrantr = encryptWarrantr(aesStr, "./warrantrPublic.cer");
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(encryptWarrantr);
        return m.replaceAll("");
    }

    /**
     * 商户
     *
     * @param aesStr
     * @param publicKey
     * @return
     */
    public static String encryptWarrantr1(String aesStr, PublicKey publicKey) {
        try {
            // 对数据加密
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] keyByte = cipher.doFinal(aesStr.getBytes());
            return Base64.encodeBase64String(keyByte);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    // 去掉小数点后面的0
    public static String getPrettyNumber(BigDecimal totalMoney) {
        String fee = "0";
        if (totalMoney != null) {
            fee = BigDecimal.valueOf(Double.parseDouble(totalMoney.multiply(new BigDecimal("100")).toString())).stripTrailingZeros().toPlainString();
        }
        return fee;
    }

    // post请求方法
    public static void post(String url) {
        try {
            HttpClient httpclient = new HttpClient();
            PostMethod post = new PostMethod(url);
            int httpcode = httpclient.executeMethod(post);
            System.out.println("httpcode--------------->" + httpcode);
            String info = new String(post.getResponseBody(), StandardCharsets.UTF_8);
            System.out.println("info------------------->" + info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
