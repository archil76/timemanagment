package com.home.timemanagment;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Properties;

public class ManageApplication {
	private static final int appVersion = 3;

	protected static int getAppVersion() {
		return 2;
	}

	public static boolean createDatabase(final Properties properties, final Connection connection) {
		if (properties == null) {
			return false;
		}
		boolean databaseIsCreated = false;
		try {
			final Statement statement = connection.createStatement();
			final String sql = "CREATE DATABASE " + properties.getProperty("nameDB") + ";";
			statement.executeUpdate(sql);
			databaseIsCreated = true;
		} catch (Exception ex) {
		}
		return databaseIsCreated;
	}

	public static boolean createAllTables(final Connection connection) {
		boolean tablesIsCreated = false;
		try {
			final Statement statement = connection.createStatement();
			String sql = "CREATE TABLE params (name VARCHAR(36) NOT NULL, intValue INT DEFAULT '0')";
			statement.executeUpdate(sql);
			sql = "INSERT params VALUES ('appVersion', 2)";
			statement.executeUpdate(sql);
			sql = "CREATE TABLE tasks (id VARCHAR(36) PRIMARY KEY UNIQUE, name VARCHAR(256) DEFAULT NULL, isActual TINYINT(1) DEFAULT '0', state INT DEFAULT '0')";
			statement.executeUpdate(sql);
			sql = "CREATE TABLE timeintervals (task_id VARCHAR(36) NOT NULL, startTime DATETIME NOT NULL, endTime DATETIME DEFAULT NULL, duration BIGINT DEFAULT '0', PRIMARY KEY (task_id, startTime))";
			statement.executeUpdate(sql);
			tablesIsCreated = true;
		} catch (Exception ex) {
		}
		return tablesIsCreated;
	}

	protected static boolean updateDatabase(final Connection connection) {

		boolean databaseIsUpdated = false;

		int appVersionInDB = 0;

		String sql = "SELECT intValue FROM params WHERE name = 'appVersion'";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				appVersionInDB = resultSet.getInt(1);
			}
		} catch (Exception ex) {
			return false;
		}

		if (appVersionInDB == appVersion) {
			return true;
		}

		int curAppVersion = appVersionInDB;

		if (curAppVersion == 0) {
			curAppVersion++;
		}

		if (curAppVersion == 1) {
			if(updateDatabaseInVer2(connection)) {
				curAppVersion++;	
			} else {
				return false;
			}
			
		}

		if (curAppVersion == 2) {
			if(updateDatabaseInVer3(connection)) {
				curAppVersion++;	
			} else {
				return false;
			}
		}

		databaseIsUpdated = true;

		return databaseIsUpdated;
	}

	private static boolean updateDatabaseInVer2(final Connection connection) {
		boolean tablesIsCreated = false;
		try(Statement statement = connection.createStatement()) {
			
			String sql = "ALTER TABLE tasks ADD state INT DEFAULT '0'";
			statement.executeUpdate(sql);
			
			sql = "UPDATE params SET intValue = 2 WHERE name = 'appVersion'";
			statement.executeUpdate(sql);
			
			tablesIsCreated = true;
		
		} catch (Exception ex) {
		}
		return tablesIsCreated;
	}

	private static boolean updateDatabaseInVer3(final Connection connection) {
		boolean tablesIsCreated = false;
		try {
			Statement statement = connection.createStatement();
			String sql = "UPDATE tasks SET isActual = 0 where not (id in (select task_id from timeintervals where endTime is NULL))";
			statement.executeUpdate(sql);
			sql = "UPDATE params SET intValue = 3 WHERE name = 'appVersion'";
			statement.executeUpdate(sql);
			tablesIsCreated = true;
		} catch (Exception ex) {
		}
		return tablesIsCreated;
	}
}