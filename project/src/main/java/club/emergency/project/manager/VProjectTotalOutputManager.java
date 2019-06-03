package club.emergency.project.manager;

import club.emergency.project.model.VProjectTotalOutput;
import club.map.core.manager.GenericManager;

import java.util.List;

public interface VProjectTotalOutputManager extends GenericManager<VProjectTotalOutput, Integer> {
    List<VProjectTotalOutput> searchByProjectId(Integer id);
}