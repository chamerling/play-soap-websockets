# A simple WebSockets + SOAP POC

## What
Based on SOAP sample provided by Marc Deschamps [https://github.com/marcuspocus/soap-examples/](https://github.com/marcuspocus/soap-examples/), I just added the WebSocket stuff to display SOAP requests in the browser when the service is called.

## How
You can use [SOAPUI](http://soapui.org/) and the sample project located in the test folder (MyService-wsdl-soapui-project.xml) to invoke services and see what happens on [http://localhost:9000](http://localhost:9000 "Home").

## Results
WebSocket and Play rocks! There are some missing messages (see problems section) but with some reasonable load it works quite well.

## Problems
Under *stress* tests, it looks like some messages are lost on the WebSocket stuff side. To reproduce, open two browser windows on [http://localhost:9000](http://localhost:9000 "Home") and launch the SOAPUI Load Test. This light load test sends 60 requests using two threads. At the end of the test, some messages are not displayed in the browser (have a look to the counter of the last message which should be equal to '59').

Since I am not a JS expert nor a Play one, the problem may be somewhere out of Play, or not...

Cheers,

Christophe H.


