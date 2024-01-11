package com.aitschool.common.controller;

import com.aitschool.common.exception.BusinessException;
import com.aitschool.common.response.CommonResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 所有异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResponse exceptionHandler(Exception e) throws Exception {
        CommonResponse commonResp = new CommonResponse();
        LOG.error("系统异常：", e);
        commonResp.setError_code(1);
        commonResp.setMessage("系统出现异常，请联系管理员");
        return commonResp;
    }

    /**
     * 业务异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResponse businessExceptionHandler(BusinessException e) {
        CommonResponse commonResp = new CommonResponse();
        LOG.error("业务异常：{}", e.getE());
        commonResp.setError_code(1);
        commonResp.setMessage(e.getE());
        return commonResp;
    }

    /**
     * 404 资源
     * @param e
     * @return
     */
    @ExceptionHandler(value = NoResourceFoundException.class)
    @ResponseBody
    public CommonResponse noResourceExceptionHandler(Exception e) throws Exception {
        CommonResponse commonResp = new CommonResponse();
        commonResp.setError_code(1);
        commonResp.setMessage(e.getMessage());
        return commonResp;
    }


    /**
     * 校验异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResponse handleValidationExceptions(
            MethodArgumentNotValidException e) {
        CommonResponse commonResp = new CommonResponse();
        LOG.error("校验异常：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setError_code(1);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }

}
