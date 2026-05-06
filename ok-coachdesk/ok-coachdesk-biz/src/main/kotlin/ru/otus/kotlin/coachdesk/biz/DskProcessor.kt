package ru.otus.kotlin.coachdesk.biz

import DskContext
import DskCorSettings
import models.DskCommand
import ru.otus.kotlin.coachdesk.biz.general.initStatus
import ru.otus.kotlin.coachdesk.biz.general.operation
import ru.otus.kotlin.coachdesk.biz.general.stubs
import ru.otus.kotlin.coachdesk.biz.stubs.stubCannotDelete
import ru.otus.kotlin.coachdesk.biz.stubs.stubCreateSuccess
import ru.otus.kotlin.coachdesk.biz.stubs.stubDbError
import ru.otus.kotlin.coachdesk.biz.stubs.stubDeleteSuccess
import ru.otus.kotlin.coachdesk.biz.stubs.stubMismatchSearchString
import ru.otus.kotlin.coachdesk.biz.stubs.stubNoCase
import ru.otus.kotlin.coachdesk.biz.stubs.stubNotFound
import ru.otus.kotlin.coachdesk.biz.stubs.stubReadSuccess
import ru.otus.kotlin.coachdesk.biz.stubs.stubSearchSuccess
import ru.otus.kotlin.coachdesk.biz.stubs.stubUpdateSuccess
import ru.otus.kotlin.coachdesk.biz.stubs.stubValidationBadClientId
import ru.otus.kotlin.coachdesk.biz.stubs.stubValidationBadField
import ru.otus.kotlin.coachdesk.biz.stubs.stubValidationBadCoachId
import ru.otus.kotlin.coachdesk.biz.stubs.stubValidationBadId
import ru.otus.kotlin.coachdesk.biz.stubs.stubValidationBadStartsAt
import ru.otus.kotlin.coachdesk.cor.rootChain

class DskProcessor(
    private val corSettings: DskCorSettings = DskCorSettings.NONE
) {

    suspend fun exec(ctx: DskContext) =
        businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain {
        initStatus("Инициализация статуса")

        operation("Создание тренировки", DskCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadField("Имитация ошибки валидации поля")
                stubValidationBadCoachId("Имитация ошибки валидации id тренера")
                stubValidationBadClientId("Имитация ошибки валидации id клиента")
                stubValidationBadStartsAt("Имитация ошибки валидации времени начала тренировки")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }

        operation("Получить тренировку", DskCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id тренировки")
                stubNotFound("Имитация ошибки отсутствующей тренировки")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }

        operation("Изменить тренировку", DskCommand.UPDATE) {
            stubs("Обработка стабов") {
                stubUpdateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id тренировки")
                stubValidationBadField("Имитация ошибки валидации поля")
                stubValidationBadCoachId("Имитация ошибки валидации id тренера")
                stubValidationBadClientId("Имитация ошибки валидации id клиента")
                stubValidationBadStartsAt("Имитация ошибки валидации времени начала тренировки")
                stubNotFound("Имитация ошибки отсутствующей тренировки")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }

        operation("Удалить тренировку", DskCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id тренировки")
                stubNotFound("Имитация ошибки отсутствующей тренировки")
                stubCannotDelete("Имитация ошибки удаления тренировки")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }

        operation("Поиск тренировок", DskCommand.SEARCH) {
            stubs("Обработка стабов") {
                stubSearchSuccess("Имитация успешной обработки", corSettings)
                stubMismatchSearchString("Имитация ошибки валидации строки поиска")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
    }.build()

}
