package com.course.server.service.impl;

import com.course.server.domain.File;
import com.course.server.dto.FileDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.FileMapper;
import com.course.server.service.IFileService;
import com.course.server.utils.CopyUtil;
import com.course.server.utils.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
public class FileServiceImpl implements IFileService {
    @Resource
    private FileMapper fileMapper;

    @Override
    public void list(PageDto<FileDto> pageDto) {
        Example example = new Example(File.class);
        example.orderBy("createdAt").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<File> fileList = fileMapper.selectByExample(example);
        PageInfo<File> pageInfo = new PageInfo<>(fileList);
        pageDto.setTotal(pageInfo.getTotal());
        List<FileDto> fileDtoList = CopyUtil.copyList(fileList, FileDto.class);
        pageDto.setList(fileDtoList);
    }


    @Override
    @Transactional
    public void save(FileDto fileDto) {
        File fileDb = selectByKey(fileDto.getKey());
        if (fileDb == null) {
            File file = CopyUtil.copy(fileDto, File.class);
            insert(file);
        } else {
            fileDb.setShardIndex(fileDto.getShardIndex());
            update(fileDb);
        }
    }

    private void insert(File file) {
        file.setId(UuidUtil.getShortUuid());
        fileMapper.insertSelective(file);
    }

    private void update(File file) {
        fileMapper.updateByPrimaryKeySelective(file);
    }


    @Override
    @Transactional
    public void delete(String id) {
        fileMapper.deleteByPrimaryKey(id);
    }

    private File selectByKey(String key){
        Example example = new Example(File.class);
        example.createCriteria().andEqualTo("key", key);
        List<File> fileList = fileMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(fileList)){
            return null;
        }else {
            return fileList.get(0);
        }
    }
}