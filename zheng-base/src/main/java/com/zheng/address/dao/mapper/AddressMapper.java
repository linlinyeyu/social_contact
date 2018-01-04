package com.zheng.address.dao.mapper;

import com.zheng.address.dao.model.Address;
import com.zheng.address.dao.model.CharAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    /* ====================================================================================== */
    /**
     * 模糊查找省信息
     * @param pro
     * @return
     */
    Address findPro(String pro);

    /**
     * 地址名称，父地址id获取地址信息
     * @param name
     * @param parent_id
     * @return
     */
    Address findAddress(String name, int parent_id);

    /**
     * 根据id查找地址，包括删除的地址
     * @param id
     * @return
     */
    Address findAddressById(int id);

    Map<String, String> findCountyById(@Param("province_id")Integer province_id, @Param("city_id")Integer city_id,
                                       @Param("county_id") Integer county_id, @Param("town_id") Integer town_id );

    /**
     * 获取指定id地址的下级地址列表,设置
     * @param update_parent_id
     * @return
     */
    List<Address> sonList(int update_parent_id);

    /**
     * 获取指定id地址的下级地址列表，显示
     * @param parent_id
     * @return
     */
    List<Address> nextList(int parent_id);

    /**
     * 发布地址
     */
    void publishAddress();

    /**
     * 获取全部地址
     * @return
     */
    List<Address> getPublish();

    /**
     * 获取字母地址列表
     * @return
     */
    List<CharAddress> charAddress();

    /**
     * 搜索4级地址信息，返回相关信息
     * */
    List<Map<String, Object>> searchAddress(String area);
}