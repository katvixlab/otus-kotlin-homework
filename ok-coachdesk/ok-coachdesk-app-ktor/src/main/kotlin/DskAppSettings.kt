package ok.coachdesk.app

import DskCorSettings
import ru.otus.kotlin.coachdesk.app.common.IDskAppSettings
import ru.otus.kotlin.coachdesk.biz.DskProcessor

data class DskAppSettings(
    val appUrls: List<String> = emptyList(),
    override val corSettings: DskCorSettings = DskCorSettings(),
    override val processor: DskProcessor = DskProcessor(corSettings),
    ) : IDskAppSettings