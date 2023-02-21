package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 tel_info
 * 
 * @author ruoyi
 * @date 2022-06-25
 */
public class TelInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 展示级别 */
    @Excel(name = "展示级别")
    private Long level;

    /** 部门展示级别 */
    @Excel(name = "部门展示级别")
    private Long departmentLevel;

    /** 部门内展示级别 */
    @Excel(name = "部门内展示级别")
    private Long officeLevel;

    /** 校区 */
    @Excel(name = "校区")
    private String campus;

    /** 组织类型 */
    @Excel(name = "组织类型")
    private String mechanism;

    /** 部门类型 */
    @Excel(name = "部门类型")
    private String departmentType;

    /** 办公楼 */
    @Excel(name = "办公楼")
    private String officeBuilder;

    /** 办公室号 */
    @Excel(name = "办公室号")
    private String officeNumber;

    /** 办公室名 */
    @Excel(name = "办公室名")
    private String officeName;

    /** 电话 */
    @Excel(name = "电话")
    private String telephone;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLevel(Long level) 
    {
        this.level = level;
    }

    public Long getDepartmentLevel() {
        return departmentLevel;
    }

    public void setDepartmentLevel(Long departmentLevel) {
        this.departmentLevel = departmentLevel;
    }

    public Long getOfficeLevel() {
        return officeLevel;
    }

    public void setOfficeLevel(Long officeLevel) {
        this.officeLevel = officeLevel;
    }

    public Long getLevel()
    {
        return level;
    }
    public void setCampus(String campus) 
    {
        this.campus = campus;
    }

    public String getCampus() 
    {
        return campus;
    }
    public void setMechanism(String mechanism) 
    {
        this.mechanism = mechanism;
    }

    public String getMechanism() 
    {
        return mechanism;
    }
    public void setDepartmentType(String departmentType) 
    {
        this.departmentType = departmentType;
    }

    public String getDepartmentType() 
    {
        return departmentType;
    }
    public void setOfficeBuilder(String officeBuilder) 
    {
        this.officeBuilder = officeBuilder;
    }

    public String getOfficeBuilder() 
    {
        return officeBuilder;
    }
    public void setOfficeNumber(String officeNumber) 
    {
        this.officeNumber = officeNumber;
    }

    public String getOfficeNumber() 
    {
        return officeNumber;
    }
    public void setOfficeName(String officeName) 
    {
        this.officeName = officeName;
    }

    public String getOfficeName() 
    {
        return officeName;
    }
    public void setTelephone(String telephone) 
    {
        this.telephone = telephone;
    }

    public String getTelephone() 
    {
        return telephone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("level", getLevel())
            .append("departmentLevel", getDepartmentLevel())
            .append("officeLevel", getOfficeLevel())
            .append("campus", getCampus())
            .append("mechanism", getMechanism())
            .append("departmentType", getDepartmentType())
            .append("officeBuilder", getOfficeBuilder())
            .append("officeNumber", getOfficeNumber())
            .append("officeName", getOfficeName())
            .append("telephone", getTelephone())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
