/*
Diese Datei ist für die Verbindung und Kommunikation über HTTP, HTTPS etc. zuständig.
Für eine andere Art der Kommunikation nutze eine andere Datei.
 */

const app  = require("../app"),
    http = require("http"),
    mongoose = require("mongoose");

//TODO implementiere eine andere ip, so dass sich echte Clients direkt verbinden können. Zurzeit funktioniert nur ein Emulator mit localhost-ip.
const hostname = "127.0.0.1";
const port = process.env.PORT || 8081;

let server = http.createServer(app);

//connect mongodb with mongoose plugin
mongoose.connect("mongodb://localhost/db");
let db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
    console.log('Successfully connected to database!');
});

server.listen(port,  () => {
    console.log("Server running on http:/" + hostname + ":" + port)
});
