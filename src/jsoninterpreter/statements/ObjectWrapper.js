//@flow
import Statement from './Statement';

export default class ObjectWrapper {
    target: Statement;

    constructor(target: Statement) {
        this.target = target;
    }
}
