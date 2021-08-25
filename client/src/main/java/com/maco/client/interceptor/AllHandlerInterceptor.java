package com.maco.client.interceptor;

import com.maco.common.enums.MySelfEnums;
import com.maco.common.po.ResultMap;
import com.maco.common.utils.Constants;
import com.maco.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Component
public class AllHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ResultMap resultMap = new ResultMap();
        if(Constants.isUnCheckUrl(request.getRequestURI())){
            String sessionToken = UUID.randomUUID().toString().replace("-", "");
            request.getSession().setAttribute("token", sessionToken);
            response.addHeader("token", sessionToken);
            return true;
        }
        String token = request.getHeader("token");
        String sessionToken = String.valueOf(request.getSession().getAttribute("token"));
        if (!StringUtils.hasLength(token)) {
            log.info("请求头中没有token，不允许访问");
            resultMap.setRetcodeRetmsg(MySelfEnums.MySelfCommEnums.TOKEN_VALIDATE_FAIL);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JsonUtils.toJson(resultMap));
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        }
        if (token.equals(sessionToken)) {
            sessionToken = UUID.randomUUID().toString().replace("-", "");
            request.getSession().setAttribute("token", sessionToken);
            response.addHeader("token", sessionToken);
            return true;
        } else {
            log.info("请求头中token与服务器token不匹配");
            resultMap.setRetcodeRetmsg(MySelfEnums.MySelfCommEnums.TOKEN_VALIDATE_FAIL);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JsonUtils.toJson(resultMap));
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        }
    }
}
