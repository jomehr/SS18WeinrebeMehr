/*
Diese Datei ist für die Verbindung und Kommunikation über HTTP, HTTPS etc. zuständig.
Für eine andere Art der Kommunikation nutze eine andere Datei.
 */

const app  = require("../app"),
    http = require("http"),
    mongoose = require("mongoose");

const localHost = "127.0.0.1";
const localWlan = "192.168.0.172";
const mobileHotspot = "192.168.43.1";
const port = process.env.PORT || 1337;

let server = http.createServer(app);

//connect mongodb with mongoose plugin
mongoose.connect("mongodb://localhost/db");
let db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
    console.log('Successfully connected to database!');
});

server.listen(port,  () => {
    console.log("Server running on http:/" + localWlan + ":" + port)
});
