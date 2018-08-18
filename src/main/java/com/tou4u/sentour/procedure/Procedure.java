package com.tou4u.sentour.procedure;

import java.io.IOException;
import java.util.List;

import com.tou4u.sentour.db.connect.SQLExecutor;

public class Procedure {

	private String procedureName;

	public Procedure(String procedureName) {
		this.procedureName = procedureName;
	}

	public boolean runProcedure(List<Object> inParameter, List<Object> outParameter, int... sqlTypes) {
		SQLExecutor se = new SQLExecutor();
		boolean success;
		try {
			se.callQuery(procedureName, inParameter, outParameter, sqlTypes);
			se.closeQuery();
			success = true;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			success = false;
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			success = false;
		}
		return success;
	}

	public boolean runProcedure(List<Object> inParameter) {
		SQLExecutor se = new SQLExecutor();
		boolean success;
		try {
			se.callQuery(procedureName, inParameter);
			se.closeQuery();
			success = true;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			success = false;
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			success = false;
		}
		return success;
	}

}
