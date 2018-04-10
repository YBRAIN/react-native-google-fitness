import Fitness, {
    DataPoint,
    DataReadRequest,
    DataSet,
    DataSource,
    DataType,
    Field,
    TimeUnit,
    Value,
} from 'react-native-google-fitness';
import {BlockStmt, LocalVariable} from 'react-native-google-fitness/src/jsoninterpreter';

const TAG = 'BasicHistoryApi';
const HOUR_MS = 1000 * 60 * 60;

export default class BasicHistoryApi {
    /**
     * Creates a {@link DataSet} and inserts it into user's Google Fit history.
     */
    static async insertData() {
        // Create a new dataset and insertion request.
        const dataSet = BasicHistoryApi.createFitnessData();

        // Then, invoke the History API to insert the data.
        console.log('Inserting the dataset in the History API.');
        return Fitness.History.insertData(dataSet);
    }

    /**
     * Creates and returns a {@link DataSet} of step count data for insertion using the History API.
     */
    static createFitnessData() {
        console.log('Creating a new data insert request.');

        // [START build_insert_data_request]
        // Set a start and end time for our data, using a start time of 1 hour before this moment.
        const endTime = Date.now();
        const startTime = endTime - HOUR_MS;

        const stepCountDelta = 950;

        const $dataSet = new LocalVariable('dataSet', DataSet);
        const $dataPoint = new LocalVariable('dataPoint', DataPoint);
        const $dataSource = new LocalVariable('dataSource', DataSource);

        const dataSetStmt = new BlockStmt(
            // Create a data source
            $dataSource.set(new DataSource.Builder()
                .setAppPackageName('com.ybrain.rn.googlefitness.example')
                .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .setStreamName(TAG + ' - step count')
                .setType(DataSource.TYPE_RAW)
                .build()),

            // Create a data set
            $dataSet.set(DataSet.create($dataSource.get())),
            // For each data point, specify a start time, end time, and the data value -- in this case,
            // the number of new steps.
            $dataPoint.set(
                $dataSet.asJS()
                    .createDataPoint().asJS(DataPoint)
                    .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS),
            ),
            $dataPoint.asJS()
                .getValue(Field.FIELD_STEPS).asJS(Value)
                .setInt(stepCountDelta),
            $dataSet.asJS()
                .add($dataPoint.get()),
            $dataSet.get(),
        );
        // [END build_insert_data_request]

        return dataSetStmt;
    }

    /**
     * Asynchronous task to read the history data. When the task succeeds, it will print out the data.
     */
    static readHistoryData() {
        // Begin by creating the query.
        const readRequest = BasicHistoryApi.queryFitnessData();

        // Invoke the History API to fetch the data with the query
        return Fitness.History.readData(readRequest);
    }

    /**
     * Returns a {@link DataReadRequest} for all step count changes in the past week.
     */
    static queryFitnessData() {
        // [START build_read_data_request]
        // Setting a start and end date using a range of 1 week before this moment.
        const endTime = Date.now();
        const startTime = endTime - HOUR_MS * 24 * 7;

        console.log('Range Start: ' + new Date(startTime));
        console.log('Range End: ' + new Date(endTime));

        const readRequest = new DataReadRequest.Builder()
        // The data request can specify multiple data types to return, effectively
        // combining multiple data queries into one call.
        // In this example, it's very unlikely that the request is for several hundred
        // datapoints each consisting of a few steps and a timestamp.  The more likely
        // scenario is wanting to see how many steps were walked per day, for 7 days.
            .aggregate_dataType(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
            // Analogous to a "Group By" in SQL, defines how data should be aggregated.
            // bucketByTime allows for a time span, whereas bucketBySession would allow
            // bucketing by "sessions", which would need to be defined in code.
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .build();
        // [END build_read_data_request]

        return readRequest;
    }

}