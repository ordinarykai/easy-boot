package com.easy.boot.mybatis.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.util.Properties;

/**
 * @author kai
 */
public class YamlUtil {

    public static final Properties PROPERTIES;

    static {
        PROPERTIES = loadConfig();
    }

    public static Properties loadConfig() {
        Properties properties = new Properties();

        try {
            properties.putAll(loadBootConfig("application.yml"));
        } catch (Exception ignored) {
            properties.putAll(loadBootConfig("application.yaml"));
        }
        try {
            properties.putAll(loadBootConfig("bootstrap.yml"));
        } catch (Exception ignored) {
            try {
                properties.putAll(loadBootConfig("bootstrap.yaml"));
            } catch (Exception ignored1) {

            }
        }

        String env = properties.getProperty("spring.profiles.active");
        if (StringUtils.isNotBlank(env)) {
            try {
                properties.putAll(loadBootConfig("application-" + env + ".yml"));
            } catch (Exception ignored) {
                properties.putAll(loadBootConfig("application-" + env + ".yaml"));
            }
        }
        return properties;

    }

    private static Properties loadBootConfig(String classpathConfig) {
        Resource parentResource = new ClassPathResource(classpathConfig);
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(parentResource);
        return yamlPropertiesFactoryBean.getObject();
    }

    public static Object get(Object key) {
        return PROPERTIES.get(key);
    }

    public static Object get(Object key, Object defaultValue) {
        return PROPERTIES.getOrDefault(key, defaultValue);
    }

    public static Object getAndAssert(Object key) {
        Object obj = PROPERTIES.get(key);
        Assert.notNull(obj, "please add property: " + key);
        return obj;
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static String getPropertyAndAssert(String key) {
        String property = PROPERTIES.getProperty(key);
        Assert.hasText(property, "please add property: " + key);
        return property;
    }
}
