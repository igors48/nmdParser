rem  This script takes care of creating the nmd classpath.

If %OS%'==Windows_NT' Set NTSwitch=/F "Tokens=*"
If %OS%'==WINNT' Set NTSwitch=/F "Tokens=*"
For %NTSwitch% %%V In (%1) Do set NMD_CLASSPATH=%NMD_CLASSPATH%;%%V


