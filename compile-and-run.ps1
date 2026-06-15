$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot
Push-Location -LiteralPath $root

Write-Host '========================================'
Write-Host ' Honor of Kings IMS - Compile and Run'
Write-Host " Project: $root"
Write-Host " Build dir: $(Get-Location)"
Write-Host '========================================'
Write-Host ''

$outDir = Join-Path (Get-Location) 'out'
Write-Host '[1/4] Cleaning old out folder...'
if (Test-Path -LiteralPath $outDir) {
    Remove-Item -LiteralPath $outDir -Recurse -Force
}
[void][System.IO.Directory]::CreateDirectory($outDir)

Write-Host '[2/4] Collecting source files...'
$javaFiles = @(Get-ChildItem -LiteralPath (Join-Path (Get-Location) 'src') -Recurse -Filter '*.java' | ForEach-Object { $_.FullName })
Write-Host "Found $($javaFiles.Count) Java files."

Write-Host '[3/4] Compiling...'
& javac -encoding UTF-8 -d $outDir @javaFiles
if ($LASTEXITCODE -ne 0) {
    Write-Host 'COMPILE FAILED.'
    Pop-Location
    exit 1
}

if (-not (Test-Path -LiteralPath (Join-Path $outDir 'Main.class'))) {
    Write-Host 'ERROR: out\Main.class was not created.'
    Pop-Location
    exit 1
}

Write-Host 'Compile OK.'
Write-Host 'Main.class exists: YES'
if (Test-Path -LiteralPath (Join-Path $outDir 'util\AdminInitializer.class')) {
    Write-Host 'AdminInitializer.class exists: YES'
} else {
    Write-Host 'AdminInitializer.class exists: NO (admin accounts are also in Main.java)'
}

Write-Host ''
Write-Host '[4/4] Starting program...'
Write-Host 'Login: 饭团linj44 / 070530'
Write-Host '       红糖guoy10 / 123456'
Write-Host ''
chcp 65001 | Out-Null
& java -Dfile.encoding=UTF-8 -cp $outDir Main

Pop-Location
