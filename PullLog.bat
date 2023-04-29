@echo 
echo --------------------adb wait-for-device--------------------
adb wait-for-device
For /F " tokens=1 delims= " %%a in ('adb get-serialno') do (
  set tmpDevSN=%%a
)
set str=%tmpDevSN%
set dates=%date:~0,4%%date:~5,2%%date:~8,2%%time:~0,2%%time:~3,2%%time:~6,2%
set file=%str%_%dates%
md %file%
echo %file%
timeout /t 2
adb root 
adb wait-for-device
adb shell setenforce 0
adb shell xdump -b all -k -s all -z
adb pull /storage/emulated/0/ylog .\%file%
adb pull /data/xdumps .\%file%
start .\%file%
pause