--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.6
-- Dumped by pg_dump version 9.5.5

-- Started on 2017-03-29 09:17:35

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 182 (class 1259 OID 16576)
-- Name: product_package; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE product_package (
    id integer NOT NULL,
    description character varying(500) NOT NULL,
    type character varying(100) NOT NULL,
    product_pallet_id integer
);


ALTER TABLE product_package OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 16574)
-- Name: product_package_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE product_package_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE product_package_id_seq OWNER TO postgres;

--
-- TOC entry 2110 (class 0 OID 0)
-- Dependencies: 181
-- Name: product_package_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE product_package_id_seq OWNED BY product_package.id;


--
-- TOC entry 1986 (class 2604 OID 16579)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_package ALTER COLUMN id SET DEFAULT nextval('product_package_id_seq'::regclass);


--
-- TOC entry 2105 (class 0 OID 16576)
-- Dependencies: 182
-- Data for Name: product_package; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY product_package (id, description, type, product_pallet_id) FROM stdin;
1	Lego City	Toys	\N
2	Lego City	Toys	\N
3	Lego City	Toys	\N
4	Lego City	Toys	\N
26	Wooden toys for 3-5 years	Toys	\N
27	Wooden toys for babies	Toys	\N
28	Wooden toys for 3-5 years	Toys	\N
29	Wooden toys for babies	Toys	\N
30	Puzzle for 3-5 years	Toys	\N
31	Puzzle for babies	Toys	\N
32	Wooden toys for 3-5 years	Toys	\N
33	Wooden toys for babies	Toys	\N
34	Puzzle for 3-5 years	Toys	\N
35	Puzzle for babies	Toys	\N
86	Puzzle for 3-5 years	Toys	43
87	Puzzle for babies	Toys	43
90	Puzzle for 3-5 years	Toys	45
91	Puzzle for babies	Toys	45
92	Wooden toys for 3-5 years	Toys	46
93	Wooden toys for babies	Toys	46
94	Puzzle for 3-5 years	Toys	47
95	Puzzle for babies	Toys	47
96	Wooden toys for 3-5 years	Toys	48
97	Wooden toys for babies	Toys	48
98	Puzzle for 3-5 years	Toys	49
99	Puzzle for babies	Toys	49
100	Wooden toys for 3-5 years	Toys	50
101	Wooden toys for babies	Toys	50
102	Puzzle for 3-5 years	Toys	51
103	Puzzle for babies	Toys	51
104	Wooden toys for 3-5 years	Toys	52
105	Wooden toys for babies	Toys	52
106	Puzzle for 3-5 years	Toys	53
107	Puzzle for babies	Toys	53
110	Puzzle for 3-5 years	Toys	55
111	Puzzle for babies	Toys	55
114	Puzzle for 3-5 years	Toys	57
115	Puzzle for babies	Toys	57
116	Wooden toys for 3-5 years	Toys	58
117	Wooden toys for babies	Toys	58
118	Puzzle for 3-5 years	Toys	59
119	Puzzle for babies	Toys	59
120	Wooden toys for 3-5 years	Toys	60
121	Wooden toys for babies	Toys	60
122	Puzzle for 3-5 years	Toys	61
123	Puzzle for babies	Toys	61
124	Wooden toys for 3-5 years	Toys	62
125	Wooden toys for babies	Toys	62
126	Puzzle for 3-5 years	Toys	63
127	Puzzle for babies	Toys	63
128	Wooden toys for 3-5 years	Toys	64
129	Wooden toys for babies	Toys	64
130	Puzzle for 3-5 years	Toys	65
131	Puzzle for babies	Toys	65
132	Wooden toys for 3-5 years	Toys	66
133	Wooden toys for babies	Toys	66
134	Puzzle for 3-5 years	Toys	67
135	Puzzle for babies	Toys	67
138	Puzzle for 3-5 years	Toys	69
139	Puzzle for babies	Toys	69
36	Wooden toys for 3-5 years	Toys	\N
37	Wooden toys for babies	Toys	\N
38	Puzzle for 3-5 years	Toys	\N
39	Puzzle for babies	Toys	\N
140	Food for children	Food	70
141	Cereals	Food	70
44	Wooden toys for 3-5 years	Toys	\N
45	Wooden toys for babies	Toys	\N
46	Puzzle for 3-5 years	Toys	\N
47	Puzzle for babies	Toys	\N
48	Wooden toys for 3-5 years	Toys	\N
49	Wooden toys for babies	Toys	\N
50	Puzzle for 3-5 years	Toys	\N
51	Puzzle for babies	Toys	\N
52	Wooden toys for 3-5 years	Toys	\N
53	Wooden toys for babies	Toys	\N
144	Food for children	Food	\N
145	Cereals	Food	\N
56	Wooden toys for 3-5 years	Toys	\N
57	Wooden toys for babies	Toys	\N
58	Puzzle for 3-5 years	Toys	\N
59	Puzzle for babies	Toys	\N
60	Wooden toys for 3-5 years	Toys	\N
61	Wooden toys for babies	Toys	\N
62	Puzzle for 3-5 years	Toys	\N
63	Puzzle for babies	Toys	\N
64	Wooden toys for 3-5 years	Toys	\N
65	Wooden toys for babies	Toys	\N
66	Puzzle for 3-5 years	Toys	\N
67	Puzzle for babies	Toys	\N
88	Wooden toys for 3-5 years	Toys	\N
89	Wooden toys for babies	Toys	\N
68	Wooden toys for 3-5 years	Toys	\N
69	Wooden toys for babies	Toys	\N
142	Milk for babies	Food	\N
143	Cereals for babies	Food	\N
54	Puzzle for 3-5 years	Toys	\N
55	Puzzle for babies	Toys	\N
70	Puzzle for 3-5 years	Toys	\N
71	Puzzle for babies	Toys	\N
72	Wooden toys for 3-5 years	Toys	\N
73	Wooden toys for babies	Toys	\N
74	Puzzle for 3-5 years	Toys	\N
75	Puzzle for babies	Toys	\N
76	Wooden toys for 3-5 years	Toys	\N
77	Wooden toys for babies	Toys	\N
78	Puzzle for 3-5 years	Toys	\N
79	Puzzle for babies	Toys	\N
136	Wooden toys for 3-5 years	Toys	\N
137	Wooden toys for babies	Toys	\N
80	Wooden toys for 3-5 years	Toys	\N
81	Wooden toys for babies	Toys	\N
108	Wooden toys for 3-5 years	Toys	\N
109	Wooden toys for babies	Toys	\N
84	Wooden toys for 3-5 years	Toys	\N
85	Wooden toys for babies	Toys	\N
112	Wooden toys for 3-5 years	Toys	\N
113	Wooden toys for babies	Toys	\N
82	Puzzle for 3-5 years	Toys	\N
83	Puzzle for babies	Toys	\N
40	Wooden toys for 3-5 years	Toys	\N
41	Wooden toys for babies	Toys	\N
42	Puzzle for 3-5 years	Toys	\N
43	Puzzle for babies	Toys	\N
146	Milk for babies	Food	\N
147	Cereals for babies	Food	\N
150	Milk for babies	Food	75
151	Cereals for babies	Food	75
152	Food for children	Food	76
153	Cereals	Food	76
154	Milk for babies	Food	77
155	Cereals for babies	Food	77
156	Food for children	Food	78
157	Cereals	Food	78
158	Milk for babies	Food	79
159	Cereals for babies	Food	79
160	Food for children	Food	80
161	Cereals	Food	80
162	Milk for babies	Food	81
163	Cereals for babies	Food	81
164	Food for children	Food	82
165	Cereals	Food	82
166	Milk for babies	Food	83
167	Cereals for babies	Food	83
170	Milk for babies	Food	85
171	Cereals for babies	Food	85
172	Food for children	Food	86
173	Cereals	Food	86
176	Food for children	Food	88
177	Cereals	Food	88
178	Milk for babies	Food	89
179	Cereals for babies	Food	89
180	Food for children	Food	90
181	Cereals	Food	90
184	Pack1	Type1	92
185	Pack2	Type2	92
186	D1	T1	\N
187	D1	T1	\N
190	Lego for 1 to 3 years	Lego Duplo	95
191	Lego for 3 to 7 years	Lego City	95
192	Lots	Kittens	96
193	Lots	Chocolate	96
200	Lego for 4 to 9 years	Lego City	100
201	Lego for 9 years +	Lego Technic	100
196	P4	T4	\N
197	D5	T5	\N
188	D1	T1	\N
189	D2	P2	\N
202	Lego for 2 to 3 years	Lego Duplo	\N
203	Lego for girls 3 to 7 years	Lego Friends	\N
198	vfsfvf	svs	\N
199	vsfvsf	fvsfvs	\N
168	Food for children	Food	\N
169	Cereals	Food	\N
182	Milk for babies	Food	\N
183	Cereals for babies	Food	\N
174	Milk for babies	Food	\N
175	Cereals for babies	Food	\N
148	Food for children	Food	\N
149	Cereals	Food	\N
214	fsf	fdf	\N
215	asfs	sfsd	\N
212	sds	asd	\N
213	asd	dsfd	\N
210	dfg	sdd	\N
211	sfg	sds	\N
208	T1	P1	\N
209	T2	P2	\N
206	T1	P1	\N
207	T2	P2	\N
216	Lego for 4 to 9 years	Lego City	108
217	Lego for 9 years +	Lego Technic	108
204	D1	P1	\N
205	D2	P2	\N
218	Lego for 1 to 3 years	Lego Duplo	109
219	Lego for girls 3 to 7 years	Lego Friends	109
194	D3	P3	\N
195	D4	P4	\N
220	Wooden bikes	Bikes for toddlers	110
221	For 4+ years	Bikes for kids	110
\.


--
-- TOC entry 2111 (class 0 OID 0)
-- Dependencies: 181
-- Name: product_package_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('product_package_id_seq', 221, true);


--
-- TOC entry 1988 (class 2606 OID 16584)
-- Name: package_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_package
    ADD CONSTRAINT package_pk PRIMARY KEY (id);


--
-- TOC entry 1989 (class 2606 OID 16603)
-- Name: product_pallet_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_package
    ADD CONSTRAINT product_pallet_id FOREIGN KEY (product_pallet_id) REFERENCES product_pallet(id);


-- Completed on 2017-03-29 09:17:35

--
-- PostgreSQL database dump complete
--

