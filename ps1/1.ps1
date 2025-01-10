$currentDirectory = Get-Location
$javaFolder = "$env:JAVA_HOME"
$javaExe = "$javaFolder\bin\java.exe"
$fqwJar = "$currentDirectory\fqw-1.0.0.jar"
Write-Host "$fqwJar"

if (Test-Path $fqwJar) {
    if (Test-Path $javaExe) {
        Start-Process -FilePath $javaExe -ArgumentList "-jar", $fqwJar -NoNewWindow
    } else {
        Write-Host "Java executable not found at $javaExe"
    }
} else {
    Write-Host "JAR file not found at $hubJar"
}