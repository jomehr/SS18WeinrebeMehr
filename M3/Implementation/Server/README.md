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

#### Use Case: "Kassenzettel erstellen"
* POST-Anfrage mit REST-Client auf 'http://localhost:8081/receipt' mit Body (besipielsweise):  
```
"owner": "5aef1438538d242004cfa306",                        #ID des Gläubigers
  "store": "Kaufland",
  "imagePath": "folgt",
  "article": [
    {
        "name": "Schinken",
        "price": 1.2,                                       #Einzelpreis
        "participation": [{
            "participant":"5aef13ec0ce6cd04cc6935eb",       #ID des Schuldners
            "percentage": 0.2                               #prozentualer Verbrauch
            },
            {
            "participant":"5aef14d6538d242004cfa307",
            "percentage": 0.8
        }],
        "amount": 2,
        "priceAll": 2.4
    },
    {
            "name": "Käse",
            "price": 2.1,
            "participation": [{
                "participant":"5aee3db751bfa727f804afca",
                "percentage": 0.4
                },
                {
                "participant":"5aef14e8ad03c72a307fa672",
                "percentage": 0.6
            }],
            "amount": 2,
            "priceAll": 4.2
        }
  ],
  "total": 6.6,   #folgende Angaben sind nicht nötig aber möglich
  "payment": 10,
  "change": 3.4
 }
```
* GET-Anfrage auf 'http://localhost:8081/receipt' liefert alle exisiterenden Kassenzettel mit ihren Daten  
* GET-Anfrage auf 'http://localhost:8081/receipt/{receiptId}' liefert nur den geforderte Kassenzettel  

Nach dem gleichen Schema funktionieren alle Routen

#### Use Case: Abrechnung vollziehen
* PubSub Test-Client starten
```
cd ../Client
node pubsubClient.js
```
Zuerst muss eine Abrechnung existieren:
* POST-Anfrage 'http://localhost:8081/settlement' mit Body (beispielsweise): 
```
{
    "creditor": "5aee1cdd2a47720b64b13c31",
    "receipts": [
        "5af2f5d2bea66a11dcd65157",
        "5af85de0d9f7511fa0066f8e"
    ]
}
 ```
 * creditor referenziert den Gläubiger und receipts die Kassenzettel, welche für die Kalkulation der Abrechnung benötigt werden  
 * In der Antwort des Servers ist die ID der Abrechnung herauzulesen
 * PATCH-Anfrage (kein Body benötigt) auf 'http://localhost:8081/settlement/{settlementId}/calc' startet den Abrechnungsalgorithmus
 * Der Algorithmus aktualisiert die Abrechnung automatisch mit einem Array aus Schuldnern und den zu zahlenenden Beträgen
 * Der subscribende Client erhält Nachrichten mit Angaben der Schuldnerid und den für den jeweiligen Artikel  zu zahlenden Betrag
 * Der subscribende Client published daraufhin dem Server, dass die Abrechnung aktzeptiert wurde.