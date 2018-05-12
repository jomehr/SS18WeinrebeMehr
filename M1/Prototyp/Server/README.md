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

Der Server sollte nun in Betrieb sein und in der Konsole bestätigen, dass
* er auf http://localhost:8081 ansprechbar ist
* und sich erfolgreich mit MongoDB verknüpft hat

## Nutzung
Der Server orientiert sich an der REST-Architektur und implementiert die HTTP CRUD-Methoden POST, GET, PATCH und DELETE  

Zurzeit folgen die Routen noch dem Schema /{model}/{modelId}.  
Siehe: https://github.com/jomehr/SS18WeinrebeMehr/tree/master/M1/Prototyp/Server/routes  

Das Template {model} basiert auf den in der Datenbank eingesetzten Modellen.  
Siehe: https://github.com/jomehr/SS18WeinrebeMehr/tree/master/M1/Prototyp/Server/models   

### Beispiele

#### Use Case: "Gruppe erstellen"
* POST-Anfrage mit REST-Client auf 'http://localhost:8081/group' mit Body:  
```
{
    "name": "Testgruppe X",
    "creator": "5aee1cdd2a47720b64b13c31",
    "participants": [
        "5aef14d6538d242004cfa307",
        "5aef18726e8b491228866cf8"
    ]
}
```
* in "creator" und "participants" werden jeweils IDs von Nutzern gespeichert, mit denen sie referenziert werden können.  
Zusätzliche Einträge sind möglich, solange sie sich an das Schema aus models/group.js halten.  
* GET-Anfrage auf 'http://localhost:8081/group' liefert alle exisiterenden Gruppen mit ihren Daten  
* GET-Anfrage auf 'http://localhost:8081/group/{groupId}' liefert nur die geforderte Gruppe  

Nach dem gleichen Schema funktionieren alle Routen

#### Use Case: Abrechnung vollziehen
Zuerst muss eine Abrechnung existieren:  
* POST-Anfrage 'http://localhost:8081/settlement' mit Body: 
```
{
    "creditor": "5aee1cdd2a47720b64b13c31",
    "receipts": [
        "5af2f5d2bea66a11dcd65157"
    ]
}
 ```
 * creditor referenziert den Gläubiger und receipts die Kassenzettel, welche für die Kalkulation der Abrechnung benötigt werden  
 * In der Antwort des Servers ist die ID der Abrechnung herauzulesen
 * PATCH-Anfrage (kein Body benötigt) auf 'http://localhost:8081/settlement/{settlementId}/calc' startet den Abrechnungsalgorithmus
 * Der Algorithmus aktualisiert die Abrechnung automatisch mit einem Array aus Schuldnern und den zu zahlenenden Beträgen