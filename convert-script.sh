gdalwarp -ts 30 30 -r cubic -co "TFW=YES" treecover_990.nc treecover_30pts.tif
gdal_translate -co "TILED-YES" -of GMT treecover_30pts.tif treecover_30pts.nc

