package com.ybrain.jsoninterpreter.statements;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Collection of primitive array type expressions to do not create too much Statement subclasses.
 * All values can be simply expressed in String type.
 */
public class PrimitiveArrayStmt extends Statement {
    private transient static final HashMap<String, ArrayCaster> CASTERS = new HashMap<>();

    String type;
    String[] values;

    private interface ArrayCaster {
        Object cast(String[] values) throws Exception;
    }

    static {
        CASTERS.put("boolean[]", new ArrayCaster() {
            public boolean[] cast(String[] values) throws Exception {
                boolean[] result = new boolean[values.length];
                for (int i = 0; i < values.length; i++) {
                    result[i] = Boolean.valueOf(values[i]);
                }
                return result;
            }
        });

        CASTERS.put("long[]", new ArrayCaster() {
            public long[] cast(String[] values) throws Exception {
                long[] result = new long[values.length];
                for (int i = 0; i < values.length; i++) {
                    result[i] = Long.valueOf(values[i]);
                }
                return result;
            }
        });

        CASTERS.put("int[]", new ArrayCaster() {
            public int[] cast(String[] values) throws Exception {
                int[] result = new int[values.length];
                for (int i = 0; i < values.length; i++) {
                    result[i] = Integer.valueOf(values[i]);
                }
                return result;
            }
        });

        CASTERS.put("float[]", new ArrayCaster() {
            public float[] cast(String[] values) throws Exception {
                float[] result = new float[values.length];
                for (int i = 0; i < values.length; i++) {
                    result[i] = Float.valueOf(values[i]);
                }
                return result;
            }
        });

        CASTERS.put("double[]", new ArrayCaster() {
            public double[] cast(String[] values) throws Exception {
                double[] result = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                    result[i] = Double.valueOf(values[i]);
                }
                return result;
            }
        });

        CASTERS.put("char[]", new ArrayCaster() {
            public char[] cast(String[] values) throws Exception {
                char[] result = new char[values.length];
                for (int i = 0; i < values.length; i++) {
                    result[i] = values[i].charAt(0);
                }
                return result;
            }
        });

        CASTERS.put("String[]", new ArrayCaster() {
            public String[] cast(String[] values) throws Exception {
                return values;
            }
        });
    }

    @Override
    public Object run(JavaContext context) throws Exception {
        try {
            return CASTERS.get(type).cast(values);
        } catch (Exception e) {
            throw new InterpreterException(context, "Failed to cast array value", e);
        }

    }

    @Override
    public String toString() {
        return "PrimitiveArrayStmt{" +
                "type='" + type + '\'' +
                ", values=" + Arrays.toString(values) +
                '}';
    }
}
