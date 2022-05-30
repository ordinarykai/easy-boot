package com.easy.boot.core.util.ali;

import lombok.Data;

/**
 * @author kai
 */
@Data
public class AliYunSmsChannel {

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

}
