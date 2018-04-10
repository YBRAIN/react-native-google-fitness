
package com.ybrain.jsoninterpreter.statements;

import java.util.Arrays;

/**
 * The member function of the last returned object is called sequentially.
 * Created by sangwoo on 2018. 4. 7..
 */
public class ChainedMethodCallStmt extends Statement {
    Statement target;
    MemberMethodCallStmt callChains[];

    @Override
    public Object run(JavaContext context) throws Exception {
        Object targetObject = target.execute(context, null);

        // Invoke chained call for last returned object
        Object lastReturnValue = targetObject;
        for (int i = 0; i < callChains.length; i++) {
            MemberMethodCallStmt methodCall = callChains[i];
            methodCall.target = new LocalVariableReadStmt(JavaContext.LAST_RETURN);
            lastReturnValue = methodCall.execute(context, "seq=" + i);
        }
        return lastReturnValue;
    }

    @Override
    public String toString() {
        return "ChainedMethodCallStmt{" +
                "target=" + target +
                ", callChains=" + Arrays.toString(callChains) +
                '}';
    }
}
