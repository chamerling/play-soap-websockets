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

	public static void index() {
		render();
	}
}