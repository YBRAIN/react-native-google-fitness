/*
 * Copyright (C) YBrain Inc - All Rights Reserved. 2017.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by sangwoo on 2018. 4. 11..
 * @flow
 */

import Statement from './Statement';

/**
 * List of consecutive statements to execute
 */
export default class BlockStmt extends Statement {
    statements: Statement[] = [];

    constructor(...statements: Statement[]) {
        super('BlockStmt');
        statements.forEach(stmt => this.statements.push(stmt));
    }

    appendStatement(statement: Statement) {
        this.statements.push(statement);
    }
}