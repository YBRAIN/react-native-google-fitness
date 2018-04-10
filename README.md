# react-native-google-fitness
A flexible React Native module for Google Fit

##### Why is it flexible?
For example, you can query history data using Builder-Pattern of DataReadRequest like Android native way!~
```
        const HOUR_MS = 1000 * 60 * 60;

        // Setting a start and end date using a range of 1 week before this moment.
        const endTime = Date.now();
        const startTime = endTime - HOUR_MS * 24 * 7;

        const readRequest = new DataReadRequest.Builder()
            .aggregate_dataType(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .build();

        const result = await Fitness.History.readData(readRequest);
```

Quite awesome, isn't it?

##### More features
 * Support Flow type checking (IDE auto-complete)
 * Support Promise (not callback) for API result

## Getting started

### Install NPM package

`$ npm install react-native-google-fitness --save`

###### Automatic native library link
`$ react-native link react-native-google-fitness`

###### or Manual native library link
1. Append the following lines to `android/settings.gradle`:
   ```
   include ':react-native-google-fit'
   project(':react-native-google-fit').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-google-fit/android')
   ```

2. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
   ```
     compile project(':react-native-google-fit')
   ```

3. Open up `android/app/src/main/java/.../MainApplication.java`
    * Add `import com.ybrain.rn.GoogleFitnessPackage;` to the imports at the top of the file
    * Add `new GoogleFitnessPackage(),` to the list returned by the `getPackages()` method.

### Android SDK setup
In order to build Android source code, you'll need following SDK setups
```
   Android Support Repository
   Android Support Library
   Google Play services
   Google Repository
   Google Play APK Expansion Library
```

### Enable Google Fitness API for your application
In order for your app to communicate properly with the Google Fitness API
Follow steps in https://developers.google.com/fit/android/get-api-key
   1. Enable Google Fit API in your Google API Console.
    Also you need to generate new client ID for your app and provide both debug and release SHA keys.
    Another step is to configure the consent screen, etc.

   2. Provide the SHA1 sum of the certificate used for signing your
   application to Google. This will enable the GoogleFit plugin to communicate
   with the Fit application in each smartphone where the application is installed.



## COMMON USAGE

(Example application is included in this repository.)

1. `import GoogleFit from 'react-native-google-fitness';`

2. Authorize:

    ```javascript
    GoogleFit.onAuthorize(() => {
      dispatch('AUTH SUCCESS');
    });
    
    GoogleFit.onAuthorizeFailure(() => {
      dispatch('AUTH ERROR');
    });
    
    GoogleFit.authorize();
    
    // ...
    // Call when authorized
    GoogleFit.startRecording((callback) => {
      // Process data from Google Fit Recording API (no google fit app needed)
    });
    ```

3. Retrieve Steps For Period

    ```javascript
    const options = {
      startDate: "2017-01-01T00:00:17.971Z", // required ISO8601Timestamp
      endDate: new Date().toISOString() // required ISO8601Timestamp
    };
    
    GoogleFit.getDailyStepCountSamples(options, (err, res) => {
      if (err) {
        throw err;
      }
    
      console.log("Daily steps >>>", res);
    });
    ```

**Response:**

```javascript


```

### Changelog


### Changelog:

```
0.0.1   -  Initial commit
```

Copyright (c) 2018-present, YBRAIN Inc.
sangwoo.maeng@ybrain.com
