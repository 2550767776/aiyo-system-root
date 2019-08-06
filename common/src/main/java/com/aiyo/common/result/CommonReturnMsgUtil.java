package com.aiyo.common.result;

/**
 * 消息返回工具
 */
public class CommonReturnMsgUtil {
    /**
     * 根据判断和操作方式，返回对应的消息，错误，正确
     *
     * @param result
     * @param operatorWays
     * @return
     */
    public static R getCommonReturnMsg(boolean result, ResultMsgConstant.OperatorWays operatorWays) {
        if (result) {
            return R.ok(ResultMsgConstant.getSuccessResultMsg(operatorWays));
        } else {
            return R.ok(ResultMsgConstant.getFailResultMsg(operatorWays));
        }
    }

}
