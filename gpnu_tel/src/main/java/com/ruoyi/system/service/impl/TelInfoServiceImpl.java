package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TelInfoMapper;
import com.ruoyi.system.domain.TelInfo;
import com.ruoyi.system.service.ITelInfoService;

import javax.annotation.Resource;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-25
 */
@Service
public class TelInfoServiceImpl implements ITelInfoService 
{
    @Resource
    private TelInfoMapper telInfoMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public TelInfo selectTelInfoById(Long id)
    {
        return telInfoMapper.selectTelInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param telInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TelInfo> selectTelInfoList(TelInfo telInfo)
    {
        return telInfoMapper.selectTelInfoList(telInfo);
    }

    @Override
    public List<TelInfo> fuzzyQueryTelInfo(TelInfo telInfo) {
        return telInfoMapper.fuzzyQueryTelInfo(telInfo);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param telInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTelInfo(TelInfo telInfo)
    {
        telInfo.setCreateTime(DateUtils.getNowDate());
        return telInfoMapper.insertTelInfo(telInfo);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param telInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTelInfo(TelInfo telInfo)
    {
        telInfo.setUpdateTime(DateUtils.getNowDate());
        return telInfoMapper.updateTelInfo(telInfo);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteTelInfoByIds(Long[] ids)
    {
        return telInfoMapper.deleteTelInfoByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteTelInfoById(Long id)
    {
        return telInfoMapper.deleteTelInfoById(id);
    }
}
