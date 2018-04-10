//@flow
import Parameter from './Parameter';
import CallStmt from './CallStmt';
import Statement from './Statement';

export default class MemberMethodCallStmt extends CallStmt {
    target: ?Statement;
    methodName: string;

    /**
     *
     * @param target targetObject
     * @param methodName
     * @param parameters
     */
    constructor(target: ?Statement, methodName: string, parameters?: Parameter[] = []): void {
        super('MemberMethodCallStmt');
        this.target = target;
        this.methodName = methodName;
        this.parameters = parameters;
    }
}
