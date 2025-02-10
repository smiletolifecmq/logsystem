package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 客户信息对象 sys_clientele
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
public class SysClientele extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;

    /** 照片 */
    @Excel(name = "照片")
    private String img;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 个人专长 */
    @Excel(name = "个人专长")
    private String expertise;

    /** 兴趣爱好 */
    @Excel(name = "兴趣爱好")
    private String hobbies;

    /** 出生 */
    @Excel(name = "出生")
    private String born;

    /** 是否吸烟，0否1是 */
    @Excel(name = "是否吸烟，0否1是")
    private Long smoke;

    /** 酒量 */
    @Excel(name = "酒量")
    private String capacity;

    /** 毕业学校 */
    @Excel(name = "毕业学校")
    private String school;

    /** 学历 */
    @Excel(name = "学历")
    private String education;

    /** 身高，单位米 */
    @Excel(name = "身高，单位米")
    private String height;

    /** 职称 */
    @Excel(name = "职称")
    private String jobtitle;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 职位 */
    @Excel(name = "职位")
    private String position;

    /** 家庭住址 */
    @Excel(name = "家庭住址")
    private String address;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setImg(String img) 
    {
        this.img = img;
    }

    public String getImg() 
    {
        return img;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setExpertise(String expertise) 
    {
        this.expertise = expertise;
    }

    public String getExpertise() 
    {
        return expertise;
    }
    public void setHobbies(String hobbies) 
    {
        this.hobbies = hobbies;
    }

    public String getHobbies() 
    {
        return hobbies;
    }
    public void setBorn(String born) 
    {
        this.born = born;
    }

    public String getBorn() 
    {
        return born;
    }
    public void setSmoke(Long smoke) 
    {
        this.smoke = smoke;
    }

    public Long getSmoke() 
    {
        return smoke;
    }
    public void setCapacity(String capacity) 
    {
        this.capacity = capacity;
    }

    public String getCapacity() 
    {
        return capacity;
    }
    public void setSchool(String school) 
    {
        this.school = school;
    }

    public String getSchool() 
    {
        return school;
    }
    public void setEducation(String education) 
    {
        this.education = education;
    }

    public String getEducation() 
    {
        return education;
    }
    public void setHeight(String height) 
    {
        this.height = height;
    }

    public String getHeight() 
    {
        return height;
    }
    public void setJobtitle(String jobtitle) 
    {
        this.jobtitle = jobtitle;
    }

    public String getJobtitle() 
    {
        return jobtitle;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }
    public void setPosition(String position) 
    {
        this.position = position;
    }

    public String getPosition() 
    {
        return position;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("img", getImg())
            .append("name", getName())
            .append("expertise", getExpertise())
            .append("hobbies", getHobbies())
            .append("born", getBorn())
            .append("smoke", getSmoke())
            .append("capacity", getCapacity())
            .append("school", getSchool())
            .append("education", getEducation())
            .append("height", getHeight())
            .append("jobtitle", getJobtitle())
            .append("unit", getUnit())
            .append("position", getPosition())
            .append("address", getAddress())
            .toString();
    }
}
