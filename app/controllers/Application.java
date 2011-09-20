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