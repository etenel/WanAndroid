package com.wls.poke.entity


import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class UserInfoEntity(
    val coinInfo: CoinInfo = CoinInfo(),
    val userInfo: UserInfo = UserInfo(),
) {
    @Serializable
    @Keep
    data class CoinInfo(
        val coinCount: Int = 0,
        val level: Int = 0,
        val nickname: String = "",
        val rank: String = "",
        val userId: Int = 0,
        val username: String = "",
    )

    @Serializable
    @Keep
    data class UserInfo(
        val admin: Boolean = false,
        val chapterTops: List<String> = listOf(),
        val coinCount: Int = 0,
        val collectIds: List<Int> = listOf(),
        val email: String = "",
        val icon: String = "",
        val id: Int = 0,
        val nickname: String = "",
        val password: String = "",
        val publicName: String = "",
        val token: String = "",
        val type: Int = 0,
        val username: String = "",
    )
}