if (-not (Get-Command docker-compose -ErrorAction SilentlyContinue)){
    Write-Host "Docker is might not be installed..."
    Pause
} else{
    $env:DOCKER_BUILDKIT = "1"
    $env:COMPOSE_BAKE = "true"
#    Start-Process "docker" -ArgumentList "build", "-t", "emiren-co/fqworkstation-root:latest", "../." -Wait
#    Start-Process "docker" -ArgumentList "save", "-o", "fqworkstation.tar", "emiren-co/fqworkstation-root:latest" -Wait
#    Start-Process "docker" -ArgumentList "load", "-i", "fqworkstation.tar" -Wait
    Start-Process "docker-compose" -ArgumentList "up", "--build"
}
