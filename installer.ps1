if (-Not (New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent())).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
    Write-Host "This script requires admin privileges to run. Please run it as an administrator."
    pause
    exit
}


$currentDirectory = Get-Location
$postgresInstallerPath = "$currentDirectory\postgresql.exe"

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
    "--superpassword", "2d948479128cc52587634853dca66dbc022d88a1b928993eeba723e687298b5c",
    "--servicepassword", "2d948479128cc52587634853dca66dbc022d88a1b928993eeba723e687298b5c",
    "--servicename", "postgresql",
    "--serverport", "5432"
)

if (Test-Path $postgresInstallerPath) {
    Start-Process -FilePath $postgresInstallerPath -ArgumentList $arguments -Wait
} else {
    Write-Host "PostgreSQL installer not found."
}

$fqwDatabaseName = "info-system-department"
$supportDatabaseName = "support-data"
$user = 1032211216
$password = 2d948479128cc52587634853dca66dbc022d88a1b928993eeba723e687298b5c

try {
    & psql -U postgres -d postgres -c "CREATE DATABASE $fqwDatabaseName;"
    & psql -U postgres -d postgres -c "CREATE DATABASE $supportDatabaseName;"
    & psql -U postgres -d postgres -c "CREATE ROLE $user WITH PASSWORD '$password';"
    & psql -U postgres -d $fqwDatabaseName -c "GRANT ALL PRIVILEGES ON DATABASE $fqwDatabaseName TO $user;"
    & psql -U postgres -d $supportDatabaseName -c "GRANT ALL PRIVILEGES ON DATABASE $supportDatabaseName TO $user;"
} catch {
    Write-Host "Error creating databases and role: $($Error[0].Message)"
    exit
}