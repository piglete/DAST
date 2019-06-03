package club.emergency.project.mapper.sqlprovider;

import club.map.core.mapper.SqlProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ByPlusTaskFileProvider extends SqlProvider {
    protected final Log log = LogFactory.getLog(getClass());

    public String recordList(Integer recordTypeId,String regionCode) {
        String sql = " select id as id, " +
                "project_id as projectId, " +
                "folder_name as folderName, " +
                "folder_full_name as folderFullName " +
                "'folder' as type " +
                "from v_record_file " +
                "where parent_id = 0 ";
        if (recordTypeId != null) {
            sql += " and record_type_id = " + recordTypeId;
        }
        if (!"".equals(regionCode)) {
            sql += " and region_code = " + regionCode;
        }

        return sql;
    }
    public String getFolderByParentId(Integer parentId) {
        String sql = "select folder_name as name, " +
                "id as id, " +
                "parent_id as parentId, " +
                "project_id as projectId, " +
                "folder_full_name as url," +
                "'folder' as type " +
                "from by_plus_task_file_folder " +
                "where 1 = 1 ";
        if (parentId != null) {
            sql += " and parent_id = " + parentId;
        }
        return sql;
    }
    public String getFilesByFolderId(Integer parentId) {
        String sql = "select a.file_name as name, " +
                "a.id as id, " +
                "a.folder_id as parentId, " +
                "b.project_id as projectId, " +
                "a.file_url as url, " +
                "'file' as type " +
                "from by_plus_task_file a, by_plus_task_file_folder b " +
                "where a.folder_id = b.id ";
        if (parentId != null) {
            sql += " and a.folder_id = " + parentId;
        }
        return sql;
    }
}