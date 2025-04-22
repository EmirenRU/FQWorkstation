if (-not (Get-Command docker-compose -ErrorAction SilentlyContinue)){
    Write-Host "Docker is might not be installed..."
    Pause
} else{
    set DOCKER_BUILDKIT=1
    Start-Process "docker" -ArgumentList "build", "-t", "emiren-co/fqworkstation-root:latest", "../." -Wait
#    Start-Process "docker" -ArgumentList "save", "-o", "fqworkstation.tar", "emiren-co/fqworkstation-root:latest" -Wait
#    Start-Process "docker" -ArgumentList "load", "-i", "fqworkstation.tar" -Wait
    Start-Process "docker-compose" -ArgumentList "up", "--build"
}
