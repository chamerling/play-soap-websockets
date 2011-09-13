# A simple WebSockets + SOAP POC

## What
Based on SOAP sample provided by Marc Deschamps [https://github.com/marcuspocus/soap-examples/](https://github.com/marcuspocus/soap-examples/), I just added the WebSocket stuff to display SOAP requests in the browser when the service is called.

## How
You can use [SOAPUI](http://soapui.org/) and the sample project located in the test folder (MyService-wsdl-soapui-project.xml) to invoke services and see what happens on [http://localhost:9000](http://localhost:9000 "Home").

## Results
WebSocket and Play rocks! You can build really good stuff in less than 100 lines of code...

## Problems
The only problem is a conception one where you can loose messages if you launch several clients at the same time. Since the event stream can not really be shared between clients, some messages can be lost. What is amazing is that it works in some cases: To reproduce, open two browser windows on [http://localhost:9000](http://localhost:9000 "Home") and launch the SOAPUI Load Test. This light load test sends 60 requests using two threads. At the end of the test, some messages are not displayed in the browser (have a look to the counter of the last message which should be equal to '59').
A simple solution will be to create stream per client with some client ID for example: Not my goal for now.

## Screenshot

<img src="http://f.cl.ly/items/460f2I350k1C2d1O452p/websockets-screenshot.png"/>

Cheers,

Christophe H.


