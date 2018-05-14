package com.ybrain.rn.googlefitness;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.Field;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Convert given types to JSON
 */
public class JSONEncoder {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"; // ISO8601 FORMAT
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public static WritableNativeArray convertDataSet(DataSet dataSet) {
        WritableNativeArray jsonDataSet = new WritableNativeArray();
        for (DataPoint dp : dataSet.getDataPoints()) {
            WritableNativeMap jsonDataPoint = new WritableNativeMap();

            jsonDataPoint.putString("type", dp.getDataType().getName());
            jsonDataPoint.putString("source", dp.getOriginalDataSource().getAppPackageName());
            jsonDataPoint.putString("start", dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            jsonDataPoint.putString("end", dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));

            WritableNativeArray jsonFieldValuePairs = new WritableNativeArray();
            for (Field field : dp.getDataType().getFields()) {
                WritableNativeMap jsonFieldValuePair = new WritableNativeMap();
                jsonFieldValuePair.putString("field", field.getName());
                jsonFieldValuePair.putString("value", dp.getValue(field).toString());
                jsonFieldValuePairs.pushMap(jsonFieldValuePair);
            }
            jsonDataPoint.putArray("fields", jsonFieldValuePairs);

            jsonDataSet.pushMap(jsonDataPoint);
        }
        return jsonDataSet;
    }

    private static WritableMap convertDataSource(DataSource ds) {
        WritableNativeMap map = new WritableNativeMap();
        map.putInt("type", ds.getType());
        map.putString("name", ds.getName());
        map.putString("appPackageName", ds.getAppPackageName());
        map.putString("streamIdentifier", ds.getStreamIdentifier());
        return map;
    }

    public static WritableNativeArray convertDataSets(List<DataSet> dataSets) {
        WritableNativeArray jsonDataSets = new WritableNativeArray();
        for (DataSet dataSet : dataSets) {
            jsonDataSets.pushArray(convertDataSet(dataSet));
        }
        return jsonDataSets;
    }

    public static WritableNativeArray convertBuckets(List<Bucket> buckets) {
        WritableNativeArray jsonBuckets = new WritableNativeArray();
        for (Bucket bucket : buckets) {
            WritableNativeMap jsonBucket = new WritableNativeMap();
            jsonBucket.putString("start", dateFormat.format(bucket.getStartTime(TimeUnit.MILLISECONDS)));
            jsonBucket.putString("end", dateFormat.format(bucket.getEndTime(TimeUnit.MILLISECONDS)));
            jsonBucket.putInt("type", bucket.getBucketType());
            jsonBucket.putString("activity", bucket.getActivity());
            jsonBucket.putArray("dataSets", convertDataSets(bucket.getDataSets()));
            jsonBuckets.pushMap(jsonBucket);
        }
        return jsonBuckets;
    }

}
