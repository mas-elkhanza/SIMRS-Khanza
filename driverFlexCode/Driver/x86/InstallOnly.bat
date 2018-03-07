@echo off

msiexec /i Setup.msi /qn /l*v c:\msi.trace

REM Comment out the line above and uncomment one of the following lines
REM to install only the feature desired.

REM msiexec /i Setup.msi ADDLOCAL="FpRecLibs" /qn /l*v c:\msi.trace
REM msiexec /i Setup.msi ADDLOCAL="ThinClientRDP" /qn /l*v c:\msi.trace
REM msiexec /i Setup.msi ADDLOCAL="ActiveXFpRecLibs" /qn /l*v c:\msi.trace
REM msiexec /i Setup.msi ADDLOCAL="ActiveXThinClientRDP" /qn /l*v c:\msi.trace
REM msiexec /i Setup.msi ADDLOCAL="dotNETFpRecLibs" /qn /l*v c:\msi.trace
REM msiexec /i Setup.msi ADDLOCAL="dotNETThinClientRDP" /qn /l*v c:\msi.trace
REM msiexec /i Setup.msi ADDLOCAL="JavaFpRecLibs" /qn /l*v c:\msi.trace
REM msiexec /i Setup.msi ADDLOCAL="JavaThinClientRDP" /qn /l*v c:\msi.trace

REM Pass /norestart to the msiexec command to disable reboot after 
REM installation.

@pause

REM this batch file will perform a silent install