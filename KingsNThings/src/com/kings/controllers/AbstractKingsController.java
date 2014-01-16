package com.kings.controllers;

import com.kings.database.DataAccess;

public abstract class AbstractKingsController {
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
}
