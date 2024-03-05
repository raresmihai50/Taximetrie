--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

-- Started on 2024-03-05 10:22:35

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 24660)
-- Name: comanda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comanda (
    id bigint NOT NULL,
    id_persoana bigint,
    id_sofer bigint,
    data timestamp without time zone
);


ALTER TABLE public.comanda OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 24641)
-- Name: persoana; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persoana (
    id bigint NOT NULL,
    username character varying,
    name character varying
);


ALTER TABLE public.persoana OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 24648)
-- Name: sofer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sofer (
    id bigint NOT NULL,
    indicativmasina character varying
);


ALTER TABLE public.sofer OWNER TO postgres;

--
-- TOC entry 4795 (class 0 OID 24660)
-- Dependencies: 217
-- Data for Name: comanda; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.comanda (id, id_persoana, id_sofer, data) FROM stdin;
1	1	2	2024-01-20 14:30:30
37096	4	5	2024-01-21 16:08:29
8305	1	5	2024-01-21 16:08:16
87345	4	5	2024-01-21 17:40:36
52012	3	5	2024-01-21 18:10:51
\.


--
-- TOC entry 4793 (class 0 OID 24641)
-- Dependencies: 215
-- Data for Name: persoana; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persoana (id, username, name) FROM stdin;
1	raresmihai50	Ghiurau Rares
2	antonel	Anton Pan
3	lotzi	Szabo Ricci
4	nush	Marean
5	driver	Baby Driver
\.


--
-- TOC entry 4794 (class 0 OID 24648)
-- Dependencies: 216
-- Data for Name: sofer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sofer (id, indicativmasina) FROM stdin;
2	Renault Clio
5	Subaru WRX
\.


--
-- TOC entry 4642 (class 2606 OID 24647)
-- Name: persoana Persoana_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persoana
    ADD CONSTRAINT "Persoana_pkey" PRIMARY KEY (id);


--
-- TOC entry 4644 (class 2606 OID 24654)
-- Name: sofer Sofer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sofer
    ADD CONSTRAINT "Sofer_pkey" PRIMARY KEY (id);


--
-- TOC entry 4646 (class 2606 OID 24676)
-- Name: comanda comanda_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda
    ADD CONSTRAINT comanda_pkey PRIMARY KEY (id);


--
-- TOC entry 4647 (class 2606 OID 24655)
-- Name: sofer Sofer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sofer
    ADD CONSTRAINT "Sofer_id_fkey" FOREIGN KEY (id) REFERENCES public.persoana(id);


--
-- TOC entry 4648 (class 2606 OID 24677)
-- Name: comanda comanda_id_persoana_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda
    ADD CONSTRAINT comanda_id_persoana_fkey FOREIGN KEY (id_persoana) REFERENCES public.persoana(id) NOT VALID;


--
-- TOC entry 4649 (class 2606 OID 24682)
-- Name: comanda comanda_id_sofer_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda
    ADD CONSTRAINT comanda_id_sofer_fkey FOREIGN KEY (id_sofer) REFERENCES public.sofer(id) NOT VALID;


-- Completed on 2024-03-05 10:22:36

--
-- PostgreSQL database dump complete
--

