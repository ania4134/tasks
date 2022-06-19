goto runcrud

:runcrud
call runcrud.bat
if "%ERRORLEVEL%" == "0" goto webbrowser
echo.
echo Runcrud file has errors.
goto fail

:webbrowser
start "chrome" http://localhost:8080/crud/v1/tasks
if if "%ERRORLEVEL%" == "0" goto end
echo.
echo Web browser cannot be open.

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.