package com.ybrain.jsoninterpreter.statements;

/**
 * A executable Statement or an Expression (for simplicity) in Java BNF
 */
public abstract class Statement {
    String stmtType;

    public final Object execute(JavaContext context, String callMetaInfo) throws Exception {
        context.pushStmt(this, callMetaInfo);
        Object returnValue = run(context);
        context.popStmt();
        context.setLocalVar(JavaContext.LAST_RETURN, returnValue);
        return returnValue;
    }

    public abstract Object run(JavaContext context) throws Exception;
}
