/**
 * Copyright (c) 2017-present, Stanislav Doskalenko - doskalenko.s@gmail.com
 * All rights reserved.
 * <p>
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 * <p>
 * Based on Asim Malik android source code, copyright (c) 2015
 **/
package com.ybrain.rn.googlefitness;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactContext;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;

public class GoogleSignInManager implements ActivityEventListener {
    private static final String TAG = "RNGoogleFit";
    private static final int REQUEST_OAUTH_REQUEST_CODE = 100;

    private ReactContext mReactContext;
    private ResultListener mListener;

    public GoogleSignInManager(ReactContext reactContext) {
        this.mReactContext = reactContext;
        mReactContext.addActivityEventListener(this);
    }

    /**
     * Request GoogleFit API via GoogleSignIn
     * @param activity
     * @param listener
     * @return false if already signed in
     */
    public boolean requestPermissions(Activity activity,  FitnessOptions fitnessOptions, ResultListener listener) {
        mListener = listener;

        GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(mReactContext);
        if (!GoogleSignIn.hasPermissions(lastSignedInAccount, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    activity,
                    REQUEST_OAUTH_REQUEST_CODE,
                    lastSignedInAccount,
                    fitnessOptions);
            return true;
        }
        return false;
    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
            if(mListener !=null){
                mListener.onResult(resultCode);
            }
        }
    }

    public static interface ResultListener {
        void onResult(int resultCode);
    }
}
