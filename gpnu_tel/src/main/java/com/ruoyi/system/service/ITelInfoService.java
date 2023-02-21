package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TelInfo;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-06-25
 */
public interface ITelInfoService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public TelInfo selectTelInfoById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param telInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TelInfo> selectTelInfoList(TelInfo telInfo);

    /**
     * 查询【请填写功能名称】列表
     *模糊查询
     * @param telInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TelInfo> fuzzyQueryTelInfo(TelInfo telInfo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param telInfo 【请填写功能名称】
     * @return 结果
     */
    public int insertTelInfo(TelInfo telInfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param telInfo 【请填写功能名称】
     * @return 结果
     */
    public int updateTelInfo(TelInfo telInfo);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteTelInfoByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteTelInfoById(Long id);
}