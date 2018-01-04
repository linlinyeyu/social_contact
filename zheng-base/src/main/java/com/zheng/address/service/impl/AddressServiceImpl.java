package com.zheng.address.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zheng.address.dao.model.CharAddress;
import com.zheng.common.annotation.BaseService;
import com.zheng.common.base.BaseResult;
import com.zheng.common.base.BaseServiceImplPinet;
import com.zheng.address.dao.mapper.AddressMapper;
import com.zheng.address.dao.model.Address;
import com.zheng.address.service.AddressService;
import com.zheng.common.constants.CacheConstants;
import com.zheng.common.util.ErrorCodeEnum;
import com.zheng.common.util.FormatResponseUtil;
import com.zheng.common.util.RedisUtil;
import com.zheng.common.util.StringUtil;
import com.zheng.oss.upload.UploadFactory;
import com.zheng.oss.upload.bean.FileEntity;
import com.zheng.settings.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
* AddressService实现
* Created by shuzheng on 2017/12/21.
*/
@Service
@Transactional
@BaseService
public class AddressServiceImpl extends BaseServiceImplPinet<AddressMapper, Address> implements AddressService {

    private static Logger _log = LoggerFactory.getLogger(AddressServiceImpl.class);


    private Set<String> addressSet = new HashSet<>();

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    SettingsService settingsService;

    @Override
    public int findPro(String pro){

        //验证输入信息长度，小于2不执行查询，大于2，截取前2位
        if(StringUtil.isEmpty(pro) || pro.length() < 2){
            return 0;
        }
        if(pro.length() > 2){
            pro = pro.substring(0,2);
        }

        //查找缓存
        String key = CacheConstants.ADDRESS + "0_" + pro;
        Integer proId = RedisUtil.getObject(key, Integer.class);
        if(proId != null){
            return proId;
        }

        //查找数据库
        Address address = addressMapper.findPro(pro);
        if(address == null){
            RedisUtil.setObject(key, 0);
            addressSet.add(key);
            return 0;
        }

        //添加缓存
        RedisUtil.setObject(key, address.getId());
        addressSet.add(key);

        //返回对应地址信息id
        return address.getId();
    }

    @Override
    public int[] findCity(String pro,String city){

        //地址搜索信息长度验证
        if(pro == null || pro.length() < 2){
            return new int[]{0, 0};
        }

        //获取地址信息数组
        int proId = this.findPro(pro);
        if(proId == 0){
            return new int[]{0, 0};
        }
        int cityId = this.findId(city, proId);

        //返回地址信息数组
        return new int[]{proId, cityId};

    }

    @Override
    public int[] findCountry(String pro, String city, String country){
        int[] pc = this.findCity(pro, city);
        if(pc[1] == 0){
            return new int[]{pc[0], pc[1], 0};
        }
        int countryId = this.findId(country, pc[1]);
        return new int[]{pc[0], pc[1], countryId};
    }

    @Override
    public int[] findTown(String pro, String city, String country, String town){
        int[] pcc = findCountry(pro, city, country);
        if(pcc[2] == 0){
            return new int[]{pcc[0], pcc[1], pcc[2], 0};
        }
        int townId = this.findId(town, pcc[2]);
        return new int[]{pcc[0], pcc[1], pcc[2], townId};
    }

    public String[] findCountyById(Integer proId,Integer cityId, Integer countyId){
        if(proId == null || proId == 0){
            return new String[]{null, null, null};
        }
        if(cityId == null){
            cityId = 0;
        }
        if(countyId == null){
            countyId = 0;
        }

        Map<String, String> map = addressMapper.findCountyById(proId, cityId, countyId, 0);

        if(map == null){
            return new String[]{null, null, null};
        }

        return new String[]{map.get("province_name"), map.get("city_name"), map.get("county_name")};
    }

    public String[] findTownById(Integer proId, Integer cityId, Integer countyId, Integer townId){
        if(proId == null || proId == 0){
            return new String[]{null, null, null, null};
        }
        if(cityId == null){
            cityId = 0;
        }
        if(countyId == null){
            countyId = 0;
        }
        if(townId == null){
            townId = 0;
        }

        Map<String, String> map = addressMapper.findCountyById(proId, cityId, countyId, townId);

        if(map == null){
            return new String[]{null, null, null, null};
        }



        return new String[]{map.get("province_name"), map.get("city_name"), map.get("county_name"), map.get("town_name")};
    }

    @Override
    public int findId(String name,int parent_id){
        if(StringUtil.isEmpty(name)){
            return 0;
        }
        String key = CacheConstants.ADDRESS + parent_id + "_" + name;
        Integer addressId = RedisUtil.getObject(key, Integer.class);
        if(addressId != null){
            return addressId;
        }
        Address address = addressMapper.findAddress(name, parent_id);
        if(address == null){
            RedisUtil.setObject(key, 0);
            addressSet.add(key);
            return 0;
        }
        RedisUtil.setObject(key, address.getId());
        addressSet.add(key);
        return address.getId();
    }

    @Override
    public Address getAddressById(int id){
        String key = CacheConstants.ADDRESS + id;
        Address address = RedisUtil.getObject(key, Address.class);
        if(address != null){
            return address;
        }

        address = addressMapper.findAddressById(id);
        if(address != null){
            RedisUtil.setObject(key, address);
            addressSet.add(key);
        }
        return address;
    }

    @Override
    public String getAddressName(Integer id) {
        if(id == null){
            return null;
        }
        Address address = this.getAddressById(id);
        if(address == null){
            return null;
        }
        return address.getName();
    }

    @Override
    public BaseResult create(String name, String shortname, String pinyin, int pid) {
        if(StringUtil.isEmpty(name)){
            return FormatResponseUtil.error(ErrorCodeEnum.ADDRESS_NAME_EMPTY);
        }

        if(pid < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.ADDRESS_CANNOT_CREATE_PROVINCE);
        }

        Address pAddress = addressMapper.findAddressById(pid);
        if(pAddress == null){
            return FormatResponseUtil.error(ErrorCodeEnum.ADDRESS_PARENT_EMPTY);
        }

        Address address = new Address();
        address.setUpdateName(name);
        address.setUpdateShortName(shortname);
        address.setUpdateParentId(pid);
        address.setUpdateLevel((short)(pAddress.getUpdateLevel() +1));
        if(address.getUpdateLevel() == 2 && StringUtil.isEmpty(pinyin)){
            return FormatResponseUtil.error(ErrorCodeEnum.ADDRESS_CITY_PINYIN_EMPTY);
        }
        address.setUpdatePinyin(pinyin);
        address.setUpdateIsDeleted(false);
        int num = addressMapper.insertSelective(address);
        if(num < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.FAILED);
        }
        return FormatResponseUtil.formatResponse(address);
    }

    @Override
    public BaseResult update(Address address) {
        if(address == null){
            return FormatResponseUtil.error(ErrorCodeEnum.ADDRESS_PARAM_EMPTY);
        }
        if(address.getId() == null){
            return FormatResponseUtil.error(ErrorCodeEnum.ADDRESS_ID_EMPTY);
        }
        Address pro = addressMapper.findAddressById(address.getId());
        if(pro == null){
            return FormatResponseUtil.error(ErrorCodeEnum.ADDRESS_EMPTY);
        }
        if(pro.getLevel() != null && pro.getLevel() == 0){
            return FormatResponseUtil.error(ErrorCodeEnum.ADDRESS_PROVINCE_ERROR);
        }

        int num = addressMapper.updateByPrimaryKeySelective(address);
        if(num < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.FAILED);
        }
        return FormatResponseUtil.formatResponse();

    }

    @Override
    public List<Address> sonList(int id) {
        if(id < 0){
            return null;
        }
        List<Address> sonList = addressMapper.sonList(id);
        return sonList;
    }

    @Override
    public List<Address> nextList(int id) {
        if(id < 0){
            return null;
        }
        List<Address> sonList = addressMapper.nextList(id);
        return sonList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult publishAddress() throws Exception{
        //发布地址
        //第一步修改数据库全部地址信息
        addressMapper.publishAddress();

        //第二步获取全部地址信息，整理地址层级关系
        List<Address> addressList = addressMapper.getPublish();
        addressList = leaderAddress(addressList);


        //第三步，上传服务器
        //获取版本号
        String key = CacheConstants.ADDRESS_VERSION;
        Integer version = null;
        //json串化地址信息
        String address = JSONObject.toJSONString(addressList);
        //上传文件到服务器
        InputStream in = new ByteArrayInputStream(address.getBytes("UTF-8"));
        version = Integer.valueOf(settingsService.getByKey(key, "0"));
        String filename = "address_"+version+".json";
        FileEntity tResult = UploadFactory.getInstance().uploadByStream(in, filename);
        in.close();
        System.out.println(JSONObject.toJSONString(tResult));

        //第四步修改服务器版本号，修改服务器地址下载信息
        if(tResult == null){
            return FormatResponseUtil.error(ErrorCodeEnum.FAILED);
        }
        //更新数据库地址
        String addressKey = CacheConstants.ADDRESS_URL;
        String url = tResult.getUrl();
        String addressUrl = settingsService.getByKey(addressKey, url);
        if(!addressUrl.equals(url)){
            boolean flag = settingsService.updateKey(addressKey, url);
            if(!flag){
                return FormatResponseUtil.error(ErrorCodeEnum.FAILED);
            }
        }
        //更新下次更新版本号
        boolean flag = settingsService.updateKey(key, "" + (version + 1));
        if(!flag){
            return FormatResponseUtil.error(ErrorCodeEnum.FAILED);
        }

        //第五步，清空当前所有地址缓存
        for(String cachekey : addressSet){
            RedisUtil.remove(cachekey);
        }
        return FormatResponseUtil.formatResponse(addressList);
    }

    @Override
    public String getAddressUrl() {
        String key = CacheConstants.ADDRESS_URL;
        return settingsService.getByKey(key, "");
    }

    @Override
    public BaseResult deleted(int id) {
        if(id < 1){
            return FormatResponseUtil.error(ErrorCodeEnum.ADDRESS_ID_EMPTY);
        }
        List<Address> sonList = this.sonList(id);
        if(sonList != null && sonList.size() > 0){
            return FormatResponseUtil.error(ErrorCodeEnum.ADDRESS_HAS_SON);
        }
        Address address = new Address();
        address.setId(id);
        address.setUpdateIsDeleted(true);
        return this.update(address);
    }

    @Override
    public List<CharAddress> charAddress() {
        return addressMapper.charAddress();
    }

    @Override
    public List<Map<String, Object>> searchAddress(String keyword) {
        return addressMapper.searchAddress(keyword);
    }

    /**
     * 整理地址等级关系
     * @param addresses
     * @return
     */
    private List<Address> leaderAddress(List<Address> addresses){

        //若传入的参数为空则直接返回
        if(addresses == null || addresses.size() == 0){
            return addresses;
        }
        //暂存省级地址的map集合，得到地址存id为key，地址实体类为value
        Map<Integer, Address> proMap = new HashMap<>();
        //暂存所有地址工具集合，得到地址存id为key，地址实体类为value
        Map<Integer, Address> util = new HashMap<>();
        //记录省级地址排序位置，存储地址id
        List<Integer> sort = new ArrayList<>();

        //遍历所有地址
        for(Address address : addresses){
            //地址工具类添加指定地址
            util.put(address.getId(), address);

            //若为省级地址，添加至省级地址map，并记录地址id位置
            if(address.getLevel() != null && address.getLevel() == 1){
                proMap.put(address.getId(), address);
                sort.add(address.getId());
            }

            //若为非省级地址
            if(address.getLevel() != null && address.getLevel() > 1){
                //从工具地址中获取上级地址，并获取上级地址中的子地址列表成员
                //若该列表为空，则重新建立列表，若该列表存在，添加对应成员
                Address addressUtil =  util.get(address.getParentId());
                if(addressUtil == null){
                    address.setParentId(null);
                    address.setLevel(null);
                    continue;
                }
                List<Address> utils = addressUtil.getSonList();
                if(utils == null){
                    utils = new ArrayList<>();
                    addressUtil.setSonList(utils);
                }
                utils.add(address);
            }
            address.setParentId(null);
            address.setLevel(null);
        }

        //整理处理后的最后数据，返回
        List<Address> returnList = new ArrayList<>();
        for(Integer id : sort){
            returnList.add(proMap.get(id));
        }

        return returnList;
    }

}