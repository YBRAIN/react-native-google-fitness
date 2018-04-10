//@flow
import Statement from '../../jsoninterpreter/statements/Statement';
import StaticMethodCallStmt from '../../jsoninterpreter/statements/StaticMethodCallStmt';
import DataSource from './DataSource';
import DataPoint from './DataPoint';
import Parameter from '../../jsoninterpreter/statements/Parameter';
import MemberMethodCallStmt from '../../jsoninterpreter/statements/MemberMethodCallStmt';
import ObjectWrapper from '../../jsoninterpreter/statements/ObjectWrapper';

/**
 * @see https://developers.google.com/android/reference/com/google/android/gms/fitness/data/DataSet
 */
export default class DataSet extends ObjectWrapper {
    static className = 'com.google.android.gms.fitness.data.DataSet';

    /**
     * Creates a new data set to hold data points for the given dataSource.
     * @param dataSource
     * @return {Statement}
     */
    static create(dataSource: Statement): Statement {
        return new StaticMethodCallStmt(DataSet.className, 'create', [
            new Parameter(DataSource.className, dataSource),
        ]);
    }

    constructor(target: Statement) {
        super(target);
    }

    /**
     * Creates an empty data point for this data set's data source.
     * @return {Statement}
     */
    createDataPoint(): Statement {
        return new MemberMethodCallStmt(this.target, 'createDataPoint');
    }

    /**
     * Adds a data point to this data set.
     * @param dataPoint
     */
    add(dataPoint: Statement): Statement {
        return new MemberMethodCallStmt(this.target, 'add', [
            new Parameter(DataPoint.className, dataPoint),
        ]);
    }
}