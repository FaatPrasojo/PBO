
Set oWS = CreateObject("WScript.Shell")
sLinkFile = CreateObject("Scripting.FileSystemObject").GetParentFolderName(WScript.ScriptFullName) & "\BattleSimulator.lnk"
Set oLink = oWS.CreateShortcut(sLinkFile)
oLink.TargetPath = "javaw.exe"
oLink.Arguments = "-jar BattleSimulator.jar"
oLink.WorkingDirectory = CreateObject("Scripting.FileSystemObject").GetParentFolderName(WScript.ScriptFullName)
oLink.Description = "RPG Battle Simulator"
oLink.IconLocation = "java.exe,0"
oLink.Save

WScript.Echo "✓ Shortcut created: BattleSimulator.lnk"
WScript.Echo ""
WScript.Echo "Double-click BattleSimulator.lnk to launch the game!"
