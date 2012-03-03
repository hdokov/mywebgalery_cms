
    create table accounts (
        id  bigserial not null,
        created timestamp without time zone,
        permissions text,
        updated timestamp without time zone,
        name text,
        owner int8 not null,
        primary key (id),
        unique (name)
    );

    create table apps (
        id  bigserial not null,
        created timestamp without time zone,
        permissions text,
        updated timestamp without time zone,
        accountId int8 not null,
        css text,
        descr text,
        js text,
        keywords text,
        logo text,
        name text,
        owner int8 not null,
        template text,
        theme text,
        title text,
        wrap text,
        primary key (id),
        unique (name)
    );

    create table audits (
        id int8 not null,
        pageName varchar(255),
        request varchar(255),
        timestamp timestamp,
        username varchar(255),
        primary key (id)
    );

    create table categories (
        id  bigserial not null,
        created timestamp without time zone,
        permissions text,
        updated timestamp without time zone,
        appId int8 not null,
        name text,
        ordered int4,
        parentCategory bigint,
        primary key (id),
        unique (appId, name)
    );

    create table menus (
        id  bigserial not null,
        created timestamp without time zone,
        permissions text,
        updated timestamp without time zone,
        appId int8 not null,
        name text,
        ordered int4,
        uri text,
        parent bigint,
        primary key (id),
        unique (appId, name)
    );

    create table modules (
        id  bigserial not null,
        created timestamp without time zone,
        permissions text,
        updated timestamp without time zone,
        appId int8 not null,
        content text,
        data text,
        descr text,
        displayName text,
        name text,
        pages text,
        type text,
        primary key (id),
        unique (appId, name)
    );

    create table pages (
        id  bigserial not null,
        created timestamp without time zone,
        permissions text,
        updated timestamp without time zone,
        appId int8 not null,
        categoryId int8,
        content text,
        defaultPage bool not null,
        title text,
        type text,
        url text,
        primary key (id),
        unique (appId, url)
    );

    create table users (
        id  bigserial not null,
        created timestamp without time zone,
        permissions text,
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
        primary key (id),
        unique (token),
        unique (accountId, email),
        unique (accountId, name)
    );

    create index account_name_index on accounts (name);

    create index app_name_index on apps (name);

    create index app_account_index on apps (accountId);

    create index category_app_index on categories (appId);

    alter table categories 
        add constraint FK4D47461C8A0CE329 
        foreign key (parentCategory) 
        references categories;

    create index menu_app_index on menus (appId);

    alter table menus 
        add constraint FK62F96F461F8820C 
        foreign key (parent) 
        references menus;

    create index module_app_index on modules (appId);

    create index module_name_index on modules (name);

    create index page_app_index on pages (appId);

    create index page_category_index on pages (categoryId);

    create index page_url_index on pages (url);

    create index user_name_index on users (name);

    create index user_email_index on users (email);

    create index user_account_index on users (accountId);

    create sequence hibernate_sequence;
