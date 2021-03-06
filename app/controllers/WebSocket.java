/*
 * Copyright 2011 Christophe Hamerling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
