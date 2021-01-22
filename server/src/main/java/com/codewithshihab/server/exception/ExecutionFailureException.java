package com.codewithshihab.server.exception;

import com.codewithshihab.server.models.Error;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExecutionFailureException extends Exception{
    private Error error;

    public ExecutionFailureException(Error error) {
        super();
        this.error = error;
    }
}
