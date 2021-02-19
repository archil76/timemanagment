package com.home.timemanagment;

public enum DatabaseState
{
    Error("Error", 0), 
    PropertyNotExist("PropertyNotExist", 1), 
    DatabaseConnectionFailed("DatabaseConnectionFailed", 2), 
    jdbcDriverFailed("jdbcDriverFailed", 3), 
    TableNotExist("TableNotExist", 4), 
    OK("OK", 5);
    
    private DatabaseState(final String name, final int ordinal) {
    }
}