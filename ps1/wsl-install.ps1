if (-not ([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
    Write-Host "Please run this script as an administrator."
    exit
}

try {
    Write-Host "Installing Windows Subsystem for Linux (WSL)..."
    Start-Process "wsl" -ArgumentList "--install" -Wait
    Write-Host "WSL installation command executed successfully."
} catch {
    Write-Host "An error occurred while trying to install WSL: $_"
}

Read-Host -Prompt "Press Enter to continue..."