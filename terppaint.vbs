'Installer Script for TerpPaint 4.0

On Error Resume Next

Dim WshShell: Set WshShell = WScript.CreateObject("WScript.Shell")
Dim fso: Set fso = CreateObject("Scripting.FileSystemObject")
Dim projpath, binpath, fisrcpath, srcpath, mainclass
Dim files, rc, javapath, javacpath, objIE, strIETitle, blnFlag

mainclass="TerpPaint"
binpath="bin"
srcpath="src\java"
fisrcpath="src\java-fault_injected"
junitfilepath="testing\junit\java"
outputpath="output"
files="*.java"
'javapath="C:\Program Files\Java\jdk1.5.0_02\bin\"
'javacpath="C:\Program Files\Java\jdk1.5.0_02\bin\"

' 追加
' javacpath = "C:\Program Files\Java\jdk-23\bin\"
javacpath = "C:\Program Files (x86)\Java\jdk1.5.0_02\bin\"


'------------------------------------------------------------------------------
Sub Compile(javafilespath)
   On Error Resume Next
   
   'Test javac
   WshShell.Run """" & javacpath & "javac.exe""", 0, true
   If Err.Number <> 0 Then
'      javacpath = InputBox ("Please enter the path to javac.exe.  To " & _
'         "this message, add the path to javac.exe to you PATH environment " & _
'	 "variable or enter the path in the javacpath variable in this " & _
'	 "script.", "Path to the Java Compiler", "Enter path here")
      MsgBox "You must install javac to compile TerpPaint." & _
         "If javac is installed, make sure to add javac to your PATH" & _
         "or to the javacpath environment variable." & _
	 "You may download javac from java.sun.com.", 0, "Error: Missing javac"
      Wscript.Quit(1)
   End If

   fso.CreateFolder fso.GetAbsolutePathName(binpath)
   fso.CopyFile fso.GetAbsolutePathName(srcpath & "\prefs.txt"), _
   	fso.GetAbsolutePathName(binpath & "\prefs.txt")
   fso.CopyFile fso.GetAbsolutePathName(srcpath & "\windowRegistry.txt"), _
   	fso.GetAbsolutePathName(binpath & "\windowRegistry.txt")
   fso.CopyFile fso.GetAbsolutePathName(junitfilepath & "\123.bmp"), _
        fso.GetAbosultePathName(binpath * "\123.bmp")
   fso.CopyFile fso.GetAbsolutePathName(junitfilepath & "\test.gif"), _
        fso.GetAbosultePathName(binpath * "\test.gif")
   fso.CopyFolder fso.GetAbsolutePathName(srcpath & "\help"), _
   	fso.GetAbsolutePathName(binpath & "\help")
   fso.CopyFolder fso.GetAbsolutePathName(srcpath & "\images"), _
   	fso.GetAbsolutePathName(binpath & "\images")
   rc = Wshshell.run ("""" & javacpath & "javac.exe"" -nowarn -encoding UTF-8 -d " & _
      binpath & " -sourcepath " & javafilespath &  " " & _
      javafilespath & "\" & files, 0, true)

   If rc <> 0 Then
      MsgBox "Please download the most recent release from " & _
         "terppaint.terpoffice.com", 0, "Error"
   Else
      If javafilespath = srcpath Then
         MsgBox "TerpPaint was successfully compiled. Please " & _
         "double-click " & "on terppaint.vbs or run wscript terppaint.vbs " & _
         " or cscript terppaint.vbs to start TerpPaint", 0, "Compiled"
      End If
   End If

End Sub


'------------------------------------------------------------------------------
Sub CompileJUnit(path)
   On Error Resume Next

   WshShell.Run """" & javacpath & "javac.exe"" -d " & binpath & " -classpath " & _
      binpath & ";junit.jar " & path & "\*.java", 0, true

End Sub


'------------------------------------------------------------------------------
Sub Run()
   On Error Resume Next
	
   'Test java
   WshShell.Run """" & javapath & "java.exe""", 0, true
   If Err.Number <> 0 Then
'      javapath = InputBox ("Please enter the path to java.exe.  To " & _
'         "this message, add the path to java.exe to you PATH environment " & _'
'	 "variable or enter the path in the javapath variable in this " & _
'	 "script.", "Path to the Java", "Enter path here")
      MsgBox "You must install java to run TerpPaint." & _
  	 "If java is installed, make sure to add java to your PATH" & _
	 "or to the javapath environment variable." & _
	 "You may download java from java.sun.com", 0, "Error: Missing java"
      Wscript.Quit(1)
   End If
   
   CheckVersion
   
   'Change directory to binpath and run the mainclass
   WshShell.CurrentDirectory = binpath
   rc = WshShell.Run ("""" & javapath & "java.exe"" " & _
       mainclass, 0, true)

   If rc <> 0 Then
      MsgBox "Make sure you have run compile first", 0, "Error"
   End If
End Sub


'------------------------------------------------------------------------------
Sub Clean()
   On Error Resume Next
	
   WshShell.Run "%comspec% /c del " & binpath & "\*.class", 0, true
   WshShell.Run "%comspec% /c del " & binpath & "\*.txt", 0, true
   fso.DeleteFolder fso.GetAbsolutePathName(binpath & "\help")
   fso.DeleteFolder fso.GetAbsolutePathName(binpath & "\images")
   fso.DeleteFolder fso.GetAbsolutePathName(fisrcpath)

   MsgBox "Finished cleaning", 0, "TerpPaint -- Clean"
End Sub





'------------------------------------------------------------------------------
Sub InjectFaults()
   On Error Resume Next
   Const ForReading = 1, ForWriting = 2, ForAppending = 8
   
   Dim reJavaFiles: Set reJavaFiles = new RegExp
   Dim reTestFile: Set reTestFile = new RegExp
   Dim junitFileSteam, srcFileStream, fisrcFileStream, line, oldLine, testnum
   Dim srcFolder: Set srcFolder = fso.GetFolder(fso.GetAbsolutePathName(srcpath))
   Dim srcFiles: Set srcFiles = srcFolder.files
   Dim junitFolder: Set junitFolder = fso.GetFolder( _ 
                                      fso.GetAbsolutePathName(junitfilepath))
   Dim junitFiles: Set junitFiles = junitFolder.files


   'Create a display using IE to output progress
   strIETitle = "TerpPaint -- Inject Faults" & String(40, "-")
   blnFlag = True
   InitIE "Getting unmodified source..."

   'Defn of regex's
   reJavaFiles.Pattern = "(.*)\.java$"
   reJavaFiles.IgnoreCase = True
   reTestFile.Pattern = ".*/\*\s*TESTING::\s*(\S*)\s*\*/.*$"
   reTestFile.Global = False
   
   'Get a clean copy of all of the java files from the srcpath
   fso.CreateFolder fso.GetAbsolutePathName(fisrcpath)
   For Each srcFile in srcFiles
      If reJavaFiles.Test(srcFile.name) Then
	 fso.DeleteFile fso.GetAbsolutePathName(fisrcpath & _
	                                               "\" & srcFile.name)
         fso.CopyFile srcFile, fso.GetAbsolutePathName(fisrcpath & _
	                                               "\" & srcFile.name)
      End If
   Next
   
   'Compile the JUnit files
   MsgIE "Compiling JUnit files"
   CompileJUnit(junitfilepath)
   MsgIE "Done Compiling.  Moving onto fault injection..."
   
   'Looks at the first line of the junit test to find out which file it's testing
   'then searches for the FUALTS, inserts the fault, runs the test on the compiled
   'code, then replaces the injected fault with the original code.  However,
   'it does not readd the /*FAULT:: */ stuff so next time it will go to the next
   'fault.  If you don't understand what I just said then RTFS.
   For Each junitFile in junitFiles
      If reJavaFiles.Test(junitFile.Name) Then
         currClass = reJavaFiles.Replace(junitFile.Name, "$1")
         MsgIE currClass
         'Get the first line of the JUnit test to find out what we are testing
         Set junitFileStream = fso.OpenTextFile(junitFile)
         testFile = junitFileStream.ReadLine
         If reTestFile.Test(testFile) Then
            testFile = reTestFile.Replace(testFile, "$1")
            junitFileStream.Close
      
            'Inject 1 fault in the file, test, and repeat for each fault
            srcFile = fso.GetFile(fso.GetAbsolutePathName(fisrcpath & "\" & testFile)) 
            testnum = 0
            Do While InjectOneFault(srcFile)
   	       MsgIE currClass & " -- Fault #" & testnum & VbCrLf & _
	          "Compiling source with one fault"
	       Compile(fisrcpath)
	       MsgIE currClass & " -- Fault #" & testnum & VbCrLf & _
	          "Running JUnit tests"
	       JUTest currClass, testnum
	       testnum = testnum + 1
            Loop
         End If
      End If
   Next

   ' Close display box
   Wscript.Sleep 2000
   MsgIE "IE_Quit"

   ' Clean Up.
   Set objIE = Nothing
   Set objShell = Nothing
   MsgBox "Finished testing each fault", 0, "TerpPaint -- Inject Faults"
End Sub


'------------------------------------------------------------------------------
Sub JUTest(file, num)
   On Error Resume Next

   fso.CreateFolder outputpath
   Set outFile = fso.CreateTextFile(fso.GetAbsolutePathName(outputpath) & "\" & _
      file & num & ".txt", True)
   launchMod = """" & javapath & "java.exe "" -classpath .;..\junit.jar " & file
   
   WshShell.CurrentDirectory = binpath
   Set objScriptExec = WshShell.Exec(launchMod)
   WshShell.CurrentDirectory = ".."
   
   timeout = 20 '10^-1 sec
   i = 0

   Do While objScriptExec.Status = 0
      wscript.sleep(100)
      i = i+1
      If i > timeout Then
         objScriptExec.terminate()
      End If
   Loop
   contents = objScriptExec.StdOut.ReadAll
   outFile.write(contents)
   outFile.close
End Sub


'------------------------------------------------------------------------------
Function InjectOneFault(File)
   On Error Resume Next
   Dim reFaults: Set reFaults = new RegExp
   Dim reCorrect: Set reCorrect = new RegExp
   reFaults.Pattern = "(.*)/\*FAULT::(.*)\*/(.*)$"
   reFaults.Global = False
   reCorrect.Pattern = ".*/\*CORRECT::(.*)\*/.*$"
   reCorrect.Global = False
   Set FileStream = fso.OpenTextFile(File)

   'Use a temp file to store the contents that the current file will contain
   Set tmpFileStream = fso.CreateTextFile( File & "~" , True)
   InjectOneFault = False
   Do While not FileStream.AtEndOfStream
      line = FileStream.ReadLine
      line = reCorrect.Replace(line, "$1")
      If reFaults.Test(line) Then
         line = reFaults.Replace(line, "$2 /*CORRECT:: $1$3 */")
         tmpFileStream.WriteLine(line)
         tmpFileStream.WriteLine(FileStream.ReadAll)
         InjectOneFault = True
	 Exit Do
      Else
         tmpFileStream.WriteLine(line)
      End If
   Loop

   MsgIE "Done fault injecting"
   FileStream.Close
   tmpFileStream.Close
   fso.DeleteFile File
   fso.MoveFile File & "~", File

End Function
		
      

'------------------------------------------------------------------------------
Sub CheckVersion()
   skey = "HKLM\SOFTWARE\JavaSoft\Java Development Kit\CurrentVersion" 
   skey = wshshell.regread(skey) 
   skey = Left(skey,3) 
   if skey < 1.4 then 
      msgbox "Current version of Java (" & skey & ") is insufficient. " &_ 
             "Please download the latest version of Java available at java.sun.com" 
      Wscript.Quit(1)
   End If
End Sub   


'------------------------------------------------------------------------------
Sub RunJunit()
   On Error Resume Next
   Const ForReading = 1, ForWriting = 2, ForAppending = 8
   
   Dim reJavaFiles: Set reJavaFiles = new RegExp
   Dim junitFolder: Set junitFolder = fso.GetFolder( _ 
                                      fso.GetAbsolutePathName(junitfilepath))
   Dim junitFiles: Set junitFiles = junitFolder.files 

   'Defn of regex's
   reJavaFiles.Pattern = "(.*)\.java$"
   reJavaFiles.IgnoreCase = True
	
   'Create a display using IE to output progress
   strIETitle = "TerpPaint -- Run Junit" & String(40, "-")
   blnFlag = True
   InitIE "Compiling source..."

   Compile(srcpath)

   MsgIE "Compiling JUnit files"
   CompileJUnit(junitfilepath)
   
   MsgIE "Running JUnit tests"
   For Each junitFile in junitFiles
      If reJavaFiles.Test(junitFile.Name) Then
         currClass = reJavaFiles.Replace(junitFile.Name, "$1")
         MsgIE currClass

	 JUTest currClass, ""
      End If
   Next
   

   ' Close display box
   Wscript.Sleep 2000
   MsgIE "IE_Quit"

   ' Clean Up.
   Set objIE = Nothing
   Set objShell = Nothing
   MsgBox "Finished running the JUnit tests.  The outputs are in " & _
      outputpath & " under <the name of the tests>.txt",  _
      0, "TerpPaint -- Run JUnit"
End Sub


'------------------------------------------------------------------------------
Sub MsgIE(strMsg)
' Subroutine to display message in IE box and detect when the
' box is closed by the program or the user.
  On Error Resume Next
  If strMsg = "IE_Quit" Then
    blnFlag = False
    objIE.Quit
  Else
    objIE.Document.Body.InnerText = strMsg
    If Err.Number <> 0 Then
      Err.Clear
      blnFlag = False
      WScript.Echo "Program Aborted"
      WScript.Quit
    End If
    objShell.AppActivate strIETitle
  End If
End Sub

'------------------------------------------------------------------------------
Sub InitIE(strMsg)
' Subroutine to initialize the IE display box.
  Dim intWidth, intHeight, intWidthW, intHeightW
  Set objIE = CreateObject("InternetExplorer.Application")
  With objIE
    .ToolBar = False
    .StatusBar = False
    .Resizable = False
    .Navigate("about:blank")
    Do Until .readyState = 4
      Wscript.Sleep 100
    Loop
    With .document
      With .ParentWindow
        intWidth = .Screen.AvailWidth
        intHeight = .Screen.AvailHeight
        intWidthW = .Screen.AvailWidth * .40
        intHeightW = .Screen.AvailHeight * .05
        .resizeto intWidthW, intHeightW
        .moveto (intWidth - intWidthW)/2, (intHeight - intHeightW)/2
      End With
      .Write "<body> " & strMsg & " </body></html>"
      With .ParentWindow.document.body
        .style.backgroundcolor = "LightBlue"
        .scroll="no"
        .style.Font = "10pt 'Courier New'"
        .style.borderStyle = "outset"
        .style.borderWidth = "4px"
      End With
      .Title = strIETitle
      objIE.Visible = True
      Wscript.Sleep 100
      objShell.AppActivate strIETitle
    End With
  End With
End Sub

'------------------------------------------------------------------------------
'Begin Main
Set objArgs = WScript.Arguments
If objArgs.length <> 0 Then
   If objArgs(0) = "compile" Then
      Call Compile(srcpath)
   Else If objArgs(0) = "clean" Then
      Clean
   Else If objArgs(0) = "run" Then
      Run
   Else If objArgs(0) = "injectfaults" Then
      InjectFaults
   Else If objArgs(0) = "runjunit" Then
      RunJunit
   Else
      MsgBox "USAGE: wscript terppaint.vbs [run] " &_
             "wscript terppaint.vbs compile|clean [file [file file ...]]"
      rc = 1
   End If
   End If
   End If
   End If
   End If
Else
   Run
End If

WScript.Quit(rc)
