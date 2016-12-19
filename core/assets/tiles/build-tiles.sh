#!/usr/bin/sh
pushd `pwd`

echo Bash version of this script is not tested.
echo You need to have imagemagick installed to the system first.

echo Build city tiles...
pushd city
mogrify -path out-city -gravity center -background none -extent 100x80 raw\*.png 
montage -background none -mode concatenate -tile 3x out-city\*.png ..\city.png
popd

popd