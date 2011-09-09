set FB2PDF_HOME=D:\Program Files\fb2pdf\

cd /d "%FB2PDF_HOME%" 
call fb2pdf.cmd -l conversion.log "%1" 
del "%1"

echo done
exit
