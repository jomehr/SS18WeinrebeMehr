# Server

## Vorraussetzungen
Nodejs, NPM und MongoDB müssen installiert worden sein.  
MongoDB muss im Hintergrund laufen (mongod.exe).
## Installationschritte

##### Alle benötigten NPM-Module installieren
```
npm install
```
##### Server ausführen
```
npm start
```
Je nach dem in welchem Netzwerk man sich befindet sind andere IP-Adressen (Variablen in www.js) nötig, um die Verbindung mit Clients zu ermöglichen.
* falls kein Netzwerk verwendet wird und der Client als VM auf dem Server läuft, nutze ```const localHost = "127.0.0.1"```
* falls ein echter Client im lokalen Wlan verwendet wird, nutze ```const localWlan = "192.168.0.XXX```
* falls ein echter Client mit mobilem Hotspot verwendet wird, nutze ```const mobileHotspot = "192.168.48.XXX```

Der Server sollte nun in Betrieb sein und in der Konsole bestätigen, dass
* er auf http://{ip-adresse}:{port} ansprechbar ist
* und sich erfolgreich mit MongoDB verknüpft hat

## Nutzung
Der Server orientiert sich an der REST-Architektur und implementiert die HTTP CRUD-Methoden POST, GET, PATCH und DELETE  

Die REST-Routen befinden sich hier:
https://github.com/jomehr/SS18WeinrebeMehr/tree/master/M3/Implementation/Server/routes  

Die Schemas der Datenbank befinden sich hier:
https://github.com/jomehr/SS18WeinrebeMehr/tree/master/M3/Implementation/Server/models   

Falls ein REST-Client verwendet wird, muss der body den Schemas entsprechen, um die CRUD-Methoden erfolgreich durchführen zu können.  

Falls mit dem Android-Client getestet wird, müssen im Vorfeld Nutzer und eine Gruppe in der Datenbank vorhanden sein bzw. erstellt werden, da dies auf dem Client noch nicht möglich ist.  
Dafür kann MongoDB Compass geöffnet werden. 
* Falls noch keine Datenbank mit dem Namen "db" vorhanden ist, muss diese zuerst erstellt werden.
* Dannach muss für jede Collection die aus dem Ornder "db-backups" importiert werden soll, eine leere Collection erstellt werden
    * Diese sollte den gleichen Namen haben wie das zu importierende JSON
* Abschließend klicke, wenn du dich in der leeren Collection befindest, in der oberen Navigationsleiste auf "Collection" und dann auf "Import Data"
* Wähle die zugehörige JSON aus dem Dateipfad aus und klicke auf "Import"
* Die Daten sollten nun erfolgreich importiert worden sein

Kassenzettel können jedoch auf dem Client erstellt werden. Abrechnungen werden nach dem POST eines Kassenzettels automatisch erstellt.