package club.map.base.model;

/**
 * Created by Zjs-yd on 2016/8/4.
 */
public enum LogType {

    verification_code(1),;

    int index;

    LogType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
