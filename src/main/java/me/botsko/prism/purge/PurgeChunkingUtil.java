package me.botsko.prism.purge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.botsko.prism.Prism;

public class PurgeChunkingUtil {


	public static long getMinimumPrimaryKey() {
		String prefix = Prism.config.getString("prism.mysql.prefix");
		long id = 0;
		Connection conn = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		try {

			conn = Prism.getPrismDataSource().getConnection();
			s = conn.prepareStatement("SELECT MIN(id) FROM " + prefix + "data");
			rs = s.executeQuery();

			if (rs.first()) {
				id = rs.getLong(1);
			}

		}
		catch (final SQLException ignored) {

		}
		finally {
			if (rs != null)
				try {
					rs.close();
				}
				catch (final SQLException ignored) {
				}
			if (s != null)
				try {
					s.close();
				}
				catch (final SQLException ignored) {
				}
			if (conn != null)
				try {
					conn.close();
				}
				catch (final SQLException ignored) {
				}
		}
		return id;
	}


	public static long getMaximumPrimaryKey() {
		String prefix = Prism.config.getString("prism.mysql.prefix");
		long id = 0;
		Connection conn = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		try {

			conn = Prism.getPrismDataSource().getConnection();
			s = conn.prepareStatement("SELECT max(id) FROM " + prefix + "data");
			rs = s.executeQuery();
			if (rs.first()) {
				id = rs.getLong(1);
			}

		}
		catch (final SQLException ignored) {

		}
		finally {
			if (rs != null)
				try {
					rs.close();
				}
				catch (final SQLException ignored) {
				}
			if (s != null)
				try {
					s.close();
				}
				catch (final SQLException ignored) {
				}
			if (conn != null)
				try {
					conn.close();
				}
				catch (final SQLException ignored) {
				}
		}
		return id;
	}
}