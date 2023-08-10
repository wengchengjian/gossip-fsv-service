package com.weng.fsv.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wengchengjian
 * @date 2022/7/6-9:43
 */
@Slf4j
public class HttpClientUtil {
    public static String post(String url, Map<String, String> mapParameter) {
        log.debug("开始请求: url = {}, mapParameter = {}", url, mapParameter);
        // 创建httpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建POST请求
            HttpPost httpPost = new HttpPost(url);
            // 设置参数
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            // 迭代参数
            if (mapParameter != null && mapParameter.size() > 0) {
                mapParameter.forEach((k, v) -> nameValuePairList.add(new BasicNameValuePair(k, v)));
            }
            // 编码
            if (nameValuePairList.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairList);
                httpPost.setEntity(entity);
            }
            // 执行post请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response != null) {
                    log.info("请求成功, 响应状态: {}", response.getStatusLine().getStatusCode());
                    HttpEntity httpEntity = response.getEntity();
                    // 如果返回的内容不为空
                    if (httpEntity != null) {
                        return EntityUtils.toString(httpEntity);
                    }
                }
            } catch (Exception e) {
                log.error("请求异常: e = {}", e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            log.error("请求异常: e = {}", e);
            e.printStackTrace();
        }
        return null;
    }
}
