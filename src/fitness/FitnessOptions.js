//@flow
import ChainedMethodCallStmt from '../jsoninterpreter/statements/ChainedMethodCallStmt';
import PrimitiveTypes from '../jsoninterpreter/PrimitiveTypes';
import Parameter from '../jsoninterpreter/statements/Parameter';
import MemberMethodCallStmt from '../jsoninterpreter/statements/MemberMethodCallStmt';
import DataType from './data/DataType';
import StaticMethodCallStmt from '../jsoninterpreter/statements/StaticMethodCallStmt';
import StaticFieldReadStmt from '../jsoninterpreter/statements/StaticFieldReadStmt';
import FundamentalParameter from '../jsoninterpreter/statements/FundamentalParameter';

const AccessType = {
    ACCESS_READ: 0,
    ACCESS_WRITE: 1,
}

/**
 * Native FitnessOptions.Builder class wrapper.
 *
 * @see https://developers.google.com/android/reference/com/google/android/gms/fitness/FitnessOptions.Builder
 */
class FitnessOptionsBuilder {
    chainedStmt: ChainedMethodCallStmt;

    constructor() {
        this.chainedStmt = new ChainedMethodCallStmt(
            new StaticMethodCallStmt('com.google.android.gms.fitness.FitnessOptions', 'builder'),
        );
    }

    addDataType(dataType: $Values<typeof DataType>, accessType?: $Values<typeof AccessType>): FitnessOptionsBuilder {
        if (accessType === undefined) {
            this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null, 'addDataType', [
                new Parameter(DataType.className, new StaticFieldReadStmt(DataType.className, dataType)),
            ]));
        } else { // method overloading
            this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null, 'addDataType', [
                    new Parameter(DataType.className, new StaticFieldReadStmt(DataType.className, dataType)),
                    new FundamentalParameter(PrimitiveTypes.int, accessType.toString()),
                ],
            ));
        }
        return this;
    }

    build(): ChainedMethodCallStmt {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null, 'build'));
        return this.chainedStmt;
    }
}

export default {
    Builder: FitnessOptionsBuilder,
    AccessType: AccessType,
};
