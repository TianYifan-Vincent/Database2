package com.shu.carsystem.common;


public enum ResultEnum {
    //这里是可以自己定义的，方便与前端交互即可
    UNKNOWN_ERROR(-1,"未知错误"),
    USER_NOT_EXIST(1,"用户不存在"),
    USER_IS_EXISTS(2,"用户已存在"),
    DATA_IS_NULL(3,"数据为空"),
    USER_NOT_LOGIN(4,"用户未登录"),
    BAD_FORMAT(5,"格式错误"),
    Vehicle_NOT_EXISTS(6,"车辆不存在"),
    NOT_NULLABLE(7,"数据不可非空"),
    SUCCESS(200,"成功"),
    INSERT_ERROR(444,"插入失败"),
    INSERT_SUCCESS(454,"插入成功"),
    QUERY_SUCCESS(455,"查询成功"),
    UPDATE_ERROR(456,"更新失败"),
    UPDATE_SUCCESS(457,"更新成功"),
    DELETE_ERROR(333,"删除失败"),
    DELETE_SUCCESS(334,"删除成功"),
    VEHICLE_DATA(500,"车辆数据传输"),
    REPAIR_DATA(501,"维修信息传输"),
    USER_DATA(502,"用户信息传输"),
    CLIENT_DATA(503,"客户信息传输"),
    ARRANGE_FAILURE(504,"派工失败"),
    ARRANGE_SUCCESS(505,"派工成功");




    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
