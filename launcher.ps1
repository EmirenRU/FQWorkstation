$currentDir = $PWD.Path
$jarFiles = @()
$jarFolders = @(
    "$currentDir\fqw\target\",
    "$currentDir\support\target\",
    "$currentDir\protocol\target\"
)

if ($null -eq $jarFolders) {
    Write-Host "jarFolders is null."
} else {
    Write-Host "jarFolders is initialized."
}

foreach ($folder in $jarFolders) {
    if (Test-Path $folder) {
        $jarFiles += Get-ChildItem -Path $folder -Filter "*.jar" -Recurse
    } else {
        Write-Host "Directory not found: $folder"
    }
}

function Start-Jars {
    foreach ($jar in $jarFiles) {
        Start-Process java -ArgumentList "-jar `"$($jar.FullName)`""
        Log-Message "Started: $($jar.Name)"
    }
}

function Stop-Jars {
    $javaProcesses = Get-Process java -ErrorAction SilentlyContinue
    if ($javaProcesses) {
        foreach ($process in $javaProcesses) {
            Stop-Process -Id $process.Id -Force
            Log-Message "Stopped: $($process.Id)"
        }
    } else {
        Log-Message "No Java processes found."
    }
}

Write-Host "Found JAR files:"
$jarFiles | ForEach-Object { Write-Host $_.FullName }
Write-Host "Arg is $args"

switch ($args.ToLower()) {
    'start' {
        Start-Jars
    }
    'reboot' {
        Stop-Jars
        Start-Jars
    }
    'stop' {
        Stop-Jars
    }
    default {
        Write-Host "Invalid action. Please use 'start', 'reboot', or 'turn off'."
    }
}

