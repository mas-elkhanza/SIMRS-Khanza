@echo off
msiexec /x Setup.msi /qn /l*v c:\msi.trace
@pause