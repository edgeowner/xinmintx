package com.xinmintx.system.mapper;

import com.xinmintx.system.domain.Gift;
import com.xinmintx.system.domain.Member;

import java.util.List;

/**
 * 礼包Mapper接口
 * 
 * @author xinmintx
 * @date 2019-12-16
 */
public interface GiftMapper 
{
    /**
     * 查询礼包
     * 
     * @param id 礼包ID
     * @return 礼包
     */
    public Gift selectGiftById(Long id);

    /**
     * 查询礼包列表
     * 
     * @param gift 礼包
     * @return 礼包集合
     */
    public List<Gift> selectGiftList(Gift gift);

    /**
     * 新增礼包
     * 
     * @param gift 礼包
     * @return 结果
     */
    public int insertGift(Gift gift);

    /**
     * 修改礼包
     * 
     * @param gift 礼包
     * @return 结果
     */
    public int updateGift(Gift gift);

    /**
     * 删除礼包
     * 
     * @param id 礼包ID
     * @return 结果
     */
    public int deleteGiftById(Long id);

    /**
     * 批量删除礼包
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGiftByIds(String[] ids);

    List<Member> selectReceiptor(int giftId);

    int delGiftByIds(Long ids);
}
