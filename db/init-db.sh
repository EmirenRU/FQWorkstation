#!/bin/bash
set -e

echo "Starting database initialization..."

create_database() {
    local db_name=$1
    echo "Checking for database: $db_name"

    if psql -U temp -d postgres -tc "SELECT 1 FROM pg_database WHERE datname = '$db_name'" | grep -q 1; then
        echo "Database '$db_name' already exists."
    else
        echo "Creating database '$db_name'..."
        createdb -U temp "$db_name"
        echo "Database '$db_name' created successfully."
    fi
}

create_database "info-system-department"
create_database "data-support"

echo "Database initialization complete."
