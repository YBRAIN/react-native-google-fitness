/**
 * Copyright (C) YBrain Inc - All Rights Reserved. 2017.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by sangwoo on 2018. 4. 13..
 * @flow
 */
import CallStmt from './CallStmt';
import Parameter from './Parameter';

export default class ConstructorCallStmt extends CallStmt {
    className: string;

    constructor(className: string, params?: Parameter[]) {
        super('ConstructorCallStmt', params);
        this.className = className;
    }
}
