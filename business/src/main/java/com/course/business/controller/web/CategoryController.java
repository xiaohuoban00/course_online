package com.course.business.controller.web;

import com.course.server.dto.CategoryDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ICategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zmq
 * @date 2020/11/11 10:48 下午
 */
@RestController("webCategoryController")
@RequestMapping("/web/category")
public class CategoryController {


    @Resource
    private ICategoryService categoryService;

    /**
     * 列表查询
     */
    @PostMapping("/all")
    public ResponseDto all() {
        ResponseDto responseDto = new ResponseDto();
        List<CategoryDto> categoryDtoList = categoryService.all();
        responseDto.setContent(categoryDtoList);
        return responseDto;
    }
}
