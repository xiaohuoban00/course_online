package com.course.server.service.impl;

import com.course.server.domain.Sms;
import com.course.server.dto.SmsDto;
import com.course.server.dto.PageDto;
import com.course.server.enmus.SmsStatusEnum;
import com.course.server.exception.ServiceException;
import com.course.server.mapper.SmsMapper;
import com.course.server.service.ISmsService;
import com.course.server.utils.CopyUtil;
import com.course.server.utils.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class SmsServiceImpl implements ISmsService {
    @Resource
    private SmsMapper smsMapper;

    @Override
    public void list(PageDto<SmsDto> pageDto) {
        Example example = new Example(Sms.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<Sms> smsList = smsMapper.selectByExample(example);
        PageInfo<Sms> pageInfo = new PageInfo<>(smsList);
        pageDto.setTotal(pageInfo.getTotal());
        List<SmsDto> smsDtoList = CopyUtil.copyList(smsList, SmsDto.class);
        pageDto.setList(smsDtoList);
    }


    @Override
    @Transactional
    public void save(SmsDto smsDto) {
        Sms sms = CopyUtil.copy(smsDto, Sms.class);
        if (StringUtils.isEmpty(smsDto.getId())) {
            insert(sms);
        } else {
            update(sms);
        }
    }

    private void insert(Sms sms) {
        sms.setId(UuidUtil.getShortUuid());
        smsMapper.insert(sms);
    }

    private void update(Sms sms) {
        smsMapper.updateByPrimaryKeySelective(sms);
    }


    @Override
    @Transactional
    public void delete(String id) {
        smsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 发送短信验证码
     * 同手机号同操作1分钟内不能重复发送短信
     * @param smsDto
     */
    @Override
    public void sendCode(SmsDto smsDto) {
        Example example = new Example(Sms.class);
        Example.Criteria criteria = example.createCriteria();
        // 查找1分钟内有没有同手机号同操作发送记录且没被用过
        criteria.andEqualTo("mobile",smsDto.getMobile())
                .andEqualTo("use",smsDto.getUse())
                .andEqualTo("status",smsDto.getStatus())
                .andGreaterThanOrEqualTo("at", LocalDateTime.now().minusSeconds(60));
        List<Sms> smsList = smsMapper.selectByExample(example);

        if (smsList == null || smsList.size() == 0) {
            saveAndSend(smsDto);
        } else {
            throw new ServiceException("验证码发送太过频繁");
        }
    }

    /**
     * 保存并发送短信验证码
     * @param smsDto
     */
    private void saveAndSend(SmsDto smsDto) {
        // 生成6位数字
        String code = String.valueOf((int)(((Math.random() * 9) + 1) * 100000));
        smsDto.setAt(LocalDateTime.now());
        smsDto.setStatus(SmsStatusEnum.NOT_USED.getCode());
        smsDto.setCode(code);
        this.save(smsDto);

        // TODO 调第三方短信接口发送短信
    }

    /**
     * 验证码5分钟内有效，且操作类型要一致
     * @param smsDto
     */
    @Override
    public void validCode(SmsDto smsDto) {
        Example example = new Example(Sms.class);
        Example.Criteria criteria = example.createCriteria();
        // 查找5分钟内同手机号同操作发送记录
        criteria.andEqualTo("mobile",smsDto.getMobile())
                .andEqualTo("use",smsDto.getUse())
                .andGreaterThanOrEqualTo("at",LocalDateTime.now().minusSeconds(60));
        List<Sms> smsList = smsMapper.selectByExample(example);

        if (smsList != null && smsList.size() > 0) {
            Sms smsDb = smsList.get(0);
            if (!smsDb.getCode().equals(smsDto.getCode())) {
                throw new ServiceException("短信验证码不正确");
            } else {
                smsDto.setStatus(SmsStatusEnum.USED.getCode());
                smsMapper.updateByPrimaryKey(smsDb);
            }
        } else {
            throw new ServiceException("短信验证码不存在或已过期，请重新发送短信");
        }
    }
}