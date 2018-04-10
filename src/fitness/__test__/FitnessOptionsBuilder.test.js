/**
 * Copyright (C) YBrain Inc - All Rights Reserved. 2017.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by sangwoo on 2018. 4. 6..
 */

import FitnessOptions, {AccessType} from '../FitnessOptions';
import DataType from '../data/DataType';

it('just print JSON-AST built by FitnessOptions.Builder()', async () => {
    // Call all methods in builder
    const builtCall = new FitnessOptions.Builder()
        .addDataType(DataType.AGGREGATE_ACTIVITY_SUMMARY)
        .addDataType(DataType.AGGREGATE_LOCATION_BOUNDING_BOX, AccessType.ACCESS_WRITE)
        .build();
    console.log(JSON.stringify(builtCall, null, 4));
});
