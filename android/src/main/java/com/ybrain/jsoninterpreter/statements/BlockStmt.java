package com.ybrain.jsoninterpreter.statements;

import java.util.Arrays;

public class BlockStmt extends Statement {
    Statement statements[];

    @Override
    public Object run(JavaContext context) throws Exception {
        for (int i = 0; i < statements.length; i++) {
            Statement statement = statements[i];
            if (statement == null) {
                throw new InterpreterException(context, "Statement in BlockStmt cannot be null. Stmt at " + i);
            }
            statement.execute(context, "seq=" + i);
        }
        return context.getLocalVar(JavaContext.LAST_RETURN);
    }

    @Override
    public String toString() {
        return "BlockStmt{" +
                "statements=" + Arrays.toString(statements) +
                '}';
    }
}
