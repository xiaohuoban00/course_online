package com.course.server.service.impl;

import com.course.server.domain.Member;
import com.course.server.dto.LoginMemberDto;
import com.course.server.dto.MemberDto;
import com.course.server.dto.PageDto;
import com.course.server.exception.ServiceException;
import com.course.server.mapper.MemberMapper;
import com.course.server.service.IMemberService;
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
public class MemberServiceImpl implements IMemberService {
    @Resource
    private MemberMapper memberMapper;

    @Override
    public void list(PageDto<MemberDto> pageDto) {
        Example example = new Example(Member.class);
        example.orderBy("id").desc();
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        List<Member> memberList = memberMapper.selectByExample(example);
        PageInfo<Member> pageInfo = new PageInfo<>(memberList);
        pageDto.setTotal(pageInfo.getTotal());
        List<MemberDto> memberDtoList = CopyUtil.copyList(memberList, MemberDto.class);
        pageDto.setList(memberDtoList);
    }


    @Override
    @Transactional
    public void save(MemberDto memberDto) {
        Member member = CopyUtil.copy(memberDto, Member.class);
        if (StringUtils.isEmpty(memberDto.getId())) {
            insert(member);
        } else {
            update(member);
        }
    }

    private void insert(Member member) {
        member.setId(UuidUtil.getShortUuid());
        memberMapper.insert(member);
    }

    private void update(Member member) {
        memberMapper.updateByPrimaryKeySelective(member);
    }


    @Override
    @Transactional
    public void delete(String id) {
        memberMapper.deleteByPrimaryKey(id);
    }


    @Override
    public MemberDto findByMobile(String mobile) {
        Member member = this.selectByMobile(mobile);
        return CopyUtil.copy(member, MemberDto.class);
    }

    /**
     * 按手机号查找
     * @param mobile
     * @return
     */
    public Member selectByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }
        Example example = new Example(Member.class);
        example.createCriteria().andEqualTo("mobile",mobile);
        List<Member> memberList = memberMapper.selectByExample(example);
        if (memberList == null || memberList.size() == 0) {
            return null;
        } else {
            return memberList.get(0);
        }

    }


    @Override
    public LoginMemberDto login(MemberDto memberDto) {
        Member member = selectByMobile(memberDto.getMobile());
        if (member == null) {
            throw new ServiceException("手机号不存在");
        } else {
            if (member.getPassword().equals(memberDto.getPassword())) {
                // 登录成功
                LoginMemberDto loginMemberDto = CopyUtil.copy(member, LoginMemberDto.class);
                return loginMemberDto;
            } else {
                throw new ServiceException("密码不正确");
            }
        }
    }


    @Override
    public void resetPassword(MemberDto memberDto){
        Member memberDb = this.selectByMobile(memberDto.getMobile());
        if (memberDb == null) {
            throw new ServiceException("用户不存在");
        } else {
            Member member = new Member();
            member.setId(memberDb.getId());
            member.setPassword(memberDto.getPassword());
            memberMapper.updateByPrimaryKeySelective(member);
        }
    }
}