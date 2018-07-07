# Android Client

## Vorraussetzungen

Die Java Version, die Android SDK und Android Studio müssen installiert worden sein.  

## Konfigurationsschritte
Je nachdem in welchem Netzwerk der Server läuft, muss die IP-Adresse in RestClient.java geändert werden.  
Es muss die gleiche IP-Adresse sein, auf welcher der Server läuft:  
```private static final String BASE_URL = "http://XXX.XXX.XXX.XXX:1337/";```  

Falls nicht mit den DB_Backup-Daten gearbeitet wird und neue Daten erstellt werden wollen, müssen in TabCreate.java bestimmte Zeilen angepasst werden:

* In Zeile 80 muss jedes Mitglied der Gruppe in das Array eingefügt werden:  
```dataUser.add(new UserData({UserId}, {UserName}, {GroupId));```  
* In Zeile 169 muss die UserId des derzeit "angemeldeten" Nutzers eingefügt werden.
* In Zeile 188 muss die GroupId der Gruppe eingefügt werden:  
```  receiptObject.put("group", {GroupId});```  
