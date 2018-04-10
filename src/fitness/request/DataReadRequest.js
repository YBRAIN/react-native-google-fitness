/*
 * Copyright (C) YBrain Inc - All Rights Reserved. 2017.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by sangwoo on 2018. 4. 10..
 * @flow
 */

import ChainedMethodCallStmt from '../../jsoninterpreter/statements/ChainedMethodCallStmt';
import MemberMethodCallStmt from '../../jsoninterpreter/statements/MemberMethodCallStmt';
import ConstructorCallStmt from '../../jsoninterpreter/statements/ConstructorCallStmt';
import Statement from '../../jsoninterpreter/statements/Statement';
import Parameter from '../../jsoninterpreter/statements/Parameter';
import DataSource from '../data/DataSource';
import DataType from '../data/DataType';
import PrimitiveTypes from '../../jsoninterpreter/PrimitiveTypes';
import TimeUnit from '../TimeUnit';
import FundamentalParameter from '../../jsoninterpreter/statements/FundamentalParameter';
import StaticFieldReadStmt from '../../jsoninterpreter/statements/StaticFieldReadStmt';

class DataReadRequestBuilder {
    chainedStmt: ChainedMethodCallStmt;

    constructor() {
        this.chainedStmt = new ChainedMethodCallStmt(
            new ConstructorCallStmt('com.google.android.gms.fitness.request.DataReadRequest$Builder'),
        );
    }

    /**
     * Limits results to data meeting a quality standard.
     * @param dataQualityStandard
     */
    addFilteredDataQualityStandard(dataQualityStandard: number): DataReadRequestBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'addFilteredDataQualityStandard', [
                new FundamentalParameter(PrimitiveTypes.int, dataQualityStandard),
            ]));
        return this;
    }

    /**
     * Adds a specific data source we want aggregate data from for this request and also sets the output aggregate data type that will be returned.
     * @param dataSource
     * @param outputDataType
     */
    aggregate_dataSource(dataSource: Statement, outputDataType: $Values<typeof DataType>): DataReadRequestBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'aggregate', [
                new Parameter(DataSource.className, dataSource),
                new Parameter(DataType.className, new StaticFieldReadStmt(DataType.className, outputDataType)),
            ]));
        return this;
    }

    /**
     * Adds the default data source for the given aggregate dataType to this request and sets the output aggregate data type to be returned.
     * @param inputDataType
     * @param outputDataType
     */
    aggregate_dataType(inputDataType: $Values<typeof DataType>, outputDataType: $Values<typeof DataType>): DataReadRequestBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'aggregate', [
                new Parameter(DataType.className, new StaticFieldReadStmt(DataType.className, inputDataType)),
                new Parameter(DataType.className, new StaticFieldReadStmt(DataType.className, outputDataType)),
            ]));
        return this;
    }

    /**
     * Specifies bucket type as TYPE_ACTIVITY_SEGMENT, sets the minimum duration of each TYPE_ACTIVITY_SEGMENT for the bucket.
     *
     * @param minDuration
     * @param timeUnit
     * @param activityDataSource
     */
    bucketByActivitySegment(minDuration: number, timeUnit: $Values<typeof TimeUnit>, activityDataSource?: Statement): DataReadRequestBuilder {
        const params = [
            new FundamentalParameter(PrimitiveTypes.int, minDuration),
            new Parameter(TimeUnit.className, new StaticFieldReadStmt(TimeUnit.className, timeUnit)),
        ];
        if (activityDataSource) {
            params.push(new Parameter(DataSource.className, activityDataSource));
        }

        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null, 'bucketByActivitySegment', params));
        return this;
    }

    /**
     * Specifies bucket type as TYPE_ACTIVITY_TYPE, sets the minimum duration of each TYPE_ACTIVITY_SEGMENT for computing the buckets and sets the activity data source to be used to read activity segments from.
     *
     * @param minDuration
     * @param timeUnit
     * @param activityDataSource
     */
    bucketByActivityType(minDuration: number, timeUnit: $Values<typeof TimeUnit>, activityDataSource?: Statement): DataReadRequestBuilder {
        const params = [
            new FundamentalParameter(PrimitiveTypes.int, minDuration),
            new Parameter(TimeUnit.className, new StaticFieldReadStmt(TimeUnit.className, timeUnit)),
        ];
        if (activityDataSource) {
            params.push(new Parameter(DataSource.className, activityDataSource));
        }
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null, 'bucketByActivityType', params));
        return this;
    }

    /**
     * Specifies bucket type as TYPE_SESSION and sets the minimum duration of each Session for the bucket.
     * @param minDuration
     * @param timeUnit
     */
    bucketBySession(minDuration: number, timeUnit: $Values<typeof TimeUnit>): DataReadRequestBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'bucketBySession', [
                new FundamentalParameter(PrimitiveTypes.int, minDuration),
                new Parameter(TimeUnit.className, new StaticFieldReadStmt(TimeUnit.className, timeUnit)),
            ]));
        return this;
    }

    /**
     * Specifies bucket type as TYPE_TIME and sets the duration of each bucket.
     * @param duration
     * @param timeUnit
     */
    bucketByTime(duration: number, timeUnit: $Values<typeof TimeUnit>): DataReadRequestBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'bucketByTime', [
                new FundamentalParameter(PrimitiveTypes.int, duration),
                new Parameter(TimeUnit.className, new StaticFieldReadStmt(TimeUnit.className, timeUnit)),
            ]));
        return this;
    }

    /**
     * Enable querying the Google Fit server to fetch query results, in case the local store doesn't have data for the full requested time range.
     */
    enableServerQueries(): DataReadRequestBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'enableServerQueries'));
        return this;
    }

    /**
     * Adds a specific data source we want to read data from to this request.
     * @param dataSource
     */
    read_dataSource(dataSource: Statement): DataReadRequestBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'read', [
                new Parameter(DataSource.className, dataSource),
            ]));
        return this;
    }

    /**
     * Adds the default data source to read for the given dataType to this request.
     * @param dataType
     */
    read_dataType(dataType: $Values<typeof DataType>): DataReadRequestBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'read', [
                new FundamentalParameter(DataType.className, dataType),
            ]));
        return this;
    }

    /**
     * Limits results to the latest limit data points.
     * @param limit
     */
    setLimit(limit: number): DataReadRequestBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'setLimit', [
                new FundamentalParameter(PrimitiveTypes.int, limit),
            ]));
        return this;
    }

    /**
     * Sets the time range for our query.
     *
     * @param start
     * @param end
     * @param timeUnit
     */
    setTimeRange(start: number, end: number, timeUnit: $Values<typeof TimeUnit>): DataReadRequestBuilder {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null,
            'setTimeRange', [
                new FundamentalParameter(PrimitiveTypes.long, start),
                new FundamentalParameter(PrimitiveTypes.long, end),
                new Parameter(TimeUnit.className, new StaticFieldReadStmt(TimeUnit.className, timeUnit)),
            ]));
        return this;
    }

    /**
     * Finishes building and returns the request.
     * @return Java AST in JSON object
     */
    build(): Statement {
        this.chainedStmt.appendMethodCall(new MemberMethodCallStmt(null, 'build'));
        return this.chainedStmt;
    }
}

const DataReadRequest = {
    Builder: DataReadRequestBuilder,
    className: 'com.google.android.gms.fitness.request.DataReadRequest',
};

export default DataReadRequest;
