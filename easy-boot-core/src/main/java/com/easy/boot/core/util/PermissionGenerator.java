package com.easy.boot.core.util;

import com.easy.boot.core.auth.PreAuthorize;
import com.easy.boot.mybatis.util.YamlUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限数据生成工具类
 *
 * @author kai
 */
@Slf4j
public class PermissionGenerator {

    public static void execute() throws BeansException {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        Set<BeanDefinition> candidateComponents = provider.findCandidateComponents(new PermissionGeneratorBO().getControllerPackage());
        Set<String> permissions = new HashSet<>();
        candidateComponents.forEach((beanDefinition) -> {
            String className = beanDefinition.getBeanClassName();
            assert className != null;
            className = className.split("\\$\\$")[0];
            try {
                Class<?> clazz = Class.forName(className);
                for (Method method : clazz.getDeclaredMethods()) {
                    PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);
                    if (preAuthorize != null) {
                        permissions.add(preAuthorize.value());
                    }
                }
            } catch (ClassNotFoundException e) {
                log.error("获取权限发生异常", e);
            }
        });
        // TODO: 2022/6/2 目前只是打印出来
        log.info("=============权限集合如下=============");
        log.info(String.valueOf(permissions));
    }

    @Data
    public static class PermissionGeneratorBO {

        private String controllerPackage;

        public PermissionGeneratorBO() {
            controllerPackage = YamlUtil.getPropertyAndAssert("generator.permission.controller-package");
        }

    }

}

