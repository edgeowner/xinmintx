package com.xinmintx.system.mapper;

import com.xinmintx.system.domain.MemberBonus;
import java.util.List;

/**
 * 会员奖金池Mapper接口
 * 
 * @author xinmintx
 * @date 2020-03-12
 */
public interface MemberBonusMapper 
{
    /**
     * 查询会员奖金池
     * 
     * @param id 会员奖金池ID
     * @return 会员奖金池
     */
    public MemberBonus selectMemberBonusById(Long id);

    /**
     * 查询会员奖金池列表
     * 
     * @param memberBonus 会员奖金池
     * @return 会员奖金池集合
     */
    public List<MemberBonus> selectMemberBonusList(MemberBonus memberBonus);

    /**
     * 新增会员奖金池
     * 
     * @param memberBonus 会员奖金池
     * @return 结果
     */
    public int insertMemberBonus(MemberBonus memberBonus);

    /**
     * 修改会员奖金池
     * 
     * @param memberBonus 会员奖金池
     * @return 结果
     */
    public int updateMemberBonus(MemberBonus memberBonus);

    /**
     * 删除会员奖金池
     * 
     * @param id 会员奖金池ID
     * @return 结果
     */
    public int deleteMemberBonusById(Long id);

    /**
     * 批量删除会员奖金池
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMemberBonusByIds(String[] ids);

    Integer queryMemberBonus();

    MemberBonus queryMemberBonusInfo();
}
