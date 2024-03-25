CREATE DATABASE devsutest
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

COMMENT ON DATABASE devsutest
    IS 'db para demo';

GRANT ALL ON DATABASE devsutest TO postgres;

-- -----------------------------------------------------
-- Table "persona"
-- -----------------------------------------------------

CREATE TABLE public.persona
(
    nombre character varying(100) NOT NULL,
    genero character varying(50) NOT NULL,
    edad integer NOT NULL,
    identificacion bigint NOT NULL,
    direccion character varying(100) NOT NULL,
    telefono bigint NOT NULL,
    PRIMARY KEY (identificacion)
);

ALTER TABLE IF EXISTS public.persona
    OWNER to postgres;

COMMENT ON TABLE public.persona
    IS 'datos de la persona';

CREATE SEQUENCE public.cliente_id_cliente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cliente_id_cliente_seq OWNER TO postgres;    

ALTER SEQUENCE public.cliente_id_cliente_seq OWNED BY public.cliente.id_cliente;
ALTER TABLE ONLY public.cliente ALTER COLUMN id_cliente SET DEFAULT nextval('public.cliente_id_cliente_seq'::regclass);

-- -----------------------------------------------------
-- Table "cliente"
-- -----------------------------------------------------
CREATE TABLE public.cliente
(
    identificacion bigint NOT NULL,
    id_cliente integer NOT NULL,
    contrasenia character varying NOT NULL,
    estado integer NOT NULL,
    PRIMARY KEY (id_cliente),
    CONSTRAINT fk_persona FOREIGN KEY (identificacion)
        REFERENCES public.persona (identificacion) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.cliente
    OWNER to postgres;

GRANT ALL ON TABLE public.cliente TO postgres;    

-- -----------------------------------------------------
-- Table "tipo_cuenta"
-- -----------------------------------------------------
CREATE TABLE public.tipo_cuenta
(
    "idTipoCuenta" integer NOT NULL,
    nombre_cuenta character varying(100) NOT NULL,
    PRIMARY KEY ("idTipoCuenta")
);

ALTER TABLE IF EXISTS public.tipo_cuenta
    OWNER to postgres;

GRANT ALL ON TABLE public.tipo_cuenta TO postgres;

COMMENT ON TABLE public.tipo_cuenta
    IS 'almacen tipo de cuenta';

INSERT INTO public.tipo_cuenta values(1, 'Ahorros');
INSERT INTO public.tipo_cuenta values(2, 'Dorriente');

-- -----------------------------------------------------
-- Table "cuenta"
-- -----------------------------------------------------
CREATE TABLE public.cuenta
(	
    numero_cuenta uuid NOT NULL,
    id_tipo_cuenta integer NOT NULL,
    saldo_inicial numeric(16, 2) NOT NULL,
    saldo_disponible numeric(16, 2) NOT NULL,
    estado integer NOT NULL,
    id_cliente integer NOT NULL,
    PRIMARY KEY (numero_cuenta),
    CONSTRAINT fk_cliente FOREIGN KEY (id_cliente)
        REFERENCES public.cliente (id_cliente) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_tipo_cuenta FOREIGN KEY (id_tipo_cuenta)
        REFERENCES public.tipo_cuenta (id_tipo_cuenta) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.cuenta
    OWNER to postgres;

GRANT ALL ON TABLE public.cuenta TO postgres;

-- -----------------------------------------------------
-- Table "tipo_movimiento"
-- -----------------------------------------------------
CREATE TABLE public.tipo_movimiento
(
    id_tipo_movimiento integer NOT NULL,
    nombre character varying(50) NOT NULL,
    PRIMARY KEY (id_tipo_movimiento)
);

ALTER TABLE IF EXISTS public.tipo_movimiento
    OWNER to postgres;

GRANT ALL ON TABLE public.tipo_movimiento TO postgres;

COMMENT ON TABLE public.tipo_movimiento
    IS 'tipo movimiento de cuenta';

insert into public.tipo_movimiento values (1, 'Credito');
insert into public.tipo_movimiento values (2, 'Debito');

-- -----------------------------------------------------
-- Table "movimiento"
-- -----------------------------------------------------
CREATE TABLE public.movimiento
(
	id_movimiento integer NOT NULL,
    fecha date NOT NULL,
    id_tipo_movimiento integer NOT NULL,
    valor numeric(16, 2) NOT NULL,
    saldo numeric(16, 2) NOT NULL,
    numero_cuenta uuid NOT NULL,
    PRIMARY KEY (id_movimiento)
    CONSTRAINT fk_tipo_movimiento FOREIGN KEY (id_tipo_movimiento)
        REFERENCES public.tipo_movimiento (id_tipo_movimiento) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_cuenta FOREIGN KEY (numero_cuenta)
        REFERENCES public.cuenta (numero_cuenta) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.movimiento
    OWNER to postgres;

GRANT ALL ON TABLE public.movimiento TO postgres;

COMMENT ON TABLE public.movimiento
    IS 'almacena movimientos de cuentas';    

CREATE SEQUENCE public.movimiento_id_movimiento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.movimiento_id_movimiento_seq OWNER TO postgres;    

ALTER SEQUENCE public.movimiento_id_movimiento_seq OWNED BY public.movimiento.id_movimiento;
ALTER TABLE ONLY public.movimiento ALTER COLUMN id_movimiento SET DEFAULT nextval('public.movimiento_id_movimiento_seq'::regclass);    