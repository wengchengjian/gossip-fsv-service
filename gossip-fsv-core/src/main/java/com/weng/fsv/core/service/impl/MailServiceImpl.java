package com.weng.fsv.core.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.weng.fsv.core.config.FsvCodeProperties;
import com.weng.fsv.core.service.MailService;
import com.weng.fsv.utils.ValidateCodeUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * @author wengchengjian
 * @date 2023/8/11-13:58
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String userName;

    @Resource
    private FsvCodeProperties fsvCodeProperties;

    @Resource
    private JavaMailSender sender;

    /**
     * 验证码缓存
     */
    private Cache<String, String> codeCache;

    @PostConstruct
    public void init() {
        codeCache = Caffeine.newBuilder().expireAfterWrite(fsvCodeProperties.getTimeout()).build();
    }

    public void sendMail(String[] toUsers,String subject, String content, String... attachPaths) throws MessagingException, FileNotFoundException {
        // MIME邮件类
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        // 邮件发送方From和接收方To
        helper.setFrom(userName);
        helper.setTo(toUsers);
        // 邮件主题和内容
        helper.setSubject(subject);
        helper.setText(content);
        for (String attachPath : attachPaths) {
            // 邮件中的附件
            File attachFile = ResourceUtils.getFile(attachPath);
            helper.addAttachment(attachFile.getName(), attachFile);
        }
        // 执行邮件发送命令
        sender.send(mimeMessage);
    }

    @Override
    public void sendCodeMail(String toUser) throws Exception {
        String codeStr = ValidateCodeUtils.generateValidateCode(6);
        codeCache.put(toUser, codeStr);
        String subject = "FSV 验证你的邮件";
        //TODO 需要更精美的模板
        String mailTmp = "<h1>验证你的电子邮件地址</h1> <span>你的代码是:%s</span>";
        String content = String.format(mailTmp, codeStr);

        sendMail(new String[]{toUser}, subject, content);
    }

    @Override
    public boolean verifyCode(String user, String code) {
        String existedCode = codeCache.getIfPresent(user);
        return StringUtils.hasText(existedCode) && existedCode.equals(code);
    }

}
