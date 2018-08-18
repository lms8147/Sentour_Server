package com.tou4u.sentour.content;

import com.tou4u.sentour.procedure.Procedure;

import java.util.Vector;


public class UnlikeRequester {

	private Procedure p_add_statics;
	
	public UnlikeRequester(){
		p_add_statics = new Procedure("P_ADD_STATICS");
	}
	
	public void unlike(String contentID, String sky, String hashTag, String unlike) {
		Vector<Object> inParameter = new Vector<Object>();
		inParameter.add(contentID);
		inParameter.add(hashTag);
		inParameter.add(sky);
		inParameter.add(Integer.valueOf(unlike));
		p_add_statics.runProcedure(inParameter);
	}
}
