$currentDirectory = Get-Location
$hubJar = "$currentDirectory\hub-0.8.0.jar"
$javaFolder = "$env:LOCALAPPDATA\Java"
$javaExe = "$javaFolder\bin\java.exe"

if (Test-Path $hubJar) {
    if (Test-Path $javaExe) {
        Start-Process -FilePath $javaExe -ArgumentList "-jar", $hubJar -NoNewWindow
    } else {
        Write-Host "Java executable not found at $javaExe"
    }
} else {
    Write-Host "JAR file not found at $hubJar"
}