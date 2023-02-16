package com.group5.stackoverflow.member.mapper;

import com.group5.stackoverflow.member.dto.MemberDto;
import com.group5.stackoverflow.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberDtoPostToMember(MemberDto.Post memberDtoPost);
    Member memberDtoPatchToMember(MemberDto.Patch memberDtoPatch);
    MemberDto.Response memberToMemberDtoResponse(Member member);

    List<MemberDto.Response> membersToMemberDtoResponses(List<Member> members);

}
