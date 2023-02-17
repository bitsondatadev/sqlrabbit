--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.6
-- Dumped by pg_dump version 9.6.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

--
-- Data for Name: db_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO db_types VALUES (1, 'Trino', 'trino', 'CREATE SCHEMA IF NOT EXISTS rabbit.db_#databaseName#', 'io.trino.jdbc.TrinoDriver', NULL, NULL, NULL, NULL, '1/7253a/1', 'EXPLAIN ANALYZE ', NULL, NULL, 'host', NULL, 1, 'SHOW SCHEMAS;');

INSERT INTO hosts VALUES (1, 1, 'jdbc:trino://localhost:8080/sqlrabbit/#databaseName#', 'default', 'trino', '', NULL, FALSE);

--
-- Name: db_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('db_types_id_seq', 18, true);


--
-- Data for Name: schema_defs; Type: TABLE DATA; Schema: public; Owner: postgres
--


INSERT INTO schema_defs VALUES (1, 1, '7253a', '2021-05-11 05:28:27.178', 'CREATE TABLE customer AS
SELECT * FROM tpch.tiny.customer;

CREATE TABLE orders AS
SELECT * FROM tpch.tiny.orders;', null, '7253a6897862d3c8e1318ce3c942376a', ';', null, null, 1);


--
-- Data for Name: queries; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO queries VALUES (1, 'SELECT * 
FROM customers c JOIN orders o ON c.custkey = o.custkey
LIMIT 10;', 'd14f63d8321aecb8ab62946ea810f20b', 1, ';', NULL);

--
-- Name: schema_defs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('schema_defs_id_seq', 46, true);


--
-- PostgreSQL database dump complete
--
