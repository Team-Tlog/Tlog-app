package com.tlog.data.model.notification

enum class NotificationType(val type: String) {
    BASIC_MESSAGE("1"),
    LINK_MESSAGE("2"),
    BASIC_TSNS_MESSAGE("10"),
    FOLLOWING_TSNS_MESSAGE("11");

    companion object {
        fun fromType(code: String): NotificationType? {
            return entries.find { it.type == code }
        }
    }
}