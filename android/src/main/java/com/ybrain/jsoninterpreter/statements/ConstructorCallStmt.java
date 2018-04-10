package com.ybrain.jsoninterpreter.statements;

import java.util.Arrays;

public class ConstructorCallStmt extends CallStmt {
    String className;

    @Override
    public Object run(JavaContext context) {
        try {
            Class<?> builderClass = Class.forName(className);
            return builderClass
                    .getConstructor(getParameterTypeClasses())
                    .newInstance(getCastedParameterValues(context));
        } catch (Exception e) {
            throw new InterpreterException(context, "Failed to call constructor",e);
        }
    }

    @Override
    public String toString() {
        return "ConstructorCallStmt{" +
                "className='" + className + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
