package com.xinmintx.system.service;

import com.xinmintx.system.domain.ProcurementCommodities;
import java.util.List;

/**
 * 商品审核Service接口
 * 
 * @author xinmintx
 * @date 2019-11-29
 */
public interface IProcurementCommoditiesService 
{
    /**
     * 查询商品审核
     * 
     * @param id 商品审核ID
     * @return 商品审核
     */
    public ProcurementCommodities selectProcurementCommoditiesById(Long id);

    /**
     * 查询商品审核列表
     * 
     * @param procurementCommodities 商品审核
     * @return 商品审核集合
     */
    public List<ProcurementCommodities> selectProcurementCommoditiesList(ProcurementCommodities procurementCommodities);

    /**
     * 新增商品审核
     * 
     * @param procurementCommodities 商品审核
     * @return 结果
     */
    public int insertProcurementCommodities(ProcurementCommodities procurementCommodities);

    /**
     * 修改商品审核
     * 
     * @param procurementCommodities 商品审核
     * @return 结果
     */
    public int updateProcurementCommodities(ProcurementCommodities procurementCommodities);

    /**
     * 批量删除商品审核
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProcurementCommoditiesByIds(String ids);

    /**
     * 删除商品审核信息
     * 
     * @param id 商品审核ID
     * @return 结果
     */
    public int deleteProcurementCommoditiesById(Long id);
}
