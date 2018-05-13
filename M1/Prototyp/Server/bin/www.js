/*
Diese Datei ist für die Verbindung und Kommunikation über HTTP, HTTPS etc. zuständig.
Für eine andere Art der Kommunikation nutze eine andere Datei.
 */

const app  = require("../app"),
    http = require("http"),
    mongoose = require("mongoose"),
    faye = require("faye");

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

//faye pubsub
let bayeux = new faye.NodeAdapter({mount: "/test", timeout: 20});
bayeux.attach(server);

server.listen(port,  () => {
    console.log("Server running on http:/" + hostname + ":" + port)
});

//faye listener
bayeux.on('subscribe', function(clientId, channel) {
    console.log('[SUBSCRIBE] ' + clientId + ' -> ' + channel);
});

bayeux.on('unsubscribe', function(clientId, channel) {
    console.log('[UNSUBSCRIBE] ' + clientId + ' -> ' + channel);
});

bayeux.on('disconnect', function(clientId) {
    console.log('[DISCONNECT] ' + clientId);
});
