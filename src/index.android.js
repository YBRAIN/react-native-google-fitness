//@flow
import {NativeModules} from 'react-native';
import Statement from './jsoninterpreter/statements/Statement';
import type {DataReadResponse} from './fitness/data/DataReadResponse';

type NativeGoogleFit = {
    requestPermissions: string => Promise<SignInResult>,
    hasPermissions: string => Promise<boolean>,
    disconnect: void => Promise<void>,
    history_insertData: string => Promise<void>,
    history_readData: string => Promise<DataReadResponse>,
    history_updateData: string => Promise<void>,
    history_deleteData: string => Promise<void>,
    history_readDailyTotal: string => Promise<any>,
    history_readDailyTotalFromLocalDevice: string => Promise<any>,
}

const fitness: NativeGoogleFit = NativeModules.RNGoogleFit;

export type SignInResult =
    'SUCCESS'
    | 'CANCELED'
    | 'ALREADY_SIGNED_IN';

/**
 * @see https://developers.google.com/android/reference/com/google/android/gms/fitness/HistoryClient
 */
const HistoryClient = {
    /**
     * @see https://developers.google.com/android/reference/com/google/android/gms/fitness/HistoryClient.html#insertData(com.google.android.gms.fitness.data.DataSet)
     * @param insertRequest
     * @return {Promise<void>}
     */
    insertData(insertRequest: Statement): Promise<void> {
        return fitness.history_insertData(insertRequest.stringify());
    },

    /**
     * @see https://developers.google.com/android/reference/com/google/android/gms/fitness/HistoryClient.html#readData(com.google.android.gms.fitness.request.DataReadRequest)
     * @param readRequest
     * @return {Promise<any>}
     */
    readData(readRequest: Statement): Promise<DataReadResponse> {
        return fitness.history_readData(readRequest.stringify());
    },

    /**
     * @see https://developers.google.com/android/reference/com/google/android/gms/fitness/HistoryClient.html#updateData(com.google.android.gms.fitness.request.DataUpdateRequest)
     * @param updateRequest
     * @return {Promise<void>}
     */
    updateData(updateRequest: Statement): Promise<void> {
        return fitness.history_updateData(updateRequest.stringify());
    },

    /**
     * @see https://developers.google.com/android/reference/com/google/android/gms/fitness/HistoryClient.html#deleteData(com.google.android.gms.fitness.request.DataDeleteRequest)
     * @param deleteRequest
     * @return {Promise<void>}
     */
    deleteData(deleteRequest: Statement): Promise<void> {
        return fitness.history_insertData(deleteRequest.stringify());
    },

    history_readDailyTotal(dataType: Statement) {
        return fitness.history_readDailyTotal(dataType.stringify());
    },

    readDailyTotalFromLocalDevice(dataType: Statement) {
        return fitness.history_readDailyTotalFromLocalDevice(dataType.stringify());
    },
};

/**
 * Entry point of module
 */
export default {
    /**
     * Request Google-fit permission for this application
     *
     * @param fitnessOptions Permission options to request. Build using FitnessOptions
     * @returns {Promise<SignInResult>}
     */
    requestPermissions(fitnessOptions: Statement): Promise<SignInResult> {
        return fitness.requestPermissions(fitnessOptions.stringify());
    },

    hasPermissions(fitnessOptions: Statement): Promise<boolean> {
        return fitness.hasPermissions(fitnessOptions.stringify());
    },

    /**
     * Disconnect google sign in
     * @return {Promise<void>}
     */
    disconnect(): Promise<void> {
        return fitness.disconnect();
    },

    History: HistoryClient,
};

export {default as TimeUnit}  from './fitness/TimeUnit';

export {default as FitnessOptions}  from './fitness/FitnessOptions';
export {default as DataReadRequest}  from './fitness/request/DataReadRequest';
export {default as DataPoint}  from './fitness/data/DataPoint';
export {default as DataSet}  from './fitness/data/DataSet';
export {default as DataSource}  from './fitness/data/DataSource';
export {default as DataType}  from './fitness/data/DataType';
export {default as Field}  from './fitness/data/Field';
export {default as Value}  from './fitness/data/Value';
