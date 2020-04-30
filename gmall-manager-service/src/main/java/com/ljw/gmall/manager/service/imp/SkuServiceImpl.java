package com.ljw.gmall.manager.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ljw.gmall.bean.PmsSkuAttrValue;
import com.ljw.gmall.bean.PmsSkuImage;
import com.ljw.gmall.bean.PmsSkuInfo;
import com.ljw.gmall.bean.PmsSkuSaleAttrValue;
import com.ljw.gmall.manager.mapper.PmsSkuAttrValueMapper;
import com.ljw.gmall.manager.mapper.PmsSkuImageMapper;
import com.ljw.gmall.manager.mapper.PmsSkuInfoMapper;
import com.ljw.gmall.manager.mapper.PmsSkuSaleAttrValueMapper;
import com.ljw.gmall.service.SkuService;
import com.ljw.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    RedisUtil redisUtil;
    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        //插入skuinfo
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        //插入平台属性关联
        List<PmsSkuAttrValue> pmsSkuAttrValues = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : pmsSkuAttrValues) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }


        //插入销售属性关联
        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValues) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }

        //插入图片信息
        List<PmsSkuImage> pmsSkuImages = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : pmsSkuImages) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }
    }

    public PmsSkuInfo getSkuByIdFromDb(String skuId) {
         //商品详情
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);
        //图片列表
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> skuImages = pmsSkuImageMapper.select(pmsSkuImage);
        skuInfo.setSkuImageList(skuImages);
        return skuInfo;
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId,String ip) {
        System.out.println("ip为"+ip+"的同学:"+Thread.currentThread().getName()+"进入的商品详情的请求");
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
          //链接缓存
        Jedis jedis = redisUtil.getJedis();
        //查询缓存
        String skuKey = "sku:"+skuId+":info";
        String skuJson = jedis.get(skuKey);

        //对于parseObject 其所需的值不能为空故而需要判断
        if(StringUtils.isNotBlank(skuJson)){//if(skuJson!=null&&!skuJson.equals(""))
            System.out.println("ip为"+ip+"的同学:"+Thread.currentThread().getName()+"从缓存中获取商品详情");
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        }else{
            //缓存没有找mysql
            System.out.println("ip为"+ip+"的同学:"+Thread.currentThread().getName()+"发现缓存中没有，申请缓存的分布式锁："+"sku:" + skuId + ":lock");
            //设置分布式锁
            String token  = UUID.randomUUID().toString();
            String OK = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10*1000);// 拿到锁的线程有10秒的过期时间
           if (StringUtils.isNotBlank(OK)&&OK.equals("OK")){
               //设置成功，有权在10秒的过期时间内访问数据库
               System.out.println("ip为"+ip+"的同学:"+Thread.currentThread().getName()+"有权在10秒的过期时间内访问数据库："+"sku:" + skuId + ":lock");
               pmsSkuInfo =  getSkuByIdFromDb(skuId);
               if (pmsSkuInfo!=null){
                   //mysql查询结果存入redis
                   jedis.set("sku:"+skuId+":info",JSON.toJSONString(pmsSkuInfo));
               }else{
                   //数据库中不存在该sku
                   //为了防止缓存穿透 null或者空字符串值数值给redis
                   jedis.setex("sku:"+skuId+":info",60*3,JSON.toJSONString(""));
               }

               //在访问mysql后。将mysql的分布锁释放
               System.out.println("ip为"+ip+"的同学:"+Thread.currentThread().getName()+"使用完毕，将锁归还："+"sku:" + skuId + ":lock");
               String lockTocken = jedis.get("sku:" + skuId + ":lock");
               if (StringUtils.isNotBlank(lockTocken)&&lockTocken.equals(token)){
                   //jedis.eval("lua");可与用lua脚本，在查询到key的同时删除该key，防止高并发下的意外的发生
                   // String script = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1])else return 0 end";
                   //  jedis.eval(script, Collections.singletonList("lock"),Collections.singletonList(token));
                   jedis.del("sku:" + skuId + ":lock");// 用token确认删除的是自己的sku的锁
               }

           }else{
               // 设置失败，自旋（该线程在睡眠几秒后，重新尝试访问本方法）
               System.out.println("ip为"+ip+"的同学:"+Thread.currentThread().getName()+"没有拿到锁，开始自旋");

               return getSkuById(skuId,ip);
           }
        }
        jedis.close();
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {

        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
        return pmsSkuInfos;
    }

    @Override
    public List<PmsSkuInfo> getAllSku() {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectAll();
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            String skuId = pmsSkuInfo.getId();
            PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(skuId);
            List<PmsSkuAttrValue> select = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);
            pmsSkuInfo.setSkuAttrValueList(select);
        }
        return pmsSkuInfos;
    }

    @Override
    public boolean checkPrice(String productSkuId, BigDecimal price) {
        boolean b =false;
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(productSkuId);
        PmsSkuInfo pmsSkuInfo1 = pmsSkuInfoMapper.selectOne(pmsSkuInfo);

        BigDecimal price1 = pmsSkuInfo1.getPrice();
        if (price1.compareTo(price)==0){
            b=true;
        }
        return b;
    }

}
