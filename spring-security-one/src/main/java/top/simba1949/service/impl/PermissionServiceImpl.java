package top.simba1949.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.simba1949.common.BaseCommon;
import top.simba1949.common.rbac.Permission;
import top.simba1949.common.response.Code;
import top.simba1949.mapper.PermissionMapper;
import top.simba1949.service.PermissionService;
import top.simba1949.util.UserUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 8:04
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void insert(Permission permission) {
        permission.setDelete(BaseCommon.UN_DELETE);
        permission.setCreator(UserUtils.getCurrentUser().getName());
        permission.setCreateTime(LocalDateTime.now());

        permissionMapper.insert(permission);
    }

    @Override
    public void update(Permission permission) {
        Permission dbPermission = permissionMapper.selectByPrimaryKey(permission.getId());
        Preconditions.checkArgument(null != dbPermission, Code.ERROR_COMMON_NOT_FIND.getMsg());
        permission.setUpdater(UserUtils.getCurrentUser().getName());
        permission.setUpdateTime(LocalDateTime.now());
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public void delete(Integer id) {
        Permission dbPermission = permissionMapper.selectByPrimaryKey(id);
        Preconditions.checkArgument(null != dbPermission, Code.ERROR_COMMON_NOT_FIND.getMsg());
        dbPermission.setDelete(BaseCommon.DELETED);
        dbPermission.setUpdateTime(LocalDateTime.now());
        dbPermission.setUpdater(UserUtils.getCurrentUser().getName());
        permissionMapper.updateByPrimaryKeySelective(dbPermission);
    }

    @Override
    public Permission get(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    /**
     * TODO
     * @param permission
     * @param pageable
     * @return
     */
    @Override
    public PageInfo<Permission> list(Permission permission, Pageable pageable) {
        return null;
    }

    @Override
    public List<Permission> getPermissionByUsername(String username) {
        return permissionMapper.getPermissionByUsername(username);
    }
}
