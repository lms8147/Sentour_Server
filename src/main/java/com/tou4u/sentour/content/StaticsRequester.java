package com.tou4u.sentour.content;

import com.tou4u.sentour.procedure.Procedure;

import java.sql.Types;
import java.util.Vector;


public class StaticsRequester {

	private Procedure p_calc_statics;

	public StaticsRequester() {
		p_calc_statics = new Procedure("P_CALC_STATICS");
	}

	public double[] statics(String contentID, String sky, String hashTag) {
		Vector<Object> inParameter = new Vector<Object>();
		inParameter.add(contentID);
		inParameter.add(hashTag);
		inParameter.add(sky);
		Vector<Object> outParameter = new Vector<Object>();
		p_calc_statics.runProcedure(inParameter, outParameter, Types.DOUBLE, Types.DOUBLE);
		return new double[] {(double) outParameter.get(0), (double) outParameter.get(1)};
	}
}
