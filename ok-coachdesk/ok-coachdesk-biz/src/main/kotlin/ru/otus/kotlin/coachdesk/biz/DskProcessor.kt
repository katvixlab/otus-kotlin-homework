package ru.otus.kotlin.coachdesk.biz

import DskContext
import DskCorSettings
import models.DskCommand
import ru.otus.kotlin.coachdesk.biz.general.finishTrnFilterValidation
import ru.otus.kotlin.coachdesk.biz.general.finishTrnValidation
import ru.otus.kotlin.coachdesk.biz.general.initStatus
import ru.otus.kotlin.coachdesk.biz.general.operation
import ru.otus.kotlin.coachdesk.biz.general.stubs
import ru.otus.kotlin.coachdesk.biz.general.validation
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
import ru.otus.kotlin.coachdesk.biz.validation.initTrnFilterValidation
import ru.otus.kotlin.coachdesk.biz.validation.initTrnValidation
import ru.otus.kotlin.coachdesk.biz.validation.validateClientFullNameFormat
import ru.otus.kotlin.coachdesk.biz.validation.validateClientFullNameNotEmpty
import ru.otus.kotlin.coachdesk.biz.validation.validateClientIdNotEmpty
import ru.otus.kotlin.coachdesk.biz.validation.validateCoachIdNotEmpty
import ru.otus.kotlin.coachdesk.biz.validation.validateDurationInRange
import ru.otus.kotlin.coachdesk.biz.validation.validateFilterClientFullNameNotEmpty
import ru.otus.kotlin.coachdesk.biz.validation.validateFilterPaymentStatusNotEmpty
import ru.otus.kotlin.coachdesk.biz.validation.validateFilterStartsAtNotEmpty
import ru.otus.kotlin.coachdesk.biz.validation.validateFilterStatusNotEmpty
import ru.otus.kotlin.coachdesk.biz.validation.validateFilterTypeNotEmpty
import ru.otus.kotlin.coachdesk.biz.validation.validatePaymentStatusNotEmpty
import ru.otus.kotlin.coachdesk.biz.validation.validatePlanNotesHasText
import ru.otus.kotlin.coachdesk.biz.validation.validateResultNotesHasText
import ru.otus.kotlin.coachdesk.biz.validation.validateStartsAtNotPast
import ru.otus.kotlin.coachdesk.biz.validation.validateStartsAtNotEmpty
import ru.otus.kotlin.coachdesk.biz.validation.validateStatusNotEmpty
import ru.otus.kotlin.coachdesk.biz.validation.validateTrnIdNotEmpty
import ru.otus.kotlin.coachdesk.biz.validation.validateTypeNotEmpty
import ru.otus.kotlin.coachdesk.cor.rootChain
import ru.otus.kotlin.coachdesk.cor.worker

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

            validation {
                initTrnValidation("Выполняем глубокое копирование для валидации")
                worker("Очистка ФИО клиента") { trnValidating.clientFullName = trnValidating.clientFullName.trim() }
                worker("Очистка плана тренировки") { trnValidating.planNotes = trnValidating.planNotes.trim() }
                worker("Очистка итогов тренировки") { trnValidating.resultNotes = trnValidating.resultNotes.trim() }
                validateCoachIdNotEmpty("Проверка что id тренера не пустой")
                validateClientIdNotEmpty("Проверка что id клиента не пустой")
                validateClientFullNameNotEmpty("Проверка что ФИО не пустое")
                validateClientFullNameFormat("Проверка что ФИО состоит из фамилии и имени или фамилии, имени и отчества")
                validateStartsAtNotEmpty("Проверка что время начала тренировки не пустое")
                validateStartsAtNotPast("Проверка что время начала тренировки не в прошлом")
                validateDurationInRange("Проверка что длительность тренировки от 10 минут до 8 часов")
                validateTypeNotEmpty("Проверка что тип тренировки не пустой")
                validatePlanNotesHasText("Проверка что план тренировки пустой или содержит текст")
                validateResultNotesHasText("Проверка что итоги тренировки пустые или содержат текст")
                validateStatusNotEmpty("Проверка что статус тренировки не пустой")
                validatePaymentStatusNotEmpty("Проверка что статус оплаты не пустой")
                finishTrnValidation("Успешное завершение процедуры валидации")
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

            validation {
                initTrnValidation("Выполняем глубокое копирование для валидации")
                validateTrnIdNotEmpty("Проверка что id тренировки не пустой")
                finishTrnValidation("Успешное завершение процедуры валидации")
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

            validation {
                initTrnValidation("Выполняем глубокое копирование для валидации")
                validateTrnIdNotEmpty("Проверка что id тренировки не пустой")
                worker("Очистка ФИО клиента") { trnValidating.clientFullName = trnValidating.clientFullName.trim() }
                worker("Очистка плана тренировки") { trnValidating.planNotes = trnValidating.planNotes.trim() }
                worker("Очистка итогов тренировки") { trnValidating.resultNotes = trnValidating.resultNotes.trim() }
                validateCoachIdNotEmpty("Проверка что id тренера не пустой")
                validateClientIdNotEmpty("Проверка что id клиента не пустой")
                validateClientFullNameNotEmpty("Проверка что ФИО не пустое")
                validateStartsAtNotEmpty("Проверка что время начала тренировки не пустое")
                validateDurationInRange("Проверка что длительность тренировки от 10 минут до 8 часов")
                validateTypeNotEmpty("Проверка что тип тренировки не пустой")
                validatePlanNotesHasText("Проверка что план тренировки пустой или содержит текст")
                validateResultNotesHasText("Проверка что итоги тренировки пустые или содержат текст")
                validateStatusNotEmpty("Проверка что статус тренировки не пустой")
                validatePaymentStatusNotEmpty("Проверка что статус оплаты не пустой")
                finishTrnValidation("Успешное завершение процедуры валидации")
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

            validation {
                initTrnValidation("Выполняем глубокое копирование для валидации")
                validateTrnIdNotEmpty("Проверка что id тренировки не пустой")
                finishTrnValidation("Успешное завершение процедуры валидации")
            }
        }

        operation("Поиск тренировок", DskCommand.SEARCH) {
            stubs("Обработка стабов") {
                stubSearchSuccess("Имитация успешной обработки", corSettings)
                stubMismatchSearchString("Имитация ошибки валидации строки поиска")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }

            validation {
                initTrnFilterValidation("Выполняем глубокое копирование фильтра для валидации")
                worker("Очистка ФИО клиента в фильтре") {
                    trnFilterValidating.clientFullName = trnFilterValidating.clientFullName.trim()
                }
                validateFilterClientFullNameNotEmpty("Проверка что ФИО в фильтре не пустое")
                validateFilterStartsAtNotEmpty("Проверка что время начала в фильтре не пустое")
                validateFilterTypeNotEmpty("Проверка что тип тренировки в фильтре не пустой")
                validateFilterStatusNotEmpty("Проверка что статус тренировки в фильтре не пустой")
                validateFilterPaymentStatusNotEmpty("Проверка что статус оплаты в фильтре не пустой")
                finishTrnFilterValidation("Успешное завершение процедуры валидации фильтра")
            }
        }
    }.build()

}
