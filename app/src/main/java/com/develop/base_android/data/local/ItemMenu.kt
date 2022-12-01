package com.develop.base_android.data.local

import com.develop.base_android.BuildConfig
import com.develop.base_android.R

enum class ItemMenu(val keyNameItem: String) {
    RECRUITER("HB1000"),
    RECRUITER_MANAGER("HB1010"),
    CONTRACTOR("HB1030"),
    INFORMATION("HB1040"),
    REFUND("HB1070"),
    NOTIFICATION("HB1080"),
    TASK("HB1090"),
    SIMULATOR("1100"),
    CHAT("HB1110"),
    INVITATION("HB1120"),
    SETTING("11");

    val icon: Int
        get() = when (this) {
            RECRUITER_MANAGER -> R.drawable.ic_funderaser_manager
            CONTRACTOR -> R.drawable.ic_holder
            INFORMATION -> R.drawable.ic_information
            REFUND -> R.drawable.ic_refund
            NOTIFICATION -> R.drawable.ic_notification
            TASK -> R.drawable.ic_note
            SIMULATOR -> R.drawable.ic_calculator
            CHAT -> R.drawable.ic_chat
            SETTING -> R.drawable.ic_setting
            RECRUITER -> R.drawable.ic_funderaiser
            INVITATION -> R.drawable.ic_invitation

        }
    val url: String?
        get() = when (this) {
            RECRUITER -> BuildConfig.API_ENDPOINT + "recruiters"
            RECRUITER_MANAGER -> BuildConfig.API_ENDPOINT + "recruiter-manager"
            CONTRACTOR -> BuildConfig.API_ENDPOINT + "insurance-contractor"
            INFORMATION -> BuildConfig.API_ENDPOINT + "insurance-information"
            REFUND -> BuildConfig.API_ENDPOINT + "refund"
            NOTIFICATION -> BuildConfig.API_ENDPOINT + "notification"
            TASK -> BuildConfig.API_ENDPOINT + "tasks"
            SIMULATOR -> BuildConfig.API_ENDPOINT + "simulations"
            CHAT -> null
            INVITATION -> BuildConfig.API_ENDPOINT + "invitations"
            SETTING -> null

        }

    val content: Int
        get() = when (this) {
            CONTRACTOR -> R.string.contractor
            INFORMATION -> R.string.information
            REFUND -> R.string.refund
            NOTIFICATION -> R.string.notification
            TASK -> R.string.task
            SIMULATOR -> R.string.simulator
            CHAT -> R.string.chat
            SETTING -> R.string.setting
            RECRUITER -> R.string.recruiters
            RECRUITER_MANAGER -> R.string.recruiter_manager
            INVITATION -> R.string.invitation
        }

    companion object {
        fun getAll(): List<ItemMenu> = values().toList()
        fun get(id: String): ItemMenu? = values().firstOrNull { it.keyNameItem == id }
    }

}