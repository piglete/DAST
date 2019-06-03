package club.emergency.by_b_contract.manager;

import club.emergency.by_b_contract.model.ByBContract;
import club.map.core.manager.GenericManager;
import club.map.core.model.Page;

/**
 * 合同管理接口
 */
public interface ByBContractManager extends GenericManager<ByBContract, Integer> {
    /**
     * 分页查询
     *
     * @param projectName
     * @param putFileTime
     * @param companyName
     * @param contractAmount
     * @param contractTypeId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page search(String projectName, String putFileTime, String companyName, Float contractAmount, Integer contractTypeId, Integer pageNo, Integer pageSize);

    /**
     * 编辑
     *
     * @param byBContract
     */
    void upperSave(ByBContract byBContract);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(String ids);
}