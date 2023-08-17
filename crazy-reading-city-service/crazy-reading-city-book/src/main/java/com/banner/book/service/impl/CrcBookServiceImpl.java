package com.banner.book.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.banner.apis.user.IUserClient;
import com.banner.book.mapper.BookMessageMapper;
import com.banner.book.mapper.CrcBookExcerptMapper;
import com.banner.book.mapper.CrcBookMapper;
import com.banner.book.service.CrcBookService;
import com.banner.model.book.dtos.*;
import com.banner.model.book.pojos.CrcBook;
import com.banner.model.book.pojos.Message;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.search.dtos.CrcBookSearchDto;
import com.banner.model.user.dtos.SimpleUserDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.banner.common.constants.RedisConstants.BOOK_RECOMMEND_KEY;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
@Service
public class CrcBookServiceImpl extends ServiceImpl<CrcBookMapper, CrcBook> implements CrcBookService {

    @Resource
    private CrcBookMapper crcBookMapper;

    @Override
    public ResponseResult getInfo(String bookId) {

        if (StrUtil.isBlank(bookId)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        CrcBookInfoDto crcBookInfoDto = crcBookMapper.getBookInfo(bookId);

        if (crcBookInfoDto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        return ResponseResult.okResult(crcBookInfoDto);
    }

    @Resource
    private CrcBookExcerptMapper crcBookExcerptMapper;

    @Resource
    private IUserClient userClient;

    @Override
    public ResponseResult getBookExcerpt(String bookId) {

        if (StrUtil.isBlank(bookId)) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);

        List<GetBookExcerptDto> crcBookExcerpts = crcBookExcerptMapper.getBookExcerptList(bookId);

        crcBookExcerpts = crcBookExcerpts.stream().peek(i -> {
            SimpleUserDto simpleUserDto = userClient.get(i.getUserId());
            i.setAvatar(simpleUserDto.getAvatar());
            i.setUserName(simpleUserDto.getUserName());
        }).collect(Collectors.toList());

        return ResponseResult.okResult(crcBookExcerpts);
    }

    @Resource
    private BookMessageMapper bookMessageMapper;

    @Override
    public ResponseResult getBookMessge(GetBookMessageDto getBookMessageDto) {

        if (getBookMessageDto.getPage() == null) getBookMessageDto.setPage(1);
        if (getBookMessageDto.getRows() == null) getBookMessageDto.setRows(10);

        List<Message> bookMessages = bookMessageMapper.getMessages(getBookMessageDto);

        return ResponseResult.okResult(bookMessages);
    }

    @Override
    public List<CrcBookSearchDto> getBookSearchList() {
        return crcBookMapper.getBookSearchList();
    }

    @Override
    public BookSimpleDto getBookSimple(Long bookId) {
        return crcBookMapper.getNameAndAuthor(bookId);
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<CrcBookSearchDto> getRecommendBook() {

        //推荐20本热门图书
        //查询缓存
        String bookJson = stringRedisTemplate.opsForValue().get(BOOK_RECOMMEND_KEY);
        List<CrcBookSearchDto> crcBookSearchDtos;
        if (StrUtil.isBlank(bookJson)) {
            //缓存未命中
            //查询数据库
            crcBookSearchDtos = crcBookMapper.getRecommendBook();
            stringRedisTemplate.opsForValue().set(BOOK_RECOMMEND_KEY,JSONUtil.toJsonStr(crcBookSearchDtos),1L, TimeUnit.DAYS);
        }
        crcBookSearchDtos = JSONUtil.toList(bookJson, CrcBookSearchDto.class);

        return crcBookSearchDtos;
    }
}
