--liquibase formatted sql
--changeset <romanovosad>:<add-external-id-column-to-movie-character-table>

ALTER TABLE public.movie_character
    ADD external_id bigint;
