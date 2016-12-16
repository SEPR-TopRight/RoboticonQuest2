@echo off
pushd %~dp0
call imagemagick.cmd

echo Build city tiles...
pushd city
mogrify -path out-city -gravity center -background none -extent 100x80 raw\*.png 
montage -alpha set -background none -mode concatenate -tile 3x out-city\*.png png32:..\city.png
popd

popd