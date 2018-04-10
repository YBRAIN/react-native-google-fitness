//@flow

/**
 * Represent  Abstract Syntax Tree (AST) of Java statement
 */
export default class Statement {
    /**
     * Statement type name. Should be matched with Java Subclass name of Statement.java
     */
    stmtType: string;

    constructor(stmtType: string) {
        this.stmtType = stmtType;
    }

    /**
     * Cast statement as target in objectWrapper
     * @param type A subclass itself which is extends ObjectWrapper
     */
    asJS<T>(type: () => T): T {
        const objectWrapper = new type();
        objectWrapper.target = this;
        return objectWrapper;
    }

    stringify() {
        return JSON.stringify(this, ...arguments);
    }
}
