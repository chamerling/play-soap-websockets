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

import java.util.Map;

import models.Stats;

import org.w3c.dom.Document;

import play.libs.IO;
import play.libs.XML;
import play.libs.XPath;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http.Header;
import play.mvc.Http.StatusCode;

/**
 * A simple Web service implementation
 * 
 * @author chamerling
 * 
 */
public class MyService extends Controller {

	@Before
	public static void setup() {
		// to be injected in ressources
		renderArgs.put("base", request.getBase());
		String location = request.getBase() + "/services/MyService";
		renderArgs.put("location", location);
		renderArgs.put("stats", Stats.get());
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

	public static void wsdl() {
		render("Application/MyService.wsdl");
	}

	private static void sayHello(Document xml) {
		String name = XPath.selectText("//sayHelloRequest/text()", xml);
		name = String.format("Hello %s!", name);
		render("Application//MyService_SayHelloResponse.xml", name);
	}

	private static void sayBye(Document xml) {
		String name = XPath.selectText("//sayByeRequest/text()", xml);
		name = String.format("Bye %s!", name);
		render("Application//MyService_SayByeResponse.xml", name);
	}

}
