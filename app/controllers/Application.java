package controllers;

import java.util.Map;

import org.w3c.dom.Document;

import play.libs.IO;
import play.libs.XML;
import play.libs.XPath;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http.Header;
import play.mvc.Http.StatusCode;

public class Application extends Controller {

	@Before
	public static void setup() {
		renderArgs.put("base", request.getBase());
		String location = request.getBase() + "/services/MyService";
		renderArgs.put("location", location);
	}

	public static void index() {
		render();
	}

	public static void wsdl() {
		render("Application/MyService.wsdl");
	}

	public static void router() {

		String body = IO.readContentAsString(request.body);
		Document xml = XML.getDocument(body);
		if (xml == null) {
			error(StatusCode.INTERNAL_ERROR, "XML is malformed...");
		}
		Map<String, Header> map = request.headers;
		Header header = map.get("soapaction");
		String soapAction = header.value();
		if (soapAction == null) {
			wsdl();
		} else {
			soapAction = soapAction.replaceAll("\"", "");
		}
		if (soapAction.endsWith("sayHello")) {
			sayHello(xml);
		} else if (soapAction.endsWith("sayBye")) {
			sayBye(xml);
		}
		error(StatusCode.NOT_FOUND, "SOAPAction not found...");
	}

	private static void sayHello(Document xml) {
		WebSocket.liveStream.publish(xml);
		String name = XPath.selectText("//sayHelloRequest/text()", xml);
		name = String.format("Hello %s!", name);
		render("Application//MyService_SayHelloResponse.xml", name);
	}

	private static void sayBye(Document xml) {
		WebSocket.liveStream.publish(xml);
		String name = XPath.selectText("//sayByeRequest/text()", xml);
		name = String.format("Bye %s!", name);
		render("Application//MyService_SayByeResponse.xml", name);
	}

}