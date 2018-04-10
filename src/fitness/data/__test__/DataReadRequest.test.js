/**
 * Copyright (C) YBrain Inc - All Rights Reserved. 2017.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by sangwoo on 2018. 4. 6..
 */

import DataRequest from '../../request/DataReadRequest';
import DataType from '../DataType';
import DataSource from '../DataSource';
import TimeUnit from '../../TimeUnit';

it('just print JSON-AST built by DataReadRequest.Builder', () => {
    const request = new DataRequest.Builder()
        .aggregate_dataType(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
        .addFilteredDataQualityStandard(DataSource.DATA_QUALITY_BLOOD_GLUCOSE_ISO151972003)
        .bucketByActivitySegment(10, TimeUnit.MINUTES,
            new DataSource.Builder()
                .setAppPackageName('com.ybrain.test')
                .setDataType(DataType.AGGREGATE_STEP_COUNT_DELTA)
                .build(),
        )
        .bucketByTime(1, TimeUnit.DAYS)
        .build();

    console.log(JSON.stringify(request, null, 4));
});
