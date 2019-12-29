package top.simba1949.service;

import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Pageable;
import top.simba1949.common.rbac.User;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 8:02
 */
public interface UserService {

    void insert(User user);

    void update(User user);

    void delete(Integer id);

    User get(Integer id);

    PageInfo<User> list(User user, Pageable pageable);

    User selectByUsername(String username);
}
