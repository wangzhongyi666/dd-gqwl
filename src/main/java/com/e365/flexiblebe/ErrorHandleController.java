package com.e365.flexiblebe;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


public class ErrorHandleController implements ErrorController {
    /**
     * 错误跳转
     * @return
     * @see org.springframework.boot.autoconfigure.web.ErrorController#getErrorPath()
     */
    @Override
    public String getErrorPath() {
        return "/login_error";
    }

    @RequestMapping
    public String errorHandle(){
        return getErrorPath();
    }
}