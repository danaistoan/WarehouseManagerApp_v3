--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.6
-- Dumped by pg_dump version 9.5.5

-- Started on 2017-05-02 11:39:29

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2181 (class 1262 OID 16573)
-- Name: warehouse; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE warehouse WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE warehouse OWNER TO postgres;

\connect warehouse

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2184 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 571 (class 1247 OID 32976)
-- Name: user_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE user_type AS ENUM (
    'A',
    'U'
);


ALTER TYPE user_type OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 188 (class 1259 OID 41173)
-- Name: planned_shipment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE planned_shipment (
    id integer NOT NULL,
    customer_name character varying(100) NOT NULL,
    quantity integer NOT NULL
);


ALTER TABLE planned_shipment OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 41171)
-- Name: planned_shipment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE planned_shipment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE planned_shipment_id_seq OWNER TO postgres;

--
-- TOC entry 2185 (class 0 OID 0)
-- Dependencies: 187
-- Name: planned_shipment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE planned_shipment_id_seq OWNED BY planned_shipment.id;


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
-- TOC entry 2186 (class 0 OID 0)
-- Dependencies: 181
-- Name: product_package_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE product_package_id_seq OWNED BY product_package.id;


--
-- TOC entry 184 (class 1259 OID 16587)
-- Name: product_pallet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE product_pallet (
    id integer NOT NULL,
    description character varying(500)
);


ALTER TABLE product_pallet OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 16585)
-- Name: product_pallet_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE product_pallet_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE product_pallet_id_seq OWNER TO postgres;

--
-- TOC entry 2187 (class 0 OID 0)
-- Dependencies: 183
-- Name: product_pallet_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE product_pallet_id_seq OWNED BY product_pallet.id;


--
-- TOC entry 191 (class 1259 OID 41183)
-- Name: shipment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE shipment (
    id integer NOT NULL,
    planned_shipment_id integer NOT NULL,
    completed boolean NOT NULL
);


ALTER TABLE shipment OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 41201)
-- Name: shipment_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE shipment_detail (
    id integer NOT NULL,
    shipment_id integer NOT NULL,
    product_pallet_id integer NOT NULL
);


ALTER TABLE shipment_detail OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 41195)
-- Name: shipment_detail_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE shipment_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE shipment_detail_id_seq OWNER TO postgres;

--
-- TOC entry 2188 (class 0 OID 0)
-- Dependencies: 192
-- Name: shipment_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE shipment_detail_id_seq OWNED BY shipment_detail.id;


--
-- TOC entry 194 (class 1259 OID 41199)
-- Name: shipment_detail_product_pallet_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE shipment_detail_product_pallet_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE shipment_detail_product_pallet_id_seq OWNER TO postgres;

--
-- TOC entry 2189 (class 0 OID 0)
-- Dependencies: 194
-- Name: shipment_detail_product_pallet_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE shipment_detail_product_pallet_id_seq OWNED BY shipment_detail.product_pallet_id;


--
-- TOC entry 193 (class 1259 OID 41197)
-- Name: shipment_detail_shipment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE shipment_detail_shipment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE shipment_detail_shipment_id_seq OWNER TO postgres;

--
-- TOC entry 2190 (class 0 OID 0)
-- Dependencies: 193
-- Name: shipment_detail_shipment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE shipment_detail_shipment_id_seq OWNED BY shipment_detail.shipment_id;


--
-- TOC entry 189 (class 1259 OID 41179)
-- Name: shipment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE shipment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE shipment_id_seq OWNER TO postgres;

--
-- TOC entry 2191 (class 0 OID 0)
-- Dependencies: 189
-- Name: shipment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE shipment_id_seq OWNED BY shipment.id;


--
-- TOC entry 190 (class 1259 OID 41181)
-- Name: shipment_planned_shipment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE shipment_planned_shipment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE shipment_planned_shipment_id_seq OWNER TO postgres;

--
-- TOC entry 2192 (class 0 OID 0)
-- Dependencies: 190
-- Name: shipment_planned_shipment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE shipment_planned_shipment_id_seq OWNED BY shipment.planned_shipment_id;


--
-- TOC entry 186 (class 1259 OID 32983)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "user" (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(20) NOT NULL,
    email character varying(50),
    user_type character varying(1) NOT NULL,
    CONSTRAINT u_type CHECK (((user_type)::text = ANY ((ARRAY['A'::character varying, 'U'::character varying])::text[])))
);


ALTER TABLE "user" OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 32981)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_id_seq OWNER TO postgres;

--
-- TOC entry 2193 (class 0 OID 0)
-- Dependencies: 185
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_id_seq OWNED BY "user".id;


--
-- TOC entry 2026 (class 2604 OID 41176)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY planned_shipment ALTER COLUMN id SET DEFAULT nextval('planned_shipment_id_seq'::regclass);


--
-- TOC entry 2022 (class 2604 OID 16579)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_package ALTER COLUMN id SET DEFAULT nextval('product_package_id_seq'::regclass);


--
-- TOC entry 2023 (class 2604 OID 16590)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_pallet ALTER COLUMN id SET DEFAULT nextval('product_pallet_id_seq'::regclass);


--
-- TOC entry 2027 (class 2604 OID 41186)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shipment ALTER COLUMN id SET DEFAULT nextval('shipment_id_seq'::regclass);


--
-- TOC entry 2028 (class 2604 OID 41187)
-- Name: planned_shipment_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shipment ALTER COLUMN planned_shipment_id SET DEFAULT nextval('shipment_planned_shipment_id_seq'::regclass);


--
-- TOC entry 2029 (class 2604 OID 41204)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shipment_detail ALTER COLUMN id SET DEFAULT nextval('shipment_detail_id_seq'::regclass);


--
-- TOC entry 2030 (class 2604 OID 41205)
-- Name: shipment_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shipment_detail ALTER COLUMN shipment_id SET DEFAULT nextval('shipment_detail_shipment_id_seq'::regclass);


--
-- TOC entry 2031 (class 2604 OID 41206)
-- Name: product_pallet_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shipment_detail ALTER COLUMN product_pallet_id SET DEFAULT nextval('shipment_detail_product_pallet_id_seq'::regclass);


--
-- TOC entry 2024 (class 2604 OID 32986)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);


--
-- TOC entry 2169 (class 0 OID 41173)
-- Dependencies: 188
-- Data for Name: planned_shipment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY planned_shipment (id, customer_name, quantity) FROM stdin;
\.


--
-- TOC entry 2194 (class 0 OID 0)
-- Dependencies: 187
-- Name: planned_shipment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('planned_shipment_id_seq', 1, false);


--
-- TOC entry 2163 (class 0 OID 16576)
-- Dependencies: 182
-- Data for Name: product_package; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY product_package (id, description, type, product_pallet_id) FROM stdin;
1	Lego City	Toys	\N
2	Lego City	Toys	\N
3	Lego City	Toys	\N
4	Lego City	Toys	\N
326			\N
327			\N
233	Colouring Books	Books	\N
328			\N
124	Wooden toys for 3-5 years	Toys	\N
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
122	Puzzle for 3-5 years	Toys	61
123	Puzzle for babies	Toys	61
130	Puzzle for 3-5 years	Toys	65
131	Puzzle for babies	Toys	65
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
132	Wooden toys for 3-5 years	Toys	\N
133	Wooden toys for babies	Toys	\N
120	Wooden toys for 3-5 years	Toys	\N
121	Wooden toys for babies	Toys	\N
86	Puzzle for 3-5 years	Toys	\N
87	Puzzle for babies	Toys	\N
90	Puzzle for 3-5 years	Toys	\N
91	Puzzle for babies	Toys	\N
92	Wooden toys for 3-5 years	Toys	\N
93	Wooden toys for babies	Toys	\N
94	Puzzle for 3-5 years	Toys	\N
95	Puzzle for babies	Toys	\N
96	Wooden toys for 3-5 years	Toys	\N
97	Wooden toys for babies	Toys	\N
98	Puzzle for 3-5 years	Toys	\N
99	Puzzle for babies	Toys	\N
118	Puzzle for 3-5 years	Toys	\N
119	Puzzle for babies	Toys	\N
126	Puzzle for 3-5 years	Toys	\N
127	Puzzle for babies	Toys	\N
128	Wooden toys for 3-5 years	Toys	\N
129	Wooden toys for babies	Toys	\N
100	Wooden toys for 3-5 years	Toys	\N
101	Wooden toys for babies	Toys	\N
134	Puzzle for 3-5 years	Toys	\N
135	Puzzle for babies	Toys	\N
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
170	Milk for babies	Food	85
171	Cereals for babies	Food	85
172	Food for children	Food	86
173	Cereals	Food	86
180	Food for children	Food	90
181	Cereals	Food	90
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
204	D1	P1	\N
205	D2	P2	\N
218	Lego for 1 to 3 years	Lego Duplo	109
219	Lego for girls 3 to 7 years	Lego Friends	109
194	D3	P3	\N
195	D4	P4	\N
220	Wooden bikes	Bikes for toddlers	110
221	For 4+ years	Bikes for kids	110
241	Milk chocolate	Chocolate	123
242	Milk chocolate	Chocolate	124
243	Milk chocolate	Chocolate	124
228	Books for children	Books	117
229	Colouring Books	Books	117
230	Books for children	Books	118
231	Colouring Books	Books	118
184	Pack1	Type1	\N
185	Pack2	Type2	\N
276	Milk Chocolate	Choco	\N
222	Books for children	Books	\N
223	Colouring Books	Books	\N
224	Books for children	Books	\N
225	Colouring Books	Books	\N
277	Dark Chocolate	Choco	\N
236	Lego for 1 to 3 years	Lego Duplo	\N
237	Lego for 9 years +	Lego Technic	\N
226	Books for children	Books	\N
227	Colouring Books	Books	\N
216	Lego for 4 to 9 years	Lego City	\N
217	Lego for 9 years +	Lego Technic	\N
234	Wooden bikes	Bikes for toddlers	\N
235	Bikes for children	Mountain bikes	\N
240	Milk chocolate	Chocolate	123
250	d1	t1	\N
251	d1	t1	\N
252	d1	t1	\N
253	d1	t1	\N
248	d1	t1	\N
249	d1	t1	\N
244	d1	t1	\N
245	d1	t1	\N
246	d1	t1	\N
247	d1	t1	\N
254	d1	t1	\N
255	d1	t1	\N
256	d2	t2	\N
257	d2	t2	\N
258	d2	t2	\N
259	d2	t2	\N
260	d1	t1	\N
261	d1	t1	\N
238	d1	t1	\N
239	d1	t1	\N
266	d1	t1	\N
267	d2	t2	\N
268	fdfd	fd	\N
269	fdf	dfd	\N
270	fdsfdss		\N
271			\N
272	s	a	\N
273	d	a	\N
264	d1	t1	\N
265	d1	t1	\N
274	d1	t1	\N
275	d2	t2	\N
262	d1	t1	\N
263	d1	t1	\N
329			\N
279	fdgd	dfg	\N
280	dfd	dfg	\N
281	fdgd	dfg	\N
330			\N
284	sdds	dffd	\N
282	dfd	dfg	\N
331			\N
232	Books for children	Books	\N
166	Milk for babies	Food	\N
167	Cereals for babies	Food	\N
154	Milk for babies	Food	\N
155	Cereals for babies	Food	\N
178	Milk for babies	Food	\N
179	Cereals for babies	Food	\N
176	Food for children	Food	\N
177	Cereals	Food	\N
285	gfggd	gfdgd	\N
286	gfdg	dfgd	\N
283	fgfg	ssf	\N
287	cbcv	xcvx	\N
288	ccv	vnvb	\N
332			\N
333			\N
290			\N
291			\N
292			\N
293			\N
294			\N
334			\N
335			\N
298			\N
295	asf	asf	\N
296	fsda	dfd	\N
297			\N
289	sds	dgfd	\N
299			\N
336			\N
300			\N
301			\N
302			\N
303	dfsfd		\N
304		T1	\N
337			\N
305		dfdsf	\N
338	cfb		\N
307			\N
306			\N
308			\N
309			\N
310			\N
311		d	\N
312			\N
341			\N
313	sg		\N
340			\N
314			\N
339			\N
315			\N
316			\N
317			\N
319			\N
318			\N
342	sad	sdsa	\N
343	asa	sds	\N
344	sdfd	fghgf	193
321			\N
320			\N
322	c	s	\N
324			\N
323			\N
325			\N
345	gfddf	fgfhg	\N
346			\N
347	dhggf	fghg	\N
348			\N
350	Dark chocolate	Choco	197
349	faas	asfds	\N
125	Wooden toys for babies	Toys	\N
351	Choco1	Dark choco	198
352	Mountain bikes	Bikes	199
355	LegoPack	LegoType	202
356	ChocoPack	ChocoType	203
357	Pack1	Type1	\N
354	Pack2	Type2	\N
353	NewPack	PackType	\N
358	BikesPack	BikesType	205
360	Pack3	Type3	\N
359	PackTest	TypeTest	\N
362	sfdg	asdfs	\N
363	sfdasd	asds	\N
364			\N
361	LegoPack	LegoType	\N
365	fgfdg	fhfhgh	\N
372	hhh	hhhhhh	\N
371	fdf	gdfd	\N
373	NewPack	NewType	218
374	LegoDuplo	Lego	219
\.


--
-- TOC entry 2195 (class 0 OID 0)
-- Dependencies: 181
-- Name: product_package_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('product_package_id_seq', 374, true);


--
-- TOC entry 2165 (class 0 OID 16587)
-- Dependencies: 184
-- Data for Name: product_pallet; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY product_pallet (id, description) FROM stdin;
117	Books
118	Books
123	Chocolate
124	Chocolate
193	Lego
197	Choco
198	Chocolate
199	Bikes
202	Lego
203	Chocolate
205	Bikes
51	Puzzle
52	Wooden toys
53	Puzzle
55	Puzzle
57	Puzzle
58	Wooden toys
61	Puzzle
65	Puzzle
69	Puzzle
70	Food
218	NewPallet
219	Lego
75	Baby-Food
76	Food
78	Food
79	Baby-Food
80	Food
81	Baby-Food
82	Food
85	Baby-Food
86	Food
90	Food
95	Lego
96	Di's Pallet
100	Lego
109	Lego
110	Bikes
\.


--
-- TOC entry 2196 (class 0 OID 0)
-- Dependencies: 183
-- Name: product_pallet_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('product_pallet_id_seq', 219, true);


--
-- TOC entry 2172 (class 0 OID 41183)
-- Dependencies: 191
-- Data for Name: shipment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY shipment (id, planned_shipment_id, completed) FROM stdin;
\.


--
-- TOC entry 2176 (class 0 OID 41201)
-- Dependencies: 195
-- Data for Name: shipment_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY shipment_detail (id, shipment_id, product_pallet_id) FROM stdin;
\.


--
-- TOC entry 2197 (class 0 OID 0)
-- Dependencies: 192
-- Name: shipment_detail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('shipment_detail_id_seq', 1, false);


--
-- TOC entry 2198 (class 0 OID 0)
-- Dependencies: 194
-- Name: shipment_detail_product_pallet_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('shipment_detail_product_pallet_id_seq', 1, false);


--
-- TOC entry 2199 (class 0 OID 0)
-- Dependencies: 193
-- Name: shipment_detail_shipment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('shipment_detail_shipment_id_seq', 1, false);


--
-- TOC entry 2200 (class 0 OID 0)
-- Dependencies: 189
-- Name: shipment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('shipment_id_seq', 1, false);


--
-- TOC entry 2201 (class 0 OID 0)
-- Dependencies: 190
-- Name: shipment_planned_shipment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('shipment_planned_shipment_id_seq', 1, false);


--
-- TOC entry 2167 (class 0 OID 32983)
-- Dependencies: 186
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "user" (id, username, password, email, user_type) FROM stdin;
1	dana	dana	dana@gmail.com	A
2	admin	admin	admin@gmail.com	A
4	user	user	user@gmail.com	U
\.


--
-- TOC entry 2202 (class 0 OID 0)
-- Dependencies: 185
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('user_id_seq', 4, true);


--
-- TOC entry 2037 (class 2606 OID 32991)
-- Name: id_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT id_pk PRIMARY KEY (id);


--
-- TOC entry 2033 (class 2606 OID 16584)
-- Name: package_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_package
    ADD CONSTRAINT package_pk PRIMARY KEY (id);


--
-- TOC entry 2035 (class 2606 OID 16592)
-- Name: pallet_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_pallet
    ADD CONSTRAINT pallet_pk PRIMARY KEY (id);


--
-- TOC entry 2039 (class 2606 OID 41178)
-- Name: planned_shipment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY planned_shipment
    ADD CONSTRAINT planned_shipment_pkey PRIMARY KEY (id);


--
-- TOC entry 2043 (class 2606 OID 41208)
-- Name: shipment_detail_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shipment_detail
    ADD CONSTRAINT shipment_detail_pk PRIMARY KEY (id);


--
-- TOC entry 2041 (class 2606 OID 41189)
-- Name: shipment_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shipment
    ADD CONSTRAINT shipment_pk PRIMARY KEY (id);


--
-- TOC entry 2045 (class 2606 OID 41219)
-- Name: planned_shipment_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shipment
    ADD CONSTRAINT planned_shipment_fk FOREIGN KEY (planned_shipment_id) REFERENCES planned_shipment(id) ON DELETE CASCADE;


--
-- TOC entry 2047 (class 2606 OID 41214)
-- Name: product_pallet_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shipment_detail
    ADD CONSTRAINT product_pallet_fk FOREIGN KEY (product_pallet_id) REFERENCES product_pallet(id) ON DELETE SET NULL;


--
-- TOC entry 2044 (class 2606 OID 16603)
-- Name: product_pallet_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY product_package
    ADD CONSTRAINT product_pallet_id FOREIGN KEY (product_pallet_id) REFERENCES product_pallet(id);


--
-- TOC entry 2046 (class 2606 OID 41209)
-- Name: shipment_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY shipment_detail
    ADD CONSTRAINT shipment_fk FOREIGN KEY (shipment_id) REFERENCES shipment(id) ON DELETE CASCADE;


--
-- TOC entry 2183 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2017-05-02 11:39:30

--
-- PostgreSQL database dump complete
--

