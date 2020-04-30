package com.ljw.gmall.manager.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.ljw.gmall.bean.PmsBaseAttrInfo;
import com.ljw.gmall.bean.PmsBaseAttrValue;
import com.ljw.gmall.bean.PmsBaseSaleAttr;
import com.ljw.gmall.manager.mapper.PmsBaseAttrInfoMapper;
import com.ljw.gmall.manager.mapper.PmsBaseAttrValueMapper;
import com.ljw.gmall.manager.mapper.PmsBaseSaleAttrMapper;
import com.ljw.gmall.service.PmsBaseAttrInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PmsBaseAttrInfoServiceImp implements PmsBaseAttrInfoService {

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    /**
     * 需要封装
     * @param catalog3Id
     * @return
     */
    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo pmsBaseAttrInfo=new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> pmsAttrInfos = pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);
        for (PmsBaseAttrInfo pmsAttrInfo : pmsAttrInfos) {
            List<PmsBaseAttrValue> pmsBaseAttrValues = new ArrayList<>();
            PmsBaseAttrValue pmsBaseAttrValue =new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(pmsAttrInfo.getId());
            pmsBaseAttrValues=pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
            pmsAttrInfo.setAttrValueList(pmsBaseAttrValues);
        }

        return pmsAttrInfos;
    }

    @Override
    public String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {

        String id = pmsBaseAttrInfo.getId();
        if (StringUtils.isBlank(id)){
            //id 为空 保存
            //保存属性并得到主键
            /**
             * 这里  insert  不管数据是否为空都会插入
             * 而   insertSelective  会判定是不是为空数据  若为空数据则不会插入
             */
            pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
            //保存属性值
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
            }


        }else{
            //id 不空 修改
            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo("id",pmsBaseAttrInfo.getId());
            pmsBaseAttrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo,example);

            //属性值
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();

            //按照属性id删除所有属性值
            PmsBaseAttrValue pmsBaseAttrValueDEL = new PmsBaseAttrValue();
            pmsBaseAttrValueDEL.setAttrId(pmsBaseAttrInfo.getId());
            pmsBaseAttrValueMapper.delete(pmsBaseAttrValueDEL);

           for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
               pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);

            }

        }

        return "success";
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {

        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        List<PmsBaseAttrValue> pmsBaseAttrValues = pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
        return pmsBaseAttrValues;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectAll();
    }

    @Override
    public List<PmsBaseAttrInfo> getAttrValueListByValueId(Set<String> valueIdSet) {
        String valueIdStr = StringUtils.join(valueIdSet, ",");//41,45,46
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.selectAttrValueListByValueId(valueIdStr);
        return pmsBaseAttrInfos;
    }
}
