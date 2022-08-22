import kotlinx.cinterop.toKStringFromUtf8
import rustlib.rusthello

fun helloFromRust(input: String): String {
    return rusthello(input)?.toKStringFromUtf8() ?: "Hello from Kotlin"
}