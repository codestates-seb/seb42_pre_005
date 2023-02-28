package com.group5.stackoverflow.helper;

public interface MemberControllerTestHelper extends ControllerTestHelper {

    String MEMBER_DEFAULT_URL = "/members";
    String MEMBER_RESOURCE_ID = "/{member-id}";
    String MEMBER_QUESTION_URL = MEMBER_DEFAULT_URL + MEMBER_RESOURCE_ID + "/questions";
    String MEMBER_RESOURCE_URI = MEMBER_DEFAULT_URL + MEMBER_RESOURCE_ID;
}
