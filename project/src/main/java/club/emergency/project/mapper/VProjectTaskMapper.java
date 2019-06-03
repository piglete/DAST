package club.emergency.project.mapper;

import club.emergency.project.mapper.sqlprovider.VProjectTaskProvider;
import club.emergency.project.model.VProjectTask;
import club.map.core.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface VProjectTaskMapper extends GenericMapper<VProjectTask, Integer> {
    @SelectProvider(type = VProjectTaskProvider.class, method = "search")
    List<VProjectTask> search(VProjectTask vProjectTask, Integer year, Integer month);
}