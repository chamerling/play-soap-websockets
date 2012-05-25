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

import java.util.List;

import models.Message;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Router;

public class Application extends Controller {

	@Before
	public static void setup() {
		// to be injected in ressources
		renderArgs.put("base", request.getBase());
		String location = request.getBase() + "/services/RawService.wsdl";
		renderArgs.put("wsdl", location);
	}

	public static void index() {
		render();
	}

	public static void websocket() {
		render();
	}

	public static void last() {
		List<Message> wsmessages = Message.find("order by date desc").fetch();
		render(wsmessages);
	}
}