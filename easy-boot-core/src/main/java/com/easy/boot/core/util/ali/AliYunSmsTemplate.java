package com.easy.boot.core.util.ali;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.easy.boot.core.api.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * @author kai
 * @date 2022/3/12 14:48
 */
@Data
@Slf4j
@AllArgsConstructor
public class AliYunSmsTemplate {

    private final AliYunSmsProperties aliYunSmsProperties;

    /**
     * 单个手机号短信发送
     *
     * @param aliYunSmsChannelKey 阿里云短信模板key
     * @param phone               要发送的手机号
     * @param templateParamMap    短信模板参数map，模板无参数时传null或空map
     */
    public void sendSms(String aliYunSmsChannelKey,
                        String phone,
                        LinkedHashMap<String, String> templateParamMap) {
        Assert.notBlank(aliYunSmsProperties.getAccessKeyId(), "请配置阿里云短信AccessKeyId");
        Assert.notBlank(aliYunSmsProperties.getAccessKeySecret(), "请配置阿里云短信AccessKeySecret");
        Assert.notNull(aliYunSmsProperties.getAliYunSmsChannelMap().get(aliYunSmsChannelKey), "没有该key: {" + aliYunSmsChannelKey + "} 的阿里云短信模板");
        log.info("开始发送短信 => accessKeyId: {}, accessKeySecret: {}", aliYunSmsProperties.getAccessKeyId(), aliYunSmsProperties.getAccessKeySecret());
        log.info("开始发送短信 => aliYunSmsChannelKey: {}, phone: {}, templateParamMap: {}", aliYunSmsChannelKey, phone, templateParamMap);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliYunSmsProperties.getAccessKeyId(), aliYunSmsProperties.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("sendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", aliYunSmsProperties.getAliYunSmsChannelMap().get(aliYunSmsChannelKey).getSignName());
        request.putQueryParameter("TemplateCode", aliYunSmsProperties.getAliYunSmsChannelMap().get(aliYunSmsChannelKey).getTemplateCode());
        if (Objects.nonNull(templateParamMap) && !templateParamMap.isEmpty()) {
            request.putQueryParameter("TemplateParam", JSONObject.toJSONString(templateParamMap));
        }
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info("短信发送返回数据：{}", response.getData());
            JSONObject data = JSON.parseObject(response.getData());
            Object code = data.get("Code");
            String okCode = "ok";
            if (okCode.equalsIgnoreCase(code.toString())) {
                return;
            }
            String limitCode = "isv.BUSINESS_LIMIT_CONTROL";
            if (limitCode.equals(code)) {
                throw new ApiException("短信发送过于频繁，请稍后重试");
            }
            String msg = data.get("Message").toString();
            log.error("发送失败，原因：{}", msg);
            throw new ApiException(msg);
        } catch (ClientException e) {
            log.error("短信发送异常", e);
        }
        throw new ApiException("发送失败");
    }

    /**
     * 多个手机号短信发送
     */
    public void sendBatchSms() {
        // todo: 2022/3/12 多个手机号短信发送
    }

}