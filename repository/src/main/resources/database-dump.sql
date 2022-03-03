--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

-- Started on 2022-02-07 21:37:34

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

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3386 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 835 (class 1247 OID 16662)
-- Name: role_types; Type: TYPE; Schema: public; Owner: ilya
--

CREATE TYPE public.role_types AS ENUM (
    'ADMIN',
    'TEACHER',
    'STUDENT'
);


ALTER TYPE public.role_types OWNER TO ilya;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 16710)
-- Name: grade; Type: TABLE; Schema: public; Owner: ilya
--

CREATE TABLE public.grade (
    id integer NOT NULL,
    value integer NOT NULL,
    student_id integer,
    group_id integer,
    theme_id integer
);


ALTER TABLE public.grade OWNER TO ilya;

--
-- TOC entry 217 (class 1259 OID 16709)
-- Name: grade_id_seq; Type: SEQUENCE; Schema: public; Owner: ilya
--

CREATE SEQUENCE public.grade_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.grade_id_seq OWNER TO ilya;

--
-- TOC entry 3387 (class 0 OID 0)
-- Dependencies: 217
-- Name: grade_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ilya
--

ALTER SEQUENCE public.grade_id_seq OWNED BY public.grade.id;


--
-- TOC entry 214 (class 1259 OID 16691)
-- Name: group_s; Type: TABLE; Schema: public; Owner: ilya
--

CREATE TABLE public.group_s (
    id integer NOT NULL,
    title character varying(50) NOT NULL,
    teacher_id integer
);


ALTER TABLE public.group_s OWNER TO ilya;

--
-- TOC entry 213 (class 1259 OID 16690)
-- Name: group_s_id_seq; Type: SEQUENCE; Schema: public; Owner: ilya
--

CREATE SEQUENCE public.group_s_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.group_s_id_seq OWNER TO ilya;

--
-- TOC entry 3388 (class 0 OID 0)
-- Dependencies: 213
-- Name: group_s_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ilya
--

ALTER SEQUENCE public.group_s_id_seq OWNED BY public.group_s.id;


--
-- TOC entry 221 (class 1259 OID 16743)
-- Name: group_student; Type: TABLE; Schema: public; Owner: ilya
--

CREATE TABLE public.group_student (
    group_id integer,
    student_id integer
);


ALTER TABLE public.group_student OWNER TO ilya;

--
-- TOC entry 222 (class 1259 OID 16756)
-- Name: group_theme; Type: TABLE; Schema: public; Owner: ilya
--

CREATE TABLE public.group_theme (
    group_id integer,
    theme_id integer
);


ALTER TABLE public.group_theme OWNER TO ilya;

--
-- TOC entry 210 (class 1259 OID 16670)
-- Name: roles; Type: TABLE; Schema: public; Owner: ilya
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    title public.role_types
);


ALTER TABLE public.roles OWNER TO ilya;

--
-- TOC entry 209 (class 1259 OID 16669)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: ilya
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_id_seq OWNER TO ilya;

--
-- TOC entry 3389 (class 0 OID 0)
-- Dependencies: 209
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ilya
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 220 (class 1259 OID 16732)
-- Name: salary; Type: TABLE; Schema: public; Owner: ilya
--

CREATE TABLE public.salary (
    id integer NOT NULL,
    salaries_key integer NOT NULL,
    value real NOT NULL,
    teacher_id integer
);


ALTER TABLE public.salary OWNER TO ilya;

--
-- TOC entry 219 (class 1259 OID 16731)
-- Name: salary_id_seq; Type: SEQUENCE; Schema: public; Owner: ilya
--

CREATE SEQUENCE public.salary_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.salary_id_seq OWNER TO ilya;

--
-- TOC entry 3390 (class 0 OID 0)
-- Dependencies: 219
-- Name: salary_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ilya
--

ALTER SEQUENCE public.salary_id_seq OWNED BY public.salary.id;


--
-- TOC entry 216 (class 1259 OID 16703)
-- Name: theme; Type: TABLE; Schema: public; Owner: ilya
--

CREATE TABLE public.theme (
    id integer NOT NULL,
    title character varying(50) NOT NULL
);


ALTER TABLE public.theme OWNER TO ilya;

--
-- TOC entry 215 (class 1259 OID 16702)
-- Name: theme_id_seq; Type: SEQUENCE; Schema: public; Owner: ilya
--

CREATE SEQUENCE public.theme_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.theme_id_seq OWNER TO ilya;

--
-- TOC entry 3391 (class 0 OID 0)
-- Dependencies: 215
-- Name: theme_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ilya
--

ALTER SEQUENCE public.theme_id_seq OWNED BY public.theme.id;


--
-- TOC entry 212 (class 1259 OID 16677)
-- Name: users; Type: TABLE; Schema: public; Owner: ilya
--

CREATE TABLE public.users (
    id integer NOT NULL,
    login character varying(50) NOT NULL,
    password bytea,
    salt bytea,
    name character varying(50) NOT NULL,
    age integer,
    role_id integer
);


ALTER TABLE public.users OWNER TO ilya;

--
-- TOC entry 211 (class 1259 OID 16676)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: ilya
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO ilya;

--
-- TOC entry 3392 (class 0 OID 0)
-- Dependencies: 211
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ilya
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 3204 (class 2604 OID 16713)
-- Name: grade id; Type: DEFAULT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.grade ALTER COLUMN id SET DEFAULT nextval('public.grade_id_seq'::regclass);


--
-- TOC entry 3202 (class 2604 OID 16694)
-- Name: group_s id; Type: DEFAULT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.group_s ALTER COLUMN id SET DEFAULT nextval('public.group_s_id_seq'::regclass);


--
-- TOC entry 3200 (class 2604 OID 16673)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 3205 (class 2604 OID 16735)
-- Name: salary id; Type: DEFAULT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.salary ALTER COLUMN id SET DEFAULT nextval('public.salary_id_seq'::regclass);


--
-- TOC entry 3203 (class 2604 OID 16706)
-- Name: theme id; Type: DEFAULT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.theme ALTER COLUMN id SET DEFAULT nextval('public.theme_id_seq'::regclass);


--
-- TOC entry 3201 (class 2604 OID 16680)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3376 (class 0 OID 16710)
-- Dependencies: 218
-- Data for Name: grade; Type: TABLE DATA; Schema: public; Owner: ilya
--

COPY public.grade (id, value, student_id, group_id, theme_id) FROM stdin;
12	3	18	1	1
15	1	18	1	1
10	2	18	1	1
21	4	18	1	1
26	9	18	1	2
28	4	18	1	2
30	9	1	4	3
31	4	1	4	3
32	7	1	4	2
33	10	1	1	1
34	8	1	1	1
35	4	1	1	2
36	2	1	4	2
\.


--
-- TOC entry 3372 (class 0 OID 16691)
-- Dependencies: 214
-- Data for Name: group_s; Type: TABLE DATA; Schema: public; Owner: ilya
--

COPY public.group_s (id, title, teacher_id) FROM stdin;
1	11 A	5
4	11 B	75
\.


--
-- TOC entry 3379 (class 0 OID 16743)
-- Dependencies: 221
-- Data for Name: group_student; Type: TABLE DATA; Schema: public; Owner: ilya
--

COPY public.group_student (group_id, student_id) FROM stdin;
1	18
1	1
4	1
\.


--
-- TOC entry 3380 (class 0 OID 16756)
-- Dependencies: 222
-- Data for Name: group_theme; Type: TABLE DATA; Schema: public; Owner: ilya
--

COPY public.group_theme (group_id, theme_id) FROM stdin;
1	1
1	2
4	2
4	3
\.


--
-- TOC entry 3368 (class 0 OID 16670)
-- Dependencies: 210
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: ilya
--

COPY public.roles (id, title) FROM stdin;
1	TEACHER
2	STUDENT
0	ADMIN
\.


--
-- TOC entry 3378 (class 0 OID 16732)
-- Dependencies: 220
-- Data for Name: salary; Type: TABLE DATA; Schema: public; Owner: ilya
--

COPY public.salary (id, salaries_key, value, teacher_id) FROM stdin;
136	4	420.1961	22
138	6	400.25784	22
139	7	257.92358	22
140	8	392.7689	22
141	9	293.71335	22
142	10	343.67413	22
143	11	251.28093	22
144	12	372.84097	22
205	1	2316.1643	75
206	2	2993.184	75
207	3	2483.6685	75
208	4	2772.9836	75
209	5	2736.1038	75
210	6	2879.925	75
211	7	2218.0278	75
212	8	2035.6715	75
213	9	2587.6946	75
214	10	2117.167	75
215	11	2297.23	75
216	12	2171.7097	75
25	1	1465.3074	5
26	2	1476.4669	5
27	3	1345.0066	5
28	4	1225.3776	5
29	5	1924.9646	5
30	6	1342.4946	5
31	7	1688.5594	5
32	8	315.24066	5
33	9	764.06866	5
34	10	1002.4911	5
35	11	940.02875	5
36	12	1969.7197	5
133	1	1	22
134	2	2	22
135	3	4	22
137	5	14	22
\.


--
-- TOC entry 3374 (class 0 OID 16703)
-- Dependencies: 216
-- Data for Name: theme; Type: TABLE DATA; Schema: public; Owner: ilya
--

COPY public.theme (id, title) FROM stdin;
1	Math
2	English
3	Java
\.


--
-- TOC entry 3370 (class 0 OID 16677)
-- Dependencies: 212
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: ilya
--

COPY public.users (id, login, password, salt, name, age, role_id) FROM stdin;
5	Mike_	\\x46a4ec1b0dbb53761ff5d9b2468f96dea50aca65	\\xc197cb36cf178739	Mike	35	1
19	Admin	\\xc059a27cdfe7f489bf2f65a53bdbb6f8d64615c3	\\x0f37a17f39a7c905	Ilya	25	0
18	Stud	\\xfe69c0edebc3c41adb3dddcb85c71445b2d3f70e	\\xaebec1d3df2ce5b4	Dan	20	2
22	Mike_2	\\xa990d34eea1bdbf9c7b66c3b59ac656c6de0699d	\\x40b38e166b42830e	M2	99	1
1	Boris	\\xfe69c0edebc3c41adb3dddcb85c71445b2d3f70e	\\xaebec1d3df2ce5b4	Boris	18	2
75	Alex	\\x49517325dc219ce2c9612e50f68e45589e97827e	\\x371ef3220370f7d9	Alex	35	1
\.


--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 217
-- Name: grade_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ilya
--

SELECT pg_catalog.setval('public.grade_id_seq', 36, true);


--
-- TOC entry 3394 (class 0 OID 0)
-- Dependencies: 213
-- Name: group_s_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ilya
--

SELECT pg_catalog.setval('public.group_s_id_seq', 7, true);


--
-- TOC entry 3395 (class 0 OID 0)
-- Dependencies: 209
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ilya
--

SELECT pg_catalog.setval('public.roles_id_seq', 3, true);


--
-- TOC entry 3396 (class 0 OID 0)
-- Dependencies: 219
-- Name: salary_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ilya
--

SELECT pg_catalog.setval('public.salary_id_seq', 216, true);


--
-- TOC entry 3397 (class 0 OID 0)
-- Dependencies: 215
-- Name: theme_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ilya
--

SELECT pg_catalog.setval('public.theme_id_seq', 14, true);


--
-- TOC entry 3398 (class 0 OID 0)
-- Dependencies: 211
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ilya
--

SELECT pg_catalog.setval('public.users_id_seq', 75, true);


--
-- TOC entry 3215 (class 2606 OID 16715)
-- Name: grade grade_pkey; Type: CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.grade
    ADD CONSTRAINT grade_pkey PRIMARY KEY (id);


--
-- TOC entry 3211 (class 2606 OID 16696)
-- Name: group_s group_s_pkey; Type: CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.group_s
    ADD CONSTRAINT group_s_pkey PRIMARY KEY (id);


--
-- TOC entry 3207 (class 2606 OID 16675)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 3217 (class 2606 OID 16737)
-- Name: salary salary_pkey; Type: CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.salary
    ADD CONSTRAINT salary_pkey PRIMARY KEY (id);


--
-- TOC entry 3213 (class 2606 OID 16708)
-- Name: theme theme_pkey; Type: CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.theme
    ADD CONSTRAINT theme_pkey PRIMARY KEY (id);


--
-- TOC entry 3209 (class 2606 OID 16684)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3221 (class 2606 OID 16721)
-- Name: grade grade_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.grade
    ADD CONSTRAINT grade_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.group_s(id);


--
-- TOC entry 3220 (class 2606 OID 16716)
-- Name: grade grade_student_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.grade
    ADD CONSTRAINT grade_student_id_fkey FOREIGN KEY (student_id) REFERENCES public.users(id);


--
-- TOC entry 3222 (class 2606 OID 16726)
-- Name: grade grade_theme_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.grade
    ADD CONSTRAINT grade_theme_id_fkey FOREIGN KEY (theme_id) REFERENCES public.theme(id);


--
-- TOC entry 3219 (class 2606 OID 16697)
-- Name: group_s group_s_teacher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.group_s
    ADD CONSTRAINT group_s_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public.users(id);


--
-- TOC entry 3224 (class 2606 OID 16746)
-- Name: group_student group_student_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.group_student
    ADD CONSTRAINT group_student_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.group_s(id);


--
-- TOC entry 3225 (class 2606 OID 16751)
-- Name: group_student group_student_student_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.group_student
    ADD CONSTRAINT group_student_student_id_fkey FOREIGN KEY (student_id) REFERENCES public.users(id);


--
-- TOC entry 3226 (class 2606 OID 16759)
-- Name: group_theme group_theme_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.group_theme
    ADD CONSTRAINT group_theme_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.group_s(id);


--
-- TOC entry 3227 (class 2606 OID 16764)
-- Name: group_theme group_theme_theme_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.group_theme
    ADD CONSTRAINT group_theme_theme_id_fkey FOREIGN KEY (theme_id) REFERENCES public.theme(id);


--
-- TOC entry 3223 (class 2606 OID 16738)
-- Name: salary salary_teacher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.salary
    ADD CONSTRAINT salary_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public.users(id);


--
-- TOC entry 3218 (class 2606 OID 16685)
-- Name: users users_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: ilya
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);


-- Completed on 2022-02-07 21:37:34

--
-- PostgreSQL database dump complete
--

