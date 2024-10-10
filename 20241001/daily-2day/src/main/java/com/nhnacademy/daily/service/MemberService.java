package com.nhnacademy.daily.service;

import com.nhnacademy.daily.model.Member;
import com.nhnacademy.daily.model.MemberCreateCommand;
import com.nhnacademy.daily.model.exception.MemberAlreadyExistsException;
import com.nhnacademy.daily.model.exception.MemberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class MemberService {

    @Autowired
    private RedisTemplate<String, Member> redisTemplate;

    private String HASH_NAME = "Member:";

    // 멤버 생성
    public void createMember(MemberCreateCommand memberCreateCommand) {
        Object o = redisTemplate.opsForHash().get(HASH_NAME, memberCreateCommand.getId());
        if (o != null) {
            throw new MemberAlreadyExistsException("already used id");
        }
        Member member = new Member(memberCreateCommand.getId(), memberCreateCommand.getName(), memberCreateCommand.getClazz(), memberCreateCommand.getLocale());
        redisTemplate.opsForHash().put(HASH_NAME, member.getId(), member);
    }

    // 멤버 리스트 조회(페이징)
    public List<Member> getMembers() {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(HASH_NAME);
        List<Member> members = new ArrayList<>(entries.size());
        for (Object value : entries.values()) {
            members.add((Member) value);
        }
        return members;
    }

    // 특정 멤버 조회
    public Member getMember(String id) {
        Object o = redisTemplate.opsForHash().get(HASH_NAME, id);
        if (o == null) {
            throw new MemberNotFoundException();
        }
        return (Member) o;
    }

    // 특정 멤버 업데이트
    public Member updateMember(String id, MemberCreateCommand updatedMember) {
        Object o = redisTemplate.opsForHash().get(HASH_NAME, id);
        if (o == null) {
            throw new MemberNotFoundException();
        }
        Member existingMember = (Member) o;
        existingMember.setName(updatedMember.getName());
        existingMember.setClazz(updatedMember.getClazz());
        existingMember.setLocale(updatedMember.getLocale());
        redisTemplate.opsForHash().put(HASH_NAME, existingMember.getId(), existingMember);
        return existingMember;
    }
}
