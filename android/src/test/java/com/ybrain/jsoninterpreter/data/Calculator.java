package com.ybrain.jsoninterpreter.data;

public class Calculator {
    public static int addIntValues(PrimitiveData a, int b) {
        return a.i + b;
    }

    public static PrimitiveData setStringToData(PrimitiveData data, String str) {
        data.str = str;
        return data;
    }

    public static int sum(int... args){
        int sum = 0;
        for (int i = 0; i < args.length; i++) {
            sum+=args[i];
        }
        return sum;
    }
}
