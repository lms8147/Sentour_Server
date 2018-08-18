package com.tou4u.sentour.view;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.json.simple.*;

public class ViewToJson {

	private View view;

	public ViewToJson(String viewName) {
		this.view = new View(viewName);
	}

	private void open(List<Object> inParameter) {
		view.open(inParameter);
	}

	private void close() {
		view.closeVIEW();
	}

	public String toJSONString(List<Object> inParameter) {
		open(inParameter);
		ResultSet rs = view.getResultSet();

		JSONObject table = new JSONObject();
		JSONArray records = new JSONArray();

		try {
			// result 의 첫 커서 부터 끝까지 데이터 출력
			while (rs.next()) {
				// 각 열에 대한 데이터 출력
				JSONObject record = educeData(rs);
				records.add(record);
			}
			// 쿼리 결과 제거
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		table.put(view.getViewName(), records);

		close();

		return table.toJSONString();
	}

	public static String a = "";

	private JSONObject educeData(ResultSet rs) {

		JSONObject record = new JSONObject();

		try {
			ResultSetMetaData md = rs.getMetaData();

			for (int idx = 1; idx <= md.getColumnCount(); idx++) {
				String columnLabel = md.getColumnLabel(idx);
				record.put(columnLabel, rs.getString(columnLabel));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return record;
	}
}
