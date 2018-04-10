/**
 * Copyright (C) YBrain Inc - All Rights Reserved. 2017.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by sangwoo on 2018. 4. 13..
 * @flow
 */

import Statement from './Statement';
import Parameter from './Parameter';

export default class CallStmt extends Statement {
    parameters: Parameter [] = [];

    constructor(type: string, parameters?: Parameter[]) {
        super(type);
        if (parameters) {
            this.parameters = parameters;
        }
    }

    appendParameter(param: Parameter): CallStmt {
        this.parameters.push(param);
        return this;
    }
}
