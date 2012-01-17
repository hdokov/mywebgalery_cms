
    create table accounts (
        id  bigserial not null,
        created timestamp without time zone,
        updated timestamp without time zone,
        name text,
        owner int8 not null,
        primary key (id)
    );

    create table apps (
        id  bigserial not null,
        created timestamp without time zone,
        updated timestamp without time zone,
        accountId int8 not null,
        descr text,
        keywords text,
        name text,
        owner int8 not null,
        title text,
        primary key (id)
    );

    create table audits (
        id int8 not null,
        pageName varchar(255),
        request varchar(255),
        timestamp timestamp,
        username varchar(255),
        primary key (id)
    );

    create table modules (
        id  bigserial not null,
        created timestamp without time zone,
        updated timestamp without time zone,
        appId int8 not null,
        content text,
        data text,
        descr text,
        name text,
        type text,
        primary key (id)
    );

    create table pages (
        id  bigserial not null,
        created timestamp without time zone,
        updated timestamp without time zone,
        appId int8 not null,
        templateId int8,
        title text,
        type text,
        url text,
        primary key (id)
    );

    create table users (
        id  bigserial not null,
        created timestamp without time zone,
        updated timestamp without time zone,
        accountId int8 not null,
        address text,
        admin bool not null,
        city text,
        country text,
        email text not null,
        firstname text not null,
        lastLoginAdmin timestamp without time zone,
        lastLoginUser timestamp without time zone,
        lastname text not null,
        name text not null,
        pass text not null,
        phone text,
        privileges varchar(255),
        token text,
        zip text,
        primary key (id)
    );

    create sequence hibernate_sequence;
