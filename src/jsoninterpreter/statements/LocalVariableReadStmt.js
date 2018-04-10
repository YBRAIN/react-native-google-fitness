//@flow
import Statement from './Statement';

export default class LocalVariableReadStmt extends Statement {
    varName: string;

    constructor(varName: string) {
        super('LocalVariableReadStmt');
        this.varName = varName;
    }
}

