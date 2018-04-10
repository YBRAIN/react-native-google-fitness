//@flow
import Statement from '../../jsoninterpreter/statements/Statement';
import MemberMethodCallStmt from '../../jsoninterpreter/statements/MemberMethodCallStmt';
import StaticMethodCallStmt from '../../jsoninterpreter/statements/StaticMethodCallStmt';
import StaticFieldReadStmt from '../../jsoninterpreter/statements/StaticFieldReadStmt';
import Parameter from '../../jsoninterpreter/statements/Parameter';
import ObjectWrapper from '../../jsoninterpreter/statements/ObjectWrapper';
import Field from './Field';
import TimeUnit from '../TimeUnit';
import DataSource from './DataSource';
import PrimitiveArrayStmt from '../../jsoninterpreter/statements/PrimitiveArrayStmt';
import PrimitiveArrayTypes from '../../jsoninterpreter/PrimitiveArrayTypes';
import FundamentalParameter from '../../jsoninterpreter/statements/FundamentalParameter';
import PrimitiveTypes from '../../jsoninterpreter/PrimitiveTypes';

/**
 * @see https://developers.google.com/android/reference/com/google/android/gms/fitness/data/DataPoint
 */
export default class DataPoint extends ObjectWrapper {
    static className = 'com.google.android.gms.fitness.data.DataPoint';

    constructor(target: Statement) {
        super(target);
    }

    /**
     * Creates a new data point for the given dataSource.
     * @param dataSource
     */
    static create(dataSource: Statement): StaticMethodCallStmt {
        return new StaticMethodCallStmt(DataSource.className, 'create', [
            new Parameter(DataSource.className, dataSource),
        ]);
    }

    getValue(field: $Values<typeof Field>): Statement {
        return new MemberMethodCallStmt(this.target, 'getValue', [
            new Parameter(Field.className, new StaticFieldReadStmt(Field.className, field)),
        ]);
    }

    /**
     * Sets the values of this data point, where the format for all of its values is float.
     * @param values
     */
    setFloatValues(values: number[]): Statement {
        return new MemberMethodCallStmt(this.target, 'getValue', [
            new Parameter(PrimitiveArrayTypes.float, new PrimitiveArrayStmt(PrimitiveArrayTypes.float, values)),
        ]);
    }

    /**
     * Sets the values of this data point, where the format for all of its values is int.
     * @param values
     */
    setIntValues(values: number[]): Statement {
        return new MemberMethodCallStmt(this.target, 'setIntValues', [
            new Parameter(PrimitiveArrayTypes.int, new PrimitiveArrayStmt(PrimitiveArrayTypes.int, values)),
        ]);
    }

    /**
     * Sets the time interval of a data point that represents an interval of time.
     * @param startTime
     * @param endTime
     * @param timeUnit
     */
    setTimeInterval(startTime: number, endTime: number, timeUnit: $Values<typeof TimeUnit>): Statement {
        return new MemberMethodCallStmt(this.target, 'setTimeInterval', [
            new FundamentalParameter(PrimitiveTypes.long, startTime),
            new FundamentalParameter(PrimitiveTypes.long, endTime),
            new Parameter(TimeUnit.className, new StaticFieldReadStmt(TimeUnit.className, timeUnit)),
        ]);
    }

    /**
     * Sets the timestamp of a data point that represent an instantaneous reading, measurement, or input.
     *
     * @param timestamp   the timestamp in the given unit, representing elapsed time since epoch
     * @param timeUnit      the unit of the given timestamp
     */
    setTimestamp(timestamp: number, timeUnit: $Values<typeof TimeUnit>): Statement {
        return new MemberMethodCallStmt(this.target, 'setTimeInterval', [
            new FundamentalParameter(PrimitiveTypes.long, timestamp),
            new Parameter(TimeUnit.className, new StaticFieldReadStmt(TimeUnit.className, timeUnit)),
        ]);
    }
}
