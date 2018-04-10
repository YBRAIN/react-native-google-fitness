package com.ybrain.jsoninterpreter.statements;

public class InterpreterException extends RuntimeException {
    public InterpreterException(JavaContext context) {
        this(context, null);
    }

    public InterpreterException(JavaContext context, String message) {
        this(context, message, null);
    }

    public InterpreterException(JavaContext context, String message, Throwable cause) {
        super(message + "\n"
             //   + "Statement : " + context.getCurrentStatement() + "\n"
                + "Callstack : \n"
                + context.getInterpreterCallStack(), cause);
    }
}
