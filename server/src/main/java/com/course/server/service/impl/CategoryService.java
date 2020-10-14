package com.course.server.service.impl;

import com.course.server.domain.Category;
import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.CategoryMapper;
import com.course.server.service.ICategoryService;
import com.course.server.utils.CopyUtil;
import com.course.server.utils.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CategoryService implements ICategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public void list(PageDto<CategoryDto> pageDto) {
        Example example = new Example(Category.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(example);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);
        pageDto.setList(categoryDtoList);
    }

    @Override
    public List<CategoryDto> all(){
        Example example = new Example(Category.class);
        example.orderBy("id").desc();
        List<Category> categoryList = categoryMapper.selectByExample(example);
        return CopyUtil.copyList(categoryList,CategoryDto.class);
    }


    @Override
    @Transactional
    public void save(CategoryDto categoryDto) {
        Category category = CopyUtil.copy(categoryDto, Category.class);
        if (StringUtils.isEmpty(categoryDto.getId())) {
            insert(category);
        } else {
            update(category);
        }
    }

    private void insert(Category category) {
        category.setId(UuidUtil.getShortUuid());
        categoryMapper.insert(category);
    }

    private void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }


    @Override
    @Transactional
    public void delete(String id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}