package com.kcylog.common.exception.review;

import com.kcylog.common.exception.base.BaseException;

public class EmployeeException extends BaseException {
    private static final long serialVersionUID = 1L;
    public EmployeeException(String code, Object[] args)
    {
        super("review", code, args, null);
    }
}
