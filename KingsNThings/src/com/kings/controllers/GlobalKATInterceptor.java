package com.kings.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class GlobalKATInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
		if(handler instanceof GenericKingsControllerInterface) {
			return ((GenericKingsControllerInterface) handler).preHandleRequest(request, response);
		}
		
		return true;
    }
	
	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler, 
			ModelAndView modelAndView) {
		
		if(handler instanceof GenericKingsControllerInterface) {
			((GenericKingsControllerInterface) handler).postHandleRequest(request, response, modelAndView);
		}
	}
}
