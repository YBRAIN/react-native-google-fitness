package com.ybrain.jsoninterpreter.statements;

import java.util.Arrays;

public class StaticMethodCallStmt extends CallStmt {
    String className;
    String methodName;

    @Override
    public Object run(JavaContext context) {
        try {
            Class<?> targetClass = Class.forName(className);
            return targetClass
                    .getMethod(methodName, getParameterTypeClasses())
                    .invoke(null, getCastedParameterValues(context));
        } catch (Exception e) {
            throw new InterpreterException(context, "Failed to call static method : ", e);
        }
    }

    @Override
    public String toString() {
        return "StaticMethodCallStmt{" +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
