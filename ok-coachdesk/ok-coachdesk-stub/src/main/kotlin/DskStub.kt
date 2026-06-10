import DskTrnStub.AD_TRN
import models.DskTrn
import models.DskTrnId
import java.util.UUID

object DskStub {
    fun get(): DskTrn = AD_TRN.copy()

    fun prepareResult(block: DskTrn.() -> Unit): DskTrn = get().apply(block)

    fun prepareListDskTrn() = listOf(
        AD_TRN.copy(trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000001"))),
        AD_TRN.copy(trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000002"))),
        AD_TRN.copy(trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000003"))),
        AD_TRN.copy(trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000004"))),
        AD_TRN.copy(trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000005"))),
        AD_TRN.copy(trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000006"))),
    )
}

