package com.maco.client.controller;

import com.maco.common.po.ResultMap;
import com.maco.common.po.ResultMapUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/wx")
public class WeixinController {
    private final WxMpService wxMpService;
    private final WxMpMessageRouter messageRouter;
    @RequestMapping("/handler")
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);

            String signature = request.getParameter("signature");
            String nonce = request.getParameter("nonce");
            String timestamp = request.getParameter("timestamp");

            if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
                // 消息签名不正确，说明不是公众平台发过来的消息
                response.getWriter().println("非法请求");
                return;
            }

            String echostr = request.getParameter("echostr");
            if (StringUtils.isNotBlank(echostr)) {
                // 说明是一个仅仅用来验证的请求，回显echostr
                response.getWriter().println(echostr);
                return;
            }

            String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ?
                    "raw" :
                    request.getParameter("encrypt_type");

            if ("aes".equals(encryptType)) {
                // 是aes加密的消息
                String msgSignature = request.getParameter("msg_signature");
                WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
                log.info("消息解密后内容为：{} ", inMessage.toString());
                WxMpXmlOutMessage outMessage = messageRouter.route(inMessage);
                if (outMessage == null) {
                    return;
                }
                response.getWriter().write(outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage()));
                return;
            }

            response.getWriter().println("不可识别的加密类型");
        } catch (IOException e) {
            log.error("腾讯回调方法异常", e);
        }
    }

    @PostMapping("/jsapiSignature")
    public ResultMap jsapiSignature(@RequestBody Map<String, String> params, HttpServletRequest request, HttpServletResponse response){
        try {
            String jsapiTicket = wxMpService.getJsapiTicket();
            return ResultMapUtil.success();
        } catch (Exception e) {
            log.error("获取jsapiTicket异常", e);
            return ResultMapUtil.exception("获取jsapiTicket异常");
        }
    }

}
