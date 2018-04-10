package com.ybrain.jsoninterpreter.statements;

/**
 * Write statement result to given named local variable in the context
 */
public class LocalVariableWriteStmt extends Statement {
    /**
     * Local variable name to write
     */
    String varName;

    /**
     * Value expression statement
     */
    Statement value;

    @Override
    public Object run(JavaContext context) throws Exception {
        if (varName == null) {
            throw new InterpreterException(context, "Variable name cannot be null");
        }
        return context.setLocalVar(varName, value.execute(context, null));
    }

    @Override
    public String toString() {
        return "LocalVariableWriteStmt{" +
                ", varName='" + varName + '\'' +
                ", value=" + value +
                '}';
    }
}
