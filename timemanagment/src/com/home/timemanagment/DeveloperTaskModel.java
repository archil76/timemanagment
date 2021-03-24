package com.home.timemanagment;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.ServletException;


public class DeveloperTaskModel {

	public static void createDeveloperTask(String parameterName, String customer_id) throws Exception {

		try {
			
			String id = UUID.randomUUID().toString();
			String name = new String(parameterName.getBytes("ISO-8859-1"), "UTF-8");
			
			DeveloperTask developerTask = new DeveloperTask(id, name);
			
			Customer customer = new Customer(customer_id, "customer");
			developerTask.setCustomer(customer);
			
			DeveloperTaskDB.insertTask(developerTask);
					
			
		} catch (Exception ex) {
			throw ex;
		}
	}


	public static void deleteDeveloperTask(String id) throws Exception {

		try {
			DeveloperTaskDB.delete(id);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public static void deleteCustomer(String id) {
		try {
			DeveloperTaskDB.deleteCustomer(id);
		} catch (Exception ex) {
			throw ex;
		}
		
	}

	public static Map<String, Object> selectDeveloperTaskFields(String id) throws Exception {

		try {
			DeveloperTask developerTask = DeveloperTaskDB.selectTaskById(id);
			List<Customer> customersList  = DeveloperTaskDB.selectAllCustomersList();
			List<TaskState> taskStateList = Arrays.asList(TaskState.values());

			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("developerTask", developerTask);
			attributes.put("customersList", customersList);
			attributes.put("taskStateList", taskStateList);

			return attributes;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public static List<Customer> selectCustomersList(){
		return DeveloperTaskDB.selectAllCustomersList();
	}
	
	public static Customer selectCustomerFields(String id) {
		
		try {
			Customer customer = DeveloperTaskDB.selectCustomerById(id);
			return customer;

		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public static void editDeveloperTask(String id, String name, String stateString, String customer_id) throws Exception {

		try {

			name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
			
			TaskState state = TaskState.valueOf(stateString);
			Customer customer = new Customer(customer_id, "customer");
			DeveloperTask developerTask = new DeveloperTask(id, name, state);
			developerTask.setCustomer(customer);
			DeveloperTaskDB.updateTask(developerTask);

		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public static void editCustomer(String id, String name) throws Exception {
		try {

			name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
			
			Customer customer = new Customer(id, name);
			DeveloperTaskDB.updateCustomer(customer);

		} catch (Exception ex) {
			throw ex;
		}
		
	}

	public static Map<String, Object> getSettings() throws Exception {
		Map<String, Object> attributes = new HashMap<String, Object>();
		try {
			final Properties properties = DeveloperTaskDB.getProperties();
			String url = "";
			String username = "";
			String password = "";
			try {

				url = properties.getProperty("url");
				username = properties.getProperty("username");
				password = properties.getProperty("password");

			} catch (Exception ex) {
				throw ex;
			}

			attributes.put("url", url);
			attributes.put("username", username);
			attributes.put("password", password);

		} catch (Exception ex) {
			throw ex;
		}

		return attributes;
	}

	public static Map<String, Object> setSettings(String url, String username, String password) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		try {

			boolean isSettingSaved = false;

			try {

				Properties properties = new Properties();
				properties.setProperty("url", url);
				properties.setProperty("username", username);
				properties.setProperty("password", password);

				boolean propertiesIsSet = DeveloperTaskDB.setProperties(properties);
				if (propertiesIsSet) {
					isSettingSaved = true;
				} else {

					result.put("error", true);
					result.put("message", "config.properties");

					return result;
				}
			} catch (Exception ex) {

				result.put("error", true);
				result.put("message", "Settings not saved");

				return result;

			}

			if (isSettingSaved) {
				checkDatabase(result);
			}

			return result;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public static ArrayList<DeveloperTasksList> getDeveloperTasksLists() {

		return DeveloperTaskDB.select();

	}

	public static boolean startOrStopTiming(String id) {

		try {

			DeveloperTask developerTask = DeveloperTaskDB.selectTaskById(id);

			if (developerTask != null) {

				boolean isActual = developerTask.isActual();
				developerTask.setActual(!isActual);

				Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
				TimeInterval timeInterval = developerTask.getLastTimeInterval();

				if (timeInterval == null) {
					developerTask.addTimeInterval(timestamp, null);
					timeInterval = developerTask.getLastTimeInterval();
					DeveloperTaskDB.insertTimeInterval(developerTask, timeInterval);
				} else if (timeInterval.getEndTime() == null) {
					timeInterval.setEndTime(timestamp);
					DeveloperTaskDB.updateTimeInterval(developerTask, timeInterval);
				} else {
					developerTask.addTimeInterval(timestamp, null);
					timeInterval = developerTask.getLastTimeInterval();
					DeveloperTaskDB.insertTimeInterval(developerTask, timeInterval);
				}
			} else {
				return false;
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return true;

	}
	
	public static void createCustomer(String parameterName) throws Exception {

		try {
			String id = UUID.randomUUID().toString();
			String name = new String(parameterName.getBytes("ISO-8859-1"), "UTF-8");
			Customer customer = new Customer(id, name);
			DeveloperTaskDB.insertCustomer(customer);

		} catch (Exception ex) {
			throw ex;
		}
	}	

	private static void checkDatabase(Map<String, Object> result) throws IOException, ServletException {

		DatabaseState databaseState = DeveloperTaskDB.checkDatabase();

		if (databaseState == DatabaseState.OK) {

			result.put("databaseState", DatabaseState.OK);
			return;

		} else if (databaseState == DatabaseState.TableNotExist) {

			boolean tablesIsCreated = createTables();

			if (tablesIsCreated) {

				result.put("databaseState", "OK");
				return;

			} else {

				result.put("error", true);
				result.put("message", "Tables is not created");
				return;

			}
		} else {

			result.put("error", true);
			result.put("message", databaseState.toString());

			return;
		}
	}

	public static boolean createTables() {
		return DeveloperTaskDB.createTables();
	}








}
