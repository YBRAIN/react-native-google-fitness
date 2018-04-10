package com.ybrain.jsoninterpreter.statements;

import java.util.Arrays;

public class MemberMethodCallStmt extends CallStmt {
    /**
     * Target object statement
     */
    Statement target;

    /**
     * Method name to call
     */
    String methodName;

    @Override
    public Object run(JavaContext context) throws Exception {
        Object targetObject = target.execute(context, null);

        if (targetObject == null) {
            throw new InterpreterException(context, "Target object is null");
        }

        try {
            return targetObject.getClass()
                    .getMethod(methodName, getParameterTypeClasses())
                    .invoke(targetObject, getCastedParameterValues(context));
        } catch (Exception e) {
            throw new InterpreterException(context, "Failed to call member method", e);
        }
    }

    @Override
    public String toString() {
        return "MemberMethodCallStmt{" +
                "target=" + target +
                ", methodName='" + methodName + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}