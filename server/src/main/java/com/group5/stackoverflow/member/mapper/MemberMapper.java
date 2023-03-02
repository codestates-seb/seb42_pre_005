package com.group5.stackoverflow.member.mapper;

import com.group5.stackoverflow.member.dto.MemberDto;
import com.group5.stackoverflow.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostToMember(MemberDto.Post memberDtoPost);
    Member memberPatchToMember(MemberDto.Patch memberDtoPatch);


    @Named("private")
    MemberDto.Response memberToMemberResponse(Member member);

    default List<MemberDto.Response> membersToMemberResponses(List<Member> members){
        return members.stream()
                .map(this::memberToMemberResponse)
                .collect(Collectors.toList());
    }

    default List<MemberDto.Response> membersToMemberResponsesForPublic(List<Member> members){
        return members.stream()
                .map(this::memberToMemberResponseForPublic)
                .collect(Collectors.toList());
    }

    default MemberDto.Response memberToMemberResponseForPublic(Member member){
        MemberDto.Response response = new MemberDto.Response();
        response.setName(member.getName());
        response.setEmail(member.getEmail());
        response.setVoteCount(member.getVoteCount());
        response.setMemberStatus(member.getMemberStatus());
        response.setMemberId(member.getMemberId());

        // todo 멤버 설정에 따라 변환가능
        response.setAge(-1);

        return response;

    };

}
