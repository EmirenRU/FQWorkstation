if (-Not (New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent())).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
    Write-Host "This script requires admin privileges to run. Please run it as an administrator."
    pause
    exit
}

$currentDirectory = Get-Location
$postgresInstallerPath = "$currentDirectory\postgresql.exe"
$psqlLocation = "C:\Program Files\PostgreSQL\bin"

$fqwDatabaseName = "fqworkstation"
$supportDatabaseName = "support"
$auth = "auth"
$user = "temp"
$password = "temp"

[System.Environment]::SetEnvironmentVariable("POSTGRES_LOGIN", "temp", [System.EnvironmentVariableTarget]::User)
[System.Environment]::SetEnvironmentVariable("POSTGRES_PASSWORD", "temp", [System.EnvironmentVariableTarget]::User)

[System.Environment]::SetEnvironmentVariable("SECURITY_LOGIN", "user", [System.EnvironmentVariableTarget]::User)
[System.Environment]::SetEnvironmentVariable("SECURITY_PASSWORD", "ihateitalready", [System.EnvironmentVariableTarget]::User)

if (Test-Path "$currentDirectory\Java") {
    # Copy the Java directory to the local app data
    Copy-Item -Path "$currentDirectory\Java" -Destination "$env:LOCALAPPDATA\Java" -Recurse -Force

    # Check if JAVA_HOME is not set
    if (-Not $env:JAVA_HOME) {
        # Set JAVA_HOME and update the PATH
        setx JAVA_HOME "$env:LOCALAPPDATA\Java"
        $newPath = "$env:LOCALAPPDATA\Java\bin;$env:PATH"
        setx PATH $newPath
    } else {
        Write-Host "JAVA_HOME is already set to: $env:JAVA_HOME"
    }
} else {
    Write-Host "The specified Java directory does not exist: $currentDirectory\Java"
}



if (Test-Path $currentDirectory\Java){
    Copy-Item -Path $currentDirectory\Java -Destination $($env:LOCALAPPDATA) -Recurse
    $env:JAVA_HOME = "$($env:LOCALAPPDATA)\Java"
    $env:PATH += ";$env:JAVA_HOME\bin"
}

$arguments = @(
    "--mode", "unattended",
    "--unattendedmodeui", "none",
    "--prefix", '"C:\Program Files\PostgreSQL"',
    "--datadir", '"C:\Program Files\PostgreSQL\data"',
    "--superpassword", "${password}",
    "--servicepassword", "${password}",
    "--servicename", "postgresql",
    "--serverport", "5432"
)

if (Test-Path $postgresInstallerPath) {
    Start-Process -FilePath $postgresInstallerPath -ArgumentList $arguments -Wait
} else {
    Write-Host "PostgreSQL installer not found."
}

try {
    & "$psqlLocation\psql" "postgresql://postgres:temp@localhost:5432" -c "CREATE USER ${user} WITH PASSWORD '${password}' LOGIN CREATEDB ;"
    & "$psqlLocation\psql"  "postgresql://${user}:${password}@localhost:5432/postgres" -c "CREATE DATABASE $fqwDatabaseName;"
    & "$psqlLocation\psql"  "postgresql://${user}:${password}@localhost:5432/postgres" -c "CREATE DATABASE $supportDatabaseName ;"
    & "$psqlLocation\psql"  "postgresql://${user}:${password}@localhost:5432/postgres" -c "CREATE DATABASE $auth ;"

} catch {
    Write-Host "Error creating databases and role: $($Error[0].Message)"
    exit
}