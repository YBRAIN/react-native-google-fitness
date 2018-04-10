//@flow
import Statement from './Statement';

export default class Parameter {
    value: Statement;
    type: string;

    /**
     * Constructor
     * @param type Canonical JAVA type names of parameters
     * @param value JAVA value expression as string or a statement object
     */
    constructor(type: string, value: Statement) {
        this.value = value;
        this.type = type;
    }
}
