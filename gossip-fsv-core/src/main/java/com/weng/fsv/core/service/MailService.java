package com.weng.fsv.core.service;

/**
 * @author wengchengjian
 * @date 2023/8/11-13:58
 */
public interface MailService {

    /**
     * 带附件的邮件发送方法
     * @param toUsers 接收人
     * @param subject 主题
     * @param content 内容
     * @param attachPaths 附件地址
     * @return java.lang.String
     * @since 2023-07-10 17:03
     */
    void sendMail(String[] toUsers,String subject, String content,String... attachPaths) throws Exception;

    void sendCodeMail(String toUser) throws Exception;

    boolean verifyCode(String user, String code);
}
