# Result

1. DOCX
 ```
 bash-3.2$ ./gradle run -PappArgs="['testDoc.docx']"
 application/vnd.openxmlformats-officedocument.wordprocessingml.document
```
 * COMMENT: docx has this very strange MIME
1. PDF
```
 bash-3.2$ ./gradle run -PappArgs="['testPdf.pdf']"
 application/pdf
 ```
1. PDF named as DOC
 ```
 bash-3.2$ ./gradle run -PappArgs="['testPdfFakeDoc.doc']"
 application/pdf
 ```
 * COMMENT: a file named incorrectly to trick tika may not succeed.
1. TIFF
```
bash-3.2$ ./gradle run -PappArgs="['testTiff.tiff']"
image/tiff
```
1. DOC
```
bash-3.2$ ./gradle run -PappArgs="['testOldDoc.doc']"
application/msword
```
