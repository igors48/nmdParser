@echo off

if "%OS%"=="Windows_NT" @setlocal
if "%OS%"=="WINNT" @setlocal

set NMD_HOME=..

rem ----- Create CLASSPATH --------------------------------------------
set NMD_CLASSPATH=%CLASSPATH%;
cd /d "%NMD_HOME%\lib"
for %%i in ("*.jar") do call "..\bin\appendcp.bat" ".\lib\%%i"
cd /d %NMD_HOME%

rem ----- call java.. ---------------------------------------------------
javaw -Xmx512m -Dconsole.charset=cp866 -classpath "%NMD_CLASSPATH%" app.NmdIuiApplication %*