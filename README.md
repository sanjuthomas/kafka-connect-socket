### Kafka Connect Socket 

Kafka Connect Socket read messages over TCP Socket. This connector can either accept connection from clients, or it can connect to a server. 

#### How to build the project
mvn clean install 

#### How to deploy the connector 

Copy <<project_home>>/target/kafka-connect-socket-1.0-SNAPSHOT-shaded.jar to <<kafka_home>>/lib </br>
Copy <<project_home>>/etc/*.properties to <<kafka_home>>/config

#### How to start the standalone connector in the server mode

<<kafka_home>>/bin/connect-standalone.sh config/connect-standalone.properties config/[server-socket-source.properties](https://github.com/sanjuthomas/kafka-connect-socket/blob/master/etc/server-socket-source.properties)

#### How to start the standalone connector in the client mode

<<kafka_home>>/bin/connect-standalone.sh config/connect-standalone.properties config/[client-socket-source.properties](https://github.com/sanjuthomas/kafka-connect-socket/blob/master/etc/client-socket-source.properties)

In the client mode, the connector will try to reconnect every minute by default if the TCP Server is not up. You can change that interval on the [client-socket-source.properties](https://github.com/sanjuthomas/kafka-connect-socket/blob/master/etc/client-socket-source.properties)

#### Limitations
1. No support for distributed mode
2. No support for more than one task thread

#### Questions
Create an issue [here](https://github.com/sanjuthomas/kafka-connect-socket/issues) <br/>
Send a note to kafka@sanju.org