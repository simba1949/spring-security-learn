package top.simba1949.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.simba1949.common.BaseCommon;
import top.simba1949.common.rbac.User;
import top.simba1949.common.response.Code;
import top.simba1949.mapper.UserMapper;
import top.simba1949.service.UserService;
import top.simba1949.util.EncryptUtils;

import java.time.LocalDateTime;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 8:03
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(User user) {
        user.setCreator("SYSTEM");
        user.setCreateTime(LocalDateTime.now());
        user.setDelete(BaseCommon.UN_DELETE);
        user.setPassword(EncryptUtils.encoder(user.getPassword()));
        userMapper.insertSelective(user);
    }

    @Override
    public void update(User user) {
        // 先查询后更新
        User dbUser = userMapper.selectByPrimaryKey(user.getId());
        Preconditions.checkArgument(null != dbUser, Code.ERROR_COMMON_NOT_FIND.getMsg());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(Integer id) {
        User dbUser = userMapper.selectByPrimaryKey(id);
        Preconditions.checkArgument(null != dbUser, Code.ERROR_COMMON_NOT_FIND.getMsg());
        dbUser.setDelete(BaseCommon.DELETED);
        userMapper.updateByPrimaryKeySelective(dbUser);
    }

    @Override
    public User get(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * TODO
     * @param user
     * @param pageable
     * @return
     */
    @Override
    public PageInfo<User> list(User user, Pageable pageable) {

        PageInfo<User> pageInfo =
                PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize())
                          .doSelectPageInfo(() ->  userMapper.select(user));

        return pageInfo;
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
