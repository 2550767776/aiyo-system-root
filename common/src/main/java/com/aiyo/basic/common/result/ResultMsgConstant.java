package com.aiyo.basic.common.result;

/**
 * 返回参数常量配置
 **/
public class ResultMsgConstant {

    public static final String DATA_RESULT_KEY = "data";
    public static final String LIST_RESULT_KEY = "list";
    public static final String PAGE_RESULT_KEY = "page";

    public static final String ROWS_RESULT_KEY = "rows";
    public static final String TOTAL_RESULT_KEY = "total";

    private static final String SUCCESS_MSG = "成功!";
    private static final String FAIL_MSG = "失败!";

    private static final String ADD_MSG = "新增";
    private static final String UPDATE_MSG = "修改";
    private static final String DELETE_MSG = "删除";

    /**
     * 返回各自类型成功的消息
     *
     * @param way
     * @return
     */
    public static String getSuccessResultMsg(OperatorWays way) {
        return way.getValue() + SUCCESS_MSG;
    }

    /**
     * 返回各自类型失败的消息
     *
     * @param way
     * @return
     */
    public static String getFailResultMsg(OperatorWays way) {
        return way.getValue() + FAIL_MSG;
    }

    public enum OperatorWays {
        //  添加
        ADD_MSG {
            @Override
            public String getValue() {
                return ResultMsgConstant.ADD_MSG;
            }
        },
        // 修改
        UPDATE_MSG {
            @Override
            public String getValue() {
                return ResultMsgConstant.UPDATE_MSG;
            }
        },
        // 删除
        DELETE_MSG {
            @Override
            public String getValue() {
                return ResultMsgConstant.DELETE_MSG;
            }
        };
        public abstract String getValue();
    }

}
