;NSIS Modern User Interface
;Start Menu Folder Selection Example Script
;Written by Joost Verburg


;--------------------------------
;Include Modern UI

  !include "MUI2.nsh"
  !include "ZipDLL.nsh"


;--------------------------------
;General

  ;Name and file
   Name "Instalacion APP Hotel"
  OutFile "APP_Hotel.exe"

  ;Default installation folder
  InstallDir "$LOCALAPPDATA\Ruta_instaladores_DI"
  
  ;Get installation folder from registry if available
  InstallDirRegKey HKCU "Software\Modern UI Test" ""

  ;Request application privileges for Windows Vista
  RequestExecutionLevel user

;--------------------------------
;Variables

  Var StartMenuFolder

;--------------------------------
;Interface Settings


  !define MUI_HEADERIMAGE "RecursosScripts\hotel.bmp"
  !define MUI_HEADERIMAGE_BITMAP "RecursosScripts\hotel.bmp"
  !define MUI_ABORTWARNING
;--------------------------------
;Pages

  !define MUI_WELCOMEPAGE_TITLE "INSTALACION DE APP Hotel"
  !define MUI_WELCOMEPAGE_TEXT "Lea los terminos del programa y elija configuracion"

  !insertmacro MUI_PAGE_WELCOME

  !insertmacro MUI_PAGE_LICENSE "${NSISDIR}\Docs\Modern UI\License.txt"
  !insertmacro MUI_PAGE_COMPONENTS
  !insertmacro MUI_PAGE_DIRECTORY
  
  ;Start Menu Folder Page Configuration
  !define MUI_STARTMENUPAGE_REGISTRY_ROOT "HKCU" 
  !define MUI_STARTMENUPAGE_REGISTRY_KEY "Software\Modern UI Test" 
  !define MUI_STARTMENUPAGE_REGISTRY_VALUENAME "Start Menu Folder"
  
  !insertmacro MUI_PAGE_STARTMENU Application $StartMenuFolder
  
  !insertmacro MUI_PAGE_INSTFILES
  
  !insertmacro MUI_UNPAGE_CONFIRM
  !insertmacro MUI_UNPAGE_INSTFILES

;--------------------------------
;Languages
 
  !insertmacro MUI_LANGUAGE "English"

;--------------------------------
;Installer Sections



Section "Programa APP Hotel" SecDummy

  SetOutPath "$INSTDIR"
  
  ;ADD YOUR OWN FILES HERE...
  File "RecursosScripts\APP_Hotel_Instalable.zip"
  !insertmacro ZIPDLL_EXTRACT "$INSTDIR\APP_Hotel_Instalable.zip" "$INSTDIR" "<ALL>"


  
  ;Store installation folder
  WriteRegStr HKCU "Software\Modern UI Test" "" $INSTDIR
  
  ;Create uninstaller
  WriteUninstaller "$INSTDIR\Uninstall.exe"

  ;Quita el ZIP
   Delete "$INSTDIR\APP_Hotel_Instalable.zip"
  
  !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
    
    ;Create shortcuts
    CreateDirectory "$SMPROGRAMS\$StartMenuFolder"
    CreateShortcut "$SMPROGRAMS\$StartMenuFolder\Uninstall.lnk" "$INSTDIR\Uninstall.exe"

 
  
  !insertmacro MUI_STARTMENU_WRITE_END

SectionEnd



;--------------------------------
;Descriptions

  ;Language strings
  LangString DESC_SecDummy ${LANG_ENGLISH} "A test section."

  ;Assign language strings to sections
  !insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
    !insertmacro MUI_DESCRIPTION_TEXT ${SecDummy} $(DESC_SecDummy)
  !insertmacro MUI_FUNCTION_DESCRIPTION_END
 
;--------------------------------
;Uninstaller Section

Section "Uninstall"

  ;ADD YOUR OWN FILES HERE... 

  Delete "$INSTDIR\Uninstall.exe"
  Delete "$INSTDIR\APPHotelFirmada.jar"

  RMDir /r "$INSTDIR\lib"

  RMDir "$INSTDIR"
  
  !insertmacro MUI_STARTMENU_GETFOLDER Application $StartMenuFolder
    
  Delete "$SMPROGRAMS\$StartMenuFolder\Uninstall.lnk"



  RMDir "$SMPROGRAMS\$StartMenuFolder"
  
  DeleteRegKey /ifempty HKCU "Software\Modern UI Test"

SectionEnd