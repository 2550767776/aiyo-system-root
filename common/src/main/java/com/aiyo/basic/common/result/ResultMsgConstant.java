package com.aiyo.basic.common.result;

/**
 * 返回参数常量配置
 **/
public class ResultMsgConstant {
    public static final String Data_Result_Key = "data";//非列表数据
    public static final String List_Result_Key = "list";//列表数据
    public static final String Page_Result_Key = "page";
    //用于后端表单数据返回
    public static final String Rows_Result_Key = "rows";
    public static final String Total_Result_Key = "total";

    private static final String sucessMsg = "成功!";
    private static final String failMsg = "失败!";

    private static final String addMsg = "新增";
    private static final String updateMsg = "修改";
    private static final String deleteMsg = "删除";
    private static final String distributeMsg = "分配";
    private static final String settingMsg = "设置";
    private static final String orderMsg = "下单";
    public static final String deleteContainsFkFailMsg = "删除的id已经被使用，不能删除";
    public static final String sealMsg = "封号";
    public static final String unsealMsg = "解除封号";
    public static final String cancelMsg = "取消资格";
    public static final String cancelserviceMsg = "取消服务";
    public static final String uncancelMsg = "恢复";
    public static final String pushMsg = "发布";
    public static final String cancelpushMsg = "取消发布";
    public static final String topMsg = "置顶";
    public static final String canceltopMsg = "取消置顶";
    public static final String operationMsg = "操作";


    /**
     * 返回各自类型成功的消息
     *
     * @param way
     * @return
     */
    public static String getSuccessResultMsg(OperatorWays way) {
        return way.getValue() + sucessMsg;
    }

    /**
     * 返回各自类型失败的消息
     *
     * @param way
     * @return
     */
    public static String getFailResultMsg(OperatorWays way) {
        return way.getValue() + failMsg;
    }

    public enum OperatorWays {
        ADD_MSG {
            @Override
            public String getValue() {
                return addMsg;
            }
        },
        UPDATE_MSG {
            @Override
            public String getValue() {
                return updateMsg;
            }
        },
        DELETE_MSG {
            @Override
            public String getValue() {
                return deleteMsg;
            }
        },
        DELETE_CONTAINS_FK_FAIL_MSG {
            @Override
            public String getValue() {
                return deleteContainsFkFailMsg;
            }
        },
        DISTRIBUTE_MSG {
            @Override
            public String getValue() {
                return distributeMsg;
            }
        },
        SETTING_MSG {
            @Override
            public String getValue() {
                return settingMsg;
            }
        },
        ORDER_MSG {
            @Override
            public String getValue() {
                return orderMsg;
            }
        },
        SEAL_MSG {
            @Override
            public String getValue() {
                return sealMsg;
            }
        },
        UNSEAL_MSG {
            @Override
            public String getValue() {
                return unsealMsg;
            }
        },
        CANCEL_MSG {
            @Override
            public String getValue() {
                return cancelMsg;
            }
        },
        UNCANCEL_MSG {
            @Override
            public String getValue() {
                return uncancelMsg;
            }
        },
        CANCELSERVICE_MSG {
            @Override
            public String getValue() {
                return cancelserviceMsg;
            }
        },
        PUSH_MSG {
            @Override
            public String getValue() {
                return pushMsg;
            }
        },
        CANCELPUSH_MSG {
            @Override
            public String getValue() {
                return cancelpushMsg;
            }
        },
        TOP_MSG {
            @Override
            public String getValue() {
                return topMsg;
            }
        },
        CANCELTOP_MSG {
            @Override
            public String getValue() {
                return canceltopMsg;
            }
        },
        OPERATION_MSG {
            @Override
            public String getValue() {
                return operationMsg;
            }
        }, CANNOT_DELETE {
            @Override
            public String getValue() {
                return deleteContainsFkFailMsg;
            }
        };

        public abstract String getValue();
    }

}
