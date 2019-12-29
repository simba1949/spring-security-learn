package top.simba1949.common.rbac;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import top.simba1949.common.BaseCommon;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/28 17:06
 */
@Data
@Table(name = "sys_user")
public class User extends BaseCommon {

    private static final long serialVersionUID = 7840604798473360816L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String username;
    @Column
    private String nickname;
    @Column
    private String password;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;
    @Column
    private Integer sex;
    @Column
    private String mobile;
    @Column
    private String email;

    @Transient
    private List<Role> roles;
}
