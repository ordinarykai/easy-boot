package com.easy.boot.core.log.trace.id;

import com.easy.boot.core.util.StringUtil;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 日志链路traceId追踪
 *
 * @author kai
 */
@Order(0)
@WebFilter(urlPatterns = "/*")
public class TraceIdFilter implements Filter {

    /**
     * 日志跟踪标识
     */
    public static final String TRACE_ID = "traceId";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        MDC.put(TRACE_ID, StringUtil.getUuid());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        MDC.clear();
    }

}