//@flow
import PrimitiveTypes from '../PrimitiveTypes';
import Statement from './Statement';

/**
 * Represent  parameter  information for basic types
 */
export default class PrimitiveValueStmt extends Statement {
    type: typeof PrimitiveTypes | string;
    value: string;

    constructor(type: string, value: string) {
        super('PrimitiveValueStmt');
        this.type = type;
        this.value = value;
    }
}
