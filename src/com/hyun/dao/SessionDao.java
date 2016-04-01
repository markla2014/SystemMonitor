package com.hyun.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.cloudwave.jdbc.CloudConnection;
import com.hyun.exception.GwtException;

@Repository
public class SessionDao extends BaseDao {

	public String[][] getOnlineSessions(CloudConnection connection)
			throws GwtException {
		ArrayList<String[]> sessions = new ArrayList<String[]>();
		try {
			String[] rows = connection.getOnlineUser();
			//sessions.add(new String[] { String.valueOf(rows.length) });
			sessions.add(new String[] { "用户", "地址", "访问时间" });
			for (int i = 0; i < rows.length; i++) {
				try {
					String[] tokens = rows[i].split(",");
					sessions.add(new String[] { tokens[0], tokens[2], tokens[1] });
				} catch (Throwable e) {
				}
			}
			return sessions.toArray(new String[0][]);
		} catch (Exception t) {
			throw new GwtException(t.getMessage());
		}
	}
}
