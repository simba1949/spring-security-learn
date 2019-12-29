package top.simba1949.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.simba1949.common.BaseCommon;
import top.simba1949.common.rbac.Role;
import top.simba1949.common.response.Code;
import top.simba1949.mapper.RoleMapper;
import top.simba1949.service.RoleService;
import top.simba1949.util.UserUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 8:03
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void insert(Role role) {
        role.setCreator(UserUtils.getCurrentUser().getName());
        role.setCreateTime(LocalDateTime.now());
        role.setDelete(BaseCommon.UN_DELETE);
        roleMapper.insertSelective(role);
    }

    @Override
    public void update(Role role) {
        // 先查询后更新
        Role dbRole = roleMapper.selectByPrimaryKey(role.getId());
        Preconditions.checkArgument(null != dbRole, Code.ERROR_COMMON_NOT_FIND.getMsg());
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void delete(Integer id) {
        // 先查询后删除
        Role dbRole = roleMapper.selectByPrimaryKey(id);
        Preconditions.checkArgument(null != dbRole, Code.ERROR_COMMON_NOT_FIND.getMsg());
        dbRole.setDelete(BaseCommon.DELETED);
        roleMapper.updateByPrimaryKey(dbRole);
    }

    @Override
    public Role get(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    /**
     * TODO
     * @param role
     * @param pageable
     * @return
     */
    @Override
    public PageInfo<Role> list(Role role, Pageable pageable) {
        return null;
    }

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> selectUserByUser(Integer userId) {
        return roleMapper.selectUserByUser(userId);
    }
}
