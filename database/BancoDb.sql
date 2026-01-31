/* =========================================================
   BancoDb.sql
   Base de datos: BancoDb
   Motor: SQL Server
   Compatible: SSMS / Azure Data Studio / sqlcmd
   Descripción: Esquema del sistema bancario
   ========================================================= */

-- =============================================
-- ELIMINAR BASE DE DATOS SI EXISTE
-- =============================================
IF EXISTS (SELECT 1 FROM sys.databases WHERE name = 'BancoDb')
BEGIN
    ALTER DATABASE BancoDb SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE BancoDb;
END
GO

-- =============================================
-- CREAR BASE DE DATOS
-- =============================================
CREATE DATABASE BancoDb;
GO

-- =============================================
-- USAR BASE DE DATOS
-- =============================================
USE BancoDb;
GO

-- =========================
-- TABLA: personas
-- =========================
	create table personas (
        edad int not null check ((edad<=150) and (edad>=0)),
        persona_id bigint identity not null,
        identificacion varchar(10) not null,
        genero varchar(20) not null check ((genero in ('MASCULINO','FEMENINO','OTRO'))),
        telefono varchar(20) not null,
        nombre varchar(100) not null,
        direccion varchar(200) not null,
        primary key (persona_id)
    );
    GO
    
-- =========================
-- TABLA: clientes
-- =========================
    create table clientes (
        persona_id bigint not null,
        estado varchar(20) not null check ((estado in ('ACTIVO','INACTIVO'))),
        contrasena varchar(255) not null,
        primary key (persona_id)
    );
    GO

-- =========================
-- TABLA: cuentas
-- =========================
    create table cuentas (
        saldo numeric(15,2) not null,
        cliente_id bigint not null,
        cuenta_id bigint identity not null,
        estado varchar(20) not null check ((estado in ('ACTIVA','INACTIVA'))),
        tipo_cuenta varchar(20) not null check ((tipo_cuenta in ('AHORROS','CORRIENTE'))),
        numero_cuenta varchar(30) not null,
        primary key (cuenta_id)
    );
    GO

-- =========================
-- TABLA: movimientos
-- =========================
    create table movimientos (
        monto numeric(15,2) not null,
        saldo_posterior numeric(15,2) not null,
        cuenta_id bigint not null,
        fecha datetime2(7) not null,
        movimiento_id bigint identity not null,
        tipo varchar(20) not null check ((tipo in ('DEPOSITO','RETIRO'))),
        primary key (movimiento_id)
    );
    GO

-- =========================
-- INDICES
-- =========================
	create index ix_movimientos_cuenta_fecha 
       on movimientos (cuenta_id, fecha);
       GO
 
 -- =========================
-- RELACIONES
-- =========================
    alter table cuentas 
       add constraint ux_cuentas_numero unique (numero_cuenta);
       GO

    alter table personas 
       add constraint UQ_personas_identificacion unique (identificacion);
       GO

    alter table clientes 
       add constraint FK_clientes_personas 
       foreign key (persona_id) 
       references personas;
       GO

    alter table cuentas 
       add constraint FK_cuentas_clientes 
       foreign key (cliente_id) 
       references clientes;
       GO

    alter table movimientos 
       add constraint FK_movimientos_cuentas 
       foreign key (cuenta_id) 
       references cuentas;
       GO
