package org.pac4j.demo.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class MyErrorController extends BasicErrorController {

    @Autowired
    public MyErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes, new ErrorProperties());
    }

    @RequestMapping(
            produces = {"text/html"}
    )
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        final HttpStatus status = getStatus(request);
        if (status == HttpStatus.UNAUTHORIZED) {
            return new ModelAndView("error401");
        } else if (status == HttpStatus.FORBIDDEN) {
            return new ModelAndView("error403");
        } else {
            return new ModelAndView("error500");
        }
    }
}
