package top.simba1949.common.rbac;

import lombok.Data;
import top.simba1949.common.BaseCommon;

import javax.persistence.*;
import java.util.List;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/28 17:07
 */
@Data
@Table(name = "sys_role")
public class Role extends BaseCommon {
    private static final long serialVersionUID = 195545746572251599L;

    public static final String ROLE_PREFIX = "ROLE_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String roleName;
    @Column
    private Integer enable;

    @Transient
    private List<User> users;
    @Transient
    private List<Permission> permissions;
}
