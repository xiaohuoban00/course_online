package com.course.system.controller.admin;

import com.course.server.dto.ResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enmus.CodeEnum;
import com.course.server.service.IResourceService;
import com.course.server.utils.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("admin/resource")
public class ResourceController {

    @Resource
    private IResourceService resourceService;

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @PostMapping("list")
    public ResponseDto list(@RequestBody PageDto<ResourceDto> pageDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(pageDto);
        resourceService.list(pageDto);
        return responseDto;
    }

    /**
     * 保存
     *
     * @param jsonStr
     * @return
     */
    @PostMapping("save")
    public ResponseDto save(@RequestBody String jsonStr) {
        ValidatorUtil.require(jsonStr,"资源");
        resourceService.saveJson(jsonStr);
        return new ResponseDto();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        resourceService.delete(id);
        return new ResponseDto();
    }

    @GetMapping("load-tree")
    public ResponseDto loadTree(){
        List<ResourceDto> resourceDtoList = resourceService.loadTree();
        return new ResponseDto(true, CodeEnum.SUCCESS.getCode(), null,resourceDtoList);
    }
}