# CoachDesk

Учебный проект на Kotlin: сервис для фитнес-тренеров, который помогает вести **тренировочные сессии**, **расписание**, **историю занятий** и **статусы оплат** в одном месте.

---

## 1) Целевая аудитория и портреты клиентов

### ЦА (в общем виде)
Фитнес-тренеры **25–50 лет** (м/ж), офлайн/онлайн/гибрид, которые ведут **10–50 клиентов** и хотят:
- быстро фиксировать тренировку (план/факт) и прогресс;
- не путаться в оплатах;
- видеть расписание на день/неделю;
- держать данные клиентов структурированно (вместо заметок/Excel/мессенджеров).

### Персоны

**Персона A — тренер в зале (офлайн)**
- Контекст: много переносов, быстрые заметки в телефоне.
- Проблема: «где записано, что мы делали прошлый раз», «кто оплатил/кто должен».
- Ценность: быстрый журнал тренировок + понятный статус оплаты.

**Персона B — самозанятый тренер (офлайн/онлайн)**
- Контекст: ведёт клиентов в разных местах/залах, часть — онлайн.
- Проблема: хаос в расписании и истории занятий, нужна система «всё в одном».
- Ценность: единая карточка клиента + история сессий + фильтры по оплатам.

---

## 2) MVP и эскиз фронтенд-представления

### MVP (минимальный полезный набор)
**Фокус текущей реализации:** CRUD и поиск по *Тренировкам/сессиям*. Клиенты представлены в тренировке через `clientId` и `clientFullName`; отдельный CRUD клиентов остаётся частью продуктового MVP и UI-концепции.

1. **Авторизация**
- Keycloak (OIDC) заложен в архитектурное видение и docker-compose окружение.
- В текущем Ktor API авторизационный middleware ещё не подключён.

2. **Клиенты (CRUD)**
- отдельный backend CRUD клиентов пока не реализован;
- в API тренировок используются `clientId` и `clientFullName`;
- карточки клиентов и клиентские экраны представлены в UI-макетах.

3. **Тренировки/сессии (CRUD)**
- создание плановой сессии;
- чтение/обновление/удаление;
- поиск по фильтрам;
- статусы: `planned | done | canceled`;
- типы: `cardio | strength | personal | crossFit | functional | other`;
- фиксация плана и результата;
- статус оплаты: `unpaid | paid`.

4. **Расписание**
- реализуется через поиск тренировок по дате начала, клиенту, типу, статусу и статусу оплаты.

### Эскиз UI (упрощённо)

**Login**
- кнопка входа > редирект в Keycloak.

**Dashboard**
- карточки: «Сегодня», «На неделе», «Неоплаченные».
- список «Сегодня» с быстрыми действиями.

**Clients**
- таблица клиентов + поиск;
- переход в карточку.

**Client Detail**
- данные клиента (зал/заметки);
- список сессий (история/план);
- кнопка «Запланировать тренировку».

**Schedule**
- список сессий по дням;
- фильтры: дата/статус/оплата/клиент.

**Session Form**
- дата/время, тип, статус, план/результат, цена, оплата.

---

## 3) Сущности приложения (доменная модель)

> Для упрощения MVP «расписание», «посещения» и «оплаты» сведены в сущность **TrainingSession**.

### Trainer
В текущей доменной модели тренер представлен идентификатором `coachId` внутри тренировочной сессии. В целевой архитектуре источник истины для тренера — Keycloak `subject` из JWT.
- `coachId`
- `fullName` (планируется из claims)
- `username/email` (планируется из claims)

### TrainingSession
- `trnId`
- `coachId`
- `clientId`
- `clientFullName`
- `startsAt` (дата+время)
- `durationMin`
- `type`: `cardio | strength | personal | crossFit | functional | other`
- `planNotes`
- `resultNotes`
- `status`: `planned | done | canceled`
- `paymentStatus`: `unpaid | paid`
- `lock` (версия для оптимистичной блокировки)

---

## 4) API (POST-only / RPC)

**Base URL:** `/v1`  
Все операции выполняются через `POST` на отдельные endpoint-ы ресурса `trn`. OpenAPI-спецификация находится в `specs/specs-ts-v1.yml`, Swagger UI доступен по `/v1/swagger`.

**Request**
```json
{
  "requestType": "create",
  "debug": { "mode": "stub", "stub": "success" },
  "trn": {
    "coachId": "11111111-1111-1111-1111-111111111111",
    "clientId": "22222222-2222-2222-2222-222222222222",
    "clientFullName": "Иван Иванов",
    "startsAt": "2026-06-05T10:00:00Z",
    "durationMin": "60",
    "type": "personal",
    "planNotes": "Силовая тренировка",
    "resultNotes": "",
    "status": "planned",
    "paymentStatus": "unpaid"
  }
}
```

**Response**
```json
{
  "responseType": "create",
  "result": "success",
  "errors": [],
  "trn": {
    "trnId": "33333333-3333-3333-3333-333333333333",
    "lock": "lock-value"
  }
}
```

**Команды**

- TrainingSession: `POST /v1/trn/create` | `POST /v1/trn/read` | `POST /v1/trn/update` | `POST /v1/trn/delete` | `POST /v1/trn/search`
- WebSocket: `/v1/ws` принимает те же v1 transport-модели запросов и возвращает v1 transport-модели ответов.
- Debug modes: `prod | test | stub`; stubs: `success | notFound | badId | badField | badCoachId | badClientId | badStartsAt | cannotDelete | mismatchSearchString | dbError`.

## 5) Архитектурное видение

**Диаграмма (DFD/архитектура) описывает:**

- CoachDesk реализован как multi-module Kotlin/JVM проект на Ktor 3.
- Транспортный слой: `ok-coachdesk-app-ktor`, REST v1 и WebSocket v1.
- Контракт API: `specs/specs-ts-v1.yml`, Jackson-модели и mapper-модуль.
- Бизнес-логика: `ok-coachdesk-biz`, цепочки COR, stubs, валидации и repo-операции.
- Доменная модель и контекст: `ok-coachdesk-common`.
- Репозитории: stub, in-memory и PostgreSQL на Exposed с Liquibase changelog.
- Логирование: отдельная log-модель `specs/specs-ts-log.yml` и logging libs на logback.
- Инфраструктура: `infra/docker-compose.yml` поднимает Postgres 16 и Liquibase; корневой `docker-compose.yml` содержит окружение Keycloak, Postgres и OpenSearch.
- Целевая внешняя схема остаётся: единая точка входа, Keycloak/OIDC, проверка Bearer JWT и работа CoachDesk с Postgres.

![Architecture](docs/architecture.png)
[Source](docs/architecture.svg)

- [Example UI online](https://www.figma.com/make/9HMxqRtRgfUqncCmn7kb7R/CoachDesk-Mobile-App-UI)
- [Example UI project](coachdesk_ui)
