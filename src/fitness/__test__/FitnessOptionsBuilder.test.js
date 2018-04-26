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
