package com.zheng.address.service;

import com.zheng.address.dao.model.CharAddress;
import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServicePinet;
import com.zheng.address.dao.model.Address;

import java.util.List;
import java.util.Map;

/**
* AddressService接口
* Created by pinet on 2017/12/21.
*/
public interface AddressService extends BaseServicePinet<Address> {


    /**
     * 根据传入省份名称获取数据库address表中省份id，存入缓存
     * @param pro
     * @return
     */
    int findPro(String pro);

    /**
     * 返回长度为2的int数组，int[0]为ProvinceId，int[1]为cityId
     * @param pro
     * @param city
     * @return
     */
    int[] findCity(String pro,String city);

    /**
     * 返回长度为3的int数组，int[0]为ProvinceId，int[1]为cityId，int[2]为countryId
     * @param pro
     * @param city
     * @return
     */
    int[] findCountry(String pro, String city, String country);

    /**
     * 返回长度为4的int数组，int[0]为ProvinceId，int[1]为cityId，int[2]为countryId,int[3]为townId
     * @param pro
     * @param city
     * @return
     */
    int[] findTown(String pro, String city, String country, String town);


    String[] findCountyById(Integer proId,Integer cityId, Integer countyId);

    String[] findTownById(Integer proId, Integer cityId, Integer countyId, Integer townId);

    /**
     * 通用方法，区域名称和父id获取地址对象
     * @param name
     * @param parent_id
     * @return
     */
    int findId(String name,int parent_id);

    /**
     * 根据地址id获取地址信息
     * @param id
     * @return
     */
    Address getAddressById(int id);

    /**
     * 根据地址id获取地址名称
     * @param id
     * @return
     */
    String getAddressName(Integer id);

    /**
     * 创建地址
     * @param name
     * @return
     */
    BaseResult create(String name, String shortname, String firstChar, int pid);

    /**
     * 修改地址
     * @param address
     * @return
     */
    BaseResult update(Address address);

    /**
     * 获取指定id地址的下级列表，获取省列表id=0
     * @param id
     * @return
     */
    List<Address> sonList(int id);

    /**
     * 获取指定id地址的下级列表，获取省列表id=0
     * @param id
     * @return
     */
    List<Address> nextList(int id);

    /**
     * 发布地址
     * @return
     */
    BaseResult publishAddress() throws Exception;

    /**
     * 获取地址url
     * @return
     */
    String getAddressUrl();

    /**
     * 删除地址
     * @param id
     * @return
     */
    BaseResult deleted(int id);

    /**
     * 获取字母地址列表
     * @return
     */
    List<CharAddress> charAddress();

    /**
     * 搜索地址信息，并将信息拼接成，province_id，city_id,county_id, town_id的形式
     * */
    List<Map<String,Object>> searchAddress(String keyword);
}