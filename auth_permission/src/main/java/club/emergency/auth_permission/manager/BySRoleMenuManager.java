package club.emergency.auth_permission.manager;

import club.emergency.auth_permission.model.BySRoleMenu;
import club.map.core.manager.GenericManager;

public interface BySRoleMenuManager extends GenericManager<BySRoleMenu, Integer> {
    void upperSave(BySRoleMenu bySRoleMenu);
}