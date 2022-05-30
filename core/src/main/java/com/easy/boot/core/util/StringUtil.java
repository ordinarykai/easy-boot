package com.easy.boot.core.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * @author kai
 * @date 2022/3/12 13:39
 */
public class StringUtil extends StringUtils {

    /**
     * 生成uuid
     *
     * @return uuid
     */
    public static String getUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 两次Md5加密
     *
     * @param plaintext 要加密的字符串
     * @return 加密后的字符串
     */
    public static String twiceMd5Encode(String plaintext) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(plaintext.getBytes(StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取文件名后缀
     *
     * @param fileName 文件名称
     * @return 文件后缀，包含.号
     */
    public static String getFileSuffix(String fileName) {
        if (Objects.isNull(fileName)) {
            return "";
        }
        int lastIndex = fileName.lastIndexOf('.');
        String suffix = "";
        if (lastIndex > 0) {
            suffix = fileName.substring(lastIndex);
        }
        return suffix;
    }

}