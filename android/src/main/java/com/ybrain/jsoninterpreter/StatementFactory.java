package com.ybrain.jsoninterpreter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ybrain.jsoninterpreter.statements.BlockStmt;
import com.ybrain.jsoninterpreter.statements.ChainedMethodCallStmt;
import com.ybrain.jsoninterpreter.statements.ConstructorCallStmt;
import com.ybrain.jsoninterpreter.statements.PrimitiveValueStmt;
import com.ybrain.jsoninterpreter.statements.LocalVariableReadStmt;
import com.ybrain.jsoninterpreter.statements.LocalVariableWriteStmt;
import com.ybrain.jsoninterpreter.statements.MemberMethodCallStmt;
import com.ybrain.jsoninterpreter.statements.PrimitiveArrayStmt;
import com.ybrain.jsoninterpreter.statements.Statement;
import com.ybrain.jsoninterpreter.statements.StaticFieldReadStmt;
import com.ybrain.jsoninterpreter.statements.StaticMethodCallStmt;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class StatementFactory {
    private static Gson gson;
    private static StatementDeserializer statementDeserializer = new StatementDeserializer();

    static {
        // Register built-in statements
        statementDeserializer.registerBuiltInStmtType(BlockStmt.class);
        statementDeserializer.registerBuiltInStmtType(ConstructorCallStmt.class);
        statementDeserializer.registerBuiltInStmtType(MemberMethodCallStmt.class);
        statementDeserializer.registerBuiltInStmtType(ChainedMethodCallStmt.class);
        statementDeserializer.registerBuiltInStmtType(StaticMethodCallStmt.class);
        statementDeserializer.registerBuiltInStmtType(StaticFieldReadStmt.class);
        statementDeserializer.registerBuiltInStmtType(LocalVariableReadStmt.class);
        statementDeserializer.registerBuiltInStmtType(LocalVariableWriteStmt.class);
        statementDeserializer.registerBuiltInStmtType(PrimitiveValueStmt.class);
        statementDeserializer.registerBuiltInStmtType(PrimitiveArrayStmt.class);

        gson = new GsonBuilder().registerTypeAdapter(Statement.class, statementDeserializer).create();
    }

    public static void registerType(Class<? extends Statement> stmtClass) {
        statementDeserializer.registerType(stmtClass);
    }

    public static Statement fromJson(String jsonString) {
        return gson.fromJson(jsonString, Statement.class);
    }

    private static class StatementDeserializer implements JsonDeserializer<Statement> {
        Map<String, Class<? extends Statement>> dataTypeRegistry = new HashMap<>();

        void registerType(Class<? extends Statement> statementClass) {
            dataTypeRegistry.put(statementClass.getCanonicalName(), statementClass);
        }

        /**
         * Built-in statement type should use Simple class name.
         *
         * @param statementClass
         */
        void registerBuiltInStmtType(Class<? extends Statement> statementClass) {
            dataTypeRegistry.put(statementClass.getSimpleName(), statementClass);
        }

        @Override
        public Statement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            if (jsonObject.get("stmtType") != null) {
                String stmtType = jsonObject.get("stmtType").getAsString();
                Class<? extends Statement> statementClass = dataTypeRegistry.get(stmtType);
                if (statementClass == null) {
                    throw new NullPointerException("Unknown statement : " + stmtType + ". Please register statement.");
                }
                return context.deserialize(jsonObject, statementClass);
            }
            return null;
        }
    }
}
