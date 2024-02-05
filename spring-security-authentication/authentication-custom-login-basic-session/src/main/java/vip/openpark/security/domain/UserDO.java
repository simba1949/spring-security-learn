package vip.openpark.security.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 表名：user
 * 表注释：用户信息表
*/
@Getter
@Setter
@Table(name = "user")
public class UserDO implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Long id;

    /**
     * 业务编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 用户登录名
     */
    @Column(name = "username")
    private String username;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 用户昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 性别，0表示女，1表示男
     */
    @Column(name = "gender")
    private Byte gender;

    /**
     * 出生日期
     */
    @Column(name = "birthday")
    private LocalDateTime birthday;

    /**
     * 民族
     */
    @Column(name = "nation")
    private String nation;

    /**
     * 国家
     */
    @Column(name = "country_name")
    private String countryName;

    /**
     * 身份证信息
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 手机号码
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 邮件
     */
    @Column(name = "email")
    private String email;

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