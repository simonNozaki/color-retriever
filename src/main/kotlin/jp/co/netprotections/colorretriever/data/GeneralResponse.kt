package jp.co.netprotections.colorretriever.data

data class GeneralResponse<T> (
        var status: Boolean,
        var message: String?,
        var color: T
)