package top.simba1949.common;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * @AUTHOR Theodore
 * @DATE 2019/12/29 10:39
 */
@Data
public class Page<E> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer pages;
    private Long total;
    private List<E> body;


    public static Page getPageFromPageInfo(PageInfo pageInfo){
        Page page = new Page<>();
        if (null != pageInfo){
            page.setPageNum(pageInfo.getPageNum());
            page.setPageSize(pageInfo.getPageSize());
            page.setPages(pageInfo.getPages());
            page.setTotal(pageInfo.getTotal());
            page.setBody(pageInfo.getList());
        }
        return page;
    }
}
