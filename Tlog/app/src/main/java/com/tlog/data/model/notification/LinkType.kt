package com.tlog.data.model.notification

enum class LinkType(val code: String) {
    MAIN_SCREEN("1"),
    MY_PAGE("2"),
    TRAVEL_INFO("10"),
    SNS_POST_DETAIL("11"),
    SNS_MY_PAGE("12"),
    CHAT_ROOM("13");

    companion object {
        fun fromCode(code: String): LinkType? {
            return entries.find { it.code == code }
        }
    }
}