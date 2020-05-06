package jp.co.netprotections.colorretriever.extension

import jp.co.netprotections.colorretriever.data.Color
import jp.co.netprotections.colorretriever.exception.ColorOperationRuntimeException
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import kotlin.properties.Delegates

/**
 * バリデータクラスを呼び出す
 */
fun invokeValidate(subject: String?): Validator {
    return Validator(subject)
}

/**
 * バリデータクラス
 */
class Validator(private var subject: String?) {

    /**
     * 中間操作を提供する
     * 提示されたデータを検証するクロージャを引数とし、真である場合次の操作に引き渡す。
     */
    fun check(closure: () -> Boolean): Validator {
        return when (closure.invoke()) {
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

class ColorValidator {

    internal constructor(color: Color) {
        _color = color
    }

    companion object ColorValidator {
        private lateinit var _color: Color
        private var hasError by Delegates.notNull<Boolean>()

        fun testName(name: String): ColorValidator {

            return when(name.isValidName()) {
                true ->  {
                    hasError = false
                    _color = Color(name, "")
                    ColorValidator
                }
                else -> {
                    hasError= true
                    _color = Color("", "")
                    ColorValidator
                }
            }
        }

        fun testColor(color: Color): ColorValidator {

            if (!color.name.isValidName() || !color.code.isValidCode()) {
                hasError = true
                _color = color
            }

            hasError = false
            return ColorValidator
        }

        fun <T> executeRepository(closure: () -> Mono<T>): Mono<T> {
            return when(hasError) {
                true -> throw ColorOperationRuntimeException("")
                else -> closure.invoke()
            }
        }
    }
}