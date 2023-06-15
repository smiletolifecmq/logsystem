package com.kcylog.common.exception.review;

public class EmployeeExistsException extends  EmployeeException{
    private static final long serialVersionUID = 1L;
    public EmployeeExistsException()
    {
        super("employee.exists", null);
    }
}
