package com.easy.boot.core.log.aop;

import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author kai
 */
public class WebLogPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private final String expression;

    public WebLogPointcutAdvisor(String expression) {
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(this.expression);
        return pointcut;
    }

}