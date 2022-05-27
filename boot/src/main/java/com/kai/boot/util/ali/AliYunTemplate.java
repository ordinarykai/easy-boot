package com.kai.boot.util.ali;

/**
 * @author kai
 * @date 2022/3/12 14:48
 */
public enum AliYunTemplate {

    /**
     * 通用验证码
     */
    COMMON_SMS("身份验证验证码",
            "xxx有限公司",
            "SMS_181105091",
            "验证码${code}，您正在进行身份验证，打死不要告诉别人哦！");

    /**
     * 模板名称
     */
    private final String templateName;
    /**
     * 模板对应的签名名称
     */
    private final String signName;
    /**
     * 模板code
     */
    private final String templateCode;
    /**
     * 模板内容
     */
    private final String templateContent;

    AliYunTemplate(String templateName,
                   String signName,
                   String templateCode,
                   String templateContent) {
        this.templateName = templateName;
        this.signName = signName;
        this.templateCode = templateCode;
        this.templateContent = templateContent;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getSignName() {
        return signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public String getTemplateContent() {
        return templateContent;
    }

}
