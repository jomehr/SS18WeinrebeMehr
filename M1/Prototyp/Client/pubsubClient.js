const faye = require("faye");

let client = new faye.Client("http://localhost:8081/test");

let subscription = client.subscribe('/settlement/*', function(message) {
    console.log(message);

    let publication = client.publish('/user/' + message.user, {
        user: message.user,
        message: "Abrechnung angenommen"
    });

    publication.callback(function() {
        console.log('[PUBLISH SUCCEEDED]');
    });
    publication.errback(function(error) {
        console.log('[PUBLISH FAILED]', error);
    });
});

subscription.callback(function() {
    console.log('[SUBSCRIBE SUCCEEDED]');
});
subscription.errback(function(error) {
    console.log('[SUBSCRIBE FAILED]', error);
});

client.bind('transport:down', function() {
    console.log('[CONNECTION DOWN]');
});
client.bind('transport:up', function() {
    console.log('[CONNECTION UP]');
});
