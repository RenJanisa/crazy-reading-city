package com.banner.book.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.banner.book.mapper.CrcBookExcerptMapper;
import com.banner.book.mapper.CrcSensitiveMapper;
import com.banner.book.service.CrcBookScanService;
import com.banner.book.utils.SensitiveWordUtil;
import com.banner.common.exception.CRCException;
import com.banner.model.book.pojos.CrcBookExcerpt;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.banner.book.utils.BookExcerptConstants.EXCERPT_PUBLIC_OK;
import static com.banner.book.utils.BookExcerptConstants.EXCERPT_PUBLIC_PROBLEM;

/**
 * @author rjj
 * @date 2023/7/28 - 9:34
 */
@Service
public class CrcBookScanServiceImpl implements CrcBookScanService {


    @Resource
    private CrcBookExcerptMapper crcBookExcerptMapper;

    @Override
    @Async
    public void scanBook(Long id) {

        //获取摘录
        CrcBookExcerpt crcBookExcerpt = crcBookExcerptMapper.selectById(id);
        if (crcBookExcerpt == null) {
            throw new RuntimeException("无该摘录");
        }

        //从内容中提取纯文本内容
        Map<String, String> text = handleText(crcBookExcerpt.getContent(), crcBookExcerpt.getThoughts());

        //审核文本内容

        if (text == null || handleSensitiveScan(text)) {
            //存在敏感词,修改状态
            crcBookExcerptMapper.updateStatus(id, EXCERPT_PUBLIC_PROBLEM);
            return;
        }

        //审核通过,修改状态
        crcBookExcerptMapper.updateStatus(id, EXCERPT_PUBLIC_OK);

    }

    @Resource
    private CrcSensitiveMapper crcSensitiveMapper;

    //审核敏感词
    private boolean handleSensitiveScan(Map<String, String> text) {

        boolean flag = false;

        //获取所有的敏感词
        List<String> sensitiveList = crcSensitiveMapper.getCrcSensitiveList();

        //初始化敏感词库
        SensitiveWordUtil.initMap(sensitiveList);

        //查看是否包含敏感词
        Map<String, Integer> map = SensitiveWordUtil.matchWords(text.get("content"));
        if (map.size() > 0) {
            flag = true;
        }

        return flag;


    }

    //提取文本内容
    private Map<String, String> handleText(String content, String thoughts) {
        //存储纯文本内容
        StringBuilder stringBuilder = new StringBuilder();
        //从自媒体文章的内容中提取文本
        if (StrUtil.isBlank(content)
                && StrUtil.isBlank(thoughts)) {
            return null;
        }

        List<Map> maps = JSONUtil.toList(content, Map.class);
        maps.addAll(JSONUtil.toList(thoughts, Map.class));

        for (Map map : maps) {
            if (map.get("type").equals("text")) {
                stringBuilder.append(map.get("value"));
            }
        }

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("content", stringBuilder.toString());
        return resultMap;
    }

}
