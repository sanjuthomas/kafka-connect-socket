#### Kafka Connect Socket 

Kafka Connect Socket read messages over TCP Socket. This connector can either accept connection from clients, or it can connect to a server. 

#### How to start in server mode

<kafka_home>/bin/connect-standalone.sh config/connect-standalone.properties config/[server-socket-source.properties] () 

#### How to start in client mode

/bin/connect-standalone.sh config/connect-standalone.properties config/[client-socket-source.properties] ()

In the client mode, the connector expect the server socket to be available before you start the connector.

