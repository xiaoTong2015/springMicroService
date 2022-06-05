package com.thoughtmechanix.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Zuul 前置过滤器
 */
@Component
public class TrackingFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    @Autowired
    private FilterUtils filterUtils;

    /**
     * 设置过滤器为前置过滤器
     * @return
     */
    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    /**
     * 返回一个整数，用来指定过滤器的执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    /**
     * 返回一个布尔值，用来指定该过滤器是否要执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    /**
     * run() 方法是每次服务通过过滤器时执行的代码
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        logger.info("run TrackingFilter（前置过滤器）");
        return null;
    }
}
