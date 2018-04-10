package com.ybrain.jsoninterpreter.statements;

import java.util.HashMap;

/**
 * Collection of primitive type expressions to do not create too much Statement subclasses.
 * All values can be simply expressed in String type.
 */
public class PrimitiveValueStmt extends Statement {
    String type;
    String value;

    private transient static final HashMap<String, TypeCaster<?>> CASTERS = new HashMap<>();

    private interface TypeCaster<ReturnType> {
        ReturnType cast(String value) throws Exception;
    }

    public PrimitiveValueStmt() {
        CASTERS.put("null", new TypeCaster<Object>() {
            public Object cast(String value) throws Exception {
                return null;
            }
        });
        CASTERS.put("String", new TypeCaster<String>() {
            public String cast(String value) throws Exception {
                return value;
            }
        });
        CASTERS.put("boolean", new TypeCaster<Boolean>() {
            public Boolean cast(String value) throws Exception {
                return Boolean.valueOf(value);
            }
        });
        CASTERS.put("long", new TypeCaster<Long>() {
            public Long cast(String value) throws Exception {
                return Long.valueOf(value);
            }
        });
        CASTERS.put("int", new TypeCaster<Integer>() {
            public Integer cast(String value) throws Exception {
                return Integer.valueOf(value);
            }
        });
        CASTERS.put("byte", new TypeCaster<Byte>() {
            public Byte cast(String value) throws Exception {
                return Byte.valueOf(value);
            }
        });
        CASTERS.put("short", new TypeCaster<Short>() {
            public Short cast(String value) throws Exception {
                return Short.valueOf(value);
            }
        });
        CASTERS.put("float", new TypeCaster<Float>() {
            public Float cast(String value) throws Exception {
                return Float.valueOf(value);
            }
        });
        CASTERS.put("double", new TypeCaster<Double>() {
            public Double cast(String value) throws Exception {
                return Double.valueOf(value);
            }
        });
        CASTERS.put("char", new TypeCaster<Character>() {
            public Character cast(String value) throws Exception {
                if (value.length() == 0) {
                    return 0;
                }
                if (value.length() == 1) {
                    return value.charAt(0);
                }
                throw new IllegalArgumentException("Char value length should be less than 1");
            }
        });
    }

    @Override
    public Object run(JavaContext context) {
        try {
            return CASTERS.get(type).cast(value);
        } catch (Exception e) {
            throw new InterpreterException(context, "Failed to cast value.", e);
        }
    }

    @Override
    public String toString() {
        return "PrimitiveValueStmt{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
