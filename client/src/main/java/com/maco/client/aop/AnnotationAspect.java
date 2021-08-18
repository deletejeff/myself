package com.maco.client.aop;

import com.maco.client.annotation.RoleAdmin;
import com.maco.client.annotation.RoleStaff;
import com.maco.client.utils.SessionUtil;
import com.maco.common.enums.MySelfEnums;
import com.maco.common.po.MyWxMpUser;
import com.maco.common.po.ResultMap;
import com.maco.common.po.UserRole;
import com.maco.common.utils.Constants;
import com.maco.common.utils.JsonUtils;
import com.maco.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
@Aspect
@AllArgsConstructor
public class AnnotationAspect {
    private final UserService userService;
    @Around("execution(* com.maco.client.controller.*.*(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            if(response == null){
                return null;
            }
            response.setContentType("application/json;charset=utf-8");
            if(Constants.isUnCheckUrl(request.getRequestURI())){
                return joinPoint.proceed();
            }
            MyWxMpUser sessionUser = SessionUtil.getSessionUser(request);
            if(sessionUser == null){
                ResultMap resultMap = new ResultMap();
                resultMap.setRetcodeRetmsg(MySelfEnums.MySelfCommEnums.USER_FAIL);
                log.info(JsonUtils.toJson(resultMap));
                response.getWriter().write(JsonUtils.toJson(resultMap));
                response.getWriter().flush();
                response.getWriter().close();
                return null;
            }
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            UserRole role = userService.getRole(sessionUser.getOpenId());
            if (method.isAnnotationPresent(RoleAdmin.class)) {
                if (!Constants.ROLE_ADMIN.equals(role.getRole())) {
                    ResultMap resultMap = new ResultMap();
                    resultMap.setRetcodeRetmsg(MySelfEnums.MySelfCommEnums.ADMIN_FAIL);
                    log.info(JsonUtils.toJson(resultMap));
                    response.getWriter().write(JsonUtils.toJson(resultMap));
                    response.getWriter().flush();
                    response.getWriter().close();
                    return null;
                }
            }else if(method.isAnnotationPresent(RoleStaff.class)){
                if (!Constants.ROLE_ADMIN.equals(role.getRole())) {
                    ResultMap resultMap = new ResultMap();
                    resultMap.setRetcodeRetmsg(MySelfEnums.MySelfCommEnums.STAFF_FAIL);
                    log.info(JsonUtils.toJson(resultMap));
                    response.getWriter().write(JsonUtils.toJson(resultMap));
                    response.getWriter().flush();
                    response.getWriter().close();
                    return null;
                }
            }
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error("aop切面异常", e);
            return joinPoint.proceed();
        }
    }
}
