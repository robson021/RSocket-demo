call mvnw.cmd clean
call mvnw.cmd install -pl common
if %errorlevel% neq 0 exit /b %errorlevel%
:: run all apps
start cmd /c mvnw.cmd spring-boot:run -pl rsocket-server
timeout 2
start cmd /c mvnw.cmd spring-boot:run -pl webapp