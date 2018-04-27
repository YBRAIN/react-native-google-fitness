package com.ybrain.rn.googlefitness;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.HistoryClient;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataUpdateRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.ybrain.jsoninterpreter.StatementFactory;
import com.ybrain.jsoninterpreter.statements.JavaContext;

public class GoogleFitModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    private static final String TAG = "RNGoogleFitness";
    private static final String REACT_MODULE = "RNGoogleFit";

    private ReactContext mReactContext;
    private GoogleSignInManager mGoogleSignInManager;

    public GoogleFitModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
        mGoogleSignInManager = new GoogleSignInManager(mReactContext);
    }

    @Override
    public String getName() {
        return REACT_MODULE;
    }

    @Override
    public void initialize() {
        super.initialize();
        getReactApplicationContext().addLifecycleEventListener(this);
    }

    @Override
    public void onHostResume() {
    }

    @Override
    public void onHostPause() {
    }

    @Override
    public void onHostDestroy() {
    }

    @ReactMethod
    public void googleSignIn(String fitnessOptionJsonStr, final Promise promise) {
        FitnessOptions fitnessOptions = null;
        try {
            fitnessOptions = (FitnessOptions) StatementFactory
                    .fromJson(fitnessOptionJsonStr)
                    .execute(new JavaContext(), null);
        } catch (Exception e) {
            Log.e(TAG, "Failed to create FitnessOptions", e);
            promise.reject(e);
            return;
        }

        if (!mGoogleSignInManager.requestPermissions(getCurrentActivity(), fitnessOptions, new GoogleSignInManager.ResultListener() {
            @Override
            public void onResult(int resultCode) {
                if (resultCode == Activity.RESULT_OK) {
                    promise.resolve("SUCCESS");
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    promise.resolve("CANCELED");
                }
            }
        })) {
            promise.resolve("ALREADY_SIGNED_IN");
        }
    }

    @ReactMethod
    public void disconnect(Promise promise) {
        Fitness.getConfigClient(mReactContext, GoogleSignIn.getLastSignedInAccount(mReactContext))
                .disableFit()
                .addOnFailureListener(new SimpleFailureListener(promise))
                .addOnCompleteListener(new VoidCompleteListener(promise));
    }

    @ReactMethod
    public void isEnabled(Promise promise) {
        if (GoogleSignIn.getLastSignedInAccount(mReactContext) != null) {
            promise.resolve(null);
        } else {
            promise.reject(new Exception("Not authorized"));
        }
    }

    private HistoryClient getHistoryClient() {
        return Fitness.getHistoryClient(mReactContext,
                GoogleSignIn.getLastSignedInAccount(mReactContext));
    }

    @ReactMethod
    public void history_insertData(String jsonStatement, final Promise promise) {
        try {
            DataSet dataSet = (DataSet) StatementFactory
                    .fromJson(jsonStatement)
                    .execute(new JavaContext(), null);
            getHistoryClient()
                    .insertData(dataSet)
                    .addOnFailureListener(new SimpleFailureListener(promise))
                    .addOnCompleteListener(new VoidCompleteListener(promise));
        } catch (Exception e) {
            Log.e(TAG, "Error in history_insertData()", e);
            promise.reject(e);
        }
    }

    @ReactMethod
    public void history_readData(String jsonStatement, final Promise promise) {
        try {
            DataReadRequest dataReadRequest = (DataReadRequest) StatementFactory
                    .fromJson(jsonStatement)
                    .execute(new JavaContext(), null);

            getHistoryClient()
                    .readData(dataReadRequest)
                    .addOnFailureListener(new SimpleFailureListener(promise))
                    .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<DataReadResponse> task) {
                            DataReadResponse result = task.getResult();
                            WritableNativeArray dataSets = JSONEncoder.convertDataSets(result.getDataSets());
                            WritableNativeArray buckets = JSONEncoder.convertBuckets(result.getBuckets());

                            WritableNativeMap map = new WritableNativeMap();
                            map.putString("status", result.getStatus().toString());
                            map.putArray("dataSets", dataSets);
                            map.putArray("buckets", buckets);
                            promise.resolve(map);
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "Error in history_readData()", e);
            promise.reject(e);
        }
    }

    @ReactMethod
    public void history_updateData(String jsonStatement, final Promise promise) {
        try {
            DataUpdateRequest updateRequest = (DataUpdateRequest) StatementFactory
                    .fromJson(jsonStatement)
                    .execute(new JavaContext(), null);
            getHistoryClient()
                    .updateData(updateRequest)
                    .addOnFailureListener(new SimpleFailureListener(promise))
                    .addOnCompleteListener(new VoidCompleteListener(promise));
        } catch (Exception e) {
            Log.e(TAG, "Error in history_updateData()", e);
            promise.reject(e);
        }
    }

    @ReactMethod
    public void history_deleteData(String jsonStatement, final Promise promise) {
        try {
            DataDeleteRequest deleteRequest = (DataDeleteRequest) StatementFactory
                    .fromJson(jsonStatement)
                    .execute(new JavaContext(), null);
            getHistoryClient()
                    .deleteData(deleteRequest)
                    .addOnFailureListener(new SimpleFailureListener(promise))
                    .addOnCompleteListener(new VoidCompleteListener(promise));
        } catch (Exception e) {
            Log.e(TAG, "Error in history_deleteData()", e);
            promise.reject(e);
        }
    }

    @ReactMethod
    public void history_readDailyTotal(String jsonStatement, final Promise promise) {
        try {
            DataType dataType = (DataType) StatementFactory
                    .fromJson(jsonStatement)
                    .execute(new JavaContext(), null);

            getHistoryClient()
                    .readDailyTotal(dataType)
                    .addOnFailureListener(new SimpleFailureListener(promise))
                    .addOnSuccessListener(new OnSuccessListener<DataSet>() {
                        @Override
                        public void onSuccess(DataSet dataSet) {
                            promise.resolve(JSONEncoder.convertDataSet(dataSet));
                        }
                    });

        } catch (Exception e) {
            Log.e(TAG, "Error in history_readDailyTotal()", e);
            promise.reject(e);
        }
    }

    @ReactMethod
    public void history_readDailyTotalFromLocalDevice(String jsonStatement, final Promise promise) {
        try {
            DataType dataType = (DataType) StatementFactory
                    .fromJson(jsonStatement)
                    .execute(new JavaContext(), null);

            getHistoryClient()
                    .readDailyTotalFromLocalDevice(dataType)
                    .addOnFailureListener(new SimpleFailureListener(promise))
                    .addOnSuccessListener(new OnSuccessListener<DataSet>() {
                        @Override
                        public void onSuccess(DataSet dataSet) {
                            promise.resolve(JSONEncoder.convertDataSet(dataSet));
                        }
                    });

        } catch (Exception e) {
            Log.e(TAG, "Error in history_readDailyTotal()", e);
            promise.reject(e);
        }
    }

    private static class SimpleFailureListener implements OnFailureListener {
        private Promise mPromise;

        public SimpleFailureListener(Promise promise) {
            mPromise = promise;
        }

        @Override
        public void onFailure(@NonNull Exception e) {
            mPromise.reject(e);
        }
    }

    private static final class VoidCompleteListener implements OnCompleteListener<Void> {
        private Promise mPromise;

        public VoidCompleteListener(Promise promise) {
            mPromise = promise;
        }

        @Override
        public void onComplete(@NonNull Task task) {
            mPromise.resolve(null);
        }
    }
}
