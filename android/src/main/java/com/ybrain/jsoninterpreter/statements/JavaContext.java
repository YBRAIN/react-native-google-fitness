package com.ybrain.jsoninterpreter.statements;

import java.util.HashMap;
import java.util.Stack;

public class JavaContext {
    /**
     * Pre-defined local variable key for last statement return value.
     * Every statement return value will be stored with this key
     */
    static final String LAST_RETURN = "$LAST_RETURNED_VALUE$";

    HashMap<String, Object> localVariables = new HashMap<>();

    Stack<CallInfo> callStack = new Stack<>();

    public Object getLocalVar(Object key) {
        return localVariables.get(key);
    }

    public Object setLocalVar(String key, Object value) {
        return localVariables.put(key, value);
    }

    public void pushStmt(Statement currentStmt, String exeMetaInfo) {
        callStack.push(new CallInfo(currentStmt, exeMetaInfo));
    }

    public void popStmt() {
        callStack.pop();
    }

    public Statement getCurrentStatement() {
        return callStack.get(callStack.size() - 1).stmt;
    }

    public String getInterpreterCallStack() {
        StringBuilder builder = new StringBuilder(100);
        for (int i = callStack.size(); i > 0; i--) {
            CallInfo call = callStack.get(i - 1);
            builder.append("    ");
            builder.append(i - 1);
            builder.append(":");
            builder.append(call.toString());
            builder.append("\n");
        }

        return builder.toString();
    }

    private static class CallInfo {
        Statement stmt;
        String metaInfo;

        public CallInfo(Statement stmt, String metaInfo) {
            this.stmt = stmt;
            this.metaInfo = metaInfo;
        }

        @Override
        public String toString() {
            if (metaInfo == null) {
                return stmt.stmtType;
            } else {
                return stmt.stmtType + ": " + metaInfo;
            }

        }
    }
}
