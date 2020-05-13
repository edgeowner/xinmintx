package com.xinmintx.common.parity;

import java.security.MessageDigest;

public class MerchantMD5
{
  public static String MD5(String inStr)
  {
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
    } catch (Exception e) {
      System.out.println(e.toString());
      e.printStackTrace();
      return "";
    }
    char[] charArray = inStr.toCharArray();
    byte[] byteArray = new byte[charArray.length];

    for (int i = 0; i < charArray.length; i++) {
      byteArray[i] = (byte)charArray[i];   //此处有bug，中文是强转，会受损
    }
    byte[] md5Bytes = md5.digest(byteArray);// byte[] md5Bytes = md5.digest(inStr.getBytes() );

    StringBuffer hexValue = new StringBuffer();

    for (int i = 0; i < md5Bytes.length; i++) {
      int val = md5Bytes[i] & 0xFF;
      if (val < 16)
        hexValue.append("0");
      hexValue.append(Integer.toHexString(val));
    }

    return hexValue.toString();
  }

  public static String KL(String inStr)
  {
    char[] a = inStr.toCharArray();
    for (int i = 0; i < a.length; i++) {
      a[i] = (char)(a[i] ^ 0x74);
    }
    String s = new String(a);
    return s;
  }

  public static String JM(String inStr)
  {
    char[] a = inStr.toCharArray();
    for (int i = 0; i < a.length; i++) {
      a[i] = (char)(a[i] ^ 0x74);
    }
    String k = new String(a);
    return k;
  }

  public static void main(String[] args) {
    String versionId = "2";
    String merchantId = "987456";
    String orderId = "t0628";
    String accmount = "1089";
    String date = "2011-06-16";
    String type = "RMB";
    String type1 = "0101";
    String key = "ag8ch6hbttbl";

    StringBuilder sb = new StringBuilder();
    sb.append(versionId).append(merchantId).append(orderId)
      .append(accmount).append(date).append(type).append(type1)
      .append(key);
   String ss="acctNo=6666666666666666666&merchantId=00000000015673&notifyUrl=http://tongzhi.gg777.info:57777/admin/callback/callback_swt&orderDate=20170413&recvAccName=张壮&requestSN=g2c_gs_41473&signType=MD5&txnAmt=9500&version=2.0R1POIBA787G1RCKGJK3VFDVZ";
    System.out.println(MD5(ss.toString()));
  }
}