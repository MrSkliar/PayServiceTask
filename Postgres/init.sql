/*Заглушка на случай если не сработает создание БД из параметров docker-compose*/
DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'paytestdb') THEN
        CREATE DATABASE paytestdb;
    END IF;
    IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'paytestuser') THEN
        CREATE USER paytestuser WITH PASSWORD '123456789';
        GRANT ALL PRIVILEGES ON DATABASE paytestdb TO paytestuser;
    END IF;
END $$;