package com.kai.boot.util.ali;

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
import com.kai.boot.api.exception.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * @author kai
 * @date 2022/3/12 14:47
 */
@Slf4j
public class AliYunSmsUtil {

    //参考文档：https://help.aliyun.com/document_detail/55284.html

    /**
     * 阿里云短信accessKeyId
     */
    private static final String ACCESS_KEY_ID = "";
    /**
     * 阿里云短信accessKeySecret
     */
    private static final String ACCESS_KEY_SECRET = "";

    /**
     * 单个手机号短信发送
     *
     * @param aliYunTemplate   阿里云短信模板枚举
     * @param phone            要发送的手机号
     * @param templateParamMap 短信模板参数map，模板无参数时传null或空map
     */
    public static void sendSms(AliYunTemplate aliYunTemplate,
                               String phone,
                               LinkedHashMap<String, String> templateParamMap) {
        Assert.notBlank(ACCESS_KEY_ID, "请配置阿里云短信AccessKeyId");
        Assert.notBlank(ACCESS_KEY_SECRET, "请配置阿里云短信AccessKeySecret");
        log.info("开始发送短信 => accessKeyId: {}, accessKeySecret: {}", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        log.info("开始发送短信 => aliYunTemplate: {}, phone: {}, templateParamMap: {}", aliYunTemplate, phone, templateParamMap);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("sendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", aliYunTemplate.getSignName());
        request.putQueryParameter("TemplateCode", aliYunTemplate.getTemplateCode());
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
