/**
 * 
 */
package controllers;

import models.Message;

import org.w3c.dom.Document;

import play.libs.IO;
import play.libs.XML;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http.StatusCode;

/**
 * Accept anything POX/SOAP/ store and publish messages...
 * 
 * @author chamerling
 * 
 */
public class RawService extends Controller {
	
	@Before
	public static void setup() {
		// to be injected in ressources
		renderArgs.put("base", request.getBase());
		String location = request.getBase() + "/services/RawService";
		renderArgs.put("location", location);
	}

	public static void any() {
		long date = System.currentTimeMillis();
		String body = IO.readContentAsString(request.body);
		if (body == null) {
			error(StatusCode.INTERNAL_ERROR, "Message is malformed...");
		}
		Message message = new Message();
		message.date = date;
		message.payload = body;

		WebSocket.liveStream.publish(message);
		render("Application//RawService_Response.xml");
	}

	/**
	 * Just to give a WSDL which is completely false but which can be required
	 * by clients.
	 */
	public static void wsdl() {
		render("Application/RawService.wsdl");
	}

}
