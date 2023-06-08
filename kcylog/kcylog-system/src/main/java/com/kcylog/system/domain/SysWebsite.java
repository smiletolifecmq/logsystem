package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 网站整合对象 sys_website
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
public class SysWebsite extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 网站自增id */
    private String webId;

    /** 网站标题 */
    @Excel(name = "网站标题")
    private String title;

    /** 网站地址 */
    @Excel(name = "网站地址")
    private String webUrl;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imgUrl;

    /** 点击数 */
    @Excel(name = "点击数")
    private String num;

    public void setWebId(String webId) 
    {
        this.webId = webId;
    }

    public String getWebId() 
    {
        return webId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setWebUrl(String webUrl) 
    {
        this.webUrl = webUrl;
    }

    public String getWebUrl() 
    {
        return webUrl;
    }
    public void setImgUrl(String imgUrl) 
    {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() 
    {
        return imgUrl;
    }
    public void setNum(String num) 
    {
        this.num = num;
    }

    public String getNum() 
    {
        return num;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("webId", getWebId())
            .append("title", getTitle())
            .append("webUrl", getWebUrl())
            .append("imgUrl", getImgUrl())
            .append("num", getNum())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
