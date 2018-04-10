//@flow
import CallStmt from './CallStmt';
import Parameter from './Parameter';

export default class StaticMethodCallStmt extends CallStmt {
    className: string;
    methodName: string;

    constructor(className: string, methodName: string, params?: Parameter[]) {
        super('StaticMethodCallStmt', params);
        this.className = className;
        this.methodName = methodName;
    }
}
