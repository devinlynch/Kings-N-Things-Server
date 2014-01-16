package com.kings.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kings.database.DataAccess;

public class GlobalKATInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
		if(handler instanceof AbstractKingsController) {
			preHandleAbstractKingsController((AbstractKingsController) handler);
		}
		
		return true;
    }
	
	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler, 
			ModelAndView modelAndView) {
		
		if(handler instanceof AbstractKingsController) {
			postHandleAbstractKingsController((AbstractKingsController) handler);
		}
	}
	
	public void preHandleAbstractKingsController(AbstractKingsController handler) {
		DataAccess access = handler.getDataAccess();
		
		if(access != null && !access.isTransactionActive()) {
			access.beginTransaction();
		}
	}
	
	public void postHandleAbstractKingsController(AbstractKingsController handler) {
		DataAccess access = handler.getDataAccess();
		
		if(access != null && access.isTransactionActive()) {
			access.commit();
		}
	}
}
