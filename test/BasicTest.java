import org.junit.Test;
import org.w3c.dom.Document;

import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.WS.WSRequest;
import play.libs.XPath;
import play.test.UnitTest;
import play.vfs.VirtualFile;

public class BasicTest extends UnitTest {

    @Test
    public void testSayHello() {
    	try {
        	WSRequest request = WS.url("http://localhost:9000/services/MyService");
        	request.headers.put("soapaction", "sayHello");
        	request.body(VirtualFile.fromRelativePath("test/sayHelloRequest.xml").contentAsString());
        	HttpResponse response = request.post();
        	Document xml = response.getXml();
        	String result = XPath.selectText("//sayHelloResponse/text()", xml);
        	assertNotNull(result);
        	assertEquals("Hello Claw!", result);
		} catch (Exception e) {
			fail("Response is not XML: " + e.getMessage());
		}
    }

    @Test
    public void testSayBye() {
    	try {
        	WSRequest request = WS.url("http://localhost:9000/services/MyService");
        	request.headers.put("soapaction", "sayBye");
        	request.body(VirtualFile.fromRelativePath("test/sayByeRequest.xml").contentAsString());
        	HttpResponse response = request.post();
        	Document xml = response.getXml();
        	String result = XPath.selectText("//sayByeResponse/text()", xml);
        	assertNotNull(result);
        	assertEquals("Bye Claw!", result);
		} catch (Exception e) {
			fail("Response is not XML: " + e.getMessage());
		}
    }
}
