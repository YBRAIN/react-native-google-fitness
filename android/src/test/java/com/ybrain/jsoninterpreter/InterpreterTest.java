package com.ybrain.jsoninterpreter;

import com.ybrain.jsoninterpreter.data.Calculator;
import com.ybrain.jsoninterpreter.data.PrimitiveData;
import com.ybrain.jsoninterpreter.statements.BlockStmt;
import com.ybrain.jsoninterpreter.statements.ChainedMethodCallStmt;
import com.ybrain.jsoninterpreter.statements.ConstructorCallStmt;
import com.ybrain.jsoninterpreter.statements.JavaContext;
import com.ybrain.jsoninterpreter.statements.LocalVariableReadStmt;
import com.ybrain.jsoninterpreter.statements.LocalVariableWriteStmt;
import com.ybrain.jsoninterpreter.statements.MemberMethodCallStmt;
import com.ybrain.jsoninterpreter.statements.PrimitiveArrayStmt;
import com.ybrain.jsoninterpreter.statements.PrimitiveValueStmt;
import com.ybrain.jsoninterpreter.statements.StaticFieldReadStmt;
import com.ybrain.jsoninterpreter.statements.StaticMethodCallStmt;

import org.junit.Assert;
import org.junit.Test;

public class InterpreterTest {

    @Test
    public void testStatementFactory() {
        Assert.assertTrue(StatementFactory.fromJson("{\"stmtType\":BlockStmt}") instanceof BlockStmt);
        Assert.assertTrue(StatementFactory.fromJson("{\"stmtType\":\"ChainedMethodCallStmt\"}") instanceof ChainedMethodCallStmt);
        Assert.assertTrue(StatementFactory.fromJson("{\"stmtType\":\"ConstructorCallStmt\"}") instanceof ConstructorCallStmt);
        Assert.assertTrue(StatementFactory.fromJson("{\"stmtType\":\"PrimitiveValueStmt\"}") instanceof PrimitiveValueStmt);
        Assert.assertTrue(StatementFactory.fromJson("{\"stmtType\":\"LocalVariableReadStmt\"}") instanceof LocalVariableReadStmt);
        Assert.assertTrue(StatementFactory.fromJson("{\"stmtType\":\"LocalVariableWriteStmt\"}") instanceof LocalVariableWriteStmt);
        Assert.assertTrue(StatementFactory.fromJson("{\"stmtType\":\"MemberMethodCallStmt\"}") instanceof MemberMethodCallStmt);
        Assert.assertTrue(StatementFactory.fromJson("{\"stmtType\":\"PrimitiveArrayStmt\"}") instanceof PrimitiveArrayStmt);
        Assert.assertTrue(StatementFactory.fromJson("{\"stmtType\":\"StaticFieldReadStmt\"}") instanceof StaticFieldReadStmt);
        Assert.assertTrue(StatementFactory.fromJson("{\"stmtType\":\"StaticMethodCallStmt\"}") instanceof StaticMethodCallStmt);
    }

    /**
     * Tests ...
     * - BlockStmt
     * - ConstructorCallStmt
     * - StaticMethodCallStmt
     * - JavaContext : Assigning and referencing Local-variable in context
     *
     * @throws Exception
     */
    @Test
    public void testLocalVariableContext() throws Exception {
        // Native code
        PrimitiveData data = new PrimitiveData();
        Calculator.setStringToData(data, "testString");

        // Json code
        //language=JSON
        String json = "{\n" +
                "  \"stmtType\": \"BlockStmt\",\n" +
                "  \"statements\": [\n" +
                "    {\n" +
                "      \"stmtType\": \"LocalVariableWriteStmt\",\n" +
                "      \"varName\": \"data\",\n" +
                "      \"value\": {\n" +
                "        \"stmtType\": \"ConstructorCallStmt\",\n" +
                "        \"className\": \"com.ybrain.jsoninterpreter.data.PrimitiveData\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"stmtType\": \"StaticMethodCallStmt\",\n" +
                "      \"className\": \"com.ybrain.jsoninterpreter.data.Calculator\",\n" +
                "      \"methodName\": \"setStringToData\",\n" +
                "      \"parameters\": [\n" +
                "        {\n" +
                "          \"type\": \"com.ybrain.jsoninterpreter.data.PrimitiveData\",\n" +
                "          \"value\": {\n" +
                "            \"stmtType\": \"LocalVariableReadStmt\",\n" +
                "            \"varName\": \"data\"\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"String\",\n" +
                "          \"value\": {\n" +
                "            \"stmtType\": \"PrimitiveValueStmt\",\n" +
                "            \"type\": \"String\",\n" +
                "            \"value\": \"testString\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Should be matched
        Assert.assertEquals(data, StatementFactory.fromJson(json).execute(new JavaContext(), null));
    }

    /**
     * Test ...
     * - Primitive type casters
     * - ChainedMethodCallStmt
     *
     * @throws Exception
     */
    @Test
    public void testPrimitiveTypeCasters() throws Exception {
        PrimitiveData expected = new PrimitiveData.Builder()
                .setStr("abc")
                .setS((short) 1)
                .setI(2)
                .setL(3)
                .setF(4)
                .setD(5)
                .setC('c')
                .setB(false)
                .build();

        //language=JSON
        String jsonStr = "{\n" +
                "  \"stmtType\": \"ChainedMethodCallStmt\",\n" +
                "  \"target\": {\n" +
                "    \"stmtType\": \"ConstructorCallStmt\",\n" +
                "    \"className\": \"com.ybrain.jsoninterpreter.data.PrimitiveData$Builder\"\n" +
                "  },\n" +
                "  \"callChains\": [\n" +
                "    {\n" +
                "      \"stmtType\": \"MemberMethodCallStmt\",\n" +
                "      \"methodName\": \"setStr\",\n" +
                "      \"parameters\": [\n" +
                "        {\n" +
                "          \"type\": \"String\",\n" +
                "          \"value\": {\n" +
                "            \"stmtType\": \"PrimitiveValueStmt\",\n" +
                "            \"type\": \"String\",\n" +
                "            \"value\": \"abc\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"stmtType\": \"MemberMethodCallStmt\",\n" +
                "      \"methodName\": \"setS\",\n" +
                "      \"parameters\": [\n" +
                "        {\n" +
                "          \"type\": \"short\",\n" +
                "          \"value\": {\n" +
                "            \"stmtType\": \"PrimitiveValueStmt\",\n" +
                "            \"type\": \"short\",            \n" +
                "            \"value\": \"1\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"stmtType\": \"MemberMethodCallStmt\",\n" +
                "      \"methodName\": \"setI\",\n" +
                "      \"parameters\": [\n" +
                "        {\n" +
                "          \"type\": \"int\",\n" +
                "          \"value\": {\n" +
                "            \"stmtType\": \"PrimitiveValueStmt\",\n" +
                "            \"type\": \"int\",\n" +
                "            \"value\": \"2\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"stmtType\": \"MemberMethodCallStmt\",\n" +
                "      \"methodName\": \"setL\",\n" +
                "      \"parameters\": [\n" +
                "        {\n" +
                "          \"type\": \"long\",\n" +
                "          \"value\": {\n" +
                "            \"stmtType\": \"PrimitiveValueStmt\",\n" +
                "            \"type\": \"long\",\n" +
                "            \"value\": \"3\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"stmtType\": \"MemberMethodCallStmt\",\n" +
                "      \"methodName\": \"setF\",\n" +
                "      \"parameters\": [\n" +
                "        {\n" +
                "          \"type\": \"float\",\n" +
                "          \"value\": {\n" +
                "            \"stmtType\": \"PrimitiveValueStmt\",\n" +
                "            \"type\": \"float\",\n" +
                "            \"value\": \"4\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"stmtType\": \"MemberMethodCallStmt\",\n" +
                "      \"methodName\": \"setD\",\n" +
                "      \"parameters\": [\n" +
                "        {\n" +
                "          \"type\": \"double\",\n" +
                "          \"value\": {\n" +
                "            \"stmtType\": \"PrimitiveValueStmt\",\n" +
                "            \"type\": \"double\",\n" +
                "            \"value\": \"5\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"stmtType\": \"MemberMethodCallStmt\",\n" +
                "      \"methodName\": \"setC\",\n" +
                "      \"parameters\": [\n" +
                "        {\n" +
                "          \"type\": \"char\",\n" +
                "          \"value\": {\n" +
                "            \"stmtType\": \"PrimitiveValueStmt\",\n" +
                "            \"type\": \"char\",\n" +
                "            \"value\": \"c\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"stmtType\": \"MemberMethodCallStmt\",\n" +
                "      \"methodName\": \"setB\",\n" +
                "      \"parameters\": [\n" +
                "        {\n" +
                "          \"type\": \"boolean\",\n" +
                "          \"value\": {\n" +
                "            \"stmtType\": \"PrimitiveValueStmt\",\n" +
                "            \"type\": \"boolean\",\n" +
                "            \"value\": \"false\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"stmtType\": \"MemberMethodCallStmt\",\n" +
                "      \"methodName\": \"build\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Object result = StatementFactory.fromJson(jsonStr).execute(new JavaContext(), null);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testCallStmtParamCastVariable() throws Exception {
        // Native code
        PrimitiveData expected = Calculator
                .setStringToData(new PrimitiveData
                                .Builder()
                                .setI(10)
                                .build()
                        , "testString");

        // Json code
        //language=JSON
        String json = "{\n" +
                "  \"stmtType\": \"StaticMethodCallStmt\",\n" +
                "  \"className\": \"com.ybrain.jsoninterpreter.data.Calculator\",\n" +
                "  \"methodName\": \"setStringToData\",\n" +
                "  \"parameters\": [\n" +
                "    {\n" +
                "      \"type\": \"com.ybrain.jsoninterpreter.data.PrimitiveData\",\n" +
                "      \"value\": {\n" +
                "        \"stmtType\": \"ChainedMethodCallStmt\",\n" +
                "        \"target\": {\n" +
                "          \"stmtType\": \"ConstructorCallStmt\",\n" +
                "          \"className\": \"com.ybrain.jsoninterpreter.data.PrimitiveData$Builder\"\n" +
                "        },\n" +
                "        \"callChains\": [\n" +
                "          {\n" +
                "            \"stmtType\": \"MemberMethodCallStmt\",\n" +
                "            \"methodName\": \"setI\",\n" +
                "            \"parameters\": [\n" +
                "              {\n" +
                "                \"type\": \"int\",\n" +
                "                \"value\": {\n" +
                "                  \"stmtType\":\"PrimitiveValueStmt\",\n" +
                "                  \"type\": \"int\",\n" +
                "                  \"value\":\"10\"\n" +
                "                }\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          {\n" +
                "            \"stmtType\": \"MemberMethodCallStmt\",\n" +
                "            \"methodName\": \"build\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"String\",\n" +
                "      \"value\": {\n" +
                "        \"stmtType\":\"PrimitiveValueStmt\",\n" +
                "        \"type\": \"String\",\n" +
                "        \"value\":\"testString\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}\n" +
                "\n" +
                "\n";

        Object result = StatementFactory.fromJson(json).execute(new JavaContext(), null);
        Assert.assertEquals(expected, result);
    }
}
