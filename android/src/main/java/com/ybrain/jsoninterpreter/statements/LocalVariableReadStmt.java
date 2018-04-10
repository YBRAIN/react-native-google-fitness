package com.ybrain.jsoninterpreter.statements;

/**
 * Returns given named object reference in the context
 */
public class LocalVariableReadStmt extends Statement {
    /**
     * Local variable name to read
     */
    String varName;

    public LocalVariableReadStmt(String varName) {
        this.varName = varName;
    }

    @Override
    public Object run(JavaContext context) throws Exception {
        if (varName == null) {
            throw new InterpreterException(context, "Variable name cannot be null");
        }
        return context.getLocalVar(varName);
    }

    @Override
    public String toString() {
        return "LocalVariableReadStmt{" +
                "varName='" + varName + '\'' +
                '}';
    }
}
