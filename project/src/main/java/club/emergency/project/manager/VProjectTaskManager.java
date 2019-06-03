package club.emergency.project.manager;

import club.emergency.project.model.VProjectTask;
import club.map.core.manager.GenericManager;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface VProjectTaskManager extends GenericManager<VProjectTask, Integer> {
    List<VProjectTask> search(VProjectTask vProjectTask, Integer year, Integer month);

    void outPutExport(VProjectTask vProjectTask, Integer year, Integer month, HttpServletResponse response);
}