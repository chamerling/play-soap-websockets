/**
 * 
 */
package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;

import play.Play;
import play.db.jpa.Model;

/**
 * @author chamerling
 * 
 */
@Entity
public class Message extends Model {

	public static long MAX = 10;

	public long date;

	@Lob
	public String payload;

	public synchronized static void push(Message message) {
		if (message == null) {
			return;
		}
		long count = count();
		if (count >= getMaxSize()) {
			// remove oldest message
			List<Message> list = Message.find("order by date asc").fetch(1);
			for (Message message2 : list) {
				message2.delete();
			}
		}
		message.save();
	}
	
	public static long getMaxSize() {
		long result = MAX;
		Object o = Play.configuration.get("app.message.size");
		if (o != null) {
			try {
				result = Long.parseLong(o.toString());
			} catch (NumberFormatException e) {
			}
		}
		return result;
	}

}
