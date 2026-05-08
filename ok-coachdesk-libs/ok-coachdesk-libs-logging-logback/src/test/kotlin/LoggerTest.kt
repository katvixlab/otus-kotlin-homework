import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import ru.otus.kotlin.coachdesk.logging.jvm.dskLoggerLogback
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertTrue

class LoggerTest {
    private val logId = "test-logger"
    val lggr = LoggerFactory.getLogger("test-logger")
    data class Xx(val x: String = "custom object")


    @Test
    fun slf4jTest() {
        @Suppress("LoggingPlaceholderCountMatchesArgumentCount")
        lggr.info("Test log {} {} {}", 1, "run first test", Xx(), Xx("created custom object"))
    }

    @Test
    fun `logger init`() {
        val output = invokeLogger {
            println("Some action")
        }

        assertTrue(Regex("Started .* $logId.*").containsMatchIn(output.toString()))
        assertTrue(output.toString().contains("Some action"))
        assertTrue(Regex("Finished .* $logId.*").containsMatchIn(output.toString()))
    }

    @Test
    fun `logger fails`() {
        val output = invokeLogger {
            throw RuntimeException("Some action")
        }

        assertTrue(Regex("Started .* $logId.*").containsMatchIn(output.toString()))
        assertTrue(Regex("Failed .* $logId.*").containsMatchIn(output.toString()))
    }

    private fun invokeLogger(block: suspend () -> Unit): ByteArrayOutputStream {
        val outputStreamCaptor = outputStreamCaptor()

        try {
            runBlocking {
                val logger = dskLoggerLogback(this::class)
                logger.doWithLogging(logId, block = block)
            }
        } catch (ignore: RuntimeException) {
        }

        return outputStreamCaptor
    }

    private fun outputStreamCaptor(): ByteArrayOutputStream {
        return ByteArrayOutputStream().apply {
            System.setOut(PrintStream(this))
        }
    }
}
