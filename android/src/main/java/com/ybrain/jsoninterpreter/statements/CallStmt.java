package com.ybrain.jsoninterpreter.statements;

import com.ybrain.jsoninterpreter.ClassUtil;

public abstract class CallStmt extends Statement {
    /**
     * Arguments to pass
     */
    Parameter parameters[] = new Parameter[0];

    /**
     * Cast parameter values to Java native
     *
     * @param context@return
     * @throws Exception
     */
    public Object[] getCastedParameterValues(JavaContext context) throws Exception {
        Object[] parameterValues = new Object[parameters.length];

        for (int idxParam = 0; idxParam < parameters.length; idxParam++) {
            Parameter parameter = parameters[idxParam];
            parameterValues[idxParam] = parameter.value.execute(context, "idx=" + idxParam);
        }
        return parameterValues;
    }

    public Class[] getParameterTypeClasses() throws ClassNotFoundException {
        Class[] classes = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            classes[i] = parameters[i].getParameterTypeClass();
        }
        return classes;
    }

    /**
     * Parameter information.
     */
    public static class Parameter {
        /**
         * Canonical name of parameter type
         */
        String type;

        /**
         * Value Expression.
         */
        Statement value;

        Class<?> getParameterTypeClass() throws ClassNotFoundException {
            return ClassUtil.classForName(type);
        }
    }
}
