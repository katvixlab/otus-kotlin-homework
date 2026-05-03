package ru.otus.kotlin.coachdesk.app.common

import DskCorSettings
import ru.otus.kotlin.coachdesk.biz.DskProcessor


interface IDskAppSettings {
    val processor: DskProcessor
    val corSettings: DskCorSettings
}
