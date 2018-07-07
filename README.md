# EIS_SS18_WeinrebeMehr
Dies ist das Repository von Armin Weinrebe und Jan Mehr für das Projekt "Entwicklung interaktiver Systeme" im Sommersemester 2018 an der TH Köln.

## Vorraussetzungen

### Server

#### Node.js und NPM
Es werden die aktuellsten Version von Node.js (derzeit 9.11.1) und NPM (derzeit 6.1.0) benötigt um den Server ausführen zu können.  
Download: https://nodejs.org/en/download/ (beinhaltet NPM)

#### MongoDB
Für die Datenbank wird der aktuellste MongoDB Community Edition Treiber benötigt (derzeit 3.6.4).  
Download: https://www.mongodb.com/download-center?jmp=nav#community  
Die Datei mongod.exe muss ausgeführt werden und im Hintergrund laufen bevor der Server sich mit der Datenbank verknüpfen kann.  

#### Command-Terminal
Um die Befehle von Node, NPM oder git ausführen zu können wird ein Terminal benötigt. Wir verwenden Git Bash für Windows.  
Download: https://git-scm.com/downloads  

#### REST-Client
Um den Server, ohne den in M2 folgenden Client, testen zu wollen, ist ein REST-Client nötig. Wir verwenden Insomnia.  
Download: https://insomnia.rest/download/

#### Clone
Klone das gesamte Repository mit folgendem Befehl:
```
git clone https://github.com/jomehr/EIS_WeinrebeMehr.git
```

#### Installation
Weitere serverspezifische Installationschritte sind in der Readme-Datei im Unterordner "M3/Implementation/Server" zu finden"  
```
cd M3/Implementation/Server
```

### Client

####Java
Es wird die Java Version 10 verwendet.

####Android SDK
Um die Anwendung auf einem Android-Gerät installieren zu können, wird eine mind. SDK von 21 (aka Android 5.0 Lollipop) benötigt.  
Die Ziel SDK ist 27 (aka Android 8.1 Oreo). Es wurde hauptsächlich auf Android 6 Marshmallow getestet.  

####Android Studio
Um die Anwendung auf einem echten Gerät oder einer VM installieren zu können, muss ein .apk-File erstellt werden. Dafür wird Android Studio benötigt.  
Download: https://developer.android.com/studio/  

Nachdem Java und die benötigten SDK installiert wurden, kann auf dem verbunden mobilen Endgerät oder der VM, 
die Anwendung mit Android Studio gestartet werden (die benötigten dependencies werden automatisch installiert).  

#### Installation
Weitere clientspezifische Installationschritte sind in der Readme-Datei im Unterordner "M3/Implementation/Client" zu finden"  
```
cd M3/Implementation/Client
```