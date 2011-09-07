/**
 * 
 */
package controllers;

import org.w3c.dom.Document;

import play.libs.F.EventStream;
import play.libs.XML;
import play.mvc.WebSocketController;
import play.utils.HTML;

/**
 * @author chamerling
 * 
 */
public class WebSocket extends WebSocketController {

	/**
	 * NOTE : This stream is shared between all browsers so messages delivery is not
	 * guarantee for all...
	 */
	public static EventStream<Document> liveStream = new EventStream<Document>();

	public static void asyncMessage() {
		while (inbound.isOpen()) {
			Document message = await(liveStream.nextEvent());
			String string = XML.serialize(message);
			if (string != null) {
				outbound.send(HTML.htmlEscape(string));
			}
		}
	}
}
