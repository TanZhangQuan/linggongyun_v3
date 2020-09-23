package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.merchant.service.CompanyLadderServiceService;
import com.example.mybatis.entity.CompanyLadderService;
import com.example.mybatis.mapper.CompanyLadderServiceDao;
import org.springframework.stereotype.Service;

@Service
public class CompanyLadderServiceServiceImpl extends ServiceImpl<CompanyLadderServiceDao, CompanyLadderService> implements CompanyLadderServiceService {
}
