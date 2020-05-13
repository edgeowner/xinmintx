package com.xinmintx.factory.controller;

import com.xinmintx.factory.common.ResultCode;
import com.xinmintx.factory.model.PoboNotify;
import com.xinmintx.factory.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName: com.xinmintx.factory.controller.MerchantsController
 * @Author:Pikachu
 * @Date: 2019/12/21 9:08
 * @Version: v1.0
 */
@Slf4j
@RestController
@RequestMapping("/merchants")
public class MerchantsController {
    @Autowired
    private HsPaymentOnBehalfOf hsPaymentOnBehalfOf;

    @Autowired
    private QueryTiedCardService tiedCardService;
    @Autowired
    private SetPwdService setPwdService;
    @Autowired
    private RelieveBindService bindService;
    @Autowired
    private IDCardVerifyService idCardVerifyService;
    @Autowired
    private QueryOpenService queryOpenService;
    @Autowired
    private InformationChangesService informationChangesService;
    @Autowired
    private MerchantsInService merchantsInService;
    @Autowired
    private ConfirmReceiptService confirmReceiptService;
    @Autowired
    private BalanceEnquiryService balanceEnquiryService;
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private WithdrawService withdrawService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private ShoppingUnitService shoppingUnitService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private PaymentOnBehalfOf paymentOnBehalfOf;
    @Autowired
    private SearchPayment searchPayment;
    @Autowired
    private WxThirdRefundService wxThirdRefundService;
    @Autowired
    private CheckReceiveService checkReceiveService;

    /**
     * 开户
     * @param paramMap
     */
    @PostMapping("/regist")
    public Map appPayment(@RequestParam Map<String,String> paramMap){
        return userAccountService.userAccount(paramMap);
    }

    /**
     * 绑卡
     * @param paramMap
     */
    @PostMapping("/tiedCard")
    public ResultCode tiedCard(@RequestParam Map<String,String> paramMap){
        return shoppingUnitService.tiedCard(paramMap);
    }

    /**
     * 短信
     * @param paramMap
     */
    @PostMapping("/note")
    public ResultCode note(@RequestParam Map<String,String> paramMap ){
        ResultCode code = new ResultCode();
        code.setData(noteService.note(paramMap));
        return code;
    }

    /**
     * 提现
     * @param paramMap
     * @return
     */
    @PostMapping("/withdraw")
    public ResultCode withdraw(@RequestParam Map<String,String> paramMap){
        return  withdrawService.withdraw(paramMap);
    }

    /**
     * 充值
     * @param paramMap
     * @return
     */
    @PostMapping("/recharge")
    public ResultCode recharge(@RequestParam Map<String,String> paramMap){

        return rechargeService.recharge(paramMap);
    }

    /**
     * 余额查询
     * @param paramMap
     * @return
     */
    @GetMapping("/balanceEnquiry")
    public ResultCode balanceEnquiry(@RequestParam Map<String,String> paramMap){
        return balanceEnquiryService.balanceEnquiry(paramMap);
    }

    /**
     * 确认收货
     * @param paramMap
     * @return
     */
    @PostMapping("/confirmReceipt")
    public ResultCode confirmReceipt(@RequestParam Map<String,String> paramMap){
        return confirmReceiptService.confirmReceipt(paramMap);
    }

    /**
     * 商户入驻
     * @param paramMap
     * @return
     */
    @PostMapping("/merchantsIn")
    public ResultCode merchantsIn(@RequestParam Map<String,String> paramMap){
        return merchantsInService.merchantsIn(paramMap);
    }

    /**
     * 商户信息修改
     * @param paramMap
     * @return
     */
    @PostMapping("/information")
    public ResultCode informationChanges(@RequestParam Map<String,String> paramMap){
        return informationChangesService.informationChanges(paramMap);
}

    /**
     * 商户查询开户审核接口
     * @param merBusRegNum
     */
    @GetMapping("/queryOpen")
    public ResultCode queryOpen(@RequestParam String merBusRegNum){
       return queryOpenService.queryOpen(merBusRegNum);
    }

    /**
     * 用户上传身份证
     * @param paramMap
     */
    @PostMapping("/IDCardVerify")
    public void IDCardVerify(@RequestParam Map<String,String> paramMap){
        idCardVerifyService.IDCardVerify(paramMap);
    }

    /**
     * 解除绑卡
     * @param paramMap
     */
    @PostMapping("/relieveBind")
    public ResultCode relieveBind(@RequestParam Map<String,String> paramMap){
        ResultCode resultCode = new ResultCode();
        resultCode.setData(bindService.relieveBind(paramMap));
        return resultCode;
    }

    /**
     *设置密码
     * @param paramMap
     */
    @PostMapping("/setPwd")
    public void setPwd(@RequestParam Map<String,String> paramMap){
        setPwdService.setPwd(paramMap);
    }
    /**
     * 查询绑卡
     * @param paramMap
     */
    @GetMapping("/queryTiedCard")
    public ResultCode queryTiedCard(@RequestParam Map<String,String> paramMap){
       return tiedCardService.queryTiedCard(paramMap);
    }

    /**
     * 代付接口
     * @param paramMap
     */
    @PostMapping("/pobo")
    public ResultCode paymentOnBehalfOf(@RequestParam Map<String,String> paramMap){
        // 回调地址:merchants/poboNotify
        return paymentOnBehalfOf.paymentOnBehalfOf(paramMap);
    }

    /**
     * 代付回调接口
     * @param pn
     */
    @PostMapping("/poboNotify")
    public ResultCode paymentOnBehalfOfNotify(PoboNotify pn){
        return paymentOnBehalfOf.paymentOnBehalfOfNotify(pn);
    }


    /**
     * 惠商用户代付回调接口
     * @param pn
     */
    @PostMapping("/hsPoboNotify")
    public ResultCode hsPaymentOnBehalfOfNotify(PoboNotify pn){
        return hsPaymentOnBehalfOf.hsPaymentOnBehalfOfNotify(pn);
    }
    /**
     * 单笔支付结果查询接口
     * @param paramMap
     */
    @PostMapping("/searchPobo")
    public ResultCode searchPaymentOnBehalfOf(@RequestParam Map<String,String> paramMap){
        return searchPayment.searchPayment(paramMap);
    }

    /**
     * 厂家退款调微信第三方退款接口
     * @param detailOrderId  订单详情表主键id
     */
    @PostMapping("/thirdRefund")
    public ResultCode WxThirdRefund(@RequestParam Integer detailOrderId){
        return wxThirdRefundService.wxThirdRefund(detailOrderId);
    }

    /**
     * 厂家退款调微信第三方退款接口
     * @param orderId  订单详情表主键id
     */
    @PostMapping("/thirdRefundMain")
    public ResultCode WxThirdRefundMain(@RequestParam Integer orderId){
        return wxThirdRefundService.wxThirdRefundMain(orderId);
    }


    /**
     * 调第三方确认收货接口
     * @param orderId  订单表主键id
     */
    @PostMapping("/checkReceive")
    public ResultCode CheckReceive(@RequestParam Integer orderId){
        return checkReceiveService.checkReceive(orderId);
    }

}
