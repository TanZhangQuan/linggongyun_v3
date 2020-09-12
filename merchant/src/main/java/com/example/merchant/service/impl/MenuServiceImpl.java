package com.example.merchant.service.impl;

import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Menu;
import com.example.mybatis.mapper.MenuDao;
import com.example.merchant.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.vo.MenuListVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Resource
    private MenuDao menuDao;

    @Override
    public ReturnJson getMenuList() {
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        List<MenuListVo> listVos=menuDao.getMenuList();
        if (listVos!=null&&listVos.size()!=0){
            returnJson=new ReturnJson("查询成功",listVos,200);
        }
        return returnJson;
    }
}
