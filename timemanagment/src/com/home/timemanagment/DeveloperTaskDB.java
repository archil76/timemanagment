package com.home.timemanagment;

import java.sql.SQLException;
import java.sql.DriverManager;

import java.io.FileOutputStream;

import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Properties;

public class DeveloperTaskDB {
	public static final String rootPath;
	public static final String PATH_TO_PROPERTIES = "config.properties";
	private static Properties properties;
	private static String connectorName;

	static {
		rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		DeveloperTaskDB.connectorName = "com.mysql.cj.jdbc.Driver";
	}

	public static ArrayList<DeveloperTasksList> select() {

		properties = getProperties();

		if (properties == null) {
			return null;
		}

		ArrayList<DeveloperTasksList> developerTasksLists = new ArrayList<DeveloperTasksList>();

		try {
			Class.forName(connectorName).getDeclaredConstructor().newInstance();

			try (Connection connection = getConnection()) {

				Statement statement = connection.createStatement();
				
				String sql = "SELECT id, name, isActual, state, task_id, startTime, endTime, duration FROM tasks LEFT JOIN timeintervals ON tasks.id = timeintervals.task_id ORDER BY state, id";
				
				ResultSet resultSet = statement.executeQuery(sql);
				
				String id = "@";
				String oldId = "@";
				
				int stateId;
				int oldStateId = -1;
				
				DeveloperTask developerTask = null;
				DeveloperTasksList developerTasksList = null;
				long developerTaskDuration = 0L;
				
				while (resultSet.next()) {
				
					id = resultSet.getString("id");
					String name = resultSet.getString("name");
					boolean isActual = resultSet.getBoolean("isActual");
					stateId = resultSet.getInt("state");
					TaskState taskState = TaskState.getStateFromId(stateId);
					
					if (stateId != oldStateId) {
						
						developerTasksList = new DeveloperTasksList();
						developerTasksList.state = taskState;			
						developerTasksList.list = new ArrayList<DeveloperTask>();
						
						developerTasksLists.add(developerTasksList);
					}
					
					if (!id.equals(oldId)) {
						
						developerTask = new DeveloperTask(id, name, taskState, isActual);
						
						if(developerTasksList.list!= null) {
							developerTasksList.list.add(developerTask);	
						}
						
						developerTaskDuration = 0L;
					}
					
					if (developerTask != null && resultSet.getString("task_id") != null) {
						
						developerTask.addTimeInterval(resultSet.getTimestamp("startTime"), resultSet.getTimestamp("endTime"));
						developerTaskDuration += resultSet.getLong("duration");
						developerTask.setMlsecDuration(developerTaskDuration);
					}
					
					oldId = id;
					oldStateId = stateId;
				}
			} catch (Exception ex) {
				return null;
			}
		}catch(Exception ex){
			return null;
		}
		
		return developerTasksLists;
	}

	public static DeveloperTask selectOne(final String id) {
		DeveloperTask developerTask = null;
		try {
			Class.forName(connectorName).getDeclaredConstructor().newInstance();

			try {
				final Connection connection = getConnection();
				try {
					final String sql = "SELECT id, name, isActual, state, task_id, startTime, endTime, duration FROM tasks LEFT JOIN timeintervals ON tasks.id = timeintervals.task_id WHERE id = ?";
					try {
						final PreparedStatement preparedStatement = connection.prepareStatement(sql);
						try {
							preparedStatement.setString(1, id);
							final ResultSet resultSet = preparedStatement.executeQuery();
							if (resultSet.next()) {
								String name = resultSet.getString("name");
								boolean isActual = resultSet.getBoolean("isActual");
								int stateId = resultSet.getInt("state");
								developerTask = new DeveloperTask(id, name, TaskState.getStateFromId(stateId),
										isActual);
								long developerTaskDuration = 0L;
								if (resultSet.getString("task_id") != null) {
									developerTask.addTimeInterval(resultSet.getTimestamp("startTime"),
											resultSet.getTimestamp("endTime"));
									developerTaskDuration += resultSet.getLong("duration");
									developerTask.setMlsecDuration(developerTaskDuration);
									while (resultSet.next()) {
										developerTask.addTimeInterval(resultSet.getTimestamp("startTime"),
												resultSet.getTimestamp("endTime"));
										developerTaskDuration += resultSet.getLong("duration");
										developerTask.setMlsecDuration(developerTaskDuration);
									}
								}
							}
						} finally {
							if (preparedStatement != null) {
								preparedStatement.close();
							}
						}
					} finally {

					}
				} finally {
					if (connection != null) {
						connection.close();
					}
				}
			} finally {

			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return developerTask;
	}

	public static int insertTask(final DeveloperTask developerTask) {

		try {
			Class.forName(connectorName).getDeclaredConstructor().newInstance();

			try (Connection connection = getConnection()) {
				
				String id = developerTask.getId();
				String sql = "INSERT INTO tasks (id, name) Values (?, ?)";
				
				PreparedStatement preparedStatement = connection.prepareStatement(sql);

				preparedStatement.setString(1, id);
				preparedStatement.setString(2, developerTask.getName());

				preparedStatement.executeUpdate();

			}
		} catch (Exception ex) {

			return 0;
		}
		return 1;
	}

	public static int insertTimeInterval(DeveloperTask developerTask, TimeInterval timeInterval) {

		try {
			Class.forName(connectorName).getDeclaredConstructor().newInstance();

			try (Connection connection = getConnection()) {
				String id = developerTask.getId();
				String sql = "INSERT INTO timeintervals (task_id, startTime, endTime, duration) Values (?, ?, ?, ?)";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);

				preparedStatement.setString(1, id);
				preparedStatement.setTimestamp(2, timeInterval.getStartTime());
				preparedStatement.setTimestamp(3, timeInterval.getEndTime());
				preparedStatement.setLong(4, timeInterval.getMlsecDuration());

				preparedStatement.executeUpdate();

				switchDeveloperTasks(developerTask, connection);

				completeTimeIntervals(developerTask, connection);
			}
		} catch (Exception ex) {

			return 0;
		}
		return 1;
	}

	public static int updateTask(final DeveloperTask developerTask) {
		try {
			Class.forName(connectorName).getDeclaredConstructor().newInstance();

			try (Connection connection = getConnection()) {
				String sql = "UPDATE tasks SET name = ?, state = ? WHERE id = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				String id = developerTask.getId();
				String name = developerTask.getName();
				int stateId = developerTask.getState().getId();

				preparedStatement.setString(1, name);
				preparedStatement.setInt(2, stateId);
				preparedStatement.setString(3, id);

				preparedStatement.executeUpdate();

			}
		} catch (Exception ex) {
			System.out.println(ex);
			return 0;
		}
		return 1;
	}

	public static int updateTimeInterval(DeveloperTask developerTask, TimeInterval timeInterval) {
		try {
			Class.forName(connectorName).getDeclaredConstructor().newInstance();

			try (Connection connection = getConnection()) {

				String sql = "UPDATE timeintervals SET endTime = ?, duration = ? WHERE task_id = ? AND startTime = ?";

				PreparedStatement preparedStatement = connection.prepareStatement(sql);

				preparedStatement.setTimestamp(1, timeInterval.getEndTime());
				preparedStatement.setLong(2, timeInterval.getMlsecDuration());
				preparedStatement.setString(3, developerTask.getId());
				preparedStatement.setTimestamp(4, timeInterval.getStartTime());

				preparedStatement.executeUpdate();

				switchDeveloperTasks(developerTask, connection);

				completeTimeIntervals(developerTask, connection);
			}
		} catch (Exception ex) {
			return 0;
		}

		return 1;
	}

	public static int delete(final String id) {
		try {
			Class.forName(connectorName).getDeclaredConstructor().newInstance();

			try (Connection connection = getConnection()) {

				String sql = "DELETE FROM tasks WHERE id = ?";

				PreparedStatement preparedStatement = connection.prepareStatement(sql);

				preparedStatement.setString(1, id);

				preparedStatement.executeUpdate();

				sql = "DELETE FROM timeintervals WHERE task_id = ?";

				preparedStatement = connection.prepareStatement(sql);

				preparedStatement.setString(1, id);

				preparedStatement.executeUpdate();

			}
		} catch (Exception ex) {
			return 0;
		}

		return 1;

	}

	private static void completeTimeIntervals(DeveloperTask developerTask, Connection connection) throws SQLException {
		String sql;
		PreparedStatement preparedStatement;
		sql = "UPDATE timeintervals SET endTime = CURRENT_TIMESTAMP(), duration = 1000*TIMESTAMPDIFF(SECOND, startTime, CURRENT_TIMESTAMP()) WHERE task_id <> ? AND endTime IS NULL";

		preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, developerTask.getId());

		preparedStatement.executeUpdate();
	}

	private static void switchDeveloperTasks(DeveloperTask developerTask, Connection connection) throws SQLException {

		String sql;
		PreparedStatement preparedStatement;

		sql = "UPDATE tasks SET isActual = ? WHERE id = ?";

		preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setBoolean(1, developerTask.isActual());
		preparedStatement.setString(2, developerTask.getId());

		preparedStatement.executeUpdate();

		sql = "UPDATE tasks SET isActual = '0' WHERE id <> ? and isActual = '1'";

		preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, developerTask.getId());

		preparedStatement.executeUpdate();

	}

	public static DatabaseState checkDatabase() {
		DeveloperTaskDB.properties = getProperties();
		if (DeveloperTaskDB.properties == null) {
			return DatabaseState.PropertyNotExist;
		}
		boolean databaseIsCreated = false;
		try {
			Class.forName(connectorName).getDeclaredConstructor().newInstance();
			try {

				try {
					Connection connection = getEmptyConnection();
					try {
						Statement statement = connection.createStatement();
						String sql = "SHOW DATABASES LIKE '" + DeveloperTaskDB.properties.getProperty("nameDB") + "'";
						ResultSet resultSet = statement.executeQuery(sql);
						if (resultSet.next()) {
							databaseIsCreated = true;
						} else {
							databaseIsCreated = ManageApplication.createDatabase(DeveloperTaskDB.properties,
									connection);
						}
					} finally {
						if (connection != null) {
							connection.close();
						}
					}
				} finally {

				}
			} catch (Exception ex) {
				return DatabaseState.jdbcDriverFailed;
			}
			if (!databaseIsCreated) {
				return DatabaseState.DatabaseConnectionFailed;
			}
			try {

				try {
					final Connection connection = getConnection();
					try {
						final Statement statement = connection.createStatement();
						final String sql = "show tables like 'params'";
						final ResultSet resultSet = statement.executeQuery(sql);
						if (resultSet.next()) {
							final boolean databaseIsUpdated = ManageApplication.updateDatabase(connection);
							if (databaseIsUpdated) {
								return DatabaseState.OK;
							}
							return DatabaseState.OK;
						} else {
							final boolean tablesIsCreated = ManageApplication.createAllTables(connection);
							if (tablesIsCreated) {
								return DatabaseState.OK;
							}
							return DatabaseState.TableNotExist;
						}
					} finally {
						if (connection != null) {
							connection.close();
						}
					}
				} finally {

				}
			} catch (Exception ex) {
				return DatabaseState.DatabaseConnectionFailed;
			}
		} catch (Exception ex) {
			return DatabaseState.jdbcDriverFailed;
		}

	}

	public static boolean createTables() {
		DeveloperTaskDB.properties = getProperties();
		if (DeveloperTaskDB.properties == null) {
			return false;
		}
		boolean tablesIsCreated = false;
		try {
			Class.forName(connectorName).getDeclaredConstructor().newInstance();
			try {

				try {
					final Connection connection = getConnection();
					try {
						final Statement statement = connection.createStatement();
						String sql = "CREATE TABLE tasks (id VARCHAR(36) PRIMARY KEY UNIQUE, name VARCHAR(256) DEFAULT NULL, isActual TINYINT(1) DEFAULT '0')";
						statement.executeUpdate(sql);
						sql = "CREATE TABLE timeintervals (task_id VARCHAR(36) NOT NULL, startTime DATETIME NOT NULL, endTime DATETIME DEFAULT NULL, duration BIGINT DEFAULT '0', PRIMARY KEY (task_id, startTime))";
						statement.executeUpdate(sql);
						tablesIsCreated = true;
					} finally {
						if (connection != null) {
							connection.close();
						}
					}
				} finally {

				}
			} catch (Exception ex) {
			}
		} catch (Exception ex2) {
		}
		return tablesIsCreated;
	}

	public static Properties getProperties() {
		DeveloperTaskDB.properties = new Properties();
		try {

			try {
				final FileInputStream fileInputStream = new FileInputStream("config.properties");
				try {
					DeveloperTaskDB.properties.load(fileInputStream);
					fileInputStream.close();
				} finally {
					if (fileInputStream != null) {
						fileInputStream.close();
					}
				}
			} finally {

			}
		} catch (Exception e) {
			return null;
		}
		toComplementSettings(DeveloperTaskDB.properties);
		return DeveloperTaskDB.properties;
	}

	private static void toComplementSettings(final Properties _properties) {
		
		final String url = _properties.getProperty("url");
		final int doubleSlashPositionInUrl = url.indexOf("//");
		final int secondarySlashPositionInUrl = url.indexOf("/", doubleSlashPositionInUrl + 2);
		final int questionPositionInUrl = url.indexOf("?");
		final int lengthUrl = url.length();
		
		final String serverURL = url.substring(0, secondarySlashPositionInUrl + 1);
		final String nameDB = url.substring(secondarySlashPositionInUrl + 1, questionPositionInUrl);
		final String parametres = url.substring(questionPositionInUrl, lengthUrl);
		
		//_properties.setProperty("serverURL", String.valueOf(serverURL) + parametres);
		_properties.setProperty("serverURL", String.valueOf(serverURL) + parametres);
		_properties.setProperty("nameDB", nameDB);
	
	}

	public static boolean setProperties(final Properties properties) {
		try {

			try {
				final FileOutputStream fileOutputStream = new FileOutputStream("config.properties");
				try {
					properties.store(fileOutputStream, "+");
					fileOutputStream.flush();
					fileOutputStream.close();
				} finally {
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
				}
			} finally {

			}
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	private static Connection getConnection() throws SQLException {
		if (DeveloperTaskDB.properties == null) {
			DeveloperTaskDB.properties = getProperties();
		}
		return DriverManager.getConnection(DeveloperTaskDB.properties.getProperty("url"),
				DeveloperTaskDB.properties.getProperty("username"), DeveloperTaskDB.properties.getProperty("password"));
	}

	private static Connection getEmptyConnection() throws SQLException {
		if (DeveloperTaskDB.properties == null) {
			DeveloperTaskDB.properties = new Properties();
		}
		return DriverManager.getConnection(DeveloperTaskDB.properties.getProperty("serverURL"),
				DeveloperTaskDB.properties.getProperty("username"), DeveloperTaskDB.properties.getProperty("password"));
	}
}