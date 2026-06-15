# Remove UTF-8 BOM from all Java sources, then compile and run.
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location -LiteralPath $root

Get-ChildItem -LiteralPath 'src' -Recurse -Filter '*.java' | ForEach-Object {
    $bytes = [System.IO.File]::ReadAllBytes($_.FullName)
    if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
        [System.IO.File]::WriteAllBytes($_.FullName, $bytes[3..($bytes.Length - 1)])
        Write-Host "Removed BOM: $($_.Name)"
    }
}

New-Item -ItemType Directory -Force -Path 'out' | Out-Null
$files = Get-ChildItem -LiteralPath 'src' -Recurse -Filter '*.java' | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out @files
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
Write-Host 'Compile OK. Starting program...'
chcp 65001 | Out-Null
java -Dfile.encoding=UTF-8 -cp out Main
