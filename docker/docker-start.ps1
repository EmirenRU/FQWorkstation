if (-not (Get-Command docker-compose -ErrorAction SilentlyContinue)){
    Write-Host "Docker is might not be installed..."
    Pause
} else{
    Start-Process "docker-compose" -ArgumentList "up", "--build"
}
