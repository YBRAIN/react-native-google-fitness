import GoogleFit from '../src/index.android';
import {BlockStmt, LocalVariable} from '../src/jsoninterpreter/index';
import {
    DataPoint,
    DataReadRequest,
    DataSet,
    DataSource,
    DataType,
    Field,
    TimeUnit,
    Value,
} from '../src/index.android';


/*
 * Example in https://developers.google.com/fit/android/history#insert_data
 */
export default class FitApi {
    static onInsertData = async () => {
        // Set a start and end time for our data, using a start time of 1 hour before this moment.
        const endTime = Date.now();
        const startTime = endTime - 1000 * 60 * 60 * 24;
        const stepCountDelta = 950;
        // Declare Java context local variables
        // Use set() or get() method to assign or read local variable
        // Use asJS() to cast Javascript wrapper class
        const $dataSource = new LocalVariable('dataSource', DataSource);
        const $dataSet = new LocalVariable('dataSet', DataSet);
        const $dataPoint = new LocalVariable('dataPoint', DataPoint);

        // Create dataSet to insert data in Java native
        const dataSet = new BlockStmt(
            // Create  a data source
            // DataSource dataSource = new DataSource.Builder() ~~ build();
            $dataSource.set(
                new DataSource.Builder()
                    .setAppPackageName('com.ybrain.sample')
                    .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                    .setStreamName(' - step count')
                    .setType(DataSource.TYPE_RAW)
                    .build(),
            ),

            // DataSet dataSet = DataSet.create(dataSource);
            $dataSet.set(DataSet.create($dataSource.get())),

            // For each data point, specify a start time, end time, and the data value -- in this case,
            // the number of new steps.
            // DataPoint dataPoint = dataSet.createDataPoint();
            $dataPoint.set($dataSet.asJS().createDataPoint()),

            // dataPoint.setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
            $dataPoint.asJS().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS),

            // dataPoint.getValue(Field.FIELD_STEPS).setInt(stepCountDelta);
            $dataPoint
                .asJS().getValue(Field.FIELD_STEPS)
                .asJS(Value).setInt(stepCountDelta),

            // dataSet.add(dataPoint);
            $dataSet.asJS().add($dataPoint.get()),

            // returns the dataSet
            $dataSet.get(),
        );

        await GoogleFit.History.insertData(dataSet);
    };

    /*
     * Example in https://developers.google.com/fit/android/history#read_detailed_and_aggregate_data
     * @return {Promise<void>}
     */
    static onReadData = async () => {
        // Setting a start and end date using a range of 1 week before this moment.
        const endTime = Date.now();
        const startTime = endTime - 1000 * 60 * 60 * 24 * 7;

        const dataReadRequest = new DataReadRequest.Builder()
            .aggregate_dataType(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .build();

        const result = await GoogleFit.History.readData(dataReadRequest);
        console.log(result);
    };

    static onUpdateData = async () => {
        // Setting a start and end date using a range of 1 week before this moment.
        const endTime = Date.now();
        const startTime = endTime - 1000 * 60 * 60 * 24 * 7;

        const dataReadRequest = new DataReadRequest.Builder()
            .aggregate_dataType(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .build();

        const result = await GoogleFit.History.readData(dataReadRequest);
        console.log(result);

        Alert.alert('Result', 'TO BE CONTINUED..',
            [{text: 'OK', onPress: () => console.log('OK Pressed')}],
            {cancelable: true},
        );
    };

    static onDeleteData = () => {
        Alert.alert('Result', 'TO BE CONTINUED..',
            [{text: 'OK', onPress: () => console.log('OK Pressed')}],
            {cancelable: true},
        );
    };
}