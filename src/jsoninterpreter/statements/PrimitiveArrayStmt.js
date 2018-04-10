//@flow
import Statement from './Statement';

export default class PrimitiveArrayStmt extends Statement {
    type: string;
    values: string[];

    constructor(type: string, values: any[]) {
        super('PrimitiveArrayStmt');
        this.values = values.map(value => value.toString());
        this.type = type;
    }
}
