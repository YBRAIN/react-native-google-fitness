//@flow
import React, {Component} from 'react';
import {Alert, Button, StyleSheet, View} from 'react-native';
import GoogleFit, {DataType, FitnessOptions} from 'react-native-google-fitness';
import BasicHistoryApi from './BasicHistoryApi';

export default class App extends Component<{}> {
    /*
     * Example in https://developers.google.com/fit/android/get-started#step_5_connect_to_the_fitness_service
     */
    onAuth = async () => {
        const authState = await GoogleFit.authorize(new FitnessOptions.Builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.AccessType.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.AccessType.ACCESS_READ)
            .build(),
        );

        Alert.alert('Result', authState,
            [{text: 'OK', onPress: () => console.log('OK Pressed')}],
            {cancelable: true},
        );
    };

    onInsert = async () => {
        await BasicHistoryApi.insertData();
        this.showAlert('Data inserted');
    };

    onRead = async () => {
        const result = await BasicHistoryApi.readHistoryData();
        console.log(result);
        this.showAlert('Success. See result in android log');
    };

    showAlert = (text: string) => {
        Alert.alert('Result', text,
            [{text: 'OK', onPress: () => console.log('OK Pressed')}],
            {cancelable: true},
        );
    };

    render() {
        return (
            <View style={styles.container}>
                <Button
                    title="Authenticate"
                    onPress={this.onAuth}
                />

                <Button
                    title="Insert data"
                    onPress={this.onInsert}
                />

                <Button
                    title="Read data"
                    onPress={this.onRead}
                />

                <Button
                    disabled
                    title="Update data"
                    onPress={() => {
                    }}
                />

                <Button
                    disabled
                    title="Delete data"
                    onPress={() => {
                    }}
                />

            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 30,
        justifyContent: 'space-around',
        alignItems: 'stretch',
        backgroundColor: '#F5FCFF',
    },
});
