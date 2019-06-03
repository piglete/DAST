package club.map.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zero-mac on 16/7/10.
 */
public class Page implements Serializable {

    private List listInfo;
    private FlipInfo flipInfo;

    public Page(FlipFilter filter, long totalCount, List list) {
        this.flipInfo = new FlipInfo(filter, totalCount);
        this.listInfo = list;
    }

    public List getListInfo() {
        return listInfo;
    }

    public void setListInfo(List listInfo) {
        this.listInfo = listInfo;
    }

    public FlipInfo getFlipInfo() {
        return flipInfo;
    }

    public void setFlipInfo(FlipInfo flipInfo) {
        this.flipInfo = flipInfo;
    }

    @Override
    public String toString() {
        return "Page{" +
                "listInfo=" + listInfo +
                ", flipInfo=" + flipInfo +
                '}';
    }
}
