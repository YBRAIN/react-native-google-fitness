//@flow
import LocalVariableReadStmt from './statements/LocalVariableReadStmt';
import LocalVariableWriteStmt from './statements/LocalVariableWriteStmt';
import Statement from './statements/Statement';
import ObjectWrapper from './statements/ObjectWrapper';

/**
 * Helper class to access local variable in Java context
 */
export default class LocalVariable {
    varName: string;
    objectWrapperType: () => ObjectWrapper;

    constructor(varName: string, objectWrapperType: () => ObjectWrapper) {
        this.varName = varName;
        this.objectWrapperType = objectWrapperType;
    }

    get() {
        return new LocalVariableReadStmt(this.varName);
    }

    set(value: Statement) {
        return new LocalVariableWriteStmt(this.varName, value);
    }

    /**
     * Cast local variable read statement to ObjectWrapper target
     * Abbr of .get().asJS(TargetType)
     *
     * @return {ObjectWrapper} TODO Use generic type T of subclass of ObjectWrapper
     */
    asJS(): ObjectWrapper {
        const wrapper: ObjectWrapper = new this.objectWrapperType();
        wrapper.target = this.get();
        return wrapper;
    }
}