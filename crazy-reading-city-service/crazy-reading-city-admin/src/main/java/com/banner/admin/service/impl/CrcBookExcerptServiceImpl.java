package com.banner.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.banner.admin.mapper.CrcBookExcerptMapper;
import com.banner.admin.service.CrcBookExcerptService;
import com.banner.common.utils.ThreadLocalUtil;
import com.banner.model.book.dtos.CrcBookExcerptDto;
import com.banner.model.book.dtos.GetExcerptInfoDto;
import com.banner.model.book.pojos.CrcBookExcerpt;
import com.banner.model.common.dtos.PageResponseResult;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-28
 */
@Service
@Slf4j
public class CrcBookExcerptServiceImpl extends ServiceImpl<CrcBookExcerptMapper, CrcBookExcerpt> implements CrcBookExcerptService {



}
