$currentDirectory = Get-Location
$javaFolder = "$env:LOCALAPPDATA\Java"
$javExe = "$javaFolder\bin\java.exe"
$fqwJar = "$currentDirectory\fqw-1.0.0.jar"
$hubJar = "$currentDirectory\hub-0.8.0.jar"

$templateDataFile = "$currentDirectory\data.sql"
$psqlLocation = "C:\Program Files\PostgreSQL\bin"

$user="temp";$password="temp";
$fqwDatabaseName = "fqworkstation" ;
if (Test-Path $hubJar -and Test-Path $fqwJar) {
    $hubProcess = Start-Process -FilePath $javExe -ArgumentList "-jar `"$hubJar`"" -PassThru
    $fqwProcess = Start-Process -FilePath $javExe -ArgumentList "-jar `"$fqwJar`"" -PassThru

    Write-Host "Hub and FQWorkstation JARs have been started."

    Start-Sleep -Seconds 180

    & "$psqlLocation\psql" "postgresql://${user}:${password}@localhost:5432/${fqwDatabaseName}" -f "$templateDataFile"

    $hubProcess.WaitForExit()
    $fqwProcess.WaitForExit()
} else {
    Write-Host "One or both JAR files are not found. Hub: $hubJar, FQWorkstation: $fqwJar"
    Pause
}