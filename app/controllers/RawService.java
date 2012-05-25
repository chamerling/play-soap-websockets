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

import java.util.Date;

import models.Message;
import models.Stats;
import models.StatusMessage;
import play.libs.IO;
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
		Stats.get().request();
		stats();

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

	protected static void stats() {
		StatusMessage statusMessage = new StatusMessage();
		statusMessage.body = "Got a new POST call on "
				+ renderArgs.get("location") + " at " + new Date(System.currentTimeMillis());
		statusMessage.stats = Stats.get();
		StatsWebSocket.liveStream.publish(statusMessage);
	}

	/**
	 * Just to give a WSDL which is completely false but which can be required
	 * by clients.
	 */
	public static void wsdl() {
		render("Application/RawService.wsdl");
	}

}
