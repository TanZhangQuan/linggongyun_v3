package com.example.merchant.service.impl;

import com.example.mybatis.entity.Menu;
import com.example.mybatis.mapper.MenuDao;
import com.example.merchant.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
