//@flow
import Statement from '../../jsoninterpreter/statements/Statement';
import ConstructorCallStmt from '../../jsoninterpreter/statements/ConstructorCallStmt';
import ChainedMethodCallStmt from '../../jsoninterpreter/statements/ChainedMethodCallStmt';
import MemberMethodCallStmt from '../../jsoninterpreter/statements/MemberMethodCallStmt';
import Parameter from '../../jsoninterpreter/statements/Parameter';
import PrimitiveTypes from '../../jsoninterpreter/PrimitiveTypes';
import FundamentalParameter from '../../jsoninterpreter/statements/FundamentalParameter';
import PrimitiveArrayStmt from '../../jsoninterpreter/statements/PrimitiveArrayStmt';
import StaticFieldReadStmt from '../../jsoninterpreter/statements/StaticFieldReadStmt';
import DataType from './DataType';
import PrimitiveArrayTypes from '../../jsoninterpreter/PrimitiveArrayTypes';

class DataSourceBuilder {
    chainedStmt: ChainedMethodCallStmt;

    constructor() {
        this.chainedStmt = new ChainedMethodCallStmt(
            new ConstructorCallStmt('com.google.android.gms.fitness.data.DataSource$Builder'),
        );
    }

    /**
     * Finishes building the data source and returns a DataSource object.
     */
    build(): Statement {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null, 'build'));
        return this.chainedStmt;
    }

    /**
     * Sets the package name for the application that is recording or computing the data.
     *
     * @param packageName
     * @return {DataSourceBuilder}
     */
    setAppPackageName(packageName: string): DataSourceBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'setAppPackageName', [
                new FundamentalParameter(PrimitiveTypes.String, packageName),
            ]));
        return this;
    }

    /*
     *Sets the package name for the application that is recording or computing the data based on the app's context.
     *
     * NOT SUPPORTED
     * setAppPackageName(Context appContext)
     */

    /**
     * Sets the list of data quality standards adhered to by this data source.
     *
     * @param dataQualityStandards
     * @return {DataSourceBuilder}
     */
    setDataQualityStandards(dataQualityStandards: number[]): DataSourceBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'setDataQualityStandards', [
                new Parameter(PrimitiveArrayTypes.int, new PrimitiveArrayStmt(PrimitiveArrayTypes.int, dataQualityStandards)),
            ]));
        return this;
    }

    /**
     * Sets the data type for the data source.
     *
     * @param dataType
     * @return {DataSourceBuilder}
     */
    setDataType(dataType: $Values<typeof DataType>): DataSourceBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'setDataType', [
                new Parameter(DataType.className, new StaticFieldReadStmt(DataType.className, dataType)),
            ]));
        return this;
    }

    /**
     * Sets an end-user-visible name for this data source.
     *
     * @param name
     * @return {DataSourceBuilder}
     */
    setName(name: string): DataSourceBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'setName', [
                new FundamentalParameter(PrimitiveTypes.String, name),
            ]));
        return this;
    }

    /**
     * The stream name uniquely identifies this particular data source among other data sources of the same type from the same underlying producer.
     *
     * @param streamName
     * @return {DataSourceBuilder}
     */
    setStreamName(streamName: string): DataSourceBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'setStreamName', [
                new FundamentalParameter(PrimitiveTypes.String, streamName),
            ]));
        return this;
    }

    /**
     * Sets the type of the data source.
     *
     * @param type
     * @return {DataSourceBuilder}
     */
    setType(type: number): DataSourceBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'setType', [
                new FundamentalParameter(PrimitiveTypes.int, type),
            ]));
        return this;
    }
}

export default class DataSource {
    static className = 'com.google.android.gms.fitness.data.DataSource';
    static Builder = DataSourceBuilder;

    static TYPE_RAW = 0;
    static TYPE_DERIVED = 1;
    static DATA_QUALITY_BLOOD_PRESSURE_ESH2002 = 1;
    static DATA_QUALITY_BLOOD_PRESSURE_ESH2010 = 2;
    static DATA_QUALITY_BLOOD_PRESSURE_AAMI = 3;
    static DATA_QUALITY_BLOOD_PRESSURE_BHS_A_A = 4;
    static DATA_QUALITY_BLOOD_PRESSURE_BHS_A_B = 5;
    static DATA_QUALITY_BLOOD_PRESSURE_BHS_B_A = 6;
    static DATA_QUALITY_BLOOD_PRESSURE_BHS_B_B = 7;
    static DATA_QUALITY_BLOOD_GLUCOSE_ISO151972003 = 8;
    static DATA_QUALITY_BLOOD_GLUCOSE_ISO151972013 = 9;
};
