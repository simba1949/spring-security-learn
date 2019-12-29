package top.simba1949.common.rbac;

import lombok.Data;
import top.simba1949.common.BaseCommon;
import javax.persistence.*;
import java.util.List;

/**
 * 资源
 *
 * @AUTHOR Theodore
 * @DATE 2019/12/28 17:18
 */
@Data
@Table(name = "sys_permission")
public class Permission extends BaseCommon {
    private static final long serialVersionUID = -6337740924256318067L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "permission_name")
    private String permissionName;
    @Column(name = "req_method")
    private String reqMethod;
    @Column
    private String uri;

    @Transient
    List<Role> roles;
}
