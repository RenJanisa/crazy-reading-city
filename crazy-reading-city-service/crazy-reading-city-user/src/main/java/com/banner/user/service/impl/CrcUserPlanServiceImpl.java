package com.banner.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.banner.apis.book.IBookClient;
import com.banner.common.utils.ThreadLocalUtil;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.user.dtos.PlanConclusionDto;
import com.banner.model.user.dtos.UserPlansDto;
import com.banner.model.user.pojos.CrcUserPlan;
import com.banner.model.user.pojos.CrcUserPlanDetail;
import com.banner.user.mapper.CrcUserPlanDetailMapper;
import com.banner.user.mapper.CrcUserPlanMapper;
import com.banner.user.service.CrcUserPlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-08-11
 */
@Service
public class CrcUserPlanServiceImpl extends ServiceImpl<CrcUserPlanMapper, CrcUserPlan> implements CrcUserPlanService {

    @Resource
    private CrcUserPlanMapper crcUserPlanMapper;

    @Resource
    private IBookClient bookClient;

    @Override
    public ResponseResult getPlans(PageDto bookPlanPageDto) {

        String userId = bookPlanPageDto.getCondition();
        if (StrUtil.isBlank(userId)) userId = ThreadLocalUtil.getId().toString();

        Integer pageSize = bookPlanPageDto.getPageSize();
        int page = (bookPlanPageDto.getPage() - 1) * pageSize;

        List<UserPlansDto> userPlansDtos = crcUserPlanMapper.getPlansPage(userId, page, pageSize);
        userPlansDtos.forEach((item) -> item.setBookInfo(bookClient.getBookSimple(item.getBookId())));

        return ResponseResult.okResult(userPlansDtos);
    }

    @Resource
    private CrcUserPlanDetailMapper crcUserPlanDetailMapper;

    @Override
    public ResponseResult addPlanConclusion(PlanConclusionDto planConclusionDto) {

        if (planConclusionDto.getFlag().equals(1)) {
            //要总结分计划
            int update = crcUserPlanDetailMapper.updateById(CrcUserPlanDetail.builder()
                    .id(Long.parseLong(planConclusionDto.getObjId()))
                    .conclusion(planConclusionDto.getConclusion())
                    //修改状态为完成
                    .status(1).build());
            if (update > 0) return ResponseResult.okResult(200,"添加成功!");
        }else if(planConclusionDto.getFlag().equals(0)){
            //要总结计划
            //查看是否所有分计划完成
            int count = crcUserPlanDetailMapper.countPlanDetailStatus(planConclusionDto.getObjId());
            if (count>0) return ResponseResult.errorResult(500,"存在分计划未完成,暂不能总结计划,请先完成所有分计划!");
            int update = crcUserPlanMapper.updateById(CrcUserPlan.builder()
                    .id(Long.parseLong(planConclusionDto.getObjId()))
                    .conclusion(planConclusionDto.getConclusion())
                    //修改状态为完成
                    .status(1).build());
            if (update > 0) return ResponseResult.okResult(200,"添加成功!");
        }

        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }
}
