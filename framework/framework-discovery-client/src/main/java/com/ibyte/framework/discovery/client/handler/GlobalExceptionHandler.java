package com.ibyte.framework.discovery.client.handler;

import com.ibyte.common.dto.Determine;
import com.ibyte.common.dto.Response;
import com.ibyte.common.dto.Stack;
import com.ibyte.common.util.ExceptionUtil;
import com.ibyte.common.util.JsonUtil;
import com.ibyte.common.util.TraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Spring 异常处理
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Slf4j
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver,
        InitializingBean {
    protected MappingJackson2JsonView view;

    @Value("${spring.application.name}")
    private String app;

    @Override
    public void afterPropertiesSet() {
        view = new MappingJackson2JsonView(JsonUtil.getMapper());
        view.setExtractValueFromSingleKeyModel(true);
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        String traceid = TraceUtil.getTraceId();
        Determine determine = ExceptionUtil.determineType(ex);
        log.error("TraceId[{}]发生异常[{}]", traceid, determine.getName(), ex);
        ModelAndView mv = new ModelAndView(view);
        Response<?> result = null;
        LinkedList<Stack> stacks = ExceptionUtil.getStacks(ex, app, traceid);
        Stack stack = stacks.getFirst();
        Map<String, Object> data = new HashMap<>();
        data.put("traceid", traceid);
        data.put("stacks", stacks);
        result = Response.err(stack.getCode(), stack.getMessage(), data);
        mv.setStatus(determine.getStatus());
        return mv.addObject(result);
    }

}