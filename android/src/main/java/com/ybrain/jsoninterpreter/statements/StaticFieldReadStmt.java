package com.ybrain.jsoninterpreter.statements;

import java.util.Arrays;

public class StaticFieldReadStmt extends CallStmt {
    /**
     * Target class canonical name
     */
    String className;

    /**
     * Field name to access
     */
    String fieldName;

    @Override
    public Object run(JavaContext context) throws Exception {
        try {
            return Class.forName(className).getField(fieldName).get(null);
        } catch (Exception e) {
            throw new InterpreterException(context, "Failed to read static field", e);
        }
    }

    @Override
    public String toString() {
        return "StaticFieldReadStmt{" +
                "className='" + className + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}