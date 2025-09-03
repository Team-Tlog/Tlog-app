package com.tlog.data.model.share

enum class ErrorType(val message: String) {
    // 네트워크
    NETWORK_ERROR("인터넷 연결을 확인해주세요"),
    NO_INTERNET("네트워크에 연결되지 않았습니다"),
    TIMEOUT("요청 시간이 초과되었습니다"),

    // HTTP Status Code
    BAD_REQUEST("입력 정보를 확인해주세요"), // 400
    UNAUTHORIZED("다시 로그인해주세요"),
    FORBIDDEN("권한이 없습니다"), // 403
    NOT_FOUND("요청한 정보를 찾을 수 없습니다"), // 404
    CONFLICT("중복 입니다."),
    SERVER_ERROR("서버에 문제가 발생했습니다"), // 500, 505


    UNKNOWN_ERROR("오류가 발생했습니다. 다시 시도해주세요") // else
}