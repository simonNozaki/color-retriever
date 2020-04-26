package jp.co.netprotections.colorretriever.extension

const val REGEX_HEX = "[a-f0-9]+"
const val REGEX_COLOR_NAME = "[a-zA-Z]+"

private fun match(pattern: String, subject: String): Boolean {
    val regex = Regex(pattern = REGEX_HEX)
    if (!regex.matches(subject)) return false// 空文字チェック
    return true
}

/**
 * 16進数の色コードを判定します。
 */
fun String.isValidCode(): Boolean {
    // 空文字チェック
    if(this.isBlank()) return false

    // 桁数チェック
    if(this.chars().count().toInt() != 6) return false

    // 半角英数字パターン
    return match(REGEX_HEX,this)
}

/**
 * 色の名前をチェックします。
 */
fun String.isValidName(): Boolean {

    if (this.isBlank()) return false

    return match(REGEX_COLOR_NAME, this)
}