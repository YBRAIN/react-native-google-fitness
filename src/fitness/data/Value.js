//@flow
import ObjectWrapper from '../../jsoninterpreter/statements/ObjectWrapper';
import Statement from '../../jsoninterpreter/statements/Statement';
import MemberMethodCallStmt from '../../jsoninterpreter/statements/MemberMethodCallStmt';
import FundamentalParameter from '../../jsoninterpreter/statements/FundamentalParameter';
import PrimitiveTypes from '../../jsoninterpreter/PrimitiveTypes';

/**
 * @see https://developers.google.com/android/reference/com/google/android/gms/fitness/data/Value
 */
export default class Value extends ObjectWrapper {
    constructor(target: Statement) {
        super(target);
    }

    /**
     * Updates this value object to represent an activity value.
     * @param activity
     */
    setActivity(activity: string): Statement {
        return new MemberMethodCallStmt(this.target, 'setActivity', [
            new FundamentalParameter(PrimitiveTypes.String, activity),
        ]);
    }

    /**
     * Updates this value object to represent a float value.
     * @param floatValue
     */
    setFloat(floatValue: number): Statement {
        return new MemberMethodCallStmt(this.target, 'setFloat', [
            new FundamentalParameter(PrimitiveTypes.float, floatValue),
        ]);
    }

    /**
     * Updates this value object to represent an int value.
     * @param intValue
     */
    setInt(intValue: number): Statement {
        return new MemberMethodCallStmt(this.target, 'setInt', [
            new FundamentalParameter(PrimitiveTypes.int, intValue),
        ]);
    }

    /**
     * Updates the value for a given key in the map to the given float value.
     * @param key
     * @param floatValue
     */
    setKeyValue(key: string, floatValue: number): Statement {
        return new MemberMethodCallStmt(this.target, 'setKeyValue', [
            new FundamentalParameter(PrimitiveTypes.String, key),
            new FundamentalParameter(PrimitiveTypes.float, floatValue),
        ]);
    }

    /**
     * Updates this value object to represent a string value.
     * @param value
     */
    setString(value: string) {
        return new MemberMethodCallStmt(this.target, 'setString', [
            new FundamentalParameter(PrimitiveTypes.String, value),
        ]);
    }
}