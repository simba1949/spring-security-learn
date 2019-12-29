package top.simba1949.common;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/28 19:16
 */
@Data
public class BaseCommon implements Serializable {
    private static final long serialVersionUID = -1515424472195722692L;

    /** 1 表示是， 0 表示否 */
    public static final Integer DELETED = 1;
    public static final Integer UN_DELETE = 0;

    @LogicDelete
    @Column(name = "is_delete")
    private Integer delete;

    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Column(name = "creator")
    private String creator;

    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Column(name = "updater")
    private String updater;
}
