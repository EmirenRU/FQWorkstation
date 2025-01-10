$currentDirectory = Get-Location
$templateDataFile = "$currentDirectory\data.sql"
$psqlLocation = "C:\Program Files\PostgreSQL\bin"

$user="temp";$password="temp";
$fqwDatabaseName = "fqworkstation" ;

& "$psqlLocation\psql" "postgresql://${user}:${password}@localhost:5432/${fqwDatabaseName}" -f "$templateDataFile"