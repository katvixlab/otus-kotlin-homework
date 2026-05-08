import DskTrnStub.AD_TRN
import models.DskTrn
import models.DskTrnId
import java.util.UUID

object DskStub {
    fun get(): DskTrn = AD_TRN.copy()

    fun prepareListDskTrn(filter: String) = listOf(
        AD_TRN.copy(trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000001")), planNotes = filter),
        AD_TRN.copy(trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000002")), planNotes = filter),
        AD_TRN.copy(trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000003")), planNotes = filter),
        AD_TRN.copy(trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000004")), planNotes = filter),
        AD_TRN.copy(trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000005")), planNotes = filter),
        AD_TRN.copy(trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000006")), planNotes = filter),
    )
}

