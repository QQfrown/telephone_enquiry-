package com.ruoyi.web.controller.gpnu_tel;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.TelInfo;
import com.ruoyi.system.service.ITelInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2022-06-25
 */
@RestController
@RequestMapping("/tel/info")
public class TelInfoController extends BaseController
{
    @Autowired
    private ITelInfoService telInfoService;

    /**
     * 查询【请填写功能名称】列表
     */
//    @PreAuthorize("@ss.hasPermi('tel:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(TelInfo telInfo)
    {
        startPage();
        List<TelInfo> list = telInfoService.selectTelInfoList(telInfo);
        return getDataTable(list);
    }

    /**
     * 模糊查询
     * @param telInfo
     * @return
     */
//    @PreAuthorize("@ss.hasPermi('tel:info:list')")
    @GetMapping("/fuzzyQuery")
    public TableDataInfo fuzzyQuery(TelInfo telInfo)
    {
        startPage();
        List<TelInfo> list = telInfoService.fuzzyQueryTelInfo(telInfo);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('tel:info:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TelInfo telInfo)
    {
        List<TelInfo> list = telInfoService.selectTelInfoList(telInfo);
        ExcelUtil<TelInfo> util = new ExcelUtil<TelInfo>(TelInfo.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('tel:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(telInfoService.selectTelInfoById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('tel:info:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TelInfo telInfo)
    {
        return toAjax(telInfoService.insertTelInfo(telInfo));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('tel:info:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TelInfo telInfo)
    {
        return toAjax(telInfoService.updateTelInfo(telInfo));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('tel:info:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(telInfoService.deleteTelInfoByIds(ids));
    }
}
