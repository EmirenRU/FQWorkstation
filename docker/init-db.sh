#!/bin/bash
set -e

# Database
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE "info-system-department";
    CREATE DATABASE "data-support";
EOSQL