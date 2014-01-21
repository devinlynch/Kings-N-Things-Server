package com.kings.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kings.database.DataAccess;
import com.kings.http.HttpResponseMessage;
import com.kings.http.HttpResponseError.ResponseError;
import com.kings.util.Utils;

/**
 * Subclass this class if the controller will be interacting with the database in its mappings.  This class will
 * handle transactions for each request of the subclassed controller.
 * @author devinlynch
 *
 */
public class AbstractDatabaseController implements GenericKingsControllerInterface {
	private DataAccess dataAccess = null;
	
	/**
	 * If no DataAccess is explicitly set through setDataAccess(), {@link DataAccess}.getInstance()
	 * is called
	 * @return
	 */
	public DataAccess getDataAccess() {
		if(dataAccess == null)
			dataAccess = DataAccess.getInstance();
		
		return dataAccess;
	}
	
	public void setDataAccess(DataAccess dataAccess) {
		this.dataAccess=dataAccess;
	}

	@Override
	public boolean preHandleRequest(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			DataAccess _access = getDataAccess();
			if(_access != null && !_access.isTransactionActive()) {
				_access.beginTransaction();
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void postHandleRequest(HttpServletRequest request,
			HttpServletResponse response, ModelAndView modelAndView) {
		try{
			DataAccess _access = getDataAccess();
			if(_access != null && _access.isTransactionActive()) {
				_access.commit();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@ExceptionHandler({Exception.class})
	public @ResponseBody String databaseError(HttpServletRequest req, Exception exception) {
		try{
			DataAccess _access = getDataAccess();
			if(_access != null && _access.isTransactionActive()) {
				_access.rollback();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		exception.printStackTrace();
		
		return Utils.toJson(genericError());
	}
	
	public HttpResponseMessage genericError() {
		return new HttpResponseMessage(ResponseError.GENERIC_ERROR);
	}
}
