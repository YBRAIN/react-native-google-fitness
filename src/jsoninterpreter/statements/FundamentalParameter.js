//@flow

import Parameter from './Parameter';
import PrimitiveValueStmt from './PrimitiveValueStmt';

/**
 * Represent basic type parameter information for Java method invocation
 */
export default class FundamentalParameter extends Parameter {
    constructor(type: string, value: any) {
        super(type, new PrimitiveValueStmt(type, value.toString()));
    }
}
