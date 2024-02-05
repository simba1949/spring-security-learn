package vip.openpark.security.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 表名：role_permission
 * 表注释：角色权限中间表
*/
@Getter
@Setter
@Table(name = "role_permission")
public class RolePermissionDO implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Long id;

    /**
     * 角色主键
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 权限主键
     */
    @Column(name = "permission_id")
    private Long permissionId;

    /**
     * 是否启用，0表示否，1表示是
     */
    @Column(name = "bl_enable")
    private Byte blEnable;

    /**
     * 是否删除，0表示否，1表示是
     */
    @Column(name = "bl_delete")
    private Byte blDelete;

    /**
     * 版本号
     */
    @Column(name = "version")
    private Long version;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 创建人真实姓名
     */
    @Column(name = "creator")
    private String creator;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id")
    private Long creatorId;

    /**
     * 创建人code
     */
    @Column(name = "creator_code")
    private String creatorCode;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modify")
    private LocalDateTime gmtModify;

    /**
     * 修改人真实姓名
     */
    @Column(name = "modifier")
    private String modifier;

    /**
     * 修改人ID
     */
    @Column(name = "modifier_id")
    private Long modifierId;

    /**
     * 修改人code
     */
    @Column(name = "modifier_code")
    private String modifierCode;

    private static final long serialVersionUID = 1L;
}