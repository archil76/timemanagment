package com.home.timemanagment;

public enum TaskState
{
    NEW("NEW", 0, "Новые", 0), 
    INPROCESS("INPROCESS", 1, "В процессе", 100), 
    COMPLETED("COMPLETED", 2, "Готово", 200), 
    WAITPAYMENT("WAITPAYMENT", 3, "Ожидают оплаты", 300), 
    PAID("PAID", 4, "Оплачено", 400);
    
    private String description;
    private int id;
    
    private TaskState(String name, int ordinal, String description, int id) {
        this.description = description;
        this.id = id;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return this.getDescription();
    }
    
    public static TaskState getStateFromId(int id) {
        switch (id) {
            case 0: {
                return TaskState.NEW;
            }
            case 100: {
                return TaskState.INPROCESS;
            }
            case 200: {
                return TaskState.COMPLETED;
            }
            case 300: {
                return TaskState.WAITPAYMENT;
            }
            case 400: {
                return TaskState.PAID;
            }
            default: {
                return TaskState.NEW;
            }
        }
    }
}