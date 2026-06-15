$srcRoot = Join-Path $PSScriptRoot 'src'
$utf8NoBom = New-Object System.Text.UTF8Encoding $false
$removed = 0

Get-ChildItem -LiteralPath $srcRoot -Recurse -Filter '*.java' | ForEach-Object {
    $bytes = [System.IO.File]::ReadAllBytes($_.FullName)
    if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
        [System.IO.File]::WriteAllBytes($_.FullName, $bytes[3..($bytes.Length - 1)])
        Write-Host "BOM removed: $($_.Name)"
        $removed++
    } else {
        $text = $utf8NoBom.GetString($bytes)
        [System.IO.File]::WriteAllText($_.FullName, $text, $utf8NoBom)
    }
}

Write-Host "Done. BOM markers removed from $removed file(s). All sources saved as UTF-8 without BOM."
