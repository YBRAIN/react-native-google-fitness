/**
 * Copyright (C) YBrain Inc - All Rights Reserved. 2017.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by sangwoo on 2018. 4. 10..
 *
 * @flow
 */
import MemberMethodCallStmt from './MemberMethodCallStmt';
import Statement from './Statement';

export default class ChainedMethodCallStmt extends Statement {
    target: Statement;
    callChains: MemberMethodCallStmt[] = [];

    /**
     * Constructor
     */
    constructor(target: Statement) {
        super('ChainedMethodCallStmt');
        this.target = target;
    }

    appendMethodCall(methodCall: MemberMethodCallStmt): void {
        this.callChains.push(methodCall);
    }
}
