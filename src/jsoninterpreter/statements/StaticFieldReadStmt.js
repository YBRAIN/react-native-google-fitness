//@flow
import Statement from './Statement';

export default class StaticFieldReadStmt extends Statement {
    className: string;
    fieldName: string;

    /**
     * Constructor
     * @param className
     * @param fieldName
     */
    constructor(className: string, fieldName: string) {
        super('StaticFieldReadStmt');
        this.className = className;
        this.fieldName = fieldName;
    }
}
