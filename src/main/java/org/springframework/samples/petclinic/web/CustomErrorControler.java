package org.springframework.samples.petclinic.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorControler implements ErrorController{

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request ) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if(status != null ) {
			int statusCode = Integer.parseInt(status.toString());
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error-404";
			}else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "error-500";
			}else if(statusCode == HttpStatus.FORBIDDEN.value()) {
				return "error-403";
			}
		}
		return "error";
	}
	
}
