//@flow
import Statement from './Statement';

export default class LocalVariableWriteStmt extends Statement {
    varName: string;
    value: Statement;

    constructor(varName: string, value: Statement) {
        super('LocalVariableWriteStmt');
        this.varName = varName;
        this.value = value;
    }
}

