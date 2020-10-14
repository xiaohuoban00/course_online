package com.course.business.controller.admin;

import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enmus.CodeEnum;
import com.course.server.service.impl.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("admin/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody PageDto<CategoryDto> pageDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        categoryService.list(pageDto);
        return responseDto;
    }

    /**
     * 保存，存在有id则更新
     *
     * @param categoryDto
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody CategoryDto categoryDto) {
        categoryService.save(categoryDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(categoryDto);
        return responseDto;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        categoryService.delete(id);
        return new ResponseDto();
    }

    @PostMapping("all")
    public ResponseDto all(){
        List<CategoryDto> categoryDtoList = categoryService.all();
        return new ResponseDto(true, CodeEnum.SUCCESS.getCode(),null,categoryDtoList);
    }
}