package com.tou4u.sentour.view;

import com.tou4u.sentour.db.connect.SQLExecutor;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;


public class View {

	private String viewName;
	private SQLExecutor se;
	private ResultSet rs;

	public View(String viewName) {
		this.viewName = viewName;
		this.se = new SQLExecutor();
		this.rs = null;
	}

	public void open(List<Object> inParameter) {
		try {
			rs = se.callQuery(viewName, inParameter);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void closeVIEW() {
		se.closeQuery();
		this.rs = null;
	}

	public String getViewName() {
		return viewName;
	}

	public ResultSet getResultSet() {
		return rs;
	}

	private ResultSet getResultSet(List<Object> inParameter) {
		ResultSet rs = null;
		try {
			rs = se.callQuery(viewName, inParameter);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return rs;
	}

}
