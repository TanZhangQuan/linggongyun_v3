package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.sms.SenSMS;
import com.example.common.util.JsonUtils;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.MerchantService;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.TaxPO;
import com.example.redis.dao.RedisDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商户信息
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantDao, Merchant> implements MerchantService {
    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private MerchantRoleDao merchantRoleDao;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SenSMS senSMS;

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Autowired
    private MerchantTaxDao merchantTaxDao;

    @Autowired
    private TaxDao taxDao;

    @Autowired
    private LinkmanDao linkmanDao;

    @Autowired
    private AddressDao addressDao;

    @Value("${TOKEN}")
    private String TOKEN;

    @Value("${PWD_KEY}")
    String PWD_KEY;

    /**
     * 根据用户名和密码进行登录
     * @param username 用户名
     * @param password 密码
     * @param response
     * @return
     */
    @Override
    public ReturnJson merchantLogin(String username, String password, HttpServletResponse response) {
        ReturnJson returnJson = new ReturnJson();
        String encryptPWD = PWD_KEY+ MD5.md5(password);
        QueryWrapper<Merchant> merchantQueryWrapper = new QueryWrapper<>();
        merchantQueryWrapper.eq("user_name",username).eq("pass_word",encryptPWD);
        Merchant me = merchantDao.selectOne(merchantQueryWrapper);
        if (me == null) {
            returnJson.setCode(300);
            returnJson.setMessage("用户名或密码错误");
            returnJson.setState("error");
            return returnJson;
        }
        returnJson.setCode(200);
        returnJson.setObj(me);
        Cookie cookie = new Cookie("token",me.getId());
        cookie.setMaxAge(60*60*24*7);
        response.addCookie(cookie);
        redisDao.set(me.getId(), JsonUtils.objectToJson(me));
        redisDao.setExpire(me.getId(),60*60*24*7);
        return returnJson;
    }

    /**
     * 根据TOKEN获取登录用户的ID
     * @param request
     * @return
     */
    @Override
    public String getId(HttpServletRequest request){
        String id = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            if (cookie.getName().equals(TOKEN)){
                id = cookie.getValue();
            }
        }
        return id;
    }

    /**
     * 发送验证码码
     * @param mobileCode
     * @return
     */
    @Override
    public ReturnJson senSMS(String mobileCode) {
        ReturnJson rj = new ReturnJson();
        Merchant merchant = this.getOne(new QueryWrapper<Merchant>().eq("login_mobile", mobileCode));
        if (merchant == null || merchant.equals(null)){
            rj.setCode(401);
            rj.setMessage("你还未注册，请先去注册！");
            return rj;
        }
        Map<String, Object> result = senSMS.senSMS(mobileCode);
        if ("000000".equals(result.get("statusCode"))) {
            rj.setCode(200);
            rj.setMessage("验证码发送成功");
            redisDao.set(mobileCode,String.valueOf(result.get("checkCode")));
            redisDao.setExpire(mobileCode,5*60);
        } else if ("160040".equals(result.get("statusCode"))) {
            rj.setCode(300);
            rj.setMessage(String.valueOf(result.get("statusMsg")));
        } else {
            rj.setCode(300);
            rj.setMessage(String.valueOf(result.get("statusMsg")));
        }
        return rj;
    }

    /**
     * 根据手机号和验证码进行登录
     * @param loginMobile 手机号码
     * @param checkCode 验证码
     * @param resource
     * @return
     */
    @Override
    public ReturnJson loginMobile(@NotBlank(message = "手机号不能为空") String loginMobile, @NotBlank(message = "验证码不能为空") String checkCode, HttpServletResponse resource) {
        ReturnJson rj = new ReturnJson();
        String redisCheckCode = redisDao.get(loginMobile);
        if (StringUtils.isBlank(redisCheckCode)){
            rj.setCode(300);
            rj.setMessage("验证码已过期，请重新获取！");
        } else if (!checkCode.equals(redisCheckCode)){
            rj.setCode(300);
            rj.setMessage("输入的验证码有误！");
        } else {
            rj.setCode(200);
            redisDao.remove(loginMobile);
            Merchant merchant = this.getOne(new QueryWrapper<Merchant>().eq("login_mobile", loginMobile));
            merchant.setPassWord("");
            merchant.setPayPwd("");
            rj.setObj(merchant);
            Cookie cookie = new Cookie("token",merchant.getId());
            cookie.setMaxAge(60*60*24*7);
            resource.addCookie(cookie);
            redisDao.set(merchant.getId(), JsonUtils.objectToJson(merchant));
            redisDao.setExpire(merchant.getId(),60*60*24*7);
        }
        return rj;
    }

    /**
     * 获取商户信息
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson merchantInfo(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        CompanyInfo companyInfo = companyInfoDao.selectById(merchant.getCompanyId());
        List<TaxPO> taxPOS = taxDao.selectByMerchantId(merchantId);
        List<Linkman> linkmanList = linkmanDao.selectList(new QueryWrapper<Linkman>().eq("merchant_id", merchantId).orderByAsc("is_not"));
        List<Address> addressList = addressDao.selectList(new QueryWrapper<Address>().eq("owner_id", merchantId).orderByAsc("is_not"));
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("merchant",merchant);
        map.put("companyInfo",companyInfo);
        map.put("taxPOS",taxPOS);
        map.put("linkmanList",linkmanList);
        map.put("addressList",addressList);
        list.add(map);
        return ReturnJson.success(list);
    }

    /**
     * 修改密码或忘记密码
     * @param loginMobile
     * @param checkCode
     * @param newPassWord
     * @return
     */
    @Override
    public ReturnJson updataPassWord(String loginMobile, String checkCode, String newPassWord) {
        String redisCode = redisDao.get(loginMobile);
        if (redisCode.equals(checkCode)) {
            Merchant merchant = new Merchant();
            merchant.setPassWord(PWD_KEY+MD5.md5(newPassWord));
            boolean flag = this.update(merchant, new QueryWrapper<Merchant>().eq("login_mobile", loginMobile));
            if (flag) {
                redisDao.remove(loginMobile);
                return ReturnJson.success("密码修改成功！");
            } else {
                return ReturnJson.success("密码修改失败！");
            }
        }
        return ReturnJson.error("你的验证码有误！");
    }


    /**
     * 根据merchantId获取权限信息
     * @param merchantId
     * @return
     */
    private MerchantRole getMerchantRole(String merchantId){
        QueryWrapper<MerchantRole> merchantRoleQueryWrapper = new QueryWrapper<MerchantRole>();
        merchantRoleQueryWrapper.eq("merchant_id",merchantId);
        return merchantRoleDao.selectOne(merchantRoleQueryWrapper);
    }

    @Override
    public Merchant findByID(String id) {
        return merchantDao.findByID(id);
    }

    @Override
    public ReturnJson getIdAndName() {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        List<Merchant> list = merchantDao.getIdAndName();
        if (list != null && list.size() > 0) {
            returnJson = new ReturnJson("查询成功", list, 200);
        }
        return returnJson;
    }

    @Override
    public String getNameById(String id) {
        return merchantDao.getNameById(id);
    }
}
