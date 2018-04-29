/*
Diese Datei ist für die Verbindung und Kommunikation über HTTP, HTTPS etc. zuständig.
Für eine andere Art der Kommunikation nutze eine andere Datei.
 */

const app  = require("../app"),
    http = require("http");

//TODO implementiere eine andere ip, so dass sich echte CLients direkt verbinden können. Zurzeit funktioniert nur ein Emulator mit localhost-ip.
const hostname = "127.0.0.1";
const port = process.env.PORT || 8081;

let server = http.createServer(app);

server.listen(port,  () => {
    console.log("Server läuft auf http:/" + hostname + ":" + port)
});
