package jp.co.netprotections.colorretriever.extension

import reactor.core.publisher.Mono

/**
 * バリデータクラスを呼び出す
 */
fun invokeValidate(subject: String?): Validator{
    return Validator(subject)
}

/**
 * バリデータクラス
 */
class Validator(var subject: String?) {

    /**
     * 中間操作を提供する
     * 提示されたデータを検証するクロージャを引数とし、真である場合次の操作に引き渡す。
     */
    fun check(closure: () -> Boolean): Validator {
        return when(closure.invoke()){
            true -> Validator(subject)
            else -> Validator(null)
        }
    }

    /**
     * Monoインスタンスを返却する
     */
    fun <T> buildMono(closure: () -> Mono<T>): Mono<T> {
        return closure.invoke()
    }
}
