package com.ybrain.jsoninterpreter.data;

import java.util.Objects;

public class PrimitiveData {
    public String str;
    public short s;
    public int i;
    public long l;
    public float f;
    public double d;
    public char c;
    public boolean b;

    public PrimitiveData() {
    }

    public PrimitiveData(String str, short s, int i, long l, float f, double d, char c, boolean b) {
        this.str = str;
        this.s = s;
        this.i = i;
        this.l = l;
        this.f = f;
        this.d = d;
        this.c = c;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimitiveData that = (PrimitiveData) o;
        return s == that.s &&
                i == that.i &&
                l == that.l &&
                Float.compare(that.f, f) == 0 &&
                Double.compare(that.d, d) == 0 &&
                c == that.c &&
                b == that.b &&
                Objects.equals(str, that.str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(str, s, i, l, f, d, c, b);
    }

    public static class Builder {
        PrimitiveData dummy = new PrimitiveData();

        public Builder() {
        }

        public Builder(String str) {
            this.dummy.str = str;
        }

        public Builder setStr(String str) {
            this.dummy.str = str;
            return this;
        }

        public Builder setS(short s) {
            this.dummy.s = s;
            return this;
        }

        public Builder setI(int i) {
            this.dummy.i = i;
            return this;
        }

        public Builder setL(long l) {
            this.dummy.l = l;
            return this;
        }

        public Builder setF(float f) {
            this.dummy.f = f;
            return this;
        }

        public Builder setD(double d) {
            this.dummy.d = d;
            return this;
        }

        public Builder setC(char c) {
            this.dummy.c = c;
            return this;
        }

        public Builder setB(boolean b) {
            this.dummy.b = b;
            return this;
        }

        public PrimitiveData build() {
            return dummy;
        }
    }

    @Override
    public String toString() {
        return "PrimitiveData{" +
                "str='" + str + '\'' +
                ", s=" + s +
                ", i=" + i +
                ", l=" + l +
                ", f=" + f +
                ", d=" + d +
                ", c=" + c +
                ", b=" + b +
                '}';
    }
}
