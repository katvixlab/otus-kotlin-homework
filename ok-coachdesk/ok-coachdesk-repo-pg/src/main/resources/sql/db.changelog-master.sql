--liquibase formatted sql

--changeset coachdesk:001-create-trn-schema
DO $$ BEGIN
CREATE TYPE public.trn_type AS ENUM (
    'NONE',
    'CARDIO',
    'STRENGTH',
    'PERSONAL',
    'CROSS_FIT',
    'FUNCTIONAL',
    'OTHER'
);
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

DO $$ BEGIN
CREATE TYPE public.trn_status AS ENUM (
    'NONE',
    'PLANNED',
    'DONE',
    'CANCELED'
);
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

DO $$ BEGIN
CREATE TYPE public.trn_payment AS ENUM (
    'NONE',
    'UNPAID',
    'PAID'
);
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

CREATE TABLE IF NOT EXISTS public.trn (
    id UUID PRIMARY KEY,
    coach_id UUID NOT NULL,
    client_id UUID NOT NULL,
    client_full_name TEXT NOT NULL,
    start_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    duration BIGINT NOT NULL,
    plan_notes TEXT NOT NULL,
    result_notes TEXT NOT NULL,
    trn_type public.trn_type NOT NULL,
    trn_status public.trn_status NOT NULL,
    trn_payment public.trn_payment NOT NULL,
    lock TEXT NOT NULL
);

--rollback DROP TABLE public.trn;
--rollback DROP TYPE public.trn_payment;
--rollback DROP TYPE public.trn_status;
--rollback DROP TYPE public.trn_type;
