/**
 * 
 */
package controllers;

import models.Message;

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
	public static EventStream<Message> liveStream = new EventStream<Message>();

	public static void asyncMessage() {
		while (inbound.isOpen()) {
			Message message = await(liveStream.nextEvent());
			if (message != null) {
				outbound.send(HTML.htmlEscape(message.payload));
			}
		}
	}
}
