---
title: Installation
permalink: /installation/
layout: single
---

## Using the Windows Installer
[Download the installer](https://github.com/GazePlay/GazePlay/releases/download/1.7.0/GazePlayInstaller.exe) and double click to open it. You will be guided through the installer, and you can then access GazePlay by the desktop icon or through the Start Menu.

<div id="navigation"></div>
<div id="listing"></div>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript">
var S3BL_IGNORE_PATH = true;
var BUCKET_NAME = 'gazeplay-dist';
var BUCKET_URL = 'https://gazeplay-dist.s3.eu-west-3.amazonaws.com';
var S3B_ROOT_DIR = 'snapshots';
var S3B_SORT = 'NEW2OLD';
var EXCLUDE_FILE = 'index.html';  // change to array to exclude multiple files
var AUTO_TITLE = true;
// var S3_REGION = 's3'; // for us-east-1
</script>
<script type="text/javascript" src="https://rufuspollock.github.io/s3-bucket-listing/list.js"></script>


## Advanced - Unpacking the .zip
You can also install GazePlay by downloading [the zip file]({{ site.zip_url }}) and extracting the contents to a safe location on your computer. You can then launch GazePlay by running; 
* `gazeplay-windows.bat` on Windows
* `gazeplay-macos.sh` on MacOS
* `gazeplay-linux.sh` on Linux 

### Problems?
Read our [FAQ]({{ site.baseurl }}/faq/) for help.

### Previous Versions
You can find previous versions of GazePlay on [the releases page](https://github.com/GazePlay/GazePlay/releases)
