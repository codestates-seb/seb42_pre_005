package com.group5.stackoverflow.member.mapper;

import com.group5.stackoverflow.member.dto.MemberDto;
import com.group5.stackoverflow.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostToMember(MemberDto.Post memberDtoPost);
    Member memberPatchToMember(MemberDto.Patch memberDtoPatch);
    MemberDto.Response memberToMemberResponse(Member member);

    List<MemberDto.Response> membersToMemberResponses(List<Member> members);

}
