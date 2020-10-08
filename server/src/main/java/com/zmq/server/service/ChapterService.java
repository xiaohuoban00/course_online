package com.zmq.server.service;

import com.zmq.server.mapper.ChapterMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zmq
 * @date 2020/10/8 3:32 下午
 */
@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;


}
