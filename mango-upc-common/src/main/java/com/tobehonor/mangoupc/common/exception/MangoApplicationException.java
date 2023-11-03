package com.tobehonor.mangoupc.common.exception;

/**
 * mango异常信息父类
 *
 * @author William Chen
 * @since 2022/8/7
 */
public class MangoApplicationException extends RuntimeException {
    
    private static final long serialVersionUID = 7644497448384705866L;
    
    /**
     * 默认构造器
     */
    public MangoApplicationException() {
    }
    
    /**
     * 带消息的异常信息
     *
     * @param message 消息
     */
    public MangoApplicationException(String message) {
        super(message);
    }
    
    /**
     * 带消息和异常原因的异常信息
     *
     * @param message 消息
     * @param cause   异常原因
     */
    public MangoApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * 带异常原因的异常信息
     *
     * @param cause 异常原因
     */
    public MangoApplicationException(Throwable cause) {
        super(cause);
    }
}
